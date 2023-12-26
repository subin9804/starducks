package org.kosta.starducks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class StarducksApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarducksApplication.class, args);
	}

}
