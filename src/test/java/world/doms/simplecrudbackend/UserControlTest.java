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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

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

}