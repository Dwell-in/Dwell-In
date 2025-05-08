const addNoti = () => {
    const title = document.querySelector("#title").value.trim();
    const content = document.querySelector("#content").value.trim();

    const today = new Date();
    const year = today.getFullYear();
    const month = ('0' + (today.getMonth() + 1)).slice(-2);
    const day = ('0' + today.getDate()).slice(-2);
    const dateString = year + '-' + month  + '-' + day;

    const noti = { title, content, dateString };
    
    let notification = JSON.parse(localStorage.getItem("notification")) || [];

    notification.push(noti);
    localStorage.setItem("notification", JSON.stringify(notification));
}

document.querySelector('.addNotiBtn').addEventListener("click", () => {
    addNoti();
    alert("공지사항이 추가 되었습니다.");
    location.href = "notification.html";
});