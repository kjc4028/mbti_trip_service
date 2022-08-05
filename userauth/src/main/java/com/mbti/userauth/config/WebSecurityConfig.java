package com.mbti.userauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mbti.userauth.user.jwt.JwtAccessDeniedHandler;
import com.mbti.userauth.user.jwt.JwtAuthenticationEntryPoint;
import com.mbti.userauth.user.jwt.JwtSecurityConfig;
import com.mbti.userauth.user.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public WebSecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable()
        // .authorizeRequests()
        // .anyRequest().permitAll() //우선 개발기간동안 모든 요청 인가처리
        // ;

        http
            .csrf().disable()
            .exceptionHandling()       
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)                
            .and()
            .formLogin().disable()
            .authorizeRequests()
            .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            //.antMatchers("/","/**").permitAll()
            .antMatchers("/user/signup","/user/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(new JwtSecurityConfig(tokenProvider));                

    }
}
