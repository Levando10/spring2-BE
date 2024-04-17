package com.example.backendglasses.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         http
                .csrf(AbstractHttpConfigurer :: disable)
                 .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                 .authorizeHttpRequests(requests -> {
                     requests
                             .requestMatchers(("/account/register"),
                                     ("/account/login"))
                             .permitAll()
                             .requestMatchers(HttpMethod.DELETE,("/product/")).hasRole("ADMIN")
                             .requestMatchers(HttpMethod.PUT,("/product/")).hasRole("ADMIN")
                             .requestMatchers(HttpMethod.POST,("/product/")).hasRole("ADMIN")
                             .requestMatchers(HttpMethod.GET,("/admin/**")).hasRole("ADMIN")
                             .requestMatchers(HttpMethod.POST,("/account/changPassword")).permitAll()
                             .requestMatchers(HttpMethod.GET,("/account/information/{idAccount}")).permitAll()
//
                             .requestMatchers(HttpMethod.POST,("/cart/addToCartHome")).hasAnyRole("USER", "ADMIN")
                             .requestMatchers(HttpMethod.GET,("/cart/**")).hasAnyRole("USER", "ADMIN")
                             .requestMatchers(HttpMethod.DELETE,("/cart/**")).hasAnyRole("USER", "ADMIN")
                             .requestMatchers(HttpMethod.POST,("/payment/**")).hasAnyRole("USER", "ADMIN")
                             .requestMatchers(HttpMethod.GET,("/payment/**")).hasAnyRole("USER", "ADMIN")
//                             .anyRequest().authenticated()

                             .anyRequest().permitAll()

                     ;
                 })
                 ;
        return  http.build();
    }
}
