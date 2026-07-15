package br.com.almoxarifado.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.
UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.
SimpleGrantedAuthority;

import org.springframework.security.core.context.
SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(
            JwtService jwtService) {

        this.jwtService = jwtService;
    }

   @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

	System.out.println(
    ">>> " +
    request.getMethod() +
    " " +
    request.getRequestURI()
		);


    String auth =
            request.getHeader("Authorization");

    if (auth == null ||
            !auth.startsWith("Bearer")) {

        filterChain.doFilter(request, response);
        return;
    }

    try {

        String token =
                auth.substring(7);

        if (jwtService.tokenValido(token)) {

            String username =
                    jwtService.extrairUsername(token);

            String role =
                    jwtService
                            .extrairClaims(token)
                            .get("role", String.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(
                                    new SimpleGrantedAuthority(role)
                            )
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);
        }

    } catch (Exception e) {

        System.out.println(
                "JWT inválido ou expirado: "
                        + e.getMessage());

        SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
}
}
