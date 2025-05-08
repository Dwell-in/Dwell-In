<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
	<link rel="stylesheet" href="${root }/resources/css/index.css">
  </head>
  <body>
    <%@ include file="/WEB-INF/views/fragments/header.jsp" %>
    <main>
      <div class="info section" style="background: url(${root }/resources/img/indexx.png) top / 100% no-repeat;">
      	<div class="title-sub">부제</div>
      	<div class="title"></div>
      	<div class="content">
      		멘트멘트멘트멘트멘트멘트멘트멘트
      	</div>
      	<div class="moveSectionIcon">
	      	<img src="${root }/resources/img/moveSectionIcon.png" alt="moveSectionIcon" onclick="moveSection('#section2')">
	      	<p>마우스 휠 이동이 가능합니다.</p>
      	</div>
      </div>
      <div class="section" id="section2" style="background: url(${root }/resources/img/indexx2.png) top / 100% no-repeat;">
      </div>
      <div class="section" style="background: url(${root }/resources/img/indexx3.png) top / 100% no-repeat;">
      </div>
      <div class="section" style="background: url(${root }/resources/img/indexx4.png) top / 100% no-repeat;">
      </div>
    </main>
    <%@ include file="/WEB-INF/views/fragments/footer1.jsp" %>
	<script src="${root }/resources/js/index.js"></script>
  </body>
</html>
