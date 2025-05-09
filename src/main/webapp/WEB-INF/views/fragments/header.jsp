<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:set var="REST_API_KEY" value="7abe84cfdb3ff3310feaca8aa90809c0" />
<c:set var="REDIRECT_URI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}/member/login" />
<script>
	const root = `${root}`;
</script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
<script src="https://kit.fontawesome.com/64319e1cb9.js" crossorigin="anonymous"></script>

<!-- jquery CDN -->
<script src="https://code.jquery.com/jquery-3.7.0.js" integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6d77a7fd96523299409a856fca87deb5&libraries=services,clusterer,drawing"></script>


<link rel="stylesheet" href="${root }/resources/css/default.css">
<link rel="stylesheet" href="${root }/resources/fonts/pretendard.css">
<link rel="stylesheet" href="${root }/resources/css/header.css">
<header>
	<nav>
		<div class="menu">
			<img class="logo" src="${root }/resources/img/logo.png" alt="logo" onclick="location.href='${root}/'">
			<a class="noti" href="${root }/board/notification-list">공지사항</a>
			<a class="noti" href="${root }/board/notification-list">메뉴2</a>
			<a class="noti" href="${root }/board/notification-list">메뉴3</a>
			<a class="noti" href="${root }/board/notification-list">메뉴4</a>
			<a class="noti" href="${root }/starred/list">수정중</a>
		</div>
		<div>
			<div class="search-div">
				<form action="#" id="searchForm">
					<select name="key">
						<option value="add">주소 검색</option>
						<option value="apt">건물명 검색</option>
						<option value="board">게시판</option>
					</select>
					<div class="border-div"></div>
					<div>
						<input class="search-input" type="text" name="word" autocomplete="off"/>
						<ul class="autocomplete-list"></ul>
					</div>
					<img class="searchImg" alt="search" src="${root }/resources/img/search.png">
				</form>
			</div>
			<div class="member">
				<c:if test="${empty SPRING_SECURITY_CONTEXT}">
					<a href="${root }/member/login">로그인</a>
					<a href="${root }/member/signup">회원가입</a>
				</c:if>
				<c:if test="${!empty SPRING_SECURITY_CONTEXT}">
					<a href="${root }/api/v1/member/logout"><img class="profile"/></a>
				</c:if>
			</div>
		</div>
	</nav>
</header>