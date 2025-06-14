document.addEventListener("DOMContentLoaded", () => {
    // detail 페이지로 이동하는 링크 만들기
    const links = document.querySelectorAll(".board-link");

    links.forEach((link) => {
        link.addEventListener("click", async function (e) {
            e.preventDefault();
            const boardId = this.dataset.id;

            try {
                const response = await fetch(`/api/v1/board/board-detail/${boardId}`);
                const result = await response.json();

                const { redirect, board } = result.data;
                // 일단 ID만 넘기도록 설정
                const params = new URLSearchParams({ boardId: board.boardId });
                window.location.href = `${root}${redirect}?${params.toString()}`;
            } catch (error) {
                console.error("에러 발생:", error);
                alert("게시글 정보를 불러오는 데 실패했습니다.");
            }
        });
    });

    // 수정 버튼
    const updateForm = document.querySelector("#update-form");
    if (updateForm) {
        updateForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            // 폼 데이터
            const boardId = updateForm.boardId.value;
            const payload = {
                categoryId: updateForm.categoryId.value,
                title: updateForm.title.value,
                userId: updateForm.userId.value,
                userName: updateForm.userName.value,
                content: updateForm.content.value,
            };

            try {
                const res = await fetch(`${root}/api/v1/board/board-update/${boardId}`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(payload),
                });

                if (!res.ok) throw new Error("네트워크 응답 오류");

                // 성공 시 목록 페이지로 이동
                window.location.href = `${root}/board/notification-list`;
            } catch (err) {
                console.error(err);
                alert("게시글 수정에 실패했습니다.");
            }
        });
    }

    // 등록 버튼
    const form = document.querySelector("#regist-form");
    if (form) {
        form.addEventListener("submit", async (e) => {
            e.preventDefault();

            // 폼 데이터
            const payload = {
                categoryId: form.categoryId.value,
                title: form.title.value,
                userId: form.userId.value,
                userName: form.userName.value,
                content: form.content.value,
            };

            try {
                console.log(`${root}/api/v1/board/board-weite`);
                const res = await fetch(`${root}/api/v1/board/board-write`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(payload),
                });

                if (!res.ok) throw new Error("네트워크 응답 오류");

                // 성공 시 목록 페이지로 이동
                window.location.href = `${root}/board/notification-list`;
            } catch (err) {
                console.error(err);
                alert("게시글 등록에 실패했습니다.");
            }
        });
    }

    // 삭제
    const deleteForm = document.querySelector("#delete-form");
    if (deleteForm) {
        deleteForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            // 폼 데이터
            const boardId = deleteForm.boardId.value;

            try {
                const res = await fetch(`${root}/api/v1/board/board-delete/${boardId}`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                });

                if (!res.ok) throw new Error("네트워크 응답 오류");

                // 성공 시 목록 페이지로 이동
                window.location.href = `${root}/board/notification-list`;
            } catch (err) {
                console.error(err);
                alert("게시글 삭제에 실패했습니다.");
            }
        });
    }
});
