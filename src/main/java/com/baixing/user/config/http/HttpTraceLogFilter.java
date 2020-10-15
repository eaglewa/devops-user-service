package com.baixing.user.config.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

/**
 * @author wangao
 * @date 2018/8/28
 */
@Slf4j
public class HttpTraceLogFilter extends OncePerRequestFilter {
    
    public static final ObjectMapper mapper = new ObjectMapper();
    
    private static final int REQUEST_LATENCY_LIMIT = 3000;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    
    private static final String NEED_TRACE_PATH_PREFIX = "/api";
    private static final String IGNORE_CONTENT_TYPE = "multipart/form-data";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        try {
            filterChain.doFilter(request, response);
            status = response.getStatus();
        } finally {
            Map<String, Object> tags = Maps.newConcurrentMap();
            tags.put("status", status);
            tags.put("path", request.getRequestURI());
            long endTime = System.currentTimeMillis();
            long latency = endTime - startTime;
            //1. 记录日志
            String trace = getTrace(request, response, startTime, endTime);
            if (status >= HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                log.error(Markers.appendEntries(tags), trace);
            } else {
                if (status >= HttpStatus.BAD_REQUEST.value() || latency > REQUEST_LATENCY_LIMIT) {
                    log.warn(Markers.appendEntries(tags), trace);
                } else {
                    log.info(Markers.appendEntries(tags), trace);
                }
            }
            //3. 异常告警
            ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
            Objects.requireNonNull(wrapper).copyBodyToResponse();
        }
    }
    
    private String getTrace(HttpServletRequest request, HttpServletResponse response, long startTime, long endTime)
            throws JsonProcessingException {
        String startTimeFormat = DateFormatUtils.format(startTime, DATE_FORMAT);
        String endTimeFormat = DateFormatUtils.format(endTime, DATE_FORMAT);
        HttpTraceLog traceLog = new HttpTraceLog();
        traceLog.setPath(request.getRequestURI());
        traceLog.setMethod(request.getMethod());
        traceLog.setTimeTaken(endTime - startTime);
        traceLog.setStartTime(startTimeFormat);
        traceLog.setEndTime(endTimeFormat);
        traceLog.setParameterMap(request.getParameterMap());
        traceLog.setStatus(response.getStatus());
        traceLog.setRequestBody(getRequestBody(request));
        traceLog.setResponseBody(getResponseBody(response));
        String trace = mapper.writeValueAsString(traceLog);
        trace = StringEscapeUtils.unescapeEcmaScript(trace);
        return trace;
    }
    
    private String getRequestBody(HttpServletRequest request) {
        String requestBody = "";
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            try {
                requestBody = IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
            } catch (IOException e) {
                log.error("read requestBody error: {}", e);
            }
        }
        return requestBody;
    }
    
    private String getResponseBody(HttpServletResponse response) {
        String responseBody = "";
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            try {
                responseBody = IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
            } catch (IOException e) {
                log.error("read responseBody error: {}", e);
            }
        }
        return responseBody;
    }
    
    @Data
    private static class HttpTraceLog {
        private String path;
        private Map<String, String[]> parameterMap;
        private String method;
        private Long timeTaken;
        private String startTime;
        private String endTime;
        private Integer status;
        private String requestBody;
        private String responseBody;
    }
    
    
    @Data
    public static class HttpTraceEvent {
        private HttpTraceLog log;
        
    }
    
    
}
