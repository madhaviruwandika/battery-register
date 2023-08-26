package com.powerLedger.registry.filter;

import com.powerLedger.registry.filter.CorrelationIdFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
public class CorrelationIdFilterTest {

    @Autowired
    CorrelationIdFilter correlationIdFilter;

    HttpServletRequest request;
    HttpServletResponse response;
    FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
    }

    @Test
    public void testGenerateCorrelationIdIfNotExist() throws ServletException, IOException {
        assertNull(request.getHeader("correlation-id"));
        correlationIdFilter.doFilter(request, response, filterChain);
        assertNotNull(MDC.get("correlation-id"));
    }

    @Test
    public void testAppendCorrelationIdToMDC() throws ServletException, IOException {
        String uuid = UUID.randomUUID().toString();
        when(request.getHeader("correlation-id")).thenReturn(uuid);
        correlationIdFilter.doFilter(request, response, filterChain);
        assertEquals(MDC.get("correlation-id"), uuid);
    }
}