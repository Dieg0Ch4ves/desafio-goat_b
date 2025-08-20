    package com.diego.desafio_goat_b.config;

    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;

    import java.util.Arrays;

    @RequiredArgsConstructor
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        private final SecurityFilter securityFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            return httpSecurity
                    .headers(headers -> {
                        headers.frameOptions(frameOptions -> frameOptions.disable());
                        headers.addHeaderWriter((request, response) -> {
                            response.addHeader("X-Custom-Header", "valor");
                        });
                    })
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authorize -> authorize

                            .requestMatchers(HttpMethod.GET, "/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/**").permitAll()

                            .anyRequest().authenticated())
                    .cors(cors -> cors
                            .configurationSource(request -> {
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                                config.addAllowedHeader("*");
                                config.addAllowedMethod("*");
                                return config;
                            }))
                    .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    }