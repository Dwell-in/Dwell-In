<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
<style>
/* 기본 스타일 */
body {
	margin: 0;
	padding: 0;
	background-color: #f4f4f9;
	font-family: 'Arial', sans-serif;
}

/* 카드 컨테이너 */
.container {
	max-width: 500px;
	margin: 50px auto;
	padding: 20px;
	background: #fff;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
	text-align: center;
	color: #333;
	margin-bottom: 20px;
}

form {
	display: flex;
	flex-direction: column;
}

label {
	font-weight: bold;
	margin-bottom: 5px;
	color: #555;
}

input[type="text"], input[type="password"], input[type="tel"], select {
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 14px;
}

input[readonly] {
	background-color: #e9ecef;
}

/* 전화번호 입력 그룹 */
.phone-group {
	display: flex;
	gap: 5px;
	align-items: center;
}

.phone-group select {
	width: 30%;
}

.phone-group input[type="tel"] {
	width: 70%;
}

/* 수정 버튼 스타일 */
.btn {
	background-color: #007bff;
	border: none;
	color: #fff;
	padding: 10px;
	border-radius: 4px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s ease;
}

.btn:hover {
	background-color: #0056b3;
}

/* 버튼 영역 중앙 정렬 */
.btn-container {
	text-align: center;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/fragments/header.jsp"%>

	<div class="container">
		<h1>회원 정보 수정</h1>
		<form action="${root}/member/update" method="post">
			<!-- 아이디(이메일) 필드 -->
			<div>
				<label for="signupID">아이디(이메일) <span class="essential">ID</span></label>
				<input type="text" name="email" value="${loginUser.email}"
					id="signupID" readonly>
			</div>

			<!-- 이름 필드 -->
			<div>
				<label for="signupUserName">이름</label> <input type="text"
					name="name" id="signupUserName" value="${loginUser.name}" required>
			</div>

			<!-- 비밀번호 필드 -->
			<div>
				<div>
					<label for="">비밀번호<span class="essential"></span></label> <input
						type="password" name="password" id="signupPW"
						placeholder="영문, 숫자, 특수문자 포함 8~20자리" required>
				</div>
			</div>
			<div>
				<div>
					<label for="">비밀번호 확인<span class="essential"></span></label> <input
						type="password" name="password2" id="signupPW2"
						placeholder="비밀번호를 다시 입력해주세요." required>

				</div>
				<div>
					<span id="pwMsg"></span>
					<p>- 비밀번호는 영문, 숫자, 특수문자(~!@#^&*)를 포함해 8~20자리로 조합해주세요.</p>
					<p>- 동일하거나 연속한 숫자와 문자를 3자 이상 사용할 수 없습니다. (예: 111, 123, aaa,
						abc)</p>
					<p>- 아이디, 생일, 휴대폰 번호 등과 관련한 숫자는 도용될 수 있으니 사용을 자제해주세요.</p>
				</div>
			</div>

			<!-- 휴대폰 번호 필드 -->
			<div>
				<label for="signupPhone">휴대폰번호</label>
				<div class="phone-group">
					<input class="tel" name="phone" type="tel" id="signupPhone"
						value="${loginUser.phone}" required>
				</div>
			</div>

			<!-- 수정 버튼 -->
			<div class="btn-container">
				<button class="btn" type="submit" id="signupBtn">수정</button>
			</div>
		</form>
	</div>
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%>
</body>
<script>
function checkPasswordMatch() {
    const password = document.getElementById('signupPW').value;
    const password2 = document.getElementById('signupPW2').value;
    const pwMsg = document.getElementById('pwMsg');
    
    if (password2 === "") {
      pwMsg.textContent = "";
      return;
    }
    
    if (password !== password2) {
       pwMsg.textContent = '비밀번호가 일치하지 않습니다.';
       pwMsg.style.color = 'red';
    } else {
       pwMsg.textContent = '비밀번호가 일치합니다.';
       pwMsg.style.color = 'green';
    }
  }

document.getElementById('signupPW2').addEventListener('input', checkPasswordMatch);
</script>
</html>
