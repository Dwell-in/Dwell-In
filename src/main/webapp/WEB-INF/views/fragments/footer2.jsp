<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${root }/resources/css/footer2.css">
<footer id="footer2">
	<div class="footer-inner">
		<ul class="part-list">
			<li><a href="#">이용약관</a></li>
			<li class="border"></li>
			<li><a href="#">개인정보처리방침</a></li>
		</ul>
		<div class="footer-desc">Copyright © Dwell-in.</div>
	</div>
</footer>

<script src="${root }/resources/js/header.js"></script>
<script src="${root }/resources/js/keys.js"></script>

<script type="text/javascript">
    const alertMsg = `${param.alertMsg}` || `${alertMsg}`; 
    if (alertMsg) {
    	alert(alertMsg);
    }
     <c:remove var="alertMsg" scope="session"/>  
</script>
