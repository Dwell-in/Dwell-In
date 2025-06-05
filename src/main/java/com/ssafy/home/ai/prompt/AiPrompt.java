package com.ssafy.home.ai.prompt;

import com.ssafy.home.ai.model.dto.AptRecommendDTO;
import com.ssafy.home.house.model.dto.CompareDetailRequestDTO;
import com.ssafy.home.house.model.dto.FilteredPropertyDTO;
import com.ssafy.home.member.favorite.model.dto.MemberFavoriteDTO;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ssafy.home.house.model.dto.HouseinfoDTO;
import com.ssafy.home.house.model.dto.HouserdealDTO;

@Component
public class AiPrompt {
	public String buildComparePrompt(List<HouseinfoDTO> details) {
	    StringBuilder sb = new StringBuilder();
	    sb.append("다음은 여러 아파트의 상세 정보와 과거 거래 내역입니다.\n");
	    sb.append("각 아파트의 건축년도, 위치, 거래 가격 추이를 참고하여 다음을 수행해 주세요:\n");
	    sb.append("1. 가격, 거래량, 연식 등을 기준으로 객관적으로 비교해 주세요.\n");
	    sb.append("2. 향후 가격 추세를 예측해 주세요 (상승/하락 여부와 이유).\n");
	    sb.append("3. 이 중 가장 추천할만한 아파트를 하나 선택하고 이유를 설명해 주세요.\n\n");

	    for (HouseinfoDTO detail : details) {
	        sb.append("아파트명: ").append(detail.getAptNm()).append("\n");
	        sb.append("도로명 주소: ").append(detail.getRoadNm()).append(" ")
	          .append(detail.getRoadNmBonbun()).append("-")
	          .append(detail.getRoadNmBubun()).append("\n");
	        sb.append("건축년도: ").append(detail.getBuildYear()).append("년\n");
	        sb.append("위치 (위도/경도): ").append(detail.getLat()).append(", ").append(detail.getLon()).append("\n");
	        sb.append("거래 조회수: ").append(detail.getViewCount()).append("회\n");
	        sb.append("거래 내역:\n");

	        List<HouserdealDTO> deals = detail.getListDeal();
	        if (deals == null || deals.isEmpty()) {
	            sb.append("- 거래 정보 없음\n");
	        } else {
	            for (HouserdealDTO deal : deals) {
	                sb.append("- ")
	                  .append(deal.getDealYear()).append("년 ")
	                  .append(deal.getDealMonth()).append("월 ")
	                  .append(deal.getDealDay()).append("일 | ")
	                  .append("면적: ").append(deal.getExclu()).append("㎡ | ")
	                  .append("가격: ").append(deal.getAmount()).append("만원\n");
	            }
	        }

	        sb.append("\n");
	    }

	    sb.append("위 정보를 바탕으로 가장 추천하는 아파트와 이유를 알려주세요.");
	    return sb.toString();
	}

	public String buildPricePredictPrompt(HouseinfoDTO houseInfo) {
		   StringBuilder sb = new StringBuilder();

		    sb.append("다음은 특정 아파트에 대한 상세 정보와 거래 기록입니다.\n");
		    sb.append("이 정보를 바탕으로 아래 내용을 분석해 주세요:\n");
		    sb.append("1. 가격 추이 분석 (거래가격이 오르고 있는지, 내리고 있는지)\n");
		    sb.append("2. 평판이나 특이사항 추정 (예: 거래량 적음, 연식 오래됨 등)\n");
		    sb.append("3. 향후 가격 전망 (상승/하락 여부와 그 이유)\n\n");

		    sb.append("[아파트 기본 정보]\n");
		    sb.append("아파트명: ").append(houseInfo.getAptNm()).append("\n");
		    sb.append("도로명 주소: ").append(houseInfo.getRoadNm()).append(" ")
		      .append(houseInfo.getRoadNmBonbun()).append("-")
		      .append(houseInfo.getRoadNmBubun()).append("\n");
		    sb.append("건축년도: ").append(houseInfo.getBuildYear()).append("년\n");
		    sb.append("위치: 위도 ").append(houseInfo.getLat()).append(", 경도 ").append(houseInfo.getLon()).append("\n");
		    sb.append("조회수: ").append(houseInfo.getViewCount()).append("회\n\n");

		    sb.append("[거래 내역]\n");
		    List<HouserdealDTO> deals = houseInfo.getListDeal();
		    if (deals == null || deals.isEmpty()) {
		        sb.append("거래 기록이 존재하지 않습니다.\n");
		    } else {
		        for (HouserdealDTO deal : deals) {
		            sb.append("- 거래일: ").append(deal.getDealYear()).append("년 ")
		              .append(deal.getDealMonth()).append("월 ").append(deal.getDealDay()).append("일, ")
		              .append("면적: ").append(deal.getExclu()).append("㎡, ")
		              .append("가격: ").append(deal.getAmount()).append("만원\n");
		        }
		    }

		    sb.append("\n위 정보로 아파트의 가치와 가격 흐름을 판단하고, 향후 가격이 어떻게 변할지 예측해 주세요.");

		    return sb.toString();
	}

