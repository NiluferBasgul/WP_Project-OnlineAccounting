package wpProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import wpProject.model.Provider;
import wpProject.model.User;
import wpProject.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException{
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String name = oAuth2User.getName();
        User user = userService.findByEmail(email);
        if (user == null){
            userService.registerNewUser(email,name, Provider.FACEBOOK);
        }
        else{
            userService.updateUser(user, name, Provider.FACEBOOK);

        }
        super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
    }
}
