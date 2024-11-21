package com.kass.backendtodo.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.kass.backendtodo.security.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {


    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);

        if(header == null || !header.startsWith(PREFIX_TOKEN) ){
            chain.doFilter(request,response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN,"");
        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            String authoritiesClaims = claims.get("role", String.class);

            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authoritiesClaims);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(grantedAuthority));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request,response);

        }catch (JwtException e){
            Map<String,String> body = new HashMap<>();
            body.put("message", "The token is invalid!" );
            body.put("error", e.getMessage());

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setContentType(CONTENT_TYPE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

        }

    }
}
