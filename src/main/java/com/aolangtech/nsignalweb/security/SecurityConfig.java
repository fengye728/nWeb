/**
 * 
 */
/**
 * @author SEELE
 *
 */
package com.aolangtech.nsignalweb.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	final static String COOKIE_USER_ID = "username";
	final static String COOKIE_USER_ROLE = "userrole";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/global/**", "/external/**").permitAll()
				//.antMatchers("/accident/**", "/charge/**", "/complaint/**", "/driver/**", "/internal/**").authenticated()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login").permitAll().successHandler(getAuthenticationSuccessHandler())
				.failureUrl("/login?error")
				.and()
			.logout()
				.logoutUrl("/logout")
				.invalidateHttpSession(true).clearAuthentication(true).deleteCookies(COOKIE_USER_ID);;
		http.sessionManagement().maximumSessions(-1).maxSessionsPreventsLogin(true).expiredUrl("/login");
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		String usersByUsernameQueryString = 
				"SELECT username, password, is_active enabled "
				+ "FROM \"authorization\" "
				+ "WHERE username = ?";
		String authoritiesByUsernameQueryString = 
				"SELECT username, role "
				+ "FROM \"authorization\" u "
				+ "WHERE username = ?";
		
		JdbcUserDetailsManager userDetailService = auth.jdbcAuthentication().dataSource(dataSource).getUserDetailsService();
		
		userDetailService.setUsersByUsernameQuery(usersByUsernameQueryString);
		userDetailService.setAuthoritiesByUsernameQuery(authoritiesByUsernameQueryString);
		
	}
	
	AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
		return new AuthenticationSuccessHandler(){
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				
				// Add user name and role to cookie
				String username = ((User)authentication.getPrincipal()).getUsername();
				Collection<GrantedAuthority> userRoleList = ((User)authentication.getPrincipal()).getAuthorities();
				String userrole = null;
				for(GrantedAuthority role : userRoleList) {
					userrole = role.getAuthority();
				}
				response.addCookie(new Cookie(COOKIE_USER_ID,  username));
				response.addCookie(new Cookie(COOKIE_USER_ROLE,  userrole));
				response.sendRedirect("/admin");
			}
			
		};
	}
}