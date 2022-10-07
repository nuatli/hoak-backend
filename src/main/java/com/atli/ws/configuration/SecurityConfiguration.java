package com.atli.ws.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true) 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserAuthService userAuthService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // Basic token disable ettik
		

		http/* .httpBasic() */.exceptionHandling().authenticationEntryPoint( new AuthEntryPoint()); // Entry point pop-up'ı kapatmak için kullanıldı
		
		
		http.authorizeRequests()
				.antMatchers(HttpMethod.PUT, "api/0.0.1/users/{username}").authenticated ()
				.antMatchers(HttpMethod.POST, "api/0.0.1/hoaxes").authenticated () // login olmadan bu apileri kullanamaz
				.antMatchers(HttpMethod.POST, "api/0.0.1/hoax-attachments").authenticated () // login olmadan bu apileri kullanamaz
				.antMatchers(HttpMethod.POST, "api/0.0.1/logout").authenticated ()
			.	and()
			.	authorizeRequests().anyRequest().permitAll();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.headers().frameOptions().disable();
		
		http.addFilterBefore(tokenFilter(),UsernamePasswordAuthenticationFilter.class);
	
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	TokenFilter tokenFilter() {
		return new TokenFilter();
	}
	
	
}
