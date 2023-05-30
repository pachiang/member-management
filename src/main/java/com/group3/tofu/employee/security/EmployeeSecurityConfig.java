package com.group3.tofu.employee.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.group3.tofu.employee.model.EmployeeService;

@Configuration
@EnableWebSecurity
public class EmployeeSecurityConfig {

	@Autowired
	private DataSource dataSource;
	
	// 當資料表的名稱跟users和authorities不同時
	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource) {

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

		// tell spring security how to find users table
		// define query to retrieve a user by username
		jdbcUserDetailsManager
				.setUsersByUsernameQuery("select account, password, enabled from Employee where account = ?");

		// tell spring security how to find authorities table
		// define query to retrieve the authorities/roles by username
		jdbcUserDetailsManager
				.setAuthoritiesByUsernameQuery("select account, authority from Employee where account = ?");
		return jdbcUserDetailsManager;
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select account, password, enabled from Employee where account = ?")
		.authoritiesByUsernameQuery("select account, authority from Employee where account = ?")
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	// bcrypt bean definition
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	// authenticationProvider bean definition
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(userService); //set the custom user details service
//        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
//        return auth;
//    }

//	@Bean
//	public InMemoryUserDetailsManager userDetailsManager() {
//		UserDetails john = User.builder().username("john").password("{noop}test123").roles("EMPLOYEE").build();
//		UserDetails mary = User.builder().username("mary").password("{noop}test123").roles("MANAGER").build();
//		return new InMemoryUserDetailsManager(john, mary);
//	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.antMatchers("/admin").hasRole("ADMIN")
//		.antMatchers("/member").hasAnyRole("ADMIN", "MEMBER")
//		.antMatchers("/user").authenticated()
//		.anyRequest().permitAll();
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				// 順序重要
				// *是當前字串，**是後面所有字串（不管幾個/）
//				.antMatchers(HttpMethod.PUT, "/employee/edit/{eid}").access("hasRole('ROLE_EMPLOYEE') and #eid == authentication.principal.id")
				.antMatchers("/login_emp", "/authenticateTheUser", "/login_emp?logout").permitAll() //設置不管有無登入都可以直接訪問(permitAll)
				.antMatchers(HttpMethod.GET, "/info/employee/").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.GET, "/employee/getEmployeePhoto/*").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.GET, "/employee/edit_my_profile").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.PUT, "/employee/edit/**").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.GET, "/employee/check").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.GET, "/employee/todayscheck").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.GET, "/employee/checks").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.POST, "/employee/check").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.PUT, "/employee/check").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.GET, "/employee/").hasAnyRole("MANAGER","EMPLOYEE")
				
				.antMatchers(HttpMethod.GET, "/employee/leave/application").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.POST, "/employee/leave/application").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.GET, "/employee/leave/approval").hasRole("MANAGER")
				.antMatchers(HttpMethod.PUT, "/employee/leave/approval").hasRole("MANAGER")
				.antMatchers(HttpMethod.PUT, "/employee/leave/approve/**").hasRole("MANAGER")
				.antMatchers(HttpMethod.PUT, "/employee/leave/reject/**").hasRole("MANAGER")
				.antMatchers(HttpMethod.GET, "/employee/leave/**").hasAnyRole("MANAGER","EMPLOYEE")
				
				.antMatchers(HttpMethod.PUT, "/employee/task/management_book/finished/**").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.PUT, "/employee/task/management_mtn/finished/**").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.PUT, "/employee/task/management_book/edit/**").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.PUT, "/employee/task/management_mtn/edit/**").hasAnyRole("MANAGER","EMPLOYEE")
				.antMatchers(HttpMethod.GET, "/employee/task/management_mtn").hasRole("MANAGER")
				.antMatchers(HttpMethod.GET, "/employee/task/management_book").hasRole("MANAGER")
				.antMatchers(HttpMethod.PUT, "/employee/task/management_mtn").hasRole("MANAGER")
				.antMatchers(HttpMethod.PUT, "/employee/task/management_book").hasRole("MANAGER")
				.antMatchers(HttpMethod.GET, "/employee/task/**").hasAnyRole("MANAGER","EMPLOYEE")
				
				.antMatchers(HttpMethod.GET, "/employee/checkrecord").hasRole("MANAGER")
				.antMatchers(HttpMethod.GET, "/employee/**").hasRole("MANAGER")
				.antMatchers(HttpMethod.POST, "/employee/**").hasRole("MANAGER")
				.antMatchers(HttpMethod.PUT, "/employee/**").hasRole("MANAGER")
				.antMatchers(HttpMethod.DELETE, "/employee/**").hasRole("MANAGER")
				
//		.antMatchers(HttpMethod.GET, "/employee/*").hasRole("MANAGER")
//		.antMatchers(HttpMethod.DELETE, "/employee/*").hasRole("MANAGER")
//		.antMatchers(HttpMethod.GET, "/findemp/*").hasRole("MANAGER")
//		.antMatchers(HttpMethod.GET, "/employee/edit/**").hasAnyRole("MANAGER", "EMPLOYEE")
//		.antMatchers(HttpMethod.PUT, "/employee/edit/**").hasAnyRole("MANAGER", "EMPLOYEE")
				
				.and()
					.formLogin()
					.loginPage("/login_emp")
					.loginProcessingUrl("/authenticateTheUser")
					.failureUrl("/login_emp?error")
					.permitAll()
					.defaultSuccessUrl("/employee/")
					
				.and()
					.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login_emp?logout")
					.permitAll()
				.and()
					.exceptionHandling()
					.accessDeniedPage("/access-denied");

		// use HTTP Basic authentication, 告訴Spring security正在使用的是basic authentication
		http.httpBasic();

		// disable Cross Site Request Forgery(CSRF)
		// in general, not required for stateless REST APIs that use POST, PUT, DELETE
		// and/or PATCH
		http.csrf().disable();

		return http.build();

	}

}
