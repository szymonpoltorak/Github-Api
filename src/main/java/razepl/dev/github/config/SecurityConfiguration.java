package razepl.dev.github.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import razepl.dev.github.config.interfaces.SecurityConfigInterface;
import razepl.dev.github.exceptions.SecurityChainException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements SecurityConfigInterface {
    @Bean
    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            httpSecurity
                    .csrf()
                    .disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/github/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            return httpSecurity.build();
        } catch (Exception exception) {
            throw new SecurityChainException("Security chain has come with an error!");
        }
    }
}
