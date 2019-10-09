package osa.projekat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import osa.projekat.entity.User;
import osa.projekat.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    UserRepository userRepository;
	
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
				
		
		return UserPrincipal.create(user);
		
	}
	
	@Transactional
	public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id);

        return UserPrincipal.create(user);
    }

}
