package com.lsh.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsh.vhr.model.RespBean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class VerificationCodeFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if ("POST".equals(req.getMethod()) && "/doLogin".equals(req.getServletPath())) {
            //登陆请求
            String code = req.getParameter("code");
            String verify_code = (String) req.getSession().getAttribute("verify_code");
            if (code == null || "".equals(code) || !code.equalsIgnoreCase(verify_code)) {
                //验证码不正确
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString(RespBean.error("验证码填写错误！")));
                out.flush();
                out.close();
                return;

            } else {
                //放行
                filterChain.doFilter(req, resp);
            }

        } else {
            //放行
            filterChain.doFilter(req, resp);
        }

    }
}
