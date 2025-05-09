<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="${root }/resources/css/notification.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/fragments/header.jsp"%>

	<div class="container py-5">

		<div class="body-nav">
			<div class="background-img">
				<h3 class="fw-bold mb-0">공지사항</h3>
			</div>
			<div class="body-menu">
				<a class="noti" href="#">공지사항</a> <a class="noti" href="#">메뉴2</a> <a
					class="noti" href="#">메뉴3</a>
			</div>
		</div>


		<!-- 게시판 목록 -->
		<div class="table-body">
			<table
				class="table table-hover table-striped text-center align-middle">
				<thead class="table-light">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>조회수</th>
						<th>작성일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${boardList}" var="item">
						<tr>
							<td>${item.boardId}</td>
							<td class="text-start ps-4"><a
								href="${root}/api/v1/board/board-detail/${item.boardId}"
								class="text-decoration-none text-dark"> ${item.title} </a></td>
							<td>${item.viewCount}</td>
							<td>${item.regDate}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<!-- 검색 폼 -->
		<c:if test="${!empty SPRING_SECURITY_CONTEXT}">
			<a href="${root}/board/board-write-form" class="btn btn-success px-4">등록</a>
		</c:if>
		<form class="row mb-4" action="${root }/api/v1/board/board-search"
			method="get" id="search-form">
			<input type="hidden" name="action" value="pattern-counting" /> <input
				type="hidden" id="currentPage" name="currentPage" value="1" />
			<div class="d-flex justify-content-end align-items-center gap-2">
				<input type="text" class="form-control w-25" name="search"
					placeholder="제목 검색">
				<button type="submit" class="btn btn-primary">검색</button>
			</div>
		</form>
		<%--    <!-- 페이지네이션 -->--%>
		<%--    <nav class="d-flex justify-content-center mt-4">--%>
		<%--        <ul class="pagination">--%>
		<%--            <c:if test="${page.hasPre}">--%>
		<%--                <li class="page-item">--%>
		<%--                    <a class="page-link" href="#" data-page="${page.startPage-1}">이전</a>--%>
		<%--                </li>--%>
		<%--            </c:if>--%>

		<%--            <c:forEach begin="${page.startPage}" end="${page.endPage}" var="item">--%>
		<%--                <li class="page-item ${page.condition.currentPage == item ? 'active' : ''}">--%>
		<%--                    <a class="page-link" href="#" data-page="${item}">${item}</a>--%>
		<%--                </li>--%>
		<%--            </c:forEach>--%>

		<%--            <c:if test="${page.hasNext}">--%>
		<%--                <li class="page-item">--%>
		<%--                    <a class="page-link" href="#" data-page="${page.endPage+1}">다음</a>--%>
		<%--                </li>--%>
		<%--            </c:if>--%>
		<%--        </ul>--%>
		<%--    </nav>--%>

	</div>

	<%@ include file="/WEB-INF/views/fragments/footer1.jsp"%>

	<%--<script>--%>
	<%--    const pageLinks = document.querySelectorAll(".pagination a");--%>
	<%--    pageLinks.forEach(link => {--%>
	<%--        link.addEventListener("click", (e) => {--%>
	<%--            e.preventDefault();--%>
	<%--            document.querySelector("#currentPage").value = link.dataset.page;--%>
	<%--            document.querySelector("#search-form").submit();--%>
	<%--        })--%>
	<%--    })--%>
	<%--</script>--%>

</body>
</html>
