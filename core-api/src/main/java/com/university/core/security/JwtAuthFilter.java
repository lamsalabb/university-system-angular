package com.university.core.security;

import com.university.core.security.permissions.PermissionLoader;
import com.university.core.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final PermissionLoader permissionLoader;

    public JwtAuthFilter(JwtUtil jwtUtil, PermissionLoader permissionLoader) {
        this.jwtUtil = jwtUtil;
        this.permissionLoader = permissionLoader;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {//null check
            String token = authHeader.substring(7);//"Bearer length, token comes right after"


            try {
                Claims claims = jwtUtil.validateToken(token);
                String userId = claims.getSubject();
                String role = claims.get("role", String.class);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

                String method = request.getMethod();
                String path = request.getRequestURI();

                if (!permissionLoader.isAllowed(role, method, path)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    SecurityContextHolder.clearContext();
                    return;
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
