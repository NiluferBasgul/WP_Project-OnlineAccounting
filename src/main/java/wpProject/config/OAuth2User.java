package wpProject.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public class OAuth2User implements org.springframework.security.oauth2.core.user.OAuth2User {

    private OAuth2User oAuth2User;

    public OAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public OAuth2User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    public String getEmail(){
        return String.valueOf(oAuth2User.getAttributes());
    }
}
