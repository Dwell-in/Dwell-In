<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
</head>
<body>
	    <%@ include file="/WEB-INF/views/fragments/header.jsp" %>
	<link rel="stylesheet" href="${root }/resources/css/login.css">
	<main class="flex flex-d-c flex-j-c flex-a-c">
        <form class="login-area flex flex-d-c flex-a-s" action="${root}/member/password-find" method="post">
            <img src="${root }/resources/img/login.png" alt="login">
            <div>
                <label class="flex flex-a-c" for="">아이디</label>
                <input type="text" id="loginID" name="email" placeholder="ID" required>
            </div>
            <button type="submit" id="loginBtn">비밀번호 찾기</button>
        </form>
    </main>
    <%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
</body>
</html>