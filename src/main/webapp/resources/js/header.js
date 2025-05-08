document.querySelector(".search-div select").addEventListener("change", function () {
  const oldEl = document.querySelector(".search-input");
  oldEl.value = "";
  const newEl = oldEl.cloneNode(true); // true면 자식 노드까지 복사됨
  oldEl.parentNode.replaceChild(newEl, oldEl);

  const select = document.querySelector(".search-div select");

  switch (select.value) {
    case "add":
      search_add();
  }
});

const search_add = () => {
  let sido, gugun, dong;
  let selectedSido, selectedGugun;

  const sido_update = () =>
    fetch(`${root}/api/v1/search/sido`)
      .then((response) => response.json())
      .then((data) => {
        sido = data.data;
        filtered = sido;
      });

  const gugun_update = (param) =>
    fetch(`${root}/api/v1/search/gugun/${param}`)
      .then((response) => response.json())
      .then((data) => (gugun = data.data));

  const dong_update = (param) =>
    fetch(`${root}/api/v1/search/dong/${param[0]}/${param[1]}`)
      .then((response) => response.json())
      .then((data) => (dong = data.data));

  sido_update();

  const inputElement = document.querySelector(".search-input");
  const listElement = document.querySelector(".autocomplete-list");
  let filtered;

  const filtering = (word) => {
    // filtered = sido.filter(s => s.includes(word.trim().split(' ').pop()));
    if (inputElement.value.split(" ").length == 3) {
      if (inputElement.value.endsWith(" ")) {
        filtered = dong.map((d) => selectedGugun + " " + d);
      } else {
        filtered = dong.filter((d) => d.includes(word.trim().split(" ").pop())).map((d) => selectedGugun + " " + d);
      }
    } else if (inputElement.value.split(" ").length == 2) {
      if (inputElement.value.endsWith(" ")) {
        filtered = gugun.map((g) => selectedSido + " " + g);
      } else {
        filtered = gugun.filter((g) => g.includes(word.trim().split(" ").pop())).map((g) => selectedSido + " " + g);
      }
    } else {
      filtered = sido.filter((s) => s.includes(word));
    }
  };

  const autocomplete = () => {
    // 자동완성 목록 표시
    listElement.innerHTML = ""; // 기존 리스트 초기화
    if (filtered.length > 0) {
      listElement.style.display = "block"; // 자동완성 목록 표시
      filtered.forEach((s) => {
        const li = document.createElement("li");
        li.textContent = s;
        li.addEventListener("click", async () => {
          await select_autocomplete(s);
        });
        listElement.appendChild(li);
      });
    } else {
      listElement.style.display = "none"; // 필터링된 항목이 없으면 목록 숨기기
    }
  };

  // 자동완성 클릭 이벤트에 들어갈 함수
  const select_autocomplete = async (s) => {
    inputElement.value = s + " "; // 클릭한 항목을 input에 표시
    // if (s.split(' ').length == 3){
    //   searchHouseInfo(s);
    // } else
    if (s.split(" ").length == 2) {
      selectedGugun = s;
      await dong_update(s.split(" "));
    } else if (s.split(" ").length == 1) {
      selectedSido = s;
      await gugun_update(s);
    }

    filtering(s);
    setTimeout(() => {
      autocomplete();
      inputElement.focus();
    }, 200);
  };

  // 'input' 이벤트 리스너 추가 (자동완성)
  inputElement.addEventListener("input", function (event) {
    const word = event.target.value;
    filtering(word);
    autocomplete();
  });

  // 스페이스바, 엔터 입력으로 자동 완성
  inputElement.addEventListener("keypress", (e) => {
    const word = e.target.value;
    if (e.key === " " || (word.split(" ").length <= 3 && e.key === "Enter")) {
      e.preventDefault();
      listElement.firstChild.click();
      setTimeout(() => {
        autocomplete();
      }, 200);
    }
  });

  // input 요소가 포커스되었을 때 자동완성 목록 표시
  inputElement.addEventListener("focus", function () {
    autocomplete();
  });

  inputElement.addEventListener("focusout", () => {
    setTimeout(() => {
      listElement.style.display = "none"; // 포커스 아웃 후 목록 숨기기
    }, 200);
  });

  const searchHouseInfo = async (word) => {
    console.log("최종 address: " + word);

    const query = new URLSearchParams({
      sido: word.split(" ")[0],
      gugun: word.split(" ")[1],
      dong: word.split(" ")[2],
    }).toString();

    console.log("요청 url: " + `${root}/api/v1/house?${query}`);
    const response = await fetch(`${root}/api/v1/house?${query}`);
    const json = await response.json();
    console.log(query);
    console.log(json);

    await addressSearch(word);
    await createMarker(json.data);
  };

  const handleSearchSubmit = () => {
    // await searchHouseInfo(inputElement.value.trim());
    window.location.href = `${root}/house/map?address=${inputElement.value.trim()}`;
  }
  document.getElementById("searchForm").addEventListener("submit", async (e) => {
    e.preventDefault(); // 기본 제출 동작 방지
    handleSearchSubmit();
  });

  document.querySelector(".searchImg").addEventListener("click", () => {
    if (inputElement.value.trim().split(' ').length == 3){
      handleSearchSubmit();
    }
  });
};

search_add();
