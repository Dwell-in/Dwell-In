const addEvent = async (num) => {
  kakao.maps.event.addListener(marker[num], "mouseover", function () {
    overlay[num].setMap(map[0]);
  });
  kakao.maps.event.addListener(marker[num], "mouseout", function () {
    overlay[num].setMap(null);
  });
  kakao.maps.event.addListener(marker[num], "click", function () {
    // 상세보기창 on
    document.querySelector(".detail").classList.add("sideViewOpen");
    document.querySelector(".detail").scrollTop = 0;
    //관심아파트인지 확인

    // 아파트 이름, 즐겨찾기 버튼 등 세팅
    setInfo(`${infoList[num].aptNm}`, infoList[num].aptSeq);
    // 조회수
    getViewCount(infoList[num].aptSeq);
    // 로드뷰
    roadviewOn(infoList[num].lat, infoList[num].lon);
    // 거래 가격 그래프
    drawChart(infoList[num]);
    // 블로그
    searchBlog(infoList[num].aptNm);
  });
};

const setInfo = async (aptNm, aptSeq) => {
  const response = await fetch(`${root}/api/v1/house/view/starred/${aptSeq}`);
  const json = await response.json();
  const isStarred = json.data.isStarred;
  const aptNmNode = document.createTextNode(aptNm);

<<<<<<< HEAD
  const staredImg = document.createElement("img");
  staredImg.className = "info-stared";
  staredImg.src = isStarred ? "/resources/img/stared_t.png" : "/resources/img/stared_f.png";

  let currentIsStarred = isStarred;
  staredImg.addEventListener("click", async () => {
    const isCurrentStarred = staredImg.src.includes("stared_t.png");
    const url = `${root}/api/v1/starred/${aptSeq}`;
    const method = isCurrentStarred ? "DELETE" : "POST";

    const response = await fetch(url, { method });

    if (!response.ok) {
      alert("관심지역 등록 실패!");
      return;
    }
    currentIsStarred = !currentIsStarred;
    staredImg.src = currentIsStarred ? `/resources/img/stared_t.png` : `/resources/img/stared_f.png`;
=======
const setInfo = async(aptNm,aptSeq) => {
	const response = await fetch(`${root}/api/v1/house/view/starred/${aptSeq}`)
	const json = await response.json();
	const isStarred = json.data.isStarred;
	const aptNmNode = document.createTextNode(aptNm);
	const staredImg = document.createElement("img");
	staredImg.className = "info-stared";
	staredImg.src = isStarred ?"/resources/img/stared_t.png" :"/resources/img/stared_f.png"
	let currentIsStarred = isStarred;
	staredImg.addEventListener("click", async () => {
		if(currentIsStarred===""){
			 redirectToLoginIfNeeded();
			   return; // 아래 코드 실행 방지
		}
	 const isCurrentStarred = staredImg.src.includes("stared_t.png");
	 const url = `${root}/api/v1/starred/${aptSeq}`;
	 const method = isCurrentStarred ? "DELETE" : "POST";
	 const response = await fetch(url, { method });
		if (!response.ok) {
		   alert("관심지역 등록 실패!");
		   return;
		 }
	    currentIsStarred = !currentIsStarred;
	    staredImg.src = currentIsStarred
	      ? `/resources/img/stared_t.png`
	      : `/resources/img/stared_f.png`;
>>>>>>> a4f7a1a6f88ebee62807b5e05363d489755da8ff
  });

  const info_aptNm = document.querySelector(".info_aptNm");
  info_aptNm.innerHTML = "";
  info_aptNm.appendChild(aptNmNode);
  info_aptNm.appendChild(staredImg);
};

// 조회수 증가 후 증가된 조회수 가져오기
const getViewCount = async (aptSeq) => {
  const response = await fetch(`${root}/api/v1/house/view/${aptSeq}`, {
    method: "PATCH",
  });
  const json = await response.json();
  console.log(`현재 매물 : ${aptSeq}`);
  document.querySelector(".info_viewCount_count").innerHTML = `${json.data.viewCount}`;
};

const getCookieName = (name) => {
  const cookies = document.cookie.split("; ");
  for (let c of cookies) {
    const [key, value] = c.split("=");
    if (key === name) return decodeURIComponent(value);
  }
  return null;
};

// 로드뷰
var roadviewContainer = document.querySelector(".info-roadview"); //로드뷰를 표시할 div
var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
var roadviewClient = new kakao.maps.RoadviewClient();

const roadviewOn = (lat, lng) => {
  var position = new kakao.maps.LatLng(lat, lng);
  roadviewClient.getNearestPanoId(position, 50, function (panoId) {
    roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
  });
};
//

// 거래 가격 그래프 그리기
let chart;
Chart.defaults.font.family = "Pretendard"; // 모든 텍스트 폰트 변경
const drawChart = (houseInfo) => {
  if (chart) {
    chart.destroy();
  }
  const labels = houseInfo.listDeal.map((deal) => `${deal.dealYear}-${String(deal.dealMonth).padStart(2, "0")}`);
  const amounts = houseInfo.listDeal.map((deal) => parseFloat(deal.amount.replace(/,/g, "")));

  const ctx = document.getElementById("priceChart").getContext("2d");
  chart = new Chart(ctx, {
    type: "line",
    data: {
      labels: labels,
      datasets: [
        {
          label: "거래가격 (만원)", // 데이터셋 라벨
          data: amounts,
          borderColor: "#BBE9FD",
          borderWidth: 1,
          pointRadius: 0, // 선만 보기
          // pointStyle: 'circle',
          // pointBackgroundColor: '#BBE9FD', // 데이터 포인트의 배경색
        },
      ],
    },
    options: {
      responsive: true,
      scales: {
        x: {
          ticks: {
            color: "white", // X축 텍스트 색상
          },
        },
        y: {
          ticks: {
            color: "white", // Y축 텍스트 색상
            callback: (value) => value,
          },
        },
      },
      plugins: {
        title: {
          display: true,
          text: "거래가격 변화 (만원)",
          color: "white", // 타이틀 색상
        },
        legend: {
          display: false, // 데이터셋 제목 숨기기
          labels: {
            color: "white", // 레전드 텍스트 색상
          },
        },
      },
    },
  });
};
//

// 아파트 이름으로 블로그 검색하기
const searchBlog = async (word) => {
  const response = await fetch(`/api/v1/search/naver/blog?query=${word}`, {
    method: "GET",
  });
  const json = await response.json();
  json.items.forEach((item) => {
    appendBlog(item);
  });
};

// info-news 안에 블로그 미리보기 생성하기
const appendBlog = async (item) => {
  const title = item.title;
  const imgUrl = await getOG2Link(item.link);

  // 서버에서 이미지 받기
  const response = await fetch(`${root}/api/v1/search/imgUrl?url=${encodeURIComponent(imgUrl)}`);
  const imageBlob = await response.blob();
  const img = URL.createObjectURL(imageBlob);

  const outerDiv = document.createElement("a");
  outerDiv.className = "item";
  outerDiv.href = item.link;
  outerDiv.target = "_blank";
  outerDiv.style.backgroundImage = `url(${img})`;

  // title 설정
  const innerDiv = document.createElement("div");
  innerDiv.className = "title";
  innerDiv.innerHTML = title;

  const container = document.querySelector(".info-news");
  outerDiv.appendChild(innerDiv);
  container.appendChild(outerDiv);
};

// url로 og:image 가져오기 (미리보기 이미지)
const getOG2Link = async (link) => {
  const response = await fetch(`${root}/api/v1/search/og?url=${link}`, {
    method: "GET",
  });
  const json = await response.json();
  return json["og:image"];
};

function redirectToLoginIfNeeded(message = "로그인 후 이용하세요. 로그인 페이지로 이동할까요?") {
  const goLogin = confirm(message);
  if (goLogin) {
    window.location.href = `${root}/member/login`;
  }
}


