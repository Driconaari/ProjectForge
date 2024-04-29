package com.example.projectforge.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public abstract class WebSecurityConfigurerAdapter {


    protected abstract void configure(HttpSecurity http) throws Exception;

    protected abstract void configure(AuthenticationManagerBuilder auth) throws Exception;
}
