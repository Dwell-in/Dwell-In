document.addEventListener("DOMContentLoaded", () => {
  document.getElementById("loginBtn").addEventListener("click", login);
  document.getElementById("logoutBtn").addEventListener("click", logout);
  document.getElementById('')
  checkLoginStatus();
});

function login() {
  const loginID = document.getElementById("loginID").value;
  const loginPW = document.getElementById("loginPW").value;

  if (!loginID || !loginPW) {
      alert("아이디와 비밀번호를 입력해주세요.");
      return;
  }

  // loginID를 기준으로 localStorage에서 사용자 데이터 가져오기
  const userData = JSON.parse(localStorage.getItem(loginID));
  if (!userData) {
      alert("해당 ID의 계정이 없습니다.");
      return;
  }

  // 비밀번호 비교
  if (userData.userPW !== loginPW) {
      alert("비밀번호가 틀렸습니다.");
      return;
  }

  // 로그인 성공 시 세션에 로그인 정보 저장
  sessionStorage.setItem("loggedInUser", loginID);
  // checkLoginStatus();
  alert(`${userData.userName}님, 로그인 성공!`);
  location.href = 'index.html';
  checkLoginStatus();
}


function checkLoginStatus() {
  const loginStatus = document.getElementById("loginStatus");
  const logoutBtn = document.getElementById("logoutBtn");
  const adminPageBtn = document.querySelector(".adminli");
  const myPageBtn = document.getElementById("myPage");
  const loggedInUser = sessionStorage.getItem("loggedInUser");

  logoutBtn.style.display = loggedInUser ? "inline-block" : "none";

  headerUpdate();
}

function logout() {
  sessionStorage.removeItem("loggedInUser");
  //checkLoginStatus();
  alert("로그아웃 되었습니다.");
  location.reload();
}

const headerUpdate = () => {
  const loggedInUser = sessionStorage.getItem("loggedInUser");
  console.log(loggedInUser);
  if (loggedInUser) {
    document.querySelector(".loginli").style.display = "none";
    document.querySelector(".signupli").style.display = "none";
    document.querySelector(".logoutli").style.display = "inline-block";
    if(loggedInUser == "admin"){
      document.getElementById("Admin").style.display = "inline-block";
      document.getElementById("myPage").style.display = "none";
    }
    else{
      document.getElementById("myPage").style.display = "inline-block";
      document.getElementById("Admin").style.display = "none";
    }
  } else {
    document.querySelector(".loginli").style.display = "inline-block";
    document.querySelector(".signupli").style.display = "inline-block";
    document.querySelector(".logoutli").style.display = "none";
  }
};

headerUpdate();