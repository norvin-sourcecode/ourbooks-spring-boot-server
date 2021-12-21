package cloudcode.v2ourbook.configuration;

import com.google.firebase.database.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        System.out.println("config Manager");
//        auth.jdbcAuthentication()
//                .dataSource(dataSource);
//
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        System.out.println("config HTTP");
//        http.authorizeRequests().anyRequest().authenticated();
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(new CorsConfigurationSource() {
            @Nullable
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.addAllowedOrigin("*");
                config.addAllowedHeader("*");
                config.addAllowedMethod("*");
                return config;
            }
        })
                .and()
                .authorizeRequests()
                .mvcMatchers("/resetPassword/**").anonymous()
                .mvcMatchers("/resetPassword/*").anonymous()
                .mvcMatchers("/user/register/").anonymous()
                .mvcMatchers("/user/register/*").anonymous()
                .mvcMatchers("/user/register/**").anonymous()
                .mvcMatchers("/user/requestPasswordReset/*").anonymous()
                .mvcMatchers("/user/requestPasswordReset/**").anonymous()
                .mvcMatchers("/user/requestPasswordReset/").anonymous()
                .mvcMatchers("/user/resetPassword/*").anonymous()
                .mvcMatchers("/user/resetPassword/**").anonymous()
                .mvcMatchers("/user/resetPassword/").anonymous()
                .mvcMatchers("/user/confirm-account/*").anonymous()
                .mvcMatchers("/user/confirm-account/**").anonymous()
                .mvcMatchers("/user/confirm-account/").anonymous()
//                .mvcMatchers("/**").anonymous()
                .anyRequest().authenticated()
//                .csrf().disable().httpBasic()
//                .and()
//                .authorizeRequests()

//                .mvcMatchers("/**").anonymous()
//                .anyRequest().authenticated()
                .and()

//                .and()
//                .mvcMatchers("/changePassword/*").anonymous()
//                .mvcMatchers("/changePassword/**").anonymous()
//                .mvcMatchers("/resetPassword/**").anonymous()
//                .mvcMatchers("/resetPassword/*").anonymous()
//                .mvcMatchers("/user/register/**").anonymous()
//                .mvcMatchers("/admin").hasAnyRole("ADMIN", "SUPERADMIN")
//                .mvcMatchers("/changePasswordAdmin/**").hasAnyRole("ADMIN", "SUPERADMIN")
//                .mvcMatchers("/changePasswordAdmin/*").hasAnyRole("ADMIN", "SUPERADMIN")
//                .mvcMatchers("/**").permitAll()
//                .anyRequest().authenticated()
//                .httpBasic()
//                .and()
//                .formLogin().disable()
//                .cors()
//                .and()
//                .csrf().disable()
                .logout()
                .logoutUrl("/perform_logout")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
                .logoutSuccessUrl("/");


        /*http
                .httpBasic()
                .and()
                .formLogin().disable()
                //.cors()
                //.and()
                //.csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/changePassword/*").anonymous()
                .mvcMatchers("/changePassword/**").anonymous()
                .mvcMatchers("/resetPassword/**").anonymous()
                .mvcMatchers("/resetPassword/*").anonymous()
                .mvcMatchers("/user/register/**").anonymous()
                .mvcMatchers("/admin").hasAnyRole("ADMIN","SUPERADMIN")
                .mvcMatchers("/changePasswordAdmin/**").hasAnyRole("ADMIN","SUPERADMIN")
                .mvcMatchers("/changePasswordAdmin/*").hasAnyRole("ADMIN","SUPERADMIN")
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID");*/


/*
        http
                .httpBasic()
                .and()
                .formLogin().disable()
                .authorizeRequests().anyRequest().authenticated();
*/

//        http
//                .csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .formLogin().disable()
//                .httpBasic();
//
//        http.formLogin()
//                .defaultSuccessUrl("/hello", true);
//        http.authorizeRequests().
//                mvcMatchers("/changePassword/*").anonymous().
//                mvcMatchers("/changePassword/**").anonymous().
//                mvcMatchers("/resetPassword/**").anonymous().
//                mvcMatchers("/resetPassword/*").anonymous().
//                mvcMatchers("/admin").hasAnyRole("ADMIN","SUPERADMIN").
//                mvcMatchers("/changePasswordAdmin/**").hasAnyRole("ADMIN","SUPERADMIN").
//                mvcMatchers("/changePasswordAdmin/*").hasAnyRole("ADMIN","SUPERADMIN").
//                anyRequest().
//                hasAnyAuthority("login","superadmin").
//                //authenticated()
//                and().
//                //formLogin().disable()*/
//                formLogin()
//                  .loginPage("/login")
//                  .loginProcessingUrl("/perform_login")
//                  .defaultSuccessUrl("/hello", true)
//                  .failureUrl("/login?error=true")
//                  .failureHandler(new AuthenticationFailureHandler() {
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//                        System.out.println("schlug fehl ");
//                    }
//               })
//                .and()
//                .logout()
//                .logoutUrl("/perform_logout")
//                .deleteCookies("JSESSIONID");

    }

}
