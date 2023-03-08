package cl.factorit.cursos.datasource.configuration;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	//inyectamos tokenManager
	private final TokenManager tokenManager;
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/swagger-config", "/users/register", "/users/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new OncePerRequestFilter() {
                	
                	// virificamos si el valor de la cabezera es o no nulo o si no comienza con la convencion "Bearer" no va generar la autentificacion
                    @Override
                    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                            filterChain.doFilter(request, response);
                            return;
                        }
                        // si hay un JWT y comienza con bearer, este se valida con el token manager, pero primero le limpiamos la convecion "Bearer" y luego lo validamos  
                        authHeader = authHeader.replace("Bearer ", "");
                        if (!tokenManager.validateJwtToken(authHeader)) {
                            filterChain.doFilter(request, response);
                            return;
                        }
                        // si el JWT es valido le pasamos el getContext a UsernamePasswordAuthenticationToken
                        SecurityContext sc = SecurityContextHolder.getContext(); 
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(tokenManager.getSubject(authHeader), null, Collections.emptyList());
                        sc.setAuthentication(auth);
                        filterChain.doFilter(request, response);
                    }
                }, BasicAuthenticationFilter.class)
                

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// STATELESS no guarda ningun tipo de datp o cookie 
                .and()
                .cors()
                .and()
                .csrf().disable();
        return http.build();
    }
	
    
    // nos permite encriptar lo utilzamos para la password
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
	
}