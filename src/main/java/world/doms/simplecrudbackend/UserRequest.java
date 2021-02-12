package world.doms.simplecrudbackend;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class UserRequest {

    @NotEmpty(message = "Field cannot be empty")
    @Size(min = 2, max = 40, message
            = "Name must be between 2 and 40 characters")
    private String firstName;

    @NotEmpty(message = "Field cannot be empty")
    @Size(min = 2, max = 40, message
            = "Name must be between 2 and 40 characters")
    private String lastName;

    @Email(message = "Please input a valid email")
    private String email;
}
