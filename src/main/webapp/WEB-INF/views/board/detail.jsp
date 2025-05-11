<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
    <title>공지사항 상세보기</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
 <%@ include file="/WEB-INF/views/fragments/header.jsp"%>
<div class="container mt-5">
    <h2 class="text-center mb-4">공지사항</h2>

    <table class="table table-bordered">
        <tbody>
        <tr>
            <th class="bg-light" style="width: 15%">제목</th>
            <td>${board.title}</td>
        </tr>
        <tr>
            <th class="bg-light">작성자</th>
            <td>${board.userName}</td>
        </tr>
        <tr>
            <th class="bg-light">조회수</th>
            <td>${board.viewCount}</td>
        </tr>
        <tr>
            <th class="bg-light">작성일</th>
            <td>${board.regTime}</td>
        </tr>
        <tr>
            <th class="bg-light">내용</th>
            <td style="min-height: 200px; white-space: pre-wrap;">${board.content}</td>
        </tr>
        </tbody>
    </table>

    <div class="d-flex justify-content-end gap-2">
    <a href="${root/board/board-update-page}" id="update-button" class="btn btn-warning" data-id="${board.boardId}">수정</a>
    <form action="${root}/api/v1/board/board-delete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
        <input type="hidden" name="boardId" value="${board.boardId}" />
        <button type="submit" class="btn btn-danger">삭제</button>
    </form>
    <a href="${root}/board/notification-list" class="btn btn-secondary">목록으로</a>
</div>

</div>
<script src="${root }/resources/js/board.js"></script>
<%@ include file="/WEB-INF/views/fragments/footer1.jsp"%>
</body>
</html>
