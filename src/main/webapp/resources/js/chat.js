// 채팅방 ID와 사용자 정보 (예시: userA와 userB의 1:1 채팅)
let roomId;
let stompClient;
const user1Id = "userA"; // 현재 로그인한 사용자
const user2Id = "userB";

const query = new URLSearchParams({
  user1Id: user1Id,
  user2Id: user2Id,
}).toString();

console.log('fetch-chat: start');
fetch(`/chat/roomId?${query}`)
  .then((res) => res.json())
  .then((data) => {
    roomId = data.data; // 서버에서 받은 roomId
    console.log(data.data);
    // 채팅 메시지 목록 가져오기
    fetch(`/chat/${roomId}`)
    .then((res) => res.json())
    .then((data) => {
      const messages = data.data;
      if (messages){
        messages.forEach(displayMessage);
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
      // 채팅방 구독
      stompClient.subscribe(`/sub/chatroom/${roomId}`, (message) => {
        const chat = JSON.parse(message.body); // 메시지를 JSON으로 파싱
        displayMessage(chat); // UI에 표시
      });
    });
});

// 메시지 전송
const sendMessage = (content) => {
  const message = {
    roomId: roomId,
    sender: user1Id,
    content: content,
  };

  // 서버에 메시지 전송
  stompClient.send("/pub/message", {}, JSON.stringify(message));
};

if (document.getElementById("chat-sub")) {
  // 전송 버튼에 클릭 이벤트 등록
  document.getElementById("chat-sub").addEventListener("click", function () {
    if (!roomId || !stompClient) return;
    const messageInput = document.getElementById("messageInput"); // 메시지 입력 필드 선택
    const messageContent = messageInput.value; // 입력된 메시지 내용 가져오기
    sendMessage(messageContent); // 메시지 전송
    messageInput.value = ""; // 메시지 입력 필드를 비우기
  });
}

// 메시지 UI에 표시하는 함수
const displayMessage = (chat) => {
  console.log(chat);
  // if (!document.getElementById("chatBox")) return;

  // const chatBox = document.getElementById("chatBox");
  // const messageElement = document.createElement("div");
  // messageElement.textContent = chat.content;
  // if (chat.sender === loginUserName) {
  //   messageElement.classList.add("sender");
  // }
  // // 시간 표시용 div 생성
  // const timeElement = document.createElement("div");
  // timeElement.classList.add("time");
  // timeElement.textContent = chat.regDateTime; // chat.dateTime에 날짜/시간 정보가 들어있다고 가정

  // // 메시지와 시간 div를 메시지 div 안에 추가
  // messageElement.appendChild(timeElement);
  // chatBox.appendChild(messageElement);
};
