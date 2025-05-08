package com.ssafy.home.common;

import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface RestControllerHelper {
    default ResponseEntity<?> handleSuccess(Object data){
        return handleSuccess(data, HttpStatus.OK);
    }
    
    default ResponseEntity<?> handleFail(Exception e){
        return handleFail(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    default ResponseEntity<?> handleSuccess(Object data, HttpStatus status){
        Map<String, Object> map = Map.of("status", "SUCCESS","data", data);
        return ResponseEntity.status(status).body(map);
    }
    
    default ResponseEntity<?> handleFail(Exception e, HttpStatus status){
        e.printStackTrace();
        Map<String, Object> map = Map.of("status", "FAIL","error", e.getMessage());
        return ResponseEntity.status(status).body(map);
    }
}
