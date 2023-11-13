package com.ionix.assessment.taskmanager.security.configuration;

import jakarta.servlet.http.HttpServletRequest;

public class JWTokenUtil {
    
	public static final String SECRET_KEY = "xcJY4fSALFl2uM5MVAWGQj/2R7xOiwCCya+03OGxGor0gzM+ttmHHpTziBom8Xxv";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long accessTokenValidity = 60*60*1000;

    public static String resolveToken(final HttpServletRequest request) {
        String authHeader = request.getHeader(TOKEN_HEADER);
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            return authHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
