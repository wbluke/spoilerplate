package spoilerplate.testing.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CafeOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeOrderApplication.class, args);
    }

}
