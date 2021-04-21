package wpProject.service;

import io.swagger.annotations.ApiResponse;
import wpProject.model.Provider;
import wpProject.model.User;
import wpProject.model.security.UserRole;

import java.util.List;
import java.util.Set;

public interface UserService {
    User findByUsername(String username);

    User findByEmail(String email);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);

    void save(User user);

    User createUser(User user, Set<UserRole> userRoles);

    User saveUser(User user);

    List<User> findUserList();

    void enableUser(String username);

    ApiResponse disableUser(String username);

    void processOAuthPostLogin(String username);

    void registerNewUser(String email, String name, Provider provider);

    void updateUser(User user, String name, Provider provider);
}
