package filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);

        // 로그인 안 했으면 로그인 페이지로 리다이렉트
        if(session == null || session.getAttribute("loginUser") == null) {
            HttpServletResponse response = (HttpServletResponse) res;
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException { }

    @Override
    public void destroy() { }
}
