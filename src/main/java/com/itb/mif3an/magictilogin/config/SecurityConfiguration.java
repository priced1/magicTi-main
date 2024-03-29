package com.itb.mif3an.magictilogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.itb.mif3an.magictilogin.service.UserService;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		    .antMatchers("/inicial**",
		    			"/inicial/***",
		    			"/registration**",
		    		     "/registration/**",
		    		  	 "/js/**",
		    		     "/css/**",
		    		     "/img/**").permitAll()
		    .and().authorizeRequests()
		    .antMatchers(GET, "/users/**").hasAnyAuthority("ROLE_USER")
		    .antMatchers(GET, "/admin/**").hasAnyAuthority("ROLE_ADMIN")
		    .antMatchers(POST, "/admin/**").hasAnyAuthority("ROLE_ADMIN")
		    .anyRequest().authenticated()
		    .and()
		    .httpBasic()
		    .and()
		    .formLogin().defaultSuccessUrl("/users/living-room", true)
		    .passwordParameter("senha")
		    .usernameParameter("email")
		    .loginPage("/login")
		    .permitAll()
		    .and()
		    .logout()
		    .invalidateHttpSession(true)
		    .clearAuthentication(true)
		    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		    .logoutSuccessUrl("/login?logout")
		    .permitAll();
	}
	
}
