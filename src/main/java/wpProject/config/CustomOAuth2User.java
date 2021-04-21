package wpProject.config;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User extends OAuth2User {
    private final OAuth2User oAuth2User;

    public CustomOAuth2User(OAuth2User oAuth2User) {
        super();
        this.oAuth2User = oAuth2User;
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
        return String.valueOf(oAuth2User.getAttributes());
    }


    public String getEmail() {
        return String.valueOf(oAuth2User.getAttributes());
    }

}
