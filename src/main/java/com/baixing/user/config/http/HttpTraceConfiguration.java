package com.baixing.user.config.http;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author wangao
 * @date 2018/8/16
 */
@Configuration
@ConditionalOnWebApplication
public class HttpTraceConfiguration {
    
    @ConditionalOnWebApplication(type = Type.SERVLET)
    static class ServletTraceFilterConfiguration {
        
        @Bean
        @RefreshScope
        public FilterRegistrationBean httpTraceLogFilter() {
            FilterRegistrationBean<HttpTraceLogFilter> registration = new FilterRegistrationBean<>();
            registration.setFilter(new HttpTraceLogFilter());
            registration.addUrlPatterns("/api/*");
            registration.setName("httpTraceLogFilter");
            registration.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
            return registration;
        }
        
    }
    
}
