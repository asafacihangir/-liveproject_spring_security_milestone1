package com.laurentiuspilca.liveproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfiguration
    extends WebSecurityConfigurerAdapter {

  @Bean
  public UserDetailsService userDetailsService() {
    var uds = new InMemoryUserDetailsManager();

    var user = User.withUsername("user")
        .password("password")
        .authorities("read")
        .build();

    uds.createUser(user);

    return uds;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf(c -> c.ignoringAntMatchers(
        "/user/**", "/client/**","/h2-console/**"
    ));

    http
        .csrf(AbstractHttpConfigurer::disable)
        .headers(headers -> headers.frameOptions().disable());

    http.authorizeRequests()
        .mvcMatchers("/user/**").permitAll()
        .mvcMatchers("/h2-console/**").permitAll()
        .mvcMatchers("/client/**").permitAll();

    super.configure(http);

  }
}
