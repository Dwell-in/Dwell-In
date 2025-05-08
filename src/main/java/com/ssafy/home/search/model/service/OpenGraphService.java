package com.ssafy.home.search.model.service;

import java.io.IOException;
import java.util.Map;

public interface OpenGraphService {
	Map<String, String> fetchOpenGraphData(String url) throws IOException;
}
