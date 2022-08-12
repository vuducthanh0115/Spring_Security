package com.example.spring_security.basic.config;
import com.example.spring_security.basic.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*public class SercurityConfig extends WebSecurityConfigurerAdapter{
 *//*@Bean
    public UserDetailsService userDetailsService(){
       InMemoryUserDetailsManager ud = new InMemoryUserDetailsManager();
       var user = User.withUsername("thanhvd")
               .password("123")
               .authorities("read")
               .build();
       ud.createUser(user);
       return ud;
   }*//*

   @Override
   public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication()
              .withUser("thanhvd")
              .password("123")
              .authorities("USER")
              .and()
              .withUser("admin")
              .password("admin")
              .authorities("ADMIN");
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers("/get-all/**").hasRole("ADMIN")
              .antMatchers("/get-by-id/**").hasRole("USER")
              .antMatchers("/delete/**").hasRole("ADMIN")
              .antMatchers("/update/**").hasRole("ADMIN")
              .antMatchers("/add/**").hasRole("ADMIN")
              .antMatchers("/**").permitAll()
              .and()
              .httpBasic();
   }

   @Bean
   public PasswordEncoder passwordEncoder(){
      return NoOpPasswordEncoder.getInstance();
   }
}*/
@Configuration
@EnableWebSecurity
public class SercurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
        //return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/get-all/**").permitAll()
                .antMatchers("/get-by-id/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/delete/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/update/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/add/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/random/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
