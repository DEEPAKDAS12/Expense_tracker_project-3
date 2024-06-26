import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> users = new HashMap<>();

    public UserService() {}

    public UserService(Map<String, User> users) {
        this.users = users;
    }

    public void registerUser(String username, String password) throws UserAlreadyExistsException {
        if (users.containsKey(username)) {
            throw new UserAlreadyExistsException("User already exists.");
        }
        users.put(username, new User(username, password));
    }

    public User authenticateUser(String username, String password) throws AuthenticationException {
        User user = users.get(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationException("Invalid username or password.");
        }
        return user;
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
