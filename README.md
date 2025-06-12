# 🏠 Dwell_In 

#### 👨‍💻 SSAFY 13기 1학기 광주 4반 Dwell_In (25.04.28 ~ 25.05.28)

![](https://velog.velcdn.com/images/ksj0314/post/6b8f70de-e0dd-41f1-a92d-0dc31790df6b/image.png)

---

## 📑 목차

* [1️⃣ 프로젝트 개요](#1️⃣-프로젝트-개요)
  * [🏠 기획 배경](#-기획-배경)
  * [🏠 팀원 소개](#-팀원-소개)
* [2️⃣ 서비스 소개](#2️⃣-서비스-소개)
  * [🏠 시연 영상](#-시연-영상)
  * [🏠 서비스 화면 및 기능](#-서비스-화면-및-기능)
* [3️⃣ 활용 기술](#3️⃣-활용-기술)
* [4️⃣ 개발 환경](#4️⃣-개발-환경)
* [5️⃣ 프로젝트 구조](#5️⃣-프로젝트-구조)
* [6️⃣ 협업 환경](#6️⃣-협업-환경)
* [7️⃣ 프로젝트 일정](#7️⃣-프로젝트-일정)
* [8️⃣ 프로젝트 산출물](#8️⃣-프로젝트-산출물)
  * [🏠 기능 정의서](#-기능-정의서)
  * [🏠 API 명세서](#-api-명세서)
  * [🏠 화면 설계서](#-화면-설계서)
  * [🏠 ERD](#-erd)
  * [🏠 클래스 다이어그램](#-클래스-다이어그램)

---

## 1️⃣ 프로젝트 개요

### 🏠 팀원 소개

| 김소중 | 양대열 | 추지웅 |
|:-:|:-:|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/fce28f5d-d812-46dd-acdf-3b5534e39e07/image.png) | ![](https://velog.velcdn.com/images/ksj0314/post/b052113b-a3a2-434b-a1e7-d71516af9618/image.png) | ![](https://velog.velcdn.com/images/ksj0314/post/f6ded87e-b859-4ec5-a597-04bfb17e1d6f/image.png) |
| Leader, FrontEnd, BackEnd | AI, BackEnd | Security, BackEnd, 배포 |

### 🏠 기획 배경

![](https://velog.velcdn.com/images/ksj0314/post/eaaf7229-d1d5-40f5-86d7-b8ab9f8f4ad3/image.png)

***집을 구하기 위해서는 많은 정보가 필요합니다.***

>
* 어떤 매물이 있지?
* 이전 거래 내역들은 어떻게 되지?
* 주변 상권은 어떻게 되지?
...

이러한 여러 정보를 알아보기 위해서는 많은 검색 시도가 필요합니다.
또한, 많은 정보를 정리하고 분석하는 노력이 필요하죠.

Dwell_In은 이러한 사용자의 번거로운 노력을 해소하고자 만들어졌습니다.
정보를 <span style="color:red;">한눈에 보기 쉽게</span> 모아서 보여주고
그래프와 AI를 통해 대신 <span style="color:red;">분석</span>해주는 서비스를 제공합니다.

---

## 2️⃣ 서비스 소개

### 🏠 시연 영상

[🔗 시연 영상 바로가기](https://www.youtube.com/watch?v=msAUq9gcWXs)

### 🏠 서비스 화면 및 기능

#### 1) 사이트 소개

저희는 사용자가 손 쉽게 부동산 정보를 검색할 수 있기를 원합니다.
그렇다면 저희 사이트의 이용이 어려우면 안되겠죠?

| Intro |
|:-:|
| ![](./reademe/index.gif) |

시작 페이지는 저희 사이트의 소개와 담아내고 있습니다.
저희 서비스의 목표와 기능을 간단한 UI와 애니메이션으로 나타내었습니다.

#### 2) 매물 검색

| 자동 완성 검색 |
|:-:|
| ![](./reademe/search.gif) |

헤더에 마우스를 올리면 검색 상자가 열립니다. 
검색할 주소를 입력할 때 자동 완성 리스트가 나타나며 엔터 혹은 마우스 클릭으로 자동 완성할 수 있습니다.

| 상세보기 |
|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/3d9db129-e44c-4a22-a54f-a4c8573ec67a/image.png) |

상세정보창에는 다양한 데이터가 들어있습니다.

1. 간단한 건물의 정보가 있고
2. 해당 건물의 실거래가를 면적별로 확인할 수 있는 그래프가 있습니다.
3. 건물의 실물을 확인할 수 있는 카카오 API를 이용한 로드뷰가 있습니다.
4. 네이버 API를 이용해 블로그와 뉴스를 검색하여 나타내며 클릭하여 접속할 수 있습니다.
5. 이러한 건물의 정보로 AI 에게 분석을 요청할 수 있습니다.

이렇게 다양한 상세보기를 지원하고 같은 컴포넌트에서 상단 탭을 변경해 등록된 매물과 주변 시설도 확인할 수 있습니다.

| 매물 보기 | 매물 상세 보기 |
|:-:|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/f2823ca4-e361-436c-909c-972058991078/image.png) | ![](https://velog.velcdn.com/images/ksj0314/post/d9892fe3-86cd-47e9-95df-16d42bc1f385/image.png) |

매물 보기 탭에서 필터링을 이용해 등록된 매물을 확인할 수 있습니다.
매물 카드를 클릭하여 매물의 상세정보를 확인 할 수 있습니다.

| 주변 시설 보기(그래프) | 주변 시설 보기(맵) |
|:-:|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/68ea9ce5-28b3-46d1-91fa-a95f4fda0435/image.png) | ![](https://velog.velcdn.com/images/ksj0314/post/9fc39b6a-cc1a-4298-9d69-bc1e38b95ca5/image.gif) |

다음 탭에서는 주변 시설의 분포도를 그래프를 통해 볼 수 있습니다.
또한, 매물의 거래를 위해 가까운 부동산 정보를 얻을 수 있습니다.
맵 페이지에서도 해당 동네의 주변 상권을 마커로 확인할 수 있습니다.

#### 3) AI

저희 사이트는 AI를 이용한 다양한 서비스를 지원합니다.

| 챗봇(Drag&Drop) |
|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/130b2147-ad03-4e5e-a382-3c5067943721/image.gif) |

채팅방 상단에는 챗봇이 고정되어 있습니다.
카드 형태의 매물들을 Drag & Drop하여 질문에 사용할 수 있습니다.
챗봇의 답변을 마크다운으로 파싱하여 출력합니다.

| AI 분석 (실거래 내역) |
|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/ff992d7c-9db3-4ade-b661-f887b3c3bb6f/image.gif) |

상세보기탭에서 AI 분석 버튼을 누르면 해당 매물의 실거래가 정보를 분석한 결과를 나타냅니다.

| AI 추천 (매물 리스트) |
|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/6328af0b-33a4-4985-bc3d-9dd5ba1fa6cb/image.gif) |

매물보기탭에서 AI 추천 버튼을 누르면 데이터 베이스에 저장된 사용자의 정보를 바탕으로 매물을 추천해줍니다.

| AI 추천 (아파트 리스트) |
|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/b5cbe814-6281-4b11-9221-1388ba2b709e/image.gif) |

맵 페이지에서 AI 추천 버튼을 누르면 챗봇이 자동으로 연결되며 저장된 사용자의 정보를 바탕으로 추천받을 수 있습니다.

#### 4) 채팅

| 실시간 채팅 |
|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/f4c29617-6b07-41a0-94d0-439ccb9c8662/image.gif) |

매물 상세보기에서 메시지 아이콘을 클릭하여 매물을 등록한 사용자와 실시간 1:1 채팅이 가능합니다.

| 채팅 알림 |
|:-:|
| ![](./reademe/chat알림.gif) |

메시지가 온 경우 프로필 상단에 알림이 표시됩니다.

#### 5) 로그인, 회원가입



#### 6) 관리자 페이지

| EC2 모니터링 | 유저 관리 |
|:-:|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/ccad7ba2-5c51-41ea-9123-8f498ca1e064/image.png) | ![](https://velog.velcdn.com/images/ksj0314/post/9cefdabd-9dce-4af8-86fe-38345f2e7623/image.png) |

관리자 페이지에서는 배포된 서버의 사용량을 확인할 수 있고 유저를 관리할 수 있습니다.

#### 7) Dwellog

| Dwellog ||
|:-:|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/8c9bd082-d960-47d2-bc9b-1518e4398862/image.png) | ![](https://velog.velcdn.com/images/ksj0314/post/7024c84d-19b0-4d92-b43c-b6d24255d185/image.png) |

Dwellog는 저희의 개발 노트가 작성된 공간입니다.
마크다운 형식으로 정리한 내용을 `marked` 패키지를 통해 파싱하여 렌더링했습니다.

SSAFY 1학기를 마무리하는 프로젝트이기에 저희와 함께 공부했던 다른 팀들, 혹은 다음 기수의 교육생분들에게 참고가 되었으면 하는 마음으로 개발노트를 작성했습니다.

***

| ![](https://velog.velcdn.com/images/ksj0314/post/5cbb8e6e-c891-461f-bbb8-ba9b6e4150ab/image.png) | ![](https://velog.velcdn.com/images/ksj0314/post/61adf5f9-7f25-458c-8c5b-1bcc3b34ca4b/image.png) |
|:-:|:-:|


BackEnd 서버가 내려가더라도 개발노트만은 보여질 수 있도록
`.md` 파일은 DB를 이용하지 않고 프로젝트 내에 직접 추가하고
`.json`을 작성해 목록을 가져올 수 있게 구성하였습니다.

[>> Dwellog 바로가기](https://dwell-in.github.io/#/dwellog/0)

---

## 3️⃣ 활용 기술



---

## 4️⃣ 개발 환경


|||
|:-:|:-:|
|Front||

![](https://img.shields.io/badge/vue-%234FC08D?style=for-the-badge&logo=vuedotjs&logoColor=white)


---

## 5️⃣ 프로젝트 구조

#### 💼  Back

```plaintext
├── 📁ai
│   ├── 📁config
│   ├── 📁controller
│   ├── 📁model
│	│	├── 📁dto
│	│	└── 📁service
│   ├── 📁prompt
│   └── 📁tool
├── 📁board
│   ├── 📁controller
│   └── 📁model
│		├── 📁dao
│		├── 📁dto
│		└── 📁service
├── 📁common
│   ├── 📁config
│   ├── 📁controller
│   ├── 📁filter
│   ├── 📁interceptor
│   └── 📁model
│		└── 📁service
├── 📁house
│   ├── 📁controller
│   ├── 📁model
│   │   ├── 📁dao
│   │   ├── 📁dto
│   │   └── 📁service
│   └── 📁util
├── 📁member
│   ├── 📁controller
│   └── 📁model
│       ├── 📁dao
│       ├── 📁dto
│       └── 📁service
├── 📁search
│   ├── 📁controller
│   └── 📁model
│       ├── 📁dao
│       ├── 📁dto
│       └── 📁service
├── 📁security
│   ├── 📁config
│   ├── 📁controller
│   ├── 📁jwt
│   └── 📁model
│       ├── 📁dao
│       ├── 📁dto
│       └── 📁service
├── 📁starred
│   ├── 📁controller
│   └── 📁model
│       ├── 📁dao
│       ├── 📁dto
│       └── 📁service
└── 📁websocket
    ├── 📁config
    ├── 📁controller
    └── 📁model
        ├── 📁dao
        ├── 📁dto
        └── 📁service
```

* BackEnd는 큰 기능 별로 패키지를 나누고
패키지 하위에 각각의 필요한 config, controller, model 등을 구성하는 구조로 설계하여 유지보수성을 높였습니다.

#### 💼 Front

```plaintext
├── 📁assets
│   ├── 📁css
│   ├── 📁data
│   ├── 📁fonts
│   └── 📁img
├── 📁components
│   ├── 📁admincomponent
│   ├── 📁ai
│   ├── 📁board
│   ├── 📁chat
│   ├── 📁dwellog
│   ├── 📁home
│   ├── 📁house
│   ├── 📁layout
│   │   ├── 📁footer
│   │   ├── 📁header
│   │   ├── 📁modal
│   │   ├── 📁nav
│   │   └── 📁side
│   ├── 📁map
│   ├── 📁member
│   ├── 📁parts
│   └── 📁starred
├── 📁lib
├── 📁stores
└── 📁views
    ├── 📁board
    ├── 📁dwellog
    └── 📁member
```

* assets: 폰트, 이미지 등의 파일을 관리합니다.
* components: 페이지 내의 각종 요소들을 컴포넌트로 만들어
기능 별로 폴더로 묶어서 관리합니다.
* lib: 템플릿과 style이 없고 여러 곳에서 재사용하는 기능들을(js) 관리합니다.
* stores: `pinia`로 관리하는 전역 상태를 관리합니다.
* views: "페이지"에 해당하는 `.vue` 파일들을 관리합니다.
해당 `.vue`파일 내에서 각종 컴포넌트를 배치하여 페이지를 구성합니다.

---

## 6️⃣ 협업 환경

#### 1. GitHub

| ![](https://velog.velcdn.com/images/ksj0314/post/bfc13fec-4064-40dd-b38b-bafc4304c672/image.png) |
|:-:|

GitHub의 Organization 서비스를 이용하여 BE, FE 분리 Repositories를 만들어 관리합니다.

| ![](https://velog.velcdn.com/images/ksj0314/post/d35668ce-ff99-4fe1-885f-6add8ae31767/image.png) | ![](https://velog.velcdn.com/images/ksj0314/post/a09c0abc-b9f0-4e49-a7c7-da975379bbd5/image.png) |
|:-:|:-:|

Branches를 개발자별로 따로 관리하고 각자 PR을 올립니다.
다른 팀원이 PR을 확인하여 리뷰를 남기고 오류를 확인하는 방식을 채택해 충돌을 최소화하고 충돌이 나더라도 복구할 수 있는 환경을 만들었습니다.

|![](https://velog.velcdn.com/images/ksj0314/post/5b2d2713-33fb-4063-831c-61b85fc5654e/image.png)|![](https://velog.velcdn.com/images/ksj0314/post/74a73607-d93c-40ca-b64a-5a08737459db/image.png)|
|:-:|:-:|

또한, 커밋 내용과 PR 제목의 네이밍 규칙을 지정해 관리하였습니다.

***

#### 2. Figma

페이지의 목업을 먼저 만들고 FE 구현을 하였습니다.

전체 페이지 구조를 한눈에 파악할 수 있으며
FE 코드를 작성하는 것에 비해 빠른 속도로 페이지의 구조를 확인할 수 있습니다.
빠른 피드백을 받을 수 있어 작업의 효율이 증가합니다.

***

#### 3. Notion

개발 코드 이외에 패키지 설치, 회의 내용, 일정 관리 등을 함께 작성하고 관리하기 위해 Notion을 이용하였습니다.

---

## 7️⃣ 프로젝트 일정

| ![](https://velog.velcdn.com/images/ksj0314/post/6c4cfcd0-8834-4c3e-a14d-6f75a9a9c5bf/image.png) |
|:-:|

---

## 8️⃣ 프로젝트 산출물

### 🏠 API 명세서

|![](https://velog.velcdn.com/images/ksj0314/post/0431e4c3-0214-49a1-b420-df0e6b8d8ce0/image.png)|
|:-:|

개발 노트 Dwellog는 저희 팀원끼리 개발 내용을 공유하기 위해서도 사용됩니다.
BE 개발자와 FE 개발자가 원할한 소통을 할 수 있게 이곳에 API 명세서를 작성하였습니다.

***

### 🏠 화면 설계서

> Figma
>
|![](https://velog.velcdn.com/images/ksj0314/post/6cc56080-898e-48d4-82f8-dbea1af1123c/image.png)|
|:-:|

***

### 🏠 ERD

|![](https://velog.velcdn.com/images/ksj0314/post/dfe986bb-6565-47a7-8edb-ad519d18b802/image.png)|
|:-:|

***

### 🏠 클래스 다이어그램

|다이어그램|구조도|
|:-:|:-:|
| ![](https://velog.velcdn.com/images/ksj0314/post/0dd73690-f79c-465b-ab17-4127daa16256/image.png) | ![](https://velog.velcdn.com/images/ksj0314/post/0ea25568-88a9-4181-b680-9735e7aaefdc/image.png) |

큰 기능 단위로 패키지를 구성하고 MVC 구조로 구현하였습니다.
