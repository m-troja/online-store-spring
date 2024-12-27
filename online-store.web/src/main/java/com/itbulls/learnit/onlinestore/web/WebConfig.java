package com.itbulls.learnit.onlinestore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.itbulls.learnit.onlinestore.web.security.DefaultAuthenticationFailureHandler;
import com.itbulls.learnit.onlinestore.web.security.DefaultAuthenticationProvider;
import com.itbulls.learnit.onlinestore.web.security.DefaultAuthenticationSuccessHandler;
import com.itbulls.learnit.onlinestore.web.security.DefaultLogoutSuccessHandler;
import com.itbulls.learnit.onlinestore.web.security.DefaultUserDetailsService;

@EnableWebSecurity
@EnableWebMvc
@Configuration
@PropertySource("classpath:app.properties")
@ComponentScan(basePackages = { "com.itbulls.learnit.onlinestore" })
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		return bean;
	}

	@Bean
	public HandlerExceptionResolver errorHandler() {
        return new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Object handler,
                                                  Exception ex) {
                ModelAndView model = new ModelAndView("error-page");
                ex.printStackTrace();
                model.addObject("exception", ex);
                model.addObject("handler", handler);
                return model;
            }
        };
    }
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/css/**", "/fonts/**", "/images/**", "/js/**", "/vendor/**")
          .addResourceLocations("/css/", "/fonts/", "/images/", "/js/", "/vendor/");	
    }
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource ();
	    messageSource.setBasenames("classpath:OnlineShopResourceBundle");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
	    return new CookieLocaleResolver();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	    localeChangeInterceptor.setParamName("lang");
	    registry.addInterceptor(localeChangeInterceptor);
	}
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new DemoHandlerInterceptor()).addPathPatterns("/test/simple-model-demo");
//		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//		localeChangeInterceptor.setParamName("lang");
//		registry.addInterceptor(localeChangeInterceptor);
//	}

	
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user1 = User.withUsername("user1").password(passwordEncoder().encode("test1")).roles("CUSTOMER")
//				.build();
//		UserDetails user2 = User.withUsername("user2").password(passwordEncoder().encode("test2")).roles("CUSTOMER")
//				.build();
//		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("root")).roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(user1, user2, admin);
//	}
	
	
	// ===== Spring Security Configuration

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.csrf()
	        .disable()
	        .authorizeHttpRequests()
		        .requestMatchers("/test/admin/**")
		        .hasRole("ADMIN")
		        .requestMatchers("/test/anonymous*")
		        .anonymous()
		        .requestMatchers("/signin")
		        .permitAll()
 		        .anyRequest()
 		        .authenticated()
	        .and()
		        .formLogin()
    	        .usernameParameter("email")		
//		        .passwordParameter("pass") // in case you want to use different parameter 
		        .loginPage("/signin")
		        .loginProcessingUrl("/signin")
		        .defaultSuccessUrl("/success-login", false)
		        .failureUrl("/signin")
		        .failureHandler(authenticationFailureHandler())
	        .and()
		        .logout()
		        .logoutUrl("/signout")
		        .logoutSuccessUrl("/homepage")
		        .deleteCookies("JSESSIONID")
		        .logoutSuccessHandler(logoutSuccessHandler());
    	return http.getOrBuild();
    }
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new DefaultAuthenticationFailureHandler();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new DefaultLogoutSuccessHandler();
	}
	
	@Bean
	public AuthenticationProvider authProvider() {
		return new DefaultAuthenticationProvider();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(authProvider());
		return authenticationManagerBuilder.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new DefaultUserDetailsService();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() 
	{
		return new DefaultAuthenticationSuccessHandler();
	}
}