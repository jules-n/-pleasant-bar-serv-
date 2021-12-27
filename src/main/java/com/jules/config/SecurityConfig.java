
package com.jules.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Setter(onMethod_ = {@Autowired})
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/users/register",
                        "/register").permitAll()
                .mvcMatchers("/users/register/", "/drinks/menu/").permitAll()
                .mvcMatchers("users/update-profile").authenticated()
                .mvcMatchers("/users/add-rights/").hasRole("admin")
                .mvcMatchers("/drinks/renew-drink/").hasRole("barman")
                .mvcMatchers("/drinks/update-drink/").hasRole("barman")
                .mvcMatchers("/drinks/add-drink/").hasRole("barman")
                .mvcMatchers("/drinks/delete-drink/").hasRole("barman")
                .mvcMatchers("/orders/update-status/").hasRole("barman")
                .mvcMatchers("/ingredients/**").hasRole("barman")
                .mvcMatchers("/ingredients/*").hasRole("barman")
                .mvcMatchers("/orders/create-order/").hasAnyRole("barman", "waiter")
                .and().httpBasic()
                .and().sessionManagement().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(userDetailsService);
    }

}
