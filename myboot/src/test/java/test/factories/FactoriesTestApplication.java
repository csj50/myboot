package test.factories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan(basePackages = {"com.example"})  // 指定扫描路径
@ComponentScan(basePackages = {"com.example"})  // 指定扫描路径
@SpringBootApplication
public class FactoriesTestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(FactoriesTestApplication.class, args);
		FactoriesTestBean test = ctx.getBean(FactoriesTestBean.class);
		System.out.println(test.Info());
	}

}
