// 채팅방 ID와 사용자 정보 (예시: userA와 userB의 1:1 채팅)
let roomId;
let stompClient;
let loginUserId; // 현재 로그인한 사용자
let subscription;
let lastUser = "default";
let listOpen = true;

// 채팅방 리스트 초기화
const chatInit = async () => {
  // 로그인 유저 받아오기
  loginUserId = await getLoginUser();

  // 채팅 UI 오픈
  document.querySelector(".sideView:has(.chat)").classList.add("open");
  // sideView 끄기 click 이벤트
  const closeBtns = document.querySelectorAll("div:has(.chat) .close");
  closeBtns.forEach((closeBtn) => {
    closeBtn.addEventListener("click", () => {
      closeBtn.parentElement.classList.remove("open");
    });
  });

  const targets = await getTargets(loginUserId);

  const listDiv = document.querySelector(".chat-list");
  listDiv.innerHTML = "";
  listDiv.classList.add("open");
  const closeBtn = document.createElement("img");
  closeBtn.src = "/resources/img/arrowL.png";
  closeBtn.classList.add("close");
  closeBtn.addEventListener("click", (e) => {
    listOpen = !listOpen;
    closeBtn.src = `/resources/img/arrow${listOpen ? "L" : "R"}.png`;
    e.target.parentElement.classList.toggle("open");
  });
  listDiv.appendChild(closeBtn);

  // AI 채팅 추가
  const AIIcon = document.createElement("img");
  AIIcon.classList.add("chat-room-icon");
  AIIcon.src = `/resources/img/chat-AI.png`;
  AIIcon.addEventListener("click", () => {
    // TODO AI와 연결하기
    connectAI();
    document.querySelectorAll(".chat-room-icon").forEach((c) => {
      c.classList.remove("selected");
    });
    AIIcon.classList.add("selected");
  });
  listDiv.appendChild(AIIcon);

  // 채팅 목록 추가
  targets.forEach((target) => {
    const chatIcon = document.createElement("img");
    chatIcon.classList.add("chat-room-icon");
    const profileImg = target.profileImg ? target.profileImg : "/resources/img/default_profile.png";
    chatIcon.src = profileImg;
    chatIcon.addEventListener("click", () => {
      connectChatRoom(target.email, profileImg);
      document.querySelectorAll(".chat-room-icon").forEach((c) => {
        c.classList.remove("selected");
      });
      chatIcon.classList.add("selected");
    });
    listDiv.appendChild(chatIcon);
  });
};

// 로그인 유저 얻어오기
const getLoginUser = async () => {
  const response = await fetch(`/api/v1/member/user-info`, {
    method: "GET",
  });
  const json = await response.json();
  return json.data.email;
};

// 채팅 상대 얻어오기
const getTargets = async (loginUserEmail) => {
  const response = await fetch(`/chat/targets/${loginUserEmail}`, {
    method: "GET",
  });
  const json = await response.json();
  return json.data;
};

// AI 접속
const connectAI = () => {
  // 채팅방 초기화
  document.querySelector(".chat-output").innerHTML = "";
};

// 채팅방 접속
const connectChatRoom = (user2Id, user2Img) => {
  // 채팅방 초기화
  document.querySelector(".chat-output").innerHTML = "";

  // 채팅방 데이터 얻기 위한 쿼리
  const query = new URLSearchParams({
    user1Id: loginUserId,
    user2Id: user2Id,
  }).toString();

  // 채팅방 데이터 요청 rest api
  fetch(`/chat/roomId?${query}`)
    .then((res) => res.json())
    .then((data) => {
      roomId = data.data; // 서버에서 받은 roomId
      // 채팅 메시지 목록 가져오기
      fetch(`/chat/${roomId}`)
        .then((res) => res.json())
        .then((data) => {
          const messages = data.data;
          if (messages) {
            messages.forEach((message) => displayMessage(message, user2Img));
          }
        });

      // WebSocket 연결
      const socket = new SockJS("/ws"); // 서버의 WebSocket endpoint
      stompClient = Stomp.over(socket); // STOMP 클라이언트

      // 디버그용
      stompClient.debug = (str) => {
        console.log("[STOMP DEBUG] ", str);
      };

      // 서버로부터 메시지를 받으면 처리
      stompClient.connect({}, () => {
        // 이전 구독 취소
        if (subscription) {
          subscription.unsubscribe();
        }
        // 채팅방 구독
        subscription = stompClient.subscribe(`/sub/chatroom/${roomId}`, (message) => {
          const chat = JSON.parse(message.body); // 메시지를 JSON으로 파싱
          displayMessage(chat, user2Img); // UI에 표시
        });
      });

      // 모두 정상 연결되면 채팅 입력 버튼에 이벤트 추가
      if (document.getElementById("chat-sub")) {
        document.getElementById("chat-sub").removeEventListener("click", subClickEvent);
        document.getElementById("chat-sub").addEventListener("click", subClickEvent);
      }
    });
};

// 채팅 입력 버튼에 클릭 이벤트로 추가할 함수
// add, remove하기 위해 함수로 선언
const subClickEvent = () => {
  if (!roomId || !stompClient) return;
  const messageInput = document.getElementById("messageInput"); // 메시지 입력 필드 선택
  const messageContent = messageInput.value; // 입력된 메시지 내용 가져오기
  sendMessage(messageContent); // 메시지 전송
  messageInput.value = ""; // 메시지 입력 필드를 비우기
};

// 메시지 전송
const sendMessage = (content) => {
  const message = {
    roomId: roomId,
    sender: loginUserId,
    content: content,
    sentAt: new Date().toLocaleString("ko-KR", {
      timeZone: "Asia/Seoul",
    }),
  };

  // 서버에 메시지 전송
  stompClient.send("/pub/message", {}, JSON.stringify(message));
};

// 메시지 UI에 표시하는 함수
const displayMessage = (chat, user2Img) => {
  console.log(lastUser);
  const chatBox = document.querySelector(".chat-output");
  const messageElement = document.createElement("div");
  messageElement.classList.add("chat-message");
  const contentElement = document.createElement("div");
  contentElement.innerHTML = chat.content.replace(/\n/g, "<br/>");
  messageElement.appendChild(contentElement);

  // 시간 표시용 div 생성
  const timeElement = document.createElement("div");
  timeElement.classList.add("time");
  timeElement.textContent = chat.sentAt.substring(11, 19);

  if (chat.sender === loginUserId) {
    messageElement.classList.add("sender");
    messageElement.prepend(timeElement);
  } else {
    messageElement.appendChild(timeElement);
  }
  if (lastUser !== chat.sender && chat.sender !== loginUserId) {
    const profile = document.createElement("img");
    profile.src = user2Img;
    messageElement.prepend(profile);
  }

  lastUser = chat.sender;

  chatBox.appendChild(messageElement);
  chatBox.scrollTop = chatBox.scrollHeight;
};
