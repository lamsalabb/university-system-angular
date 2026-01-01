package com.university.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class UserConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        //retrieve user
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, password_hash, is_active FROM users WHERE email=?");

        //concat with sql as Spring Security requires ROLE_ prefix
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT email, CONCAT('ROLE_', role) FROM users WHERE email=?");

        return jdbcUserDetailsManager;
    }
}
