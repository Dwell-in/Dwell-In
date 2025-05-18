<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#main {
 display: flex;
 height: 100vh;
 margin: 0;
}

#chatSection {
 flex: 1;
 border-right: 1px solid #ccc;
 padding: 10px;
 display: flex;
 flex-direction: column;
}

#todoSection {
 flex: 2;
 padding: 10px;
}

#chatHistory {
 border: 1px solid #ccc;
 padding: 10px;
 flex-grow: 1;
 overflow-y: scroll;
 margin-bottom: 10px;
 height: calc(100vh - 420px);
}

.message {
 margin: 5px 0;
}

.user {
 text-align: right;
 color: blue;
}

.bot {
 text-align: left;
 color: green;
}

#todoList {
 border: 1px solid #ccc;
 padding: 10px;
 height: 300px;
 overflow-y: scroll;
 margin-bottom: 10px;
}

#chatForm {
 display: flex;
 align-items: center;
}

#message {
 flex-grow: 1;
 margin-right: 10px;
 padding: 5px;
}
</style>
</head>
<body>
    <%@ include file="/WEB-INF/views/fragments/header.jsp"%>
    <div class="container">
        <div id="chatSection">
            
            <div id="chatHistory"></div>
            <input type="text" id="message" name="message" required placeholder="대화를 전송하려면 enter" />
        </div>
    </div>
    <%@ include file="/WEB-INF/views/fragments/footer1.jsp"%>
</body>
<script>
  document.querySelector('#message').addEventListener("keyup", async (e) => {
    if(e.code!='Enter'){
      return;
    }
    const message = document.querySelector('#message').value;
    const chatHistory = document.querySelector('#chatHistory');
    
    // 사용자 메시지를 채팅 기록에 추가
    const userMessage = document.createElement('div');
    userMessage.className = 'message user';
    userMessage.innerText = '사용자: ' + message;
    chatHistory.appendChild(userMessage);

    try{
    // 서버에 메시지 전송
     const response = await fetch(`/ai/simple`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ message: message }),
    });

    const json = await response.json();
     console.log(json); 
     const botMessage = document.createElement('div');
     botMessage.className = 'message bot';

    botMessage.innerText = '봇: ' + json.data.message;
    chatHistory.appendChild(botMessage);

    // 입력 필드 초기화
    document.getElementById('message').value = '';
    chatHistory.scrollTop = chatHistory.scrollHeight; 
  }catch(error) {
    console.error('Error:', error);
  }
});
</script>
</html>
