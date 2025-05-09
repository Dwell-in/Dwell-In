<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/css/chat.css">
<div class="sideView chatView">
	<img class="close" src="/resources/img/closeIcon.png" />
	<div class="chat">
		<div class="chat-header">
			<img alt="logo" src="/resources/img/logo-w.png">
		</div>
		<div class="chat-body">
			<div class="chat-list">
			</div>
			<div class="chat-box">
				<div class="chat-output"></div>
				<div class="chat-input-div">
					<div class="chat-input">
						<textarea id="messageInput" placeholder="메시지를 입력해주세요."></textarea>
						<div class="chat-btns">
							<div></div>
							<div><button id="chat-sub">전송</button></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div><!-- ./sideView -->
<!-- 웹소켓 + STOMP cdn 등록 -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
<script src="/resources/js/chat.js"></script>