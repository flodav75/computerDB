package fr.excilys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.excilys.service.UserService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "fr.excilys.service", "fr.excilys.persistence",
"fr.excilys.mappers"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserService userService;
	
	public SecurityConfig(UserService userService) {
		this.userService = userService;
		
	}
 
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

    	auth.authenticationProvider(authenticationProvider());

    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
		.antMatchers("/add").authenticated()
		.anyRequest().permitAll()
		.and().csrf().disable()
		.formLogin()
		.defaultSuccessUrl("/index",true)
		.failureUrl("/")
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/index");
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}