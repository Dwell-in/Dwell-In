package com.ssafy.home.ai.prompt;

import com.ssafy.home.house.model.dto.CompareDetailRequestDTO;
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
}
