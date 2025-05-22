package com.ssafy.home.house.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparePriceRequestDTO {
	private String promptType;
	private List<CompareSeqNmRequestDTO> aptList;
}
