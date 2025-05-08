<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
	<link rel="stylesheet" href="${root }/resources/css/map.css">
  </head>
  <body class="flex flex-d-c">
    <%@ include file="/WEB-INF/views/fragments/header.jsp" %>
    <main class="flex flex-j-c">
		<div id="map">
			<div class="loading flex flex-j-c flex-a-c display-none">
				<i class="fa-solid fa-spinner fa-spin fa-fade fa-5x"></i>
			</div>
			<div id="mapBox"></div>
			<div class="maker-btn">
				<img class="marker" src="${root }/resources/img/marker_FD6_btn.png" alt="marker_FD6_btn" data-markerName="FD6" data-makerToggle="false">
				<img class="marker" src="${root }/resources/img/marker_CE7_btn.png" alt="marker_CE7_btn" data-markerName="CE7" data-makerToggle="false">
				<img class="marker" src="${root }/resources/img/marker_SC4_btn.png" alt="marker_SC4_btn" data-markerName="SC4" data-makerToggle="false">
				<img class="marker" src="${root }/resources/img/marker_HP8_btn.png" alt="marker_HP8_btn" data-markerName="HP8" data-makerToggle="false">
				<img class="marker" src="${root }/resources/img/marker_BK9_btn.png" alt="marker_BK9_btn" data-markerName="BK9" data-makerToggle="false">
				<img class="marker" src="${root }/resources/img/marker_CS2_btn.png" alt="marker_CS2_btn" data-markerName="CS2" data-makerToggle="false">
			</div>
		</div>
		<%@ include file="/WEB-INF/views/fragments/side/detail.jsp" %>
	    <%@ include file="/WEB-INF/views/fragments/side/chat.jsp" %>
	</main>
    <%@ include file="/WEB-INF/views/fragments/footer2.jsp" %>
	<script src="${root }/resources/js/map.js"></script>
	<script src="${root }/resources/js/kakao.js"></script>
  </body>
</html>
