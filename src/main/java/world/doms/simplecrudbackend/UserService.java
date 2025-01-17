package world.doms.simplecrudbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Long createNewUser(UserRequest userRequest) {

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());

        user = userRepository.save(user);

        return user.getId();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> requestedUser = userRepository.findById(id);

        if (requestedUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID '%s' was not found", id));
        }
        return requestedUser.get();
    }

    public List<User> getUserByName(String firstName) {
        List<User> usersWithName = userRepository.findAll()
                .stream()
                .filter(user -> user.getFirstName().equals(firstName))
                .collect(Collectors.toList());

        if (usersWithName.isEmpty()) {
            throw new UserNotFoundException(String.format("No User with name: '%s' was found", firstName));
        }

        return usersWithName;
    }

    @Transactional
    public User updateUser(long id, UserRequest userToUpdateRequest) {

        Optional<User> userFromDatabase = userRepository.findById(id);

        if (userFromDatabase.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID: '%s' was not found", id));
        }

        User userToUpdate = userFromDatabase.get();

        userToUpdate.setFirstName(userToUpdateRequest.getFirstName());
        userToUpdate.setLastName(userToUpdateRequest.getLastName());
        userToUpdate.setEmail(userToUpdateRequest.getEmail());

        return userToUpdate;
    }

    public void deleteBookById(Long id) {
        userRepository.deleteById(id);
    }


}
