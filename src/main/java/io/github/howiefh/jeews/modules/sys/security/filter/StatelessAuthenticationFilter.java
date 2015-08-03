/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.security.filter;

import io.github.howiefh.jeews.modules.sys.security.token.JsonWebToken;
import io.github.howiefh.jeews.modules.sys.security.token.TokenType;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 *  @author howiefh
 */
public class StatelessAuthenticationFilter extends AuthenticatingFilter {

    protected static final String DEFAULT_LOGIN_URL= "/login";
    protected static final String DEFAULT_SIGNUP_URL= "/signup";
    protected static final String DEFAULT_FORGET_PASSWORD_URL= "/signup";
    /**
     * This class's private logger.
     */
    private static final Logger log = LoggerFactory.getLogger(StatelessAuthenticationFilter.class);

    /**
     * HTTP Authorization 头部, 值为<code>Authorization</code>
     */
    protected static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * HTTP Authentication 头部, 值为<code>WWW-Authenticate</code>
     */
    protected static final String AUTHENTICATE_HEADER = "WWW-Authenticate";

    /**
     * 应用名将出现在查询令牌时，默认是<code>application</code>。
     * 可以通过{@link #setApplicationName(String) setApplicationName} 方法覆盖
     */
    private String applicationName = "application";

    /**
     * The authcScheme 在<code>Authorization</code>头部, 默认是<code>Bearer</code>
     */
    private String authcScheme = TokenType.BEARER_AUTH;

    /**
     * The authzScheme 在<code>Authorization</code>头部, 默认是<code>Bearer</code>
     */
    private String authzScheme = TokenType.BEARER_AUTH;

    private Set<String> ignoreUrls = new HashSet<String>();

    {
    	ignoreUrls.add(DEFAULT_LOGIN_URL);
    	ignoreUrls.add(DEFAULT_SIGNUP_URL);
    	ignoreUrls.add(DEFAULT_FORGET_PASSWORD_URL);
    }
    /**
     * 在响应的头部<b><code>WWW-Authenticate</code></b>使用这个名称
     * <p/>
     * Per RFC 2617, 当终端用户需要认证时在头部显示这个名称。除非通过
     * {@link #setApplicationName(String) setApplicationName(String)} 方法设置, 默认值是'application'.
     * <p/>
     * 请通过{@link #setApplicationName(String) setApplicationName(String)} 方法查看如何使用
     *
     * @return 在 'WWW-Authenticate' 头部显示的名称.
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * 设置响应<b><code>WWW-Authenticate</code></b> 头部 .
     * <p/>
     * Per RFC 2617, 当终端用户需要认证时在头部显示这个名称。除非通过
     * {@link #setApplicationName(String) setApplicationName(String)} 方法设置, 默认值是'application'.
     * <p/>
     * 例如, 设置这个属性为 <b><code>Awesome Webapp</code></b> 响应头部将会出现如下内容
     * <p/>
     * <code>WWW-Authenticate: Bearer realm=&quot;<b>Awesome Webapp</b>&quot;</code>
     * <p/>
     *
     * @param applicationName 在 'WWW-Authenticate' 头部显示的名称.
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * 返回HTTP <b><code>Authorization</code></b>头部中此过滤器会用来判断是否是登录请求的值
     * <p/>
     * 除非被 {@link #setAuthzScheme(String) setAuthzScheme(String)} 方法覆盖，否则默认值是 <code>Bearer</code>.
     *
     * @return 返回HTTP <b><code>Authorization</code></b>头部中此过滤器会用来判断是否是登录请求的值
     */
    public String getAuthzScheme() {
        return authzScheme;
    }

    /**
     * 设置HTTP<b><code>Authorization</code></b>头部中此过滤器会用来判断是否是登录请求的值
     * <p/>
     * 除非被 {@link #setAuthzScheme(String) setAuthzScheme(String)} 方法覆盖，否则默认值是 <code>Bearer</code>.
     *
     * @param authzScheme 返回HTTP <b><code>Authorization</code></b>头部中此过滤器会用来判断是否是登录请求的值
     */
    public void setAuthzScheme(String authzScheme) {
        this.authzScheme = authzScheme;
    }

    /**
     * 返回HTTP <b><code>WWW-Authenticate</code></b>头部将被此过滤器用来发送查询令牌的响应的值。默认值是 <code>Bearer</code>
     *
     * @return 返回HTTP <b><code>WWW-Authenticate</code></b>头部将被此过滤器用来发送查询令牌的响应的值
     * @see #sendChallenge
     */
    public String getAuthcScheme() {
        return authcScheme;
    }

    /**
     * 设置HTTP <b><code>WWW-Authenticate</code></b>头部将被此过滤器用来发送查询令牌的响应的值。默认值是 <code>Bearer</code>
     *
     * @param authcScheme HTTP <b><code>WWW-Authenticate</code></b>头部将被此过滤器用来发送查询令牌的响应的值
     * @see #sendChallenge
     */
    public void setAuthcScheme(String authcScheme) {
        this.authcScheme = authcScheme;
    }

