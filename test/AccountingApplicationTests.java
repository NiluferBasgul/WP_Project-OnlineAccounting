import io.swagger.annotations.ApiResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import wpProject.model.Company;
import wpProject.model.User;
import wpProject.repository.UserRepository;
import wpProject.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ResourceBundle.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccountingApplicationTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User user;
    User toUser;
    List<Company> companyList;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        user = User.builder()
                .userId(1L)
                .username("user")
                .lastName("-")
                .username("user@gmail.com")
                .email("user@gmail.com")
                .password("asdsadsa")
                .build();

        toUser = User.builder()
                .userId(2L)
                .firstName("user")
                .lastName("-")
                .username("test@mail.com")
                .email("test@mail.com")
                .password("asdsadsa")
                .phone("123456")
                .build();

        companyList = new ArrayList<>();

    }

    @Test
    public void whenEmailIsSet_thenCreateNewUser() {

        // Given
        User newUser = User.builder()
                .userId(1L)
                .firstName("user")
                .lastName("lastname")
                .username("test")
                .email("user@gmail.com")
                .companyList(companyList)
                .build();

        // When
        when(userRepository.save(newUser)).thenReturn(newUser);

        // Then
        User user = userService.saveUser(newUser);
        assertNotNull(user);
        assertEquals(user.getName(), "user");
        assertEquals(user.getEmail(), "user@gmail.com");
    }


    @Test
    public void whenWriteUsername_thenShowProfile() {
        // Given
        List<User> users = new ArrayList<>();
        users.add(user);

        //When
        when(userRepository.findByUsername("users@gmail.com")).thenReturn((User) users);

        //Then
        List<User> userProfiles = userService.findUserList();
        assertEquals(1, userProfiles.size());
        assertEquals(user.getEmail(), userProfiles.get(0).getEmail());
    }


    @Test
    public void whenEditUserRequest_thenUserEdit() {

        // Given
        User user = User.builder()
                .userId(1L)
                .firstName("user")
                .lastName("lastname")
                .username("user@gmail.com")
                .email("user@gmail.com")
                .companyList(companyList)
                .build();


        // When
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        // Then
        ApiResponse apiResponse = (ApiResponse) userService.saveUser(user);
        assertNotNull(apiResponse);
        assertEquals(apiResponse.message(), "User successfully edited.");

    }


    @Test
    public void deleteSignatoryList_deleteById() {
        //Given
        String user1 = null;
        List<User> users = new ArrayList<>();
        users.add(user);
        //When
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        //Then
        ApiResponse apiResponse = userService.disableUser(user1);
        assertNotNull(apiResponse);
        assertEquals("User Deleted.", apiResponse.message());
    }

}
