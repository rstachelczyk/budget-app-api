package com.rstachelczyk.budget.config;

import com.rstachelczyk.budget.interceptor.RequestIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for the application. Adds an interceptor to ensure
 * that we have a value for `X-Request-Id`.
 */
@Component
public class MvcConfigurer implements WebMvcConfigurer {

  private final RequestIdInterceptor requestIdInterceptor;

  @Autowired
  public MvcConfigurer(final RequestIdInterceptor requestIdInterceptor) {
    this.requestIdInterceptor = requestIdInterceptor;
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(requestIdInterceptor);
  }
}
