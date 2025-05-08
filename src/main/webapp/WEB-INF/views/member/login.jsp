<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	    <%@ include file="/WEB-INF/views/fragments/header.jsp" %>
	<link rel="stylesheet" href="${root }/resources/css/login.css">
	<main>
        <form class="login-area" action="${root}/api/v1/member/login" method="post">
            <img class="logo" src="${root }/resources/img/logo.png" alt="login">
            <div>
                <label class="flex flex-a-c" for="">아이디</label>
                <input type="text" id="loginID" name="email" placeholder="ID" required>
            </div>
            <div>
                <label class="flex flex-a-c" for="">비밀번호</label>
                <input type="password" id="loginPW" name="password" placeholder="PASSWORD" required>
            </div>
            <button type="submit" id="loginBtn">로그인</button>
            <a class="pwFind" href="${root}/member/password-find">비밀번호 찾기</a>
            <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}">
            <img id="kakao-login" src="${root}/resources/img/kakao_login.png"></a>
        </form>
        <div></div>
    </main>
    <%@ include file="/WEB-INF/views/fragments/footer2.jsp" %>
    <script>
    	const code = `${code}`;
    </script>
    <script src="${root}/resources/js/OAuth.js"></script>
</body>
</html>