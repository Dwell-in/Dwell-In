document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("searchBtn").addEventListener("click", searchUser);
    document.getElementById("searchBtnMe").addEventListener("click", displayUserInfo);
    document.getElementById("updateBtn").addEventListener("click", updateUser);
    document.getElementById("deleteBtn").addEventListener("click", deleteUser);
    document.getElementById("findIDBtn").addEventListener("click", findID);
    document.getElementById("findPWBtn").addEventListener("click", findPassword);
    document.getElementById("allUsersBtn").addEventListener("click", showAllUsers);
    document.getElementById("clearAllBtn").addEventListener("click", clearAllUsers);
});

function searchUser() {
    const searchID = document.getElementById("searchID").value.trim();
    const result = document.getElementById("searchResult");

    if (!searchID) {
        result.innerHTML = "<strong>아이디를 입력하세요.</strong>";
        return;
    }

    const userData = localStorage.getItem(searchID);
    if (userData) {
        const parsedData = JSON.parse(userData);
        result.innerHTML = `
            <strong>아이디:</strong> ${parsedData.userID}<br>
            <strong>이름:</strong> ${parsedData.userName}<br>
            <strong>전화번호:</strong> ${parsedData.userPhone}
        `;
    } else {
        result.innerHTML = "<strong>해당 아이디가 없습니다.</strong>";
    }
}       
function displayUserInfo() {
    const loggedInUser = sessionStorage.getItem("loggedInUser"); // 현재 로그인한 유저 ID 가져오기
    const result = document.getElementById("searchResult");
    console.log(loggedInUser);
    if (!loggedInUser) {
        result.innerHTML = "<strong>로그인된 사용자 정보가 없습니다.</strong>";
        return;
    }

    const userData = localStorage.getItem(loggedInUser);
    console.log(userData);
    if (userData) {
        const parsedData = JSON.parse(userData);
        result.innerHTML = `
            <strong>아이디:</strong> ${parsedData.userID}<br>
            <strong>이름:</strong> ${parsedData.userName}<br>
            <strong>전화번호:</strong> ${parsedData.userPhone}
        `;
    } else {
        result.innerHTML = "<strong>해당 아이디의 정보가 없습니다.</strong>";
    }
}

function updateUser() {
    const updateID = document.getElementById("updateID").value.trim();
    const newPW = document.getElementById("newPW").value.trim();
    const newUserName = document.getElementById("newUserName").value.trim();
    const newPhone = document.getElementById("newPhone").value.trim();
  
    let userData = localStorage.getItem(updateID);
    if (!userData) {
        alert("해당 아이디의 회원이 존재하지 않습니다.");
        return;
    }
  
    userData = JSON.parse(userData);
    if (newPW) userData.userPW = newPW;
    if (newUserName) userData.userName = newUserName;
    if (newPhone) userData.userPhone = newPhone;
  
    localStorage.setItem(updateID, JSON.stringify(userData));
    alert("회원 정보가 성공적으로 수정되었습니다!");
}
  
function deleteUser() {
    const deleteID = document.getElementById("deleteID").value.trim();
    if (localStorage.getItem(deleteID)) {
        localStorage.removeItem(deleteID);
        alert("회원 삭제 완료!");
    } else {
        alert("해당 ID의 계정이 없습니다.");
    }
}

function findID() {
    const findPhoneID = document.getElementById("findPhoneID").value.trim();
    const result = document.getElementById("findIDResult");
  
    if (!findPhoneID) {
        result.innerHTML = "<strong>휴대폰 번호를 입력해주세요.</strong>";
        return;
    }
  
    let foundID = null;
  
    // localStorage에 저장된 모든 키를 확인
    for (let i = 0; i < localStorage.length; i++) {
        const key = localStorage.key(i);
        const userData = JSON.parse(localStorage.getItem(key));
  
        if (userData.userPhone === findPhoneID) {
            foundID = userData.userID;
            break; // 일치하는 데이터를 찾으면 중단
        }
    }
  
    if (foundID) {
        result.innerHTML = `<strong>아이디:</strong> ${foundID}`;
    } else {
        result.innerHTML = "<strong>등록된 정보가 없습니다.</strong>";
    }
}
function findPassword() {
    const findID = document.getElementById("findID").value.trim();
    const findPhone = document.getElementById("findPhone").value.trim();
    const result = document.getElementById("findPWResult");
  
    const userData = localStorage.getItem(findID);
    if (userData) {
        const parsedData = JSON.parse(userData);
        if (parsedData.userPhone === findPhone) {
            result.innerHTML = `<strong>비밀번호:</strong> ${parsedData.userPW}`;
        } else {
            result.innerHTML = "<strong>정보가 일치하지 않습니다.</strong>";
        }
    } else {
        result.innerHTML = "<strong>해당 아이디가 없습니다.</strong>";
    }
}
  
function showAllUsers() {
    const allUsers = document.getElementById("allUsers");
    const users = Object.keys(localStorage);
  
    allUsers.innerHTML = users.length 
        ? users.map(id => `<p>${id}</p>`).join("") 
        : "<p>저장된 회원이 없습니다.</p>";
}
  
function clearAllUsers() {
    if (confirm("모든 회원 정보를 삭제하시겠습니까?")) {
        localStorage.clear();
        alert("모든 회원이 삭제되었습니다.");
        showAllUsers();
    }
}