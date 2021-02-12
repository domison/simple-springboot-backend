package world.doms.simplecrudbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository personRepository;

    private final String[][] userInit = new String[][]{
            {"Dominik", "Alex", "Eva", "Maria", "Josef"},
            {"Schmitt", "Freitag", "Schneider", "Mann", "Bernstein"},
            {"DominikWSchmitt@gmail.com.", "Alex.F@hotmail.de", "S.Eva1110@studivz.de", "Alliterationen_Galore@protonmail.com","DJ_JB@m1-muenchen.de"}
    };

    @Override
    public void run(String... args) throws Exception {

        log.info("Starting data initialization process...");
        for (int i = 0; i < 5; i++) {

            User user = new User();
            user.setFirstName(userInit[0][i]);
            user.setLastName(userInit[1][i]);
            user.setEmail(userInit[2][i]);

            personRepository.save(user);
        }
        log.info("Finished data initialization process...");

    }
}
