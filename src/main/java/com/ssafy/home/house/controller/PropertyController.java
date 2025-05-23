package com.ssafy.home.house.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.house.model.dto.PropertyDTO;
import com.ssafy.home.house.model.dto.PropertySearchCondition;
import com.ssafy.home.house.model.service.PropertyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController implements RestControllerHelper {

	private final PropertyService service;

	@GetMapping
	public ResponseEntity<?> propertyList(PropertySearchCondition condition) {
		List<PropertyDTO> list = service.findPropertyList(condition);
		int total = service.findPropertyCount(condition);
		return handleSuccess(Map.of("data", list, "totalCount", total));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> propertyDetail(@PathVariable Long id) {
		return handleSuccess(Map.of("data", service.findPropertyById(id)));
	}

	@PostMapping
	public ResponseEntity<?> register(@ModelAttribute PropertyDTO dto,
	                                  @RequestParam(required = false) MultipartFile img) {
		System.out.println(img);
		try {
	        if (img != null && !img.isEmpty()) {
	            dto.setPicture(img.getBytes());
	        }
	        Long id = service.addProperty(dto);
	        return handleSuccess(Map.of("id", id));
	    } catch (IOException e) {
	        e.printStackTrace();
	        return handleFail(e);
	    }
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody PropertyDTO dto) {
		service.modifyProperty(dto);
		return handleSuccess("updated");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.removeProperty(id);
		return handleSuccess("deleted");
	}
}