	public String buildDetailPrompt(List<CompareDetailRequestDTO> request) {
		StringBuilder sb = new StringBuilder();
		sb.append("다음은 현재 여러 매물들의 정보들입니다.");

		for(CompareDetailRequestDTO detail : request){
			System.out.println("hi");
		}
		return sb.toString();
	}

	public String buildFilterRecommendPrompt(MemberFavoriteDTO favorite, List<FilteredPropertyDTO> propertyList) {
		StringBuilder sb = new StringBuilder();

		sb.append("당신은 사용자의 주거 선호 조건을 바탕으로 부동산 매물을 추천하는 AI입니다.\n");
		sb.append("아래의 사용자 선호 정보를 참고하여, 제공된 필터링된 매물 리스트 중에서 사용자의 취향에 가까운 순서대로 정렬해 주세요.\n");
		sb.append("모든 조건이 완벽히 일치하지 않더라도, 최대한 사용자의 니즈에 근접하는 방향으로 친절하게 추천해주세요.\n");
		sb.append("매물마다 내부적으로 점수를 계산해 우선순위를 판단한 뒤, **모든 매물을 추천 순서대로 포함하여** 반환하세요.\n");


		sb.append("<< 사용자 선호 정보 >>\n");
		sb.append("- 희망 지역: ").append(favorite.getPreferredLocation()).append("\n");
		sb.append("- 매매가: ").append(favorite.getBudgetMin()).append(" ~ ").append(favorite.getBudgetMax()).append(" 원\n");
		sb.append("- 월세: ").append(favorite.getRentMin()).append(" ~ ").append(favorite.getRentMax()).append(" 원\n");
		sb.append("- 주택 유형: ").append(favorite.getPreferredType()).append("\n");
		sb.append("- 최소 면적: ").append(favorite.getMinArea()).append("㎡ 이상\n");
		sb.append("- 최소 방 개수: ").append(favorite.getMinRooms()).append("개 이상\n");
		sb.append("- 출퇴근 목적지: ").append(favorite.getCommuteTarget()).append(" (").append(favorite.getCommuteTimeLimit()).append("분 이내)\n");
		sb.append("- 가족 구성: ").append(favorite.getHouseholdType()).append("\n");
		sb.append("- 선호 층수: ").append(favorite.getFloorPreference()).append("\n");
		sb.append("- 선호 건축 연도: ").append(favorite.getBuildYearPreference()).append("\n");
		sb.append("- 필수 옵션: ").append(favorite.getRequiredFeatures()).append("\n");
		sb.append("- 생활 우선순위: ").append(favorite.getLifestylePriority()).append("\n\n");

		sb.append("<< 추천 대상 매물 목록 >>\n");

		for (FilteredPropertyDTO p : propertyList) {
			sb.append("- 아파트 ID: ").append(p.getAptSeq()).append(", ");
			sb.append("유형: ").append(p.getType()).append(", ");
			sb.append("면적: ").append(p.getSupplyArea()).append("㎡, ");
			sb.append("층수: ").append(p.getFloor()).append("층, ");

			if ("매매".equals(p.getType())) {
				sb.append("매매가: ").append(p.getSalePrice()).append(" 원");
			} else if ("전세".equals(p.getType())) {
				sb.append("전세보증금: ").append(p.getDeposit()).append(" 원");
			} else if ("월세".equals(p.getType())) {
				sb.append("보증금: ").append(p.getDeposit()).append(" 원, 월세: ").append(p.getMonthlyRent()).append(" 원");
			}

			sb.append("\n");
			if (p.getOptionNames() != null && !p.getOptionNames().isEmpty()) {
				sb.append("  - 옵션: ").append(String.join(", ", p.getOptionNames())).append("\n");
			}
			if (p.getSafetyNames() != null && !p.getSafetyNames().isEmpty()) {
				sb.append("  - 안전시설: ").append(String.join(", ", p.getSafetyNames())).append("\n");
			}
			sb.append("\n");
		}

		sb.append("이제 이 매물들 중 사용자와 가장 잘 맞을 것 같은 순서로 정렬해 주세요.\n");
		sb.append("아래 형식을 참고하여 각 매물에 대한 간단한 한줄평(`comment`)도 함께 JSON 객체에 포함시켜 주세요.\n");
		sb.append("한줄평은 해당 매물이 왜 추천 순위에 있는지 간략하게 설명해주는 친절한 문장으로 작성해주세요.\n");
		sb.append("모든 매물을 추천 순서대로 포함하고, 응답은 JSON 배열로 주세요 JSON 배열 외에는 아무 글을 주지 마세요.\n\n");

		sb.append("형식 예시:\n[\n");
		sb.append("  {\n");
		sb.append("    \"aptSeq\": \"11680-291\",\n");
		sb.append("    \"type\": \"매매\",\n");
		sb.append("    \"floor\": 10,\n");
		sb.append("    \"supplyArea\": 84.97,\n");
		sb.append("    \"salePrice\": 850000000,\n");
		sb.append("    \"deposit\": null,\n");
		sb.append("    \"monthlyRent\": null,\n");
		sb.append("    \"optionNames\": [\"엘리베이터\", \"주차장\"],\n");
		sb.append("    \"safetyNames\": [\"CCTV\"],\n");
		sb.append("    \"comment\": \"엘리베이터와 주차장이 있는 고층 아파트로, 통근이 편리할 것 같아요.\"\n");
		sb.append("  },\n");
		sb.append("  {\n");
		sb.append("    \"aptSeq\": \"11800-512\",\n");
		sb.append("    \"type\": \"전세\",\n");
		sb.append("    \"floor\": 5,\n");
		sb.append("    \"supplyArea\": 59.99,\n");
		sb.append("    \"salePrice\": null,\n");
		sb.append("    \"deposit\": 400000000,\n");
		sb.append("    \"monthlyRent\": null,\n");
		sb.append("    \"optionNames\": [\"반려동물 가능\"],\n");
		sb.append("    \"safetyNames\": [\"비상벨\"],\n");
		sb.append("    \"comment\": \"반려동물 가능하고 안전시설이 잘 갖춰진 아늑한 전세 매물입니다.\"\n");
		sb.append("  }\n");
		sb.append("]\n\n");



		return sb.toString();
	}

