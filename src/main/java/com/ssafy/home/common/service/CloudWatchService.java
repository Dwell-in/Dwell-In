package com.ssafy.home.common.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.Dimension;
import software.amazon.awssdk.services.cloudwatch.model.GetMetricDataRequest;
import software.amazon.awssdk.services.cloudwatch.model.GetMetricDataResponse;
import software.amazon.awssdk.services.cloudwatch.model.Metric;
import software.amazon.awssdk.services.cloudwatch.model.MetricDataQuery;
import software.amazon.awssdk.services.cloudwatch.model.MetricDataResult;
import software.amazon.awssdk.services.cloudwatch.model.MetricStat;

@Service
@RequiredArgsConstructor
public class CloudWatchService {

	private final CloudWatchClient cloudWatchClient;

	private static final String NEW_INSTANCE_ID = "i-03c432da4692845df";

	// 지표 목록 (기본 EC2 지표 8개)
	private static final List<String> METRICS = List.of("CPUUtilization", "NetworkIn", "NetworkOut", "NetworkPacketsIn",
			"NetworkPacketsOut", "MetadataNoToken", "CPUCreditUsage", "CPUCreditBalance");

	public Map<String, List<Map<String, Object>>> getMetrics(String oldInstanceId, String range) {
		boolean isNewInstanceRange = range.equals("1h") || range.equals("12h");
		String instanceId = isNewInstanceRange ? NEW_INSTANCE_ID : oldInstanceId;
		CloudWatchClient client = isNewInstanceRange ? getClientForRegion("ap-northeast-2") : cloudWatchClient;
		Duration totalRange;
		switch (range) {
		case "1h":
			totalRange = Duration.ofHours(1);
			break;
		case "12h":
			totalRange = Duration.ofHours(12);
			break;
		case "1d":
			totalRange = Duration.ofDays(1);
			break;
		case "1w":
			totalRange = Duration.ofDays(7);
			break;
		default:
			throw new IllegalArgumentException("Invalid range: " + range);
		}

		int pointCount = 10;
		long seconds = totalRange.getSeconds() / pointCount;

		Instant end = Instant.now();
		Instant start = end.minus(totalRange);

		List<MetricDataQuery> queries = new ArrayList<>();
		for (int i = 0; i < METRICS.size(); i++) {
			queries.add(MetricDataQuery.builder().id("m" + i).metricStat(MetricStat.builder()
					.metric(Metric.builder().namespace("AWS/EC2").metricName(METRICS.get(i))
							.dimensions(Dimension.builder().name("InstanceId").value(instanceId).build()).build())
					.period((int) seconds).stat("Average").build()).returnData(true).build());
		}

		GetMetricDataRequest request = GetMetricDataRequest.builder().metricDataQueries(queries).startTime(start)
				.endTime(end).build();

		GetMetricDataResponse response = client.getMetricData(request);

		Map<String, List<Map<String, Object>>> result = new LinkedHashMap<>();
		for (int i = 0; i < METRICS.size(); i++) {
			MetricDataResult res = response.metricDataResults().get(i);
			List<Map<String, Object>> points = new ArrayList<>();
			for (int j = 0; j < res.timestamps().size(); j++) {
				Map<String, Object> point = new HashMap<>();
				point.put("time", res.timestamps().get(j).toString());
				point.put("value", res.values().get(j));
				points.add(point);
			}
			result.put(METRICS.get(i), points);
		}

		return result;
	}

	private CloudWatchClient getClientForRegion(String regionCode) {
		return CloudWatchClient.builder().region(Region.of(regionCode)).build();
	}
}
