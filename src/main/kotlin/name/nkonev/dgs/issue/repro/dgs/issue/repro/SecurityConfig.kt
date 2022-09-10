package name.nkonev.dgs.issue.repro.dgs.issue.repro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	fun springWebFilterChain(http: HttpSecurity): DefaultSecurityFilterChain {
		return http
				.csrf({c -> c.disable()})
				// Demonstrate that method security works
				// Best practice to use both for defense in depth
				.authorizeRequests({requests -> requests.anyRequest().permitAll()})
				.httpBasic(withDefaults())
				.build();
	}

	@Bean
	fun userDetailsService(): InMemoryUserDetailsManager {
		val userBuilder = User.withDefaultPasswordEncoder();
		val rob = userBuilder.username("rob").password("rob").roles("USER").build();
		val admin = userBuilder.username("admin").password("admin").roles("USER", "ADMIN").build();
		return InMemoryUserDetailsManager(rob, admin);
	}

}
