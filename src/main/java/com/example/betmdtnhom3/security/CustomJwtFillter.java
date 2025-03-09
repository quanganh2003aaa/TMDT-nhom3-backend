package com.example.betmdtnhom3.security;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component
public class CustomJwtFillter extends OncePerRequestFilter {
    @Value("${jwt.signerKey}")
    protected String SIGN_KEY;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);

        if (token != null) {
            try {
                JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());
                SignedJWT signedJWT = SignedJWT.parse(token);
                boolean isValid = signedJWT.verify(verifier);
                Date expiriTime = signedJWT.getJWTClaimsSet().getExpirationTime();

                if (isValid && expiriTime.after(new Date())) {
                    String role = getRoleFromToken(signedJWT);

                    String username = signedJWT.getJWTClaimsSet().getSubject();
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority(role)));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request){
        String token = request.getHeader("Author");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }

    private String getRoleFromToken(SignedJWT signedJWT) throws ParseException {
        return signedJWT.getJWTClaimsSet().getStringClaim("scope");
    }
}
