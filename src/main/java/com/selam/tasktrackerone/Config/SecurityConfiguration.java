package com.selam.tasktrackerone.Config;

import com.selam.tasktrackerone.Dao.EmployeeDao;
import com.selam.tasktrackerone.Model.Employee;
import com.selam.tasktrackerone.Services.UserDetailsServiceImpl;
import javassist.expr.Instanceof;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import sun.security.util.Password;

import javax.sql.DataSource;
import java.util.List;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    DataSource dataSource;

    @Bean
    public static BCryptPasswordEncoder passwordEncode() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
   /*@Bean
   public PasswordEncoder passwordEncoder() {
       return NoOpPasswordEncoder.getInstance();
   }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(
                        "SELECT employee_username, employee_password, enabled FROM employees WHERE employee_username=?"
                    )
                    .authoritiesByUsernameQuery(
                        "SELECT employee_username, authority FROM authorities WHERE employee_username =?"
                    );
        } catch (Exception e) {
            System.out.print("ERROR: login: ");
            e.printStackTrace();
        }
    }

    @Override
    protected void configure (HttpSecurity http){

        try {
            http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/manager/**").hasAuthority("manager")
                    .and().formLogin()
                    .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                    .and().exceptionHandling().accessDeniedPage("accessDenied.jsp");
        } catch (Exception e) {
            System.out.print("ERROR: auth:");
            e.printStackTrace();
        }
    }



























        /*http.csrf().disable();

        // The pages that do not require login
        http.authorizeRequests().antMatchers("/login", "/logout", "/registration", "/").permitAll();

        // For ADMIN only.
        http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");

        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/error/access-denied");

        // Config for Login Form
        http.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/diverter")//
                .failureUrl("/login?error=true")//
                .usernameParameter("email")//
                .passwordParameter("password")
                // Config for Logout Page
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }*/
}
