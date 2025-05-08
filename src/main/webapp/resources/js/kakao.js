//geolocation을 받아 올 수 있나 확인하는 if구문
if (navigator.geolocation) {
  //geolocation을 이용해 접속 위치를 얻어오기
  navigator.geolocation.getCurrentPosition(displayLocation);
}

function displayLocation(position) {
  latlon.y = position.coords.latitude; // 위도
  latlon.x = position.coords.longitude; // 경도
  createMap("mapBox", 4, 0);
  mapinit();
}

var latlon = { y: 0, x: 0 };

// 메인 지도창
var map;
// 지도를 생성하는 function입니다.
function createMap(id, lv, num) {
  var mapContainer = document.getElementById(id), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(latlon.y, latlon.x), // 지도의 중심좌표
      level: lv, // 지도의 확대 레벨
    };

  // 지도를 생성합니다
  map = new kakao.maps.Map(mapContainer, mapOption);
}

var imageSrc = `${root}/resources/img/marker.png`, // 마커이미지의 주소입니다
  imageSize = new kakao.maps.Size(30, 41), // 마커이미지의 크기입니다
  imageOption = { offset: new kakao.maps.Point(15, 29) }; // 마커 이미지 좌표 설정

let marker = []; // 각 부동산 거래에 해당하는 마커 들
let overlay = []; // 위 마커의 overlay
let marker_FD6 = []; // 주변 상권 마커 - 음식점
let marker_CE7 = []; // 주변 상권 마커 - 카페
let marker_HP8 = []; // 주변 상권 마커 - 병원
let marker_BK9 = []; // 주변 상권 마커 - 은행
let marker_SC4 = []; // 주변 상권 마커 - 학교
let marker_CS2 = []; // 주변 상권 마커 - 편의점

const markerReset = () => {
  marker.forEach((mk) => mk.setMap(null));
  marker_FD6.forEach((mk) => mk.setMap(null));
  marker_CE7.forEach((mk) => mk.setMap(null));
  marker_HP8.forEach((mk) => mk.setMap(null));
  marker_BK9.forEach((mk) => mk.setMap(null));
  marker_SC4.forEach((mk) => mk.setMap(null));
  marker_CS2.forEach((mk) => mk.setMap(null));
  overlay.forEach((mk) => mk.setMap(null));

  marker = [];
  marker_FD6 = [];
  marker_CE7 = [];
  marker_HP8 = [];
  marker_BK9 = [];
  marker_SC4 = [];
  marker_CS2 = [];
  overlay = [];
};

let infoList = [];
const createMarker = async (infos) => {
  markerReset();
  let num = 0;
  infoList = infos;
  // infos.forEach( async (info) => {   // forEach는 await를 기다려 주지 않는다.
  for (const info of infos) {
    await createOverlay(info);
    await displayMarker(marker, map, { y: info.lat, x: info.lon }, `${root}/resources/img/marker.png`);
    await addEvent(num);
    num++;
  }
};

const createOverlay = async (info) => {
  const content = `
  	<div class="overlay">
    	${info["aptNm"]}
	  </div>
  `;
  overlay.push(
    new kakao.maps.CustomOverlay({
      content: content,
      map: null,
      position: new kakao.maps.LatLng(info.lat, info.lon),
      yAnchor: 1.5,
    })
  );
};

// 지도에 마커를 표시하는 함수입니다
const displayMarker = async (arr, map, place, imageSrc) => {
  // 마커를 생성하고 지도에 표시합니다
  arr.push(
    new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(place.y, place.x),
      image: new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
    })
  );
};

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

function mapinit() {
  map.setCenter(new kakao.maps.LatLng(latlon.y, latlon.x)); // 메인화면 지도 중심변경
}

// 주소 검색 버튼
async function addressSearch(address) {
  geocoder.addressSearch(address, function (result, status) {
    // 정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {
      latlon.y = result[0].y;
      latlon.x = result[0].x;
      mapinit();

      localSearch("FD6", latlon, marker_FD6, `${root}/resources/img/marker_FD6.png`);
      localSearch("CE7", latlon, marker_CE7, `${root}/resources/img/marker_CE7.png`);
      localSearch("HP8", latlon, marker_HP8, `${root}/resources/img/marker_HP8.png`);
      localSearch("BK9", latlon, marker_BK9, `${root}/resources/img/marker_BK9.png`);
      localSearch("SC4", latlon, marker_SC4, `${root}/resources/img/marker_SC4.png`);
      localSearch("CS2", latlon, marker_CS2, `${root}/resources/img/marker_CS2.png`);
      localSearch("AG2", latlon, marker_CS2, `${root}/resources/img/marker_CS2.png`);
    }
  });
}

let placesList = [];
// 카테고리로 장소 검색
// msg.documents의 값들(response)에는 여러 정보가 담겨있으나 일단 마커만 띄울 용도로 좌표만 사용중
const localSearch = (code, latlon, arr, img) => {
  $.ajax({
    method: "POST",
    url: `https://dapi.kakao.com/v2/local/search/category.json?category\_group\_code=${code}&page=1&x=${latlon.x}&y=${latlon.y}&radius=5000`,
    headers: { Authorization: "KakaoAK 9fc095edb70a6304836b3a8f6b980ab8" },
    async: false,
  }).done(function (msg) {
    msg.documents.forEach((response) => {
      const place = { y: response.y, x: response.x };
      placesList.push({ code: code, place: place });
      displayMarker(arr, null, place, img);
    });
  });
};

// 마커 그룹 on/off
const maker_Toggle = (str, flag) => {
  let arr;
  if (str == "FD6") arr = marker_FD6;
  else if (str == "CE7") arr = marker_CE7;
  else if (str == "HP8") arr = marker_HP8;
  else if (str == "BK9") arr = marker_BK9;
  else if (str == "SC4") arr = marker_SC4;
  else if (str == "CS2") arr = marker_CS2;
  arr.forEach((mk) => {
    mk.setMap(flag ? map : null);
  });
};
