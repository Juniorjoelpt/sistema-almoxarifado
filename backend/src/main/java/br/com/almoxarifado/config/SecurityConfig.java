package br.com.almoxarifado.config;
import org.springframework.security.config.Customizer;
import br.com.almoxarifado.security.JwtAuthenticationFilter;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @PostConstruct
    public void init() {
        System.out.println(">>> SECURITY CONFIG CARREGADA <<<");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(
                    "/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/logos/**"
                                )
                    .permitAll()

                   .requestMatchers(
        "/api/usuarios/me",
        "/api/usuarios/minha-senha"
)
        .hasAnyAuthority(
    "ROLE_SUPER_ADMIN",
    "ROLE_ADMIN_PREFEITURA",
    "ROLE_USUARIO"
)

                       .requestMatchers("/api/usuarios/**")
                        .hasAuthority("ROLE_SUPER_ADMIN")
                    
                    
                    .requestMatchers("/api/prefeituras/**")
                    .hasAuthority("ROLE_SUPER_ADMIN")


                    
                    
                   
                    
                    
                    .anyRequest()
                    .authenticated()
            )
                

            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            )
                
                ;
        

        return http.build();
    }
}
