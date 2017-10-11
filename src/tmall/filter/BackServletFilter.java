package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class BackServletFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		String contextPath = httpServletRequest.getServletContext().getContextPath();
		String uri = httpServletRequest.getRequestURI();
		uri = StringUtils.remove(uri,contextPath);
		
		if (uri.startsWith("/admin_")) {
			String servletPath = StringUtils.substringBetween(uri,"_", "_")+"Servlet";
			String method = StringUtils.substringAfterLast(uri, "_");
			httpServletRequest.setAttribute("method", method);
			request.getRequestDispatcher("/"+servletPath).forward(httpServletRequest, httpServletResponse);
			return;
		}
		chain.doFilter(httpServletRequest, httpServletResponse);
	}

	@Override
	public void destroy() {
		
		
	}

	

}
