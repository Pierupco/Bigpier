package com.pier.api.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompactPrivacy implements Filter {

	private FilterConfig mFilterConfig;

	public void init(FilterConfig pFilterConfig) {
		mFilterConfig = pFilterConfig;
	}

	public void setFilterConfig(FilterConfig pFilterConfig) {
		mFilterConfig = pFilterConfig;
	}

	public FilterConfig getFilterConfig() {
		return mFilterConfig;
	}

	public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain pChain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)pRequest;
		HttpServletResponse httpResponse = (HttpServletResponse)pResponse;
		pRequest.setCharacterEncoding("UTF-8");
		String userAgent = httpRequest.getHeader("User-Agent");
		// add IE 7
		if (userAgent != null && (userAgent.indexOf("MSIE 6") > 0 || userAgent.indexOf("MSIE 7") > 0)) {
			// header for stating "compact policy" settings (for IE6)
			httpResponse.setHeader("P3P","CP=\"NOI NID ADMa OUR BUS UNI STA\"");
		}
		
		// Not the best place fo this, but put it here so we can get ISIS reader beta to work first.
		// Hard code so we let user cache big library files such as prototype and effect, should fix this later.
		String requestURI = httpRequest.getRequestURI();
		if (requestURI != null && requestURI.endsWith(".js") && requestURI.indexOf("prototype") == -1 && requestURI.indexOf("effects") == -1) 
		{ 
			httpResponse.setHeader("Pragma","no-cache");
			httpResponse.setHeader("Cache-Control","no-store,no-cache");
			httpResponse.setDateHeader ("Expires", 0);
		}
		pChain.doFilter(httpRequest,httpResponse);
	}

	public void destroy() {
		mFilterConfig = null;
	}

}


