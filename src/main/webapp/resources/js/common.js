/*
    get 방식으로 fetch 처리하기 위한 함수
     - url: 요청 url
     - param: 요청 parameter
     - isXml: 요청 방식이 xml 인지 여부
    */

const getFetch = async (url, param, isXml) => {
  try {
    const queryString = new URLSearchParams(param).toString();
    const response = await fetch(url + "?" + queryString);
    let result = "";
    if (isXml) {
      result = await response.text();
    } else {
      result = await response.json();
    }
    console.log("요청 URL: " + url, param, result);
    return result;
  } catch (e) {
    console.log(e);
    throw e;
  }
};

const getLabel = (select) => select.selectedOptions[0].text;
