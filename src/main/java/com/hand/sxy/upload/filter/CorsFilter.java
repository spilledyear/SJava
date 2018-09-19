/**
 * @author spilledyear
 * @date 2017/11/13 23:33
 *
 * 跨域处理
 */

package com.hand.sxy.upload.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "filter1", urlPatterns="/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        initParams={@WebInitParam(name="account",value="1234"),@WebInitParam(name="hotusm",value="1234")}
)
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;

        // 指定允许其他域名访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 响应类型
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        // 响应头设置
        response.setHeader("Access-Control-Allow-Headers", "token,Content-Type,Access-Control-Allow-Origin,Access-Control-Allow-Methods,Access-Control-Max-Age,authorization");
//        response.setHeader("Access-Control-Max-Age", "3600");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
