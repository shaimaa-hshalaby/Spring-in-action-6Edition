package tacos.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		
		
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		 // Copy existing authorities and add ROLE_USER
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oauth2User.getAuthorities());
        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Return a new OAuth2User with updated authorities
        return new DefaultOAuth2User(
                mappedAuthorities,
                oauth2User.getAttributes(),
                "name" // key for username attribute (depends on Facebook response)
        );

	}
	
	

}
