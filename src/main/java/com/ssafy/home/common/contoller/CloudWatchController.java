package com.ssafy.home.common.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.common.service.CloudWatchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cloudwatch")
public class CloudWatchController implements RestControllerHelper {

    private final CloudWatchService cloudWatchService;

    @GetMapping("/metrics")
    public ResponseEntity<?> getMetrics(
        @RequestParam String instanceId,
        @RequestParam(defaultValue = "1h") String range) {
        try {
            return handleSuccess(cloudWatchService.getMetrics(instanceId, range));
        } catch (Exception e) {
            return handleFail(e);
        }
    }
}

