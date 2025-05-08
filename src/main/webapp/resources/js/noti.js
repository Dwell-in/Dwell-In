if (sessionStorage.getItem('loggedInUser') == 'admin'){
    document.querySelector(".addBtn").classList.remove("display-none");
}

if (localStorage.getItem('notification')){
    const notification = JSON.parse(localStorage.getItem("notification"));
    notification.forEach(noti => {
        const content = `
            <tr>
                <td>${noti['title']}</td>
                <td>${noti['dateString']}</td>
            </tr>
        `;
        document.querySelector('tbody').innerHTML = content;
    });
    
}