<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/css/detail.css">
<div class="sideView detail">
	<img class="close" src="/resources/img/closeIcon.png" />
	<div class="info_aptNm"></div>
	<div class="info_viewCount">
		<img alt="" src="${root }/resources/img/viewCount.png">
		<div class="info_viewCount_count"></div>
	</div>
	<div class="info-roadview"></div>
	<div class="section-title">CHART</div>
	<canvas id="priceChart" height="200"></canvas>
	<div class="section-title">BLOG</div>
	<div class="info-news"></div>
</div>
<!-- chart cdn -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="/resources/js/detail.js"></script>