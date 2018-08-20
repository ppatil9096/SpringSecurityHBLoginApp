package com.pravin.spring.secuity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.pravin.spring.secuity.auth.MyDBAuthenticationService;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyDBAuthenticationService myDBAauthenticationService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
	// String password = passwordEncoder().encode("Pravin@123");
	authenticationMgr.inMemoryAuthentication().withUser("pravin").password("{noop}Pravin@123")
		.authorities("ROLE_USER");

	// For User in database.
	authenticationMgr.userDetailsService(myDBAauthenticationService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	// // for specific user usage
	// http.authorizeRequests().antMatchers("/homePage").access("hasRole('ROLE_USER')").and().formLogin()
	// .loginPage("/loginPage").defaultSuccessUrl("/homePage").failureUrl("/loginPage?error")
	// .usernameParameter("username").passwordParameter("password").and().logout()
	// .logoutSuccessUrl("/loginPage?logout").and().httpBasic();

	// for all users usage
	// http.authorizeRequests().antMatchers("/homePage").permitAll().and().formLogin().loginPage("/loginPage")
	// .defaultSuccessUrl("/homePage").failureUrl("/loginPage?error").usernameParameter("username")
	// .passwordParameter("password").and().logout().logoutSuccessUrl("/loginPage?logout").and().httpBasic();

	http.csrf().disable();

	// The pages does not require login
	http.authorizeRequests().antMatchers("/", "/welcome", "/login", "/logout").permitAll();

	// userInfo page requires login as USER or ADMIN.
	// If no login, it will redirect to /login page.
	http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

	// For ADMIN only.
	http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

	// When the user has logged in as XX.
	// But access a page that requires role YY,
	// AccessDeniedException will throw.
	http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

	// Config for Login Form
	http.authorizeRequests().and().formLogin()//
		// Submit URL of login page.
		.loginProcessingUrl("/j_spring_security_check") // Submit URL
		.loginPage("/login")//
		.defaultSuccessUrl("/userInfo")//
		.failureUrl("/login?error=true")//
		.usernameParameter("username")//
		.passwordParameter("password")
		// Config for Logout Page
		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

    }

    // @Autowired
    // private DataSource dataSource;

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception
    // {
    //
    // auth.jdbcAuthentication().dataSource(dataSource)
    // .usersByUsernameQuery("select username, password, ENABLED" + " from user_s
    // where username=?")
    // .authoritiesByUsernameQuery("select username, USER_ROLE " + "from USER_ROLES
    // where username=?");
    // }

}