	/**
     * 处理认证的请求、发送查询口令的响应
     *
     * @param request  请求
     * @param response  响应
     * @return 当请求应该被处理时返回 true。请求不应该被继续处理时返回 false
     */
    @Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean loggedIn = false; //false by default or we wouldn't be in this method
        if (isTokenLoginAttempt(request, response)) {
            loggedIn = executeLogin(request, response);
        }
        if (!loggedIn && canAccessPostRequest(request, response)) {
            // let login controller to handle the request
            return true;
		}
        if (!loggedIn) {
            sendChallenge(request, response);
        }
        return loggedIn;
    }

    /**
     * 判断是否是登录请求
     * <p/>
     * 如果请求中包含头部 {@link #AUTHORIZATION_HEADER AUTHORIZATION_HEADER}，并且其值中包含
     * {@link #getAuthzScheme() authzScheme}将返回true，否则返回false
     * @param request  请求
     * @param response  响应
     * @return 是登录请求返回true，否则返回false
     */
    protected boolean isTokenLoginAttempt(ServletRequest request, ServletResponse response) {
        String authzHeader = getAuthzHeader(request);
        return authzHeader != null && isTokenLoginAttempt(authzHeader);
    }

    protected boolean canAccessPostRequest(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String url = WebUtils.getPathWithinApplication(httpRequest);
        return (request instanceof HttpServletRequest) && httpRequest.getMethod().equalsIgnoreCase(POST_METHOD)
        		&& ignoreUrls.contains(url);
    }
    /**
     * 委派给 {@link #isTokenLoginAttempt(javax.servlet.ServletRequest, javax.servlet.ServletResponse) isTokenLoginAttempt}.
     */
    @Override
    protected final boolean isLoginRequest(ServletRequest request, ServletResponse response) {
        return this.isTokenLoginAttempt(request, response);
    }

    /**
     * 返回 {@link #AUTHORIZATION_HEADER AUTHORIZATION_HEADER}
     * <p/>
     * 将ServletRequest 转型为HttpServletRequest 返回 {@link #AUTHORIZATION_HEADER AUTHORIZATION_HEADER}
     *
     * @param request 请求
     * @return <code>Authorization</code> 头部的值
     */
    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(AUTHORIZATION_HEADER);
    }

    /**
     * 当<code>authzHeader</code>的值的开始子串和 {@link #getAuthzScheme() authzScheme}匹配返回true
     * <p/>
     * <code>String authzScheme = getAuthzScheme().toLowerCase();<br/>
     * return authzHeader.toLowerCase().startsWith(authzScheme);</code>
     *
     * @param authzHeader 'Authorization' 头部的值
     * @return 当authzHeader 和 {@link #getAuthzScheme() authzScheme}匹配时返回true
     */
    protected boolean isTokenLoginAttempt(String authzHeader) {
        //SHIRO-415: use English Locale:
        String authzScheme = getAuthzScheme().toLowerCase(Locale.ENGLISH);
        return authzHeader.toLowerCase(Locale.ENGLISH).startsWith(authzScheme);
    }

    /**
     * Builds the challenge for authorization by setting a HTTP <code>401</code> (Unauthorized) status as well as the
     * response's {@link #AUTHENTICATE_HEADER AUTHENTICATE_HEADER}.
     * 返回401状态码，设置查询口令的头部
     * <p/>
     * 结构如下
     * <p/>
     * <code>{@link #getAuthcScheme() getAuthcScheme()} + " realm=\"" + {@link #getApplicationName() getApplicationName()} + "\"";</code>
     *
     * @param request 请求
     * @param response 响应
     * @return false
     */
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("Authentication required: sending 401 Authentication challenge response.");
        }
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String authcHeader = getAuthcScheme() + " realm=\"" + getApplicationName() + "\"";
        httpResponse.setHeader(AUTHENTICATE_HEADER, authcHeader);
        return false;
    }

    /**
     * 创建 AuthenticationToken
     * <p/>
     *
     * @param request 请求
     * @param response 响应
     * @return AuthenticationToken
     */
    @Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String authorizationHeader = getAuthzHeader(request);
        if (authorizationHeader == null || authorizationHeader.length() == 0) {
            // Create an empty authentication token since there is no
            // Authorization header.
            return new JsonWebToken("");
        }

        if (log.isDebugEnabled()) {
            log.debug("Attempting to execute login with headers [" + authorizationHeader + "]");
        }

        String[] authTokens = authorizationHeader.split(" ");
        if (authTokens == null || authTokens.length < 2) {
            return new JsonWebToken("");
        }
        return new JsonWebToken(authTokens[1]);
    }
}