package tacos.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.domain.User;
import tacos.repository.UserRepository;
import tacos.security.CustomOAuth2UserService;

@Configuration
@EnableMethodSecurity(
	    prePostEnabled = true,
	    securedEnabled = true
	)
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		
		return username -> {
			
			User user = userRepository.findByUsername(username);
			if (user != null) return user;
			throw new UsernameNotFoundException("User '"+username+"' not found");
			
		};
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, CustomOAuth2UserService oAuth2UserService) throws Exception{
		
		return http
				.csrf(csrf -> csrf.disable())     // H2 requires CSRF disabled
		        .headers(headers -> headers
		            .frameOptions(frame -> frame.disable()) // H2 runs in a frame
		        )
				.authorizeHttpRequests(
					auth -> 
					auth.requestMatchers("/login","/register","/images/**","/h2-console/**","/util/**").permitAll()
						.requestMatchers("/design","/orders/**","/").hasRole("USER")
						.anyRequest().authenticated()
						
						)
				.formLogin( form -> 
								form.loginPage("/login")
									.usernameParameter("username")
									.passwordParameter("password")
									.defaultSuccessUrl("/design",true)
						
						)
				.oauth2Login(oauth2 -> oauth2
		                .loginPage("/login") // optional custom login page
		                .defaultSuccessUrl("/design",true) // redirect after successful login
		                .userInfoEndpoint(userInfo -> userInfo
		                        .userService(oAuth2UserService) // <-- inject custom user service
		                    )
		            )
				.logout(logout -> {})
				.build();
				
	}

}


