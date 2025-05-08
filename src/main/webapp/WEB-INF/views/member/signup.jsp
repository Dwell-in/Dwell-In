<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
	<link rel="stylesheet" href="${root }/resources/css/signup.css">
	<main>
        <p class="hi">SSAFY HOME에 오신걸 환영합니다!!</p>
        <form action="${root}/api/v1/member/signup" method="post">
            <div>
                <div>
                    <label for="email">아이디(이메일)<span class="essential"></span></label>
                    <input type="text" name="email" id="email" required>
                 
                </div>
                <div>
           		    <span id="emailMsg"></span>
                    <p>- 이메일은 로그인 아이디로 사용됩니다.</p>
                </div>
            </div>
            <div>
                <div>
                    <label for="">이름<span class="essential"></span></label>
                    <input type="text" name="name"id="signupUserName" value="${param.name}" placeholder="예) 홍길동 (본인실명을 띄어쓰기 없이 입력해주세요.)" required>
                </div>
            </div>
            <div>
                <div>
                    <label for="">비밀번호<span class="essential"></span></label>
                    <input type="password" name="password" id="signupPW" placeholder="영문, 숫자, 특수문자 포함 8~20자리" 
                    value="${!empty param.name ? '12345' : ''}" ${!empty param.name ? 'readonly' : ''} required>
                </div>
            </div>
            <div>
                <div>
                    <label for="">비밀번호 확인<span class="essential"></span></label>
                    <input type="password" name="password2" id="signupPW2" placeholder="비밀번호를 다시 입력해주세요."
                    value="${!empty param.name ? '12345' : ''}" ${!empty param.name ? 'readonly' : ''} required>
                    
                </div>
                <div>
                	<span id="pwMsg"></span>
                    <p>- 비밀번호는 영문, 숫자, 특수문자(~!@#^&*)를 포함해 8~20자리로 조합해주세요.</p>
                    <p>- 동일하거나 연속한 숫자와 문자를 3자 이상 사용할 수 없습니다. (예: 111, 123, aaa, abc)</p>
                    <p>- 아이디, 생일, 휴대폰 번호 등과 관련한 숫자는 도용될 수 있으니 사용을 자제해주세요.</p>
                </div>
            </div>
            <div>
                <div>
                    <label for="phone">휴대폰번호</label>
                    <input class="tel" name="phone" type="tel" id="signupPhone">
                    <span>※ 정확한 연락처 정보를 입력해 주세요.</span>
                </div>
            </div>
            <div>
                <div>
                    <label for=""></label>
                </div>
            </div>
            <div class="btns flex flex-j-c flex-a-c">
                <button class="btn lb" type="button" id="loginBtn" onclick="location.href='login.jsp'">로그인 이동</button>
                <button class="btn sb" type="submit" id="signupBtn"  >회원가입</button>
            </div>
        </form>
    </main>
    <%@ include file="/WEB-INF/views/fragments/footer1.jsp" %>
</body>
<script type="text/javascript">
function checkEmailDuplication() {
    const id = document.getElementById('signupID').value.trim();
    const address = document.getElementById('ID').value.trim();
    
    if (!id || !address) {
      document.getElementById('emailMsg').textContent = '';
      return;
    }
    
    const email = id+'@'+address;
    
    fetch('${root}/member/check-email&email=' + encodeURIComponent(email))
      .then(response => response.json())
      .then(data => {
         const emailMsg = document.getElementById('emailMsg');
         if (data.exists) {
            emailMsg.textContent = '이미 사용중인 이메일입니다.';
            emailMsg.style.color = 'red';
         } else {
            emailMsg.textContent = '사용 가능한 이메일입니다.';
            emailMsg.style.color = 'green';
         }
      })
      .catch(error => console.error('이메일 체크 오류:', error));
  }



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
document.getElementById('signupID').addEventListener('input', checkEmailDuplication);
document.getElementById('ID').addEventListener('input', checkEmailDuplication);

document.getElementById('signupPW2').addEventListener('input', checkPasswordMatch);
</script>
</html>