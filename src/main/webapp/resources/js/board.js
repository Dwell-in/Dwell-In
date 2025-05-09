// detail 페이지로 이동하는 링크 만들기
document.addEventListener("DOMContentLoaded", function () {
    const links = document.querySelectorAll(".board-link");

    links.forEach((link) => {
        link.addEventListener("click", async function (e) {
            e.preventDefault();
            const boardId = this.dataset.id;

            try {
                const response = await fetch(`/api/v1/board/board-detail/${boardId}`);
                const result = await response.json();

                const params = new URLSearchParams({
                    id: result.data.id,
                    name: result.data.name,
                    profileImage: result.data.profileImage,
                });

                window.location.href = `${root}${result.data.redirect}?${params}`;
            } catch (error) {
                console.error("에러 발생:", error);
                alert("게시글 정보를 불러오는 데 실패했습니다.");
            }
        });
    });
});
