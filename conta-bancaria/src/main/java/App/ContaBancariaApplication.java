package App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"App/WebSystem", "App"})
public class ContaBancariaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContaBancariaApplication.class, args);
    }
}
