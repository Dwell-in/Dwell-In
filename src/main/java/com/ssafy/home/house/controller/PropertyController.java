package com.ssafy.home.house.controller;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.house.model.dto.PropertyDTO;
import com.ssafy.home.house.model.dto.PropertySearchCondition;
import com.ssafy.home.house.model.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> register(@RequestBody PropertyDTO dto) {
        Long id = service.addProperty(dto);
        return handleSuccess(Map.of("id", id));
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
