/* 
 * Copyright 2018 onwards - Sunit Katkar (sunitkatkar@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sunitkatkar.blogspot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration of security related beans and methods. The access to different
 * urls within the application is defined here.
 * 
 * @author Sunit Katkar
 * @version 1.0
 * @since 1.0 (May 2018)
 *
 */
@Configuration
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * This is where access to various resources (urls) in the application is
     * defined
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .addFilterBefore(authenticationFilter(), 
                    UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/css/**", "/index").permitAll()
                .antMatchers("/user/**").authenticated()
            .and()
            .formLogin().loginPage("/login")
            .and()
            .logout()
            .logoutUrl("/logout");
       //@formatter:on 
    }

    /**
     * Create an instance of the custom authentication filter which intercepts
     * and processes the end user's login form submission for further
     * authentication processing. This filter is added before other filters so
     * that it can intercept the user login form submission and extract the the
     * additional 'tenant' field
     * 
     * @return
     * @throws Exception
     */
    public CustomAuthenticationFilter authenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(failureHandler());
        filter.setAuthenticationSuccessHandler(successHandler());
        return filter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authProvider());
    }

    /**
     * Authentication provider which provides the logged in user's credentials
     * for verification and authentication if they are coeect
     * 
     * @return
     */
    public AuthenticationProvider authProvider() {
        // The custom authentication provider defined for this app
        CustomUserDetailsAuthenticationProvider provider = new CustomUserDetailsAuthenticationProvider(
                passwordEncoder(), userDetailsService);
        return provider;
    }

    /**
     * The page to show if authentication fails
     * 
     * @return
     */
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
    }

    public SimpleUrlAuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/user/index");
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
