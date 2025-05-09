// 버튼으로 섹션 이동
const moveSection = (section) => {
  document.querySelector(section).scrollIntoView();
};

// 마우스 스크롤 가로방향
document.querySelector("main").addEventListener("wheel", (e) => {
  if (e.deltaY !== 0) {
    window.scrollBy(e.deltaY, 0); // 가로 방향으로만 스크롤
  }
});

// info 페이지 애니메이션
const title = document.querySelector(".title");
const infoChild = [
  document.querySelector(".title-sub"),
  document.querySelector(".content"),
  document.querySelector(".moveSectionIcon"),
];

const text = "Dwell-In";
let currentIndex = 0;

const intervalId = setInterval(() => {
  if (currentIndex <= text.length) {
    title.innerHTML = text.substring(0, currentIndex);
    currentIndex++;
  } else {
    clearInterval(intervalId);
    setTimeout(() => {
      infoChild.forEach((child) => {
        child.style.opacity = "1";
      });
    }, 300);
  }
}, 150);
