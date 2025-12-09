package tacos.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private final String username;
	private final String password;
	private final String fullname;
	private final String street;
	private final String city;
	private final String state;
	private final String zip;
	private final String phoneNumber;
	private final String[] userAuthorities;
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		if(userAuthorities == null || userAuthorities.length == 0) {
			return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		}
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for(String role: userAuthorities) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
		return authorities;
		
	}
	
	

	

}
