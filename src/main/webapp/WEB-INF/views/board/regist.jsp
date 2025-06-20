<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지사항 등록</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">공지사항 등록</h2>
        <a href="${root}/board/notification-list" class="btn btn-secondary">목록</a>
    </div>

    <form action="${root}/api/v1/board/board-write" id="regist-form" method="post">
    	<input type="hidden" name="categoryId" id="categoryId" value="1">
        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" name="title" required>
        </div>
		
        <div class="mb-3">
            <label for="userName" class="form-label">작성자</label>
            <input type="hidden" id="userId" name="userId" value="<sec:authentication property='principal.member.id'/>">
            <input type="text" class="form-control" id="userName" name="userName" value="<sec:authentication property='principal.member.name'/>" required>
        </div>

        <div class="mb-3">
            <label for="content" class="form-label">내용</label>
            <textarea class="form-control" id="content" name="content" rows="8" required></textarea>
        </div>

        <div class="text-end">
            <button type="submit" id="regist-button" class="btn btn-primary">등록</button>
        </div>
    </form>
</div>
<script src="${root }/resources/js/board.js"></script>
<%@ include file="/WEB-INF/views/fragments/footer1.jsp" %>
</body>
</html>