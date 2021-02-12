package world.doms.simplecrudbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleCrudBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(SimpleCrudBackendApplication.class, args);
        // Visit localhost:8000/h2-console to access database
        // No Frontend
    }

}



