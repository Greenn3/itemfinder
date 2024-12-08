package net.avaxplay.itemfinder.config;

import net.avaxplay.itemfinder.auth.DataBaseUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final DataBaseUserDetailService userDetailService;

    public SecurityConfig(DataBaseUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http

                .securityMatcher("/api/v1/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
        ;
        return http.build();
    }

    // this is temporary and should be removed
    @Bean
    @Order(1)
    public SecurityFilterChain itemfinderApiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/itemfinder/api/v1/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
        ;
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disables CSRF protection; enable if needed
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/styles/**", "/js/**", "/img/**", "/bootstrap/**", "/fonts/**").permitAll() // Permit all for static resources
                       // .requestMatchers("/api/v1/**").authenticated()                  // Require authentication for "/api/v1/**"
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/test-site3",
                                "/found-itemsV2",
                                "/found-itemsV2/**",
                                "/found-item-singularV2",
                                "/lost-itemsV2",
                                "/lost-itemsV2/**",
                                "/lost-item-singularV2/**",
                                "/registerV2",
                                "/lost-itemsSortedV2",
                                "/found-itemsSortedV2")
                        .permitAll() // Allow everyone to access the index page
                        .anyRequest().authenticated()                                   // Require authentication for other requests
                )
                //.formLogin(Customizer.withDefaults())
              //  .httpBasic(Customizer.withDefaults()) // Optionally enable HTTP Basic Authentication as default
                .formLogin(form -> form
                        .loginPage("/loginV2")                  // Set custom login page URL
                        .permitAll()                            // Allow everyone to access the login page
                        .defaultSuccessUrl("/test-site3", true) // Redirect to /test-site3 on successful login
                        .failureUrl("/login?error=true")        // Redirect to login page with error parameter on failure
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/test-site3")                  // Redirect after logout
                        .invalidateHttpSession(true)            // Explicitly invalidate the session
                        .clearAuthentication(true)              // Ensure authentication is cleared
                        .deleteCookies("JSESSIONID")            // Clear session cookie
                        .permitAll()                            // Allow everyone to access logout
                );
        //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Optional session management policy
        return http.build();
    }
//@Bean
//public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
//    http
//            .securityMatcher("/api/**")                        // Apply this configuration only to API endpoints
//            .authorizeHttpRequests(authorize -> authorize
//                    .anyRequest().authenticated()                  // Require authentication for all API requests
//            )
//            .httpBasic(withDefaults())                         // Use HTTP Basic authentication for API
//            .csrf(csrf -> csrf.disable());                     // Disable CSRF for API endpoints if needed
//
//    return http.build();
//}
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .securityMatcher("/**")                    // Apply this configuration to all other routes
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/styles/**", "/js/**", "/img/**",  "/bootstrap/**", "/fonts/**").permitAll() // Allow public access to static resources
//                        .anyRequest().authenticated()           // Require authentication for all other website requests
//                )
//                .formLogin(form -> form
//                        .loginPage("/loginV2")                   // Custom login page URL for website
//                        .permitAll()                           // Allow everyone to access the login page
//                        .defaultSuccessUrl("/test-site3", true)      // Redirect to "/home" on successful login
//                        .failureUrl("/login?error=true")       // Redirect to login page with error on failure
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")                  // URL to trigger logout
//                        .logoutSuccessUrl("/")                 // Redirect after logout
//                        .invalidateHttpSession(true)           // Explicitly invalidate the session
//                        .clearAuthentication(true)             // Ensure authentication is cleared
//                        .deleteCookies("JSESSIONID")           // Clear session cookie
//                        .permitAll()                           // Allow everyone to access logout
//                );
//
//
//
//        return http.build();
//    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(userDetailService);
        return provider;
    }
}