	public String buildAptRecommendationPrompt(MemberFavoriteDTO favorite, List<AptRecommendDTO> aptList) {
		StringBuilder sb = new StringBuilder();

		sb.append("당신은 부동산 전문가 AI로서, 사용자 선호와 입지 조건을 고려하여 아파트를 추천합니다.\n");
		sb.append("아래 사용자의 주거 선호 조건과 아파트 후보 리스트를 참고하여,\n");
		sb.append("각 아파트의 주변 환경(예: 교통, 학군, 상권, 공원, 치안 등)을 분석한 뒤,\n");
		sb.append("그에 맞는 추천 3개를 추려서 JSON 배열로 반환해 주세요.\n");
		sb.append("각 추천 항목에는 다음 정보를 포함해야 합니다:\n");
		sb.append("- aptSeq: 아파트 고유 ID\n");
		sb.append("- aptNm: 아파트 이름\n");
		sb.append("- reason: 사용자에게 추천하는 이유를 한 문장으로 작성\n\n");

		sb.append("[사용자 선호 정보]\n");
		sb.append("- 희망 지역: ").append(favorite.getPreferredLocation()).append("\n");
		sb.append("- 매매가: ").append(favorite.getBudgetMin()).append(" ~ ").append(favorite.getBudgetMax()).append(" 원\n");
		sb.append("- 월세: ").append(favorite.getRentMin()).append(" ~ ").append(favorite.getRentMax()).append(" 원\n");
		sb.append("- 주택 유형: ").append(favorite.getPreferredType()).append("\n");
		sb.append("- 최소 면적: ").append(favorite.getMinArea()).append("㎡ 이상\n");
		sb.append("- 최소 방 개수: ").append(favorite.getMinRooms()).append("개 이상\n");
		sb.append("- 출퇴근 목적지: ").append(favorite.getCommuteTarget()).append(" (").append(favorite.getCommuteTimeLimit()).append("분 이내)\n");
		sb.append("- 가족 구성: ").append(favorite.getHouseholdType()).append("\n");
		sb.append("- 선호 층수: ").append(favorite.getFloorPreference()).append("\n");
		sb.append("- 선호 건축 연도: ").append(favorite.getBuildYearPreference()).append("\n");
		sb.append("- 필수 옵션: ").append(favorite.getRequiredFeatures()).append("\n");
		sb.append("- 생활 우선순위: ").append(favorite.getLifestylePriority()).append("\n\n");

		sb.append("[추천 후보 아파트 목록]\n");
		for (AptRecommendDTO apt : aptList) {
			sb.append("- 아파트 ID: ").append(apt.getAptSeq()).append("\n");
			sb.append("  이름: ").append(apt.getAptNm()).append("\n");
			sb.append("  주소: ").append(apt.getRoadNm()).append(" ");
			if (!"0".equals(apt.getRoadNmBonbun())) sb.append(apt.getRoadNmBonbun());
			if (!"0".equals(apt.getRoadNmBubun())) sb.append("-").append(apt.getRoadNmBubun());
			sb.append("\n");
			sb.append("  위치: 위도 ").append(apt.getLat()).append(", 경도 ").append(apt.getLng()).append("\n\n");
		}

		sb.append("위 아파트 중 사용자 조건과 가장 잘 맞는 3곳을 골라주세요.\n");
		sb.append("선택한 이유는 주변 환경 정보와 사용자 조건 기반으로 작성해 주세요.\n");
		sb.append("형식 예시:\n");
		sb.append("[\n");
		sb.append("  {\n");
		sb.append("    \"aptSeq\": \"11680-291\",\n");
		sb.append("    \"aptNm\": \"래미안 리더스원\",\n");
		sb.append("    \"reason\": \"학군과 카페가 가까우며, 대중교통 접근성이 뛰어나 신혼부부에게 적합합니다.\"\n");
		sb.append("  },\n");
		sb.append("  ...\n");
		sb.append("]");

		return sb.toString();
	}



}
