package com.ssafy.home.search.model.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenGraphServiceImpl implements OpenGraphService {

	@Override
	public Map<String, String> fetchOpenGraphData(String url) throws IOException {
		// 1. 메인 페이지 HTML 파싱
	    Document mainDoc = Jsoup.connect(url)
	            .userAgent("Mozilla/5.0")
	            .timeout(5000)
	            .get();

	    // 2. og 태그 추출
	    Map<String, String> ogData = new HashMap<>();
	    for (Element meta : mainDoc.select("meta[property^=og:]")) {
	        ogData.put(meta.attr("property"), meta.attr("content"));
	    }

	    // 3. og 태그가 없고 iframe이 있을 경우, iframe 내부에서 추출
	    if (ogData.isEmpty()) {
	        Element iframe = mainDoc.selectFirst("iframe");
	        if (iframe != null) {
	            String iframeSrc = iframe.absUrl("src");
	            if (!iframeSrc.isEmpty()) {
	                Document iframeDoc = Jsoup.connect(iframeSrc)
	                        .userAgent("Mozilla/5.0")
	                        .timeout(5000)
	                        .get();

	                for (Element meta : iframeDoc.select("meta[property^=og:]")) {
	                    ogData.put(meta.attr("property"), meta.attr("content"));
	                }
	            }
	        }
	    }

	    return ogData;
	}

}
