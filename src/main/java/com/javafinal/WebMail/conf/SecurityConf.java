package com.javafinal.WebMail.conf;

import com.javafinal.WebMail.Constant.Role;
import com.javafinal.WebMail.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailService getUserDetailServices(){
        return new UserDetailService();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailServices());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getDaoAuthenticationProvider());
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin().loginPage("/signin")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/user/")
                .and()
                .csrf().disable();

    }
}
