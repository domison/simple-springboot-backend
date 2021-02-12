package world.doms.simplecrudbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@SpringBootApplication
public class SimpleCrudBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleCrudBackendApplication.class, args);
    }

}

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> createNewUser(@Valid @RequestBody UserRequest userRequest, UriComponentsBuilder uriComponentsBuilder) {

        Long id = userService.createNewUser(userRequest);

        UriComponents uriComponents = uriComponentsBuilder.path("/api/users/{id}").buildAndExpand(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}

