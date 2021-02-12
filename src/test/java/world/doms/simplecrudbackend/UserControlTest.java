package world.doms.simplecrudbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControlTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserRequest> argumentCaptor;

    @Test
    public void postingANewUserCreatesNewUserInDB() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Eva");
        userRequest.setLastName("Elster");
        userRequest.setEmail("Elster_Eva@birdwatcher.com");

        when(userService.createNewUser(argumentCaptor.capture())).thenReturn(1L);

        this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/users/1"));

        assertThat(argumentCaptor.getValue().getEmail(), is("Elster_Eva@birdwatcher.com"));
        assertThat(argumentCaptor.getValue().getFirstName(), is("Eva"));
        assertThat(argumentCaptor.getValue().getLastName(), is("Elster"));
    }

    @Test
    public void allUsersEndpointShouldReturnTwoUsers() throws Exception {

        when(userService.getAllUsers()).thenReturn(List.of(createUser(1L, "Eva", "Evangelika", "praytell@gmx.net"),
                createUser(2L, "Anton", "Antonius", "whiteknight@medivalfestival.com")));

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Eva")))
                .andExpect(jsonPath("$[0].lastName", is("Evangelika")))
                .andExpect(jsonPath("$[0].email", is("praytell@gmx.net")))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void getUserWithIdOneShouldReturnUser() throws Exception {

        when(userService.getUserById(2L)).thenReturn(createUser(2L, "Anton", "Antonius", "whiteknight@medivalfestival.com"));

        this.mockMvc.perform(get("/api/users/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName", is("Anton")))
                .andExpect(jsonPath("$.lastName", is("Antonius")))
                .andExpect(jsonPath("$.email", is("whiteknight@medivalfestival.com")))
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    public void getUserWithUnknownIdShouldReturnError() throws Exception {
        when(userService.getUserById(22222L)).thenThrow(new UserNotFoundException("User with ID 22222 was not found"));

        this.mockMvc.perform(get("/api/users/22222"))
                .andExpect(status().isNotFound());
    }

    private User createUser(long id, String firstName, String lastName, String email) {

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setId(id);

        return user;
    }

}
