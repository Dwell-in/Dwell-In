<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${root }/resources/css/footer2.css">
<footer>
	<div class="footer-inner">
		<ul class="part-list">
			<li><a href="#">이용약관</a></li>
			<li class="border"></li>
			<li><a href="#">개인정보처리방침</a></li>
		</ul>
		<div class="footer-desc">Copyright © Dwell-in.</div>
	</div>
</footer>

<!-- 웹소켓 + STOMP cdn 등록 -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
<!-- chart cdn -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="${root }/resources/js/header.js"></script>
<script src="${root }/resources/js/keys.js"></script>
<script src="${root }/resources/js/chat.js"></script>

<script type="text/javascript">
    const alertMsg = `${param.alertMsg}` || `${alertMsg}`; 
    if (alertMsg) {
    	alert(alertMsg);
    }
     <c:remove var="alertMsg" scope="session"/>  
</script>
