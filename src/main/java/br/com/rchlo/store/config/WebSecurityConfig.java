package br.com.rchlo.store.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private AuthService authService;
	
	public WebSecurityConfig(final AuthService authService) {
		this.authService = authService;
	}
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.authService)
		    .passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // desabilita CSRF
		    .authorizeRequests()
		    //.antMatchers("/categories/**", "/payments/**", "/products/**", "/reports/**").permitAll()
		    .antMatchers("/admin/**").hasRole("ADMIN")
		    //.anyRequest().authenticated()
		    .and().httpBasic(); // habilita Basic Authentication
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
	}
	
}
