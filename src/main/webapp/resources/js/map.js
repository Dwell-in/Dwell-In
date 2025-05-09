const searchHouseInfo = async (word) => {
  document.querySelector(".loading").classList.remove("display-none"); // 비동기 함수가 완료된 후 실행됩니다.

  const query = new URLSearchParams({
    sido: word.split(" ")[0],
    gugun: word.split(" ")[1],
    dong: word.split(" ")[2],
  }).toString();

  const response = await fetch(`${root}/api/v1/house?${query}`);
  const json = await response.json();

  document.querySelector(".loading").classList.add("display-none"); // 비동기 함수가 완료된 후 실행됩니다.

  await addressSearch(word);
  await createMarker(json.data);
};

const address = new URLSearchParams(window.location.search).get("address");
searchHouseInfo(address);

// 주변 상권 아이콘들에 마커 on/off 이벤트를 추가
// 'data' 속성을 이용하여 해당 아이콘에 알맞는 마커들을 선택한다.
document.querySelectorAll(".marker").forEach((mk) => {
  mk.addEventListener("click", () => {
    mk.setAttribute("data-makerToggle", mk.getAttribute("data-makerToggle") == "true" ? "false" : "true");
    maker_Toggle(mk.getAttribute("data-markerName"), mk.getAttribute("data-makerToggle") == "true" ? true : false);
  });
});
////

const addEvent = async (num) => {
  kakao.maps.event.addListener(marker[num], "mouseover", function () {
    overlay[num].setMap(map);
  });
  kakao.maps.event.addListener(marker[num], "mouseout", function () {
    overlay[num].setMap(null);
  });
  kakao.maps.event.addListener(marker[num], "click", function () {
    detailInit(infoList[num]);
  });
};
