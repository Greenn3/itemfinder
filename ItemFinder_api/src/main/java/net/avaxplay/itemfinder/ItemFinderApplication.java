package net.avaxplay.itemfinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@RestController
public class ItemFinderApplication extends SpringBootServletInitializer {

	//coment to test commit
	private static final Logger log = LoggerFactory.getLogger(ItemFinderApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ItemFinderApplication.class, args);
	}

	@GetMapping("/home")
	public String home() {
		return "OwO";
	}

	@RequestMapping("/test-site")
	public String test() {
		return "test";
	}
}
