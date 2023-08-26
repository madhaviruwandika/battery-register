package com.powerLedger.registry.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Component
public class CorrelationIdFilter implements Filter {
    private static String CORRELATION_ID = "correlation-id";
    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {

            HttpServletRequest req = (HttpServletRequest) request;
            String requestCid = req.getHeader(CORRELATION_ID);

            if(!isValidUUID(requestCid)) {
                requestCid = UUID.randomUUID().toString();
                log.warn("Valid correlation id was not found. new id is generated. id - {} ", requestCid);
            }

            // add cid to the MDC
            MDC.put(CORRELATION_ID, requestCid);
        }

        try {
            // call filter(s) upstream for the real processing of the request
            chain.doFilter(request, response);
        } finally {
            // it's important to always clean the cid from the MDC,
            // this Thread goes to the pool but it's loglines would still contain the cid.
            MDC.remove("CID");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


    public static boolean isValidUUID(String str) {
        if (str == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(str).matches();
    }
}
