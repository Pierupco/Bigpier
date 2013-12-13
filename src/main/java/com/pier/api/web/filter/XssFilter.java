package com.pier.api.web.filter;

import com.pier.support.util.WordUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

public class XssFilter implements Filter {
    protected static Logger eblogger = Logger.getLogger(XssFilter.class.getName());

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // We only check spring actions (search.action or help.action) in this filter for now. 
        String uri = request.getRequestURI();


        if (uri.endsWith("search.action") || uri.endsWith("help.action") || uri.endsWith("feedback.action") ||
                uri.endsWith("login.action") || uri.endsWith("settings.action") || uri.endsWith("newAccount.action") ||
                uri.endsWith("myProfile.action") || uri.endsWith("contactSupport.action") ||
                uri.endsWith("searchHistory.action") || uri.endsWith("savedSearch.action") ||
                uri.endsWith("runSavedSearch.action")
                ) {
            filterChain.doFilter(new RequestWrapper(request), response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        //	invalidChannelURL = MessageCodeConst.getMessageURL(MessageCodeConst.ERROR_404, true);
    }

    private static final class RequestWrapper extends HttpServletRequestWrapper {

        public RequestWrapper(HttpServletRequest servletRequest) {
            super(servletRequest);
        }

        @Override
        public String getHeader(String name) {
            // TODO Auto-generated method stub
            return super.getHeader(name);
        }

        @Override
        public Enumeration getHeaders(String name) {
            // TODO Auto-generated method stub
            return super.getHeaders(name);
        }

        @Override
        public String getParameter(String name) {
            String sanitizedValue = super.getParameter(name);
            return WordUtil.sanitizeSearchValue(sanitizedValue);
        }

        @Override
        public Map getParameterMap() {
            @SuppressWarnings("unchecked")
            Map<String, String[]> paramMap = (Map<String, String[]>) super.getParameterMap();
            if (paramMap == null || paramMap.isEmpty()) {
                return paramMap;
            }

            Map<String, String[]> sanitizedMap = new HashMap<String, String[]>();
            Iterator<String> iter = paramMap.keySet().iterator();
            while (iter.hasNext()) {
                String name = iter.next();
                sanitizedMap.put(name, this.getParameterValues(name));
            }
            return sanitizedMap;
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if (values == null) {
                return null;
            }
            int count = values.length;
            String[] sanitizedValues = new String[count];
            for (int i = 0; i < count; i++) {
                sanitizedValues[i] = WordUtil.sanitizeSearchValue(values[i]);
            }
            return sanitizedValues;
        }


        @Override
        public String getQueryString() {
            String sanitizedQueryString = super.getQueryString();
            return WordUtil.sanitizeSearchValue(sanitizedQueryString);
        }

	
	   /*
        public String getHeader(String name) {
	        String value = super.getHeader(name);
	        return WordUtil.sanitizeSearchValue(value);
	    }  */

    }


}

