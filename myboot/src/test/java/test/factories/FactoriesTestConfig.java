package test.factories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoriesTestConfig {

	@Bean
	public FactoriesTestBean factoriesTestBean() {
		return new FactoriesTestBean();
	}
}
