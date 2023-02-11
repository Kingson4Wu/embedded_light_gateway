package com.kxw.light.gateway.web.block;

import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

public class BlockInterceptor extends WebRequestHandlerInterceptorAdapter {
    /**
     * Create a new WebRequestHandlerInterceptorAdapter for the given WebRequestInterceptor.
     *
     * @param requestInterceptor the WebRequestInterceptor to wrap
     */
    public BlockInterceptor(WebRequestInterceptor requestInterceptor) {
        super(requestInterceptor);
    }
}
