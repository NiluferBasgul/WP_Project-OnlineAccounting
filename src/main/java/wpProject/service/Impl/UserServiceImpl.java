package wpProject.service.Impl;

import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wpProject.model.Provider;
import wpProject.model.User;
import wpProject.model.security.UserRole;
import wpProject.repository.RoleRepository;
import wpProject.repository.UserRepository;
import wpProject.service.AccountService;
import wpProject.service.UserService;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private AccountService accountService;
	
	public void save(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            for (UserRole ur : userRoles) {
                if (ur != null && ur.getRole() != null) {
                    roleRepository.save(ur.getRole());
                }
            }

            user.getUserRoles().addAll(userRoles);

            user.setInvoice(accountService.createInvoice());
            user.setCost(accountService.createCost());

            localUser = userRepository.save(user);
        }

        return localUser;
    }

    public boolean checkUserExists(String username, String email) {
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }

    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findUserList() {
        return userRepository.findAll();
    }

    public void enableUser(String username) {
        User user = findByUsername(username);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public ApiResponse disableUser(String username) {
        User user = findByUsername(username);
        user.setEnabled(false);
        System.out.println(user.isEnabled());
        userRepository.save(user);
        System.out.println(username + " is disabled.");
        return null;
    }

    @Autowired
    private UserRepository repo;

    public void processOAuthPostLogin(String username) {
        User existUser = repo.getUserByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setProvider(Provider.FACEBOOK);
            newUser.setEnabled(true);

            repo.save(newUser);
        }

    }

    @Override
    public void registerNewUser(String email, String name, Provider provider) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(name);
        user.setEnabled(true);
        user.setProvider(provider);

        userRepository.save(user);
    }

    @Override
    public void updateUser(User user, String name, Provider provider) {
        user.setFirstName(name);
        user.setProvider(provider);

        userRepository.save(user);
    }

}
