package wpProject.service.Impl;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import wpProject.config.CustomOAuth2User;
import wpProject.config.OAuth2User;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public org.springframework.security.oauth2.core.user.OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = (OAuth2User) super.loadUser(userRequest);
        return new CustomOAuth2User(user);
    }
}
