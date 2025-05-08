package com.spidey.openmusicapi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spidey.openmusicapi.common.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    /**
     * 认证失败处理
     * @param request 请求
     * @param response 响应
     * @param authException 认证异常
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                        ApiResponse.error(HttpServletResponse.SC_UNAUTHORIZED, "未授权的访问")));
    }

}
