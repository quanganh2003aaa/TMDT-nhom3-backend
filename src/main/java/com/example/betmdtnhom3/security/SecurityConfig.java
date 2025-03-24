package com.example.betmdtnhom3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    CustomJwtFillter customJwtFillter;
    @Autowired
    CustomJwtDecoder customJwtDecoder;

    private final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/login", "/api/auth/introspect",
            "/api/blog/id/**","/api/blog/getAll",
            "/api/brand/getAll",
            "/api/category/getAll",
            "/api/comment/getAll","/api/comment/blog/**",
            "/api/delivery/getAll",
            "/api/store/getAll",
            "/api/product/getIndex","/api/product/id/**","/api/product/getProduct","/api/product/getCategory","/api/product/getBrand",
            "/api/rate/product/**",
            "/api/user/create","/api/user/login","/api/user/otp","/api/user/newPassword",
            "/api/voucher/getAll",
            "/vnpay/vn-pay-callback"
    };

    private final String[] ADMIN_ENDPOINTS = {
            "/api/blog/create","/api/blog/update/**","/api/blog/delete/**",
            "/api/brand/create","/api/brand/update/**","/api/brand/delete/**","/api/brand/id/**",
            "/api/category/create","/api/category/update/**","/api/category/delete/**","/api/category/id/**",
            "/api/delivery/create","/api/delivery/update/**","/api/delivery/delete/**","/api/delivery/id/**",
            "/api/store/create","/api/store/update/**","/api/store/delete/**","/api/store/id/**",
            "/api/order/getAll","/api/order/confirm/**","/api/order/deliver/**","/api/order/delete/**","/api/order/getOrderSearch","/api/order/count",
            "/api/refund/getAll","/api/refund/confirm/**","/api/refund/deliver","/api/refund/success/**","/api/refund/reject/**","/api/refund/delete/**",
            "/api/product/create","/api/product/update/**","/api/product/delete/**","/api/product/id/**","/api/product/admin/getAll","/api/product/count",
            "/revenue/**",
            "/api/user/createAdmin","/api/user/updateAdmin/**","/api/user/delete/**","/api/user/delete/**","/api/user/list/**","/api/user/count",
            "/api/voucher/id/**","/api/voucher/create","/api/voucher/update/**","/api/voucher/delete/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry.requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(ADMIN_ENDPOINTS).hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)));
        http.addFilterBefore(customJwtFillter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
