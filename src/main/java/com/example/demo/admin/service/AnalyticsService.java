package com.example.demo.admin.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AnalyticsService {
    private static final String APPLICATION_NAME = "Capstone Analytics Reporting";
    private static final String KEY_FILE_LOCATION = System.getProperty("user.dir") + "/src/main/resources/analytics/capstone-384211-b0839a23314c.json";
    private static final String VIEW_ID = "288774560";

    private AnalyticsReporting analyticsReporting;

    @PostConstruct
    public void init() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleCredentials credential = GoogleCredentials
                .fromStream(new FileInputStream(KEY_FILE_LOCATION))
                .createScoped(AnalyticsReportingScopes.all());
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credential);
        analyticsReporting = new AnalyticsReporting.Builder(httpTransport, jsonFactory, requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<Long> getVisitorCount(String startDate, String endDate) throws IOException {
        // 방문자 수 데이터를 가져오기 위한 Metrics를 설정
        Metric sessions = new Metric().setExpression("ga:sessions").setAlias("sessions");
        List<Metric> metrics = Arrays.asList(sessions);

        // 데이터의 기간(DateRange)을 설정
        DateRange dateRange = new DateRange();
        dateRange.setStartDate(startDate);
        dateRange.setEndDate(endDate);

        // 데이터를 요청하기 위한 ReportRequest 객체를 생성
        ReportRequest reportRequest = new ReportRequest().setViewId(VIEW_ID).setDateRanges(Collections.singletonList(dateRange)).setMetrics(metrics);

        // ReportRequest 객체를 요청하기 위한 GetReportsRequest 객체를 생성
        GetReportsRequest getReport = new GetReportsRequest().setReportRequests(Collections.singletonList(reportRequest));

        // GetReportsRequest 객체를 서버로 요청하고 결과값 반환
        GetReportsResponse response = analyticsReporting.reports().batchGet(getReport).execute();

        // 결과값에서 Metric 값을 가져와서 반환
        List<Report> reportList = response.getReports();
        List<Long> visitorCountList = new ArrayList<>();
        for (Report report : reportList) {
            if (report.getData() != null && report.getData().getRows() != null && !report.getData().getRows().isEmpty()) {
                List<MetricHeaderEntry> metricHeaderEntries = report.getColumnHeader().getMetricHeader().getMetricHeaderEntries();
                for (MetricHeaderEntry metricHeaderEntry : metricHeaderEntries) {
                    String metricName = metricHeaderEntry.getName();
                    if ("sessions".equalsIgnoreCase(metricName)) {
                        List<DateRangeValues> dateRangeValuesList = report.getData().getRows().get(0).getMetrics();
                        Long visitorCount = Long.parseLong(dateRangeValuesList.get(0).getValues().get(0));
                        visitorCountList.add(visitorCount);
                    }
                }
            }
        }
        return visitorCountList;
    }
}

