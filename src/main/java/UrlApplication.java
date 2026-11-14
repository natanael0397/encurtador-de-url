import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"controller", "service", "repository", "model"})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "model")
public class UrlApplication {
    public static void main(String[] args) {
        SpringApplication.run(UrlApplication.class, args);
    }
}