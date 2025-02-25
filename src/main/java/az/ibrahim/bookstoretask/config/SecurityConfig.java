package az.ibrahim.bookstoretask.config;

import az.ibrahim.bookstoretask.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
//                        .requestMatchers("/api/v1/auth/**").permitAll()
//                        .requestMatchers("/api/v1/authors/**").permitAll()
//                        .requestMatchers("/api/v1/students").permitAll()
//                        .requestMatchers("/api/v1/books").permitAll()
//                        .requestMatchers("/api/v1/books/publish-book").hasAuthority("AUTHOR")
//                        .requestMatchers("/api/v1/books/{bookId}").hasAuthority("AUTHOR")
//                        .requestMatchers("/api/v1/books/{bookId}/read-book").hasAuthority("STUDENT")
//                        .requestMatchers("/api/v1/books/{bookId}/readers").permitAll()
//                        .requestMatchers("/api/v1/students").permitAll()
//                        .requestMatchers("/api/v1/students/my-currently-reading").hasAuthority("STUDENT")
//                        .requestMatchers("/api/v1/students/{studentId}/currently-reading").permitAll()
//                        .requestMatchers("/api/v1/students/subscribe/**").hasAuthority("STUDENT")
//                        .requestMatchers("/api/v1/students/unsubscribe/**").hasAuthority("STUDENT")
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


}
