package de.wi2020sebgruppe4.KinoTicketRes.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements Filter{

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {auth.inMemoryAuthentication()
        .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
        .and()
        .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
		        .csrf().disable()
		        .authorizeRequests()
		        .antMatchers("/admin/**").hasRole("ADMIN")
		        .antMatchers("/login*").permitAll()
		        .antMatchers("/registration*").permitAll()
		        .and()
		        .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .httpBasic()
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } 

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
                "https://kinoticketres.web.app/",
        		"https://localhost/",
        		"https://localhost:3000/",
        		"https://localhost:3001/",
        		"https://localhost:3002/",
        		"http://localhost/",
        		"http://localhost:3000/",
				"http://localhost:3001/",
				"http://localhost:3002/",
                "http://localhost:4200/"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        //res.setHeader("Access-Control-Allow-Origin", "https://kinoticketres.web.app");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, x-requested-with, Cache-Control");
        chain.doFilter(request, res);
    }
    @Override
    public void destroy() {
    }
} 
