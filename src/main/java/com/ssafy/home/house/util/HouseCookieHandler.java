package com.ssafy.home.house.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ssafy.home.common.CookieHandler;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HouseCookieHandler {

    private final CookieHandler cookieHandler;

    public void updateRecentViewedCookie(String aptSeq, String recentViewed, HttpServletResponse res) {
        List<String> list;

        if (recentViewed == null) {
            list = new ArrayList<>();
        } else {
            String decoded = URLDecoder.decode(recentViewed, StandardCharsets.UTF_8);
            list = new ArrayList<>(Arrays.asList(decoded.split(",")));
        }

        list.remove(aptSeq);
        list.add(0, aptSeq);

        if (list.size() > 10) {
            list = list.subList(0, 10);
        }

        String value = URLEncoder.encode(String.join(",", list), StandardCharsets.UTF_8);
        cookieHandler.addCookie(res, "recentViewed", value, 60 * 60 * 24, false);
    }
}
