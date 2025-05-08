<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>My Page</title>
<link rel="stylesheet" href="${root }/resources/css/mypage.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/fragments/header.jsp"%>
	<div class="layout">
		<aside>
			<ul>
				<li><a href="#">내 정보 수정</a></li>
				<li><a href="#">내 아파트</a></li>
				<li><a href="#">내 게시글</a></li>
			</ul>
		</aside>
		<main>
			<form action="#">
				<div>
					<label></label>
				</div>
			</form>
		</main>
	</div>
	<%@ include file="/WEB-INF/views/fragments/footer1.jsp"%>
</body>
</html>