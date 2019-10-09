package osa.projekat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import osa.projekat.entity.User;
import osa.projekat.payload.SignUpRequest;
import osa.projekat.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	List<User> users = new ArrayList<>();
	
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}
	
	public User getUser(Long id) {
		return userRepository.findOne(id);
	}

	public User updateUser(Long userId, SignUpRequest signUpRequest) {
		
		
		
		List<User> usersis = getAllUsers();
		for(User u : usersis) {
			if(u.getId() == userId) {
				int userIndex = usersis.indexOf(u);
				u.setUsername(signUpRequest.getUsername());
				u.setName(signUpRequest.getName());
				u.setPassword(signUpRequest.getPassword());
				u.setPhoto(signUpRequest.getPhoto());
				usersis.set(userIndex, u);
				return u;
			}
		}
		
		
		return null;
		
		
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
