package com.rstachelczyk.budget.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor that ensures that all requests have a valid UUID in the `X-Request-Id` header.
 */
@Component
@NoArgsConstructor
public class RequestIdInterceptor implements HandlerInterceptor {
  public static final String REQUEST_ID = "X-Request-Id";
  public static final String MDC_KEY = "requestId";
  public static final Pattern UUID_PATTERN = Pattern.compile(
          "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

  @Override
  public boolean preHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler
  ) {
    String requestIdHeader = sanitizeHeader(request.getHeader(REQUEST_ID));

    String mdcValue;
    if (StringUtils.isNotBlank(requestIdHeader)
            && requestIdHeader.matches(UUID_PATTERN.pattern())) {
      mdcValue = requestIdHeader;
      response.setHeader(REQUEST_ID, requestIdHeader);
    } else {
      mdcValue = UUID.randomUUID().toString();
      response.addHeader(REQUEST_ID, mdcValue);
    }

    MDC.put(MDC_KEY, mdcValue);
    return true;
  }

  @Override
  public void afterCompletion(
       HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex
  ) {
    MDC.clear();
  }

  private String sanitizeHeader(String header) {
    if (header == null) return null;
    return header.replace("\r", "").replace("\n", "");
  }
}
