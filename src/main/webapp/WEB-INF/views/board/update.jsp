<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지사항 수정</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">공지사항 수정</h2>
        <a href="${root}/board/notification-list" class="btn btn-secondary">목록</a>
    </div>

    <form action="${root}/board/board-update" method="post">
        <input type="hidden" name="boardId" value="${board.boardId}" />

        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" name="title" value="${board.title}" required>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">작성자 이메일</label>
            <input type="email" class="form-control" id="email" name="email" value="${board.email}" readonly>
        </div>

        <div class="mb-3">
            <label for="content" class="form-label">내용</label>
            <textarea class="form-control" id="content" name="content" rows="8" required>${board.content}</textarea>
        </div>

        <div class="text-end">
            <button type="submit" class="btn btn-primary">수정 완료</button>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/views/fragments/footer1.jsp" %>
</body>
</html>
