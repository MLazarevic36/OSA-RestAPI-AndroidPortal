package osa.projekat.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import osa.projekat.entity.User;
import osa.projekat.payload.PagedResponse;
import osa.projekat.payload.PostResponse;
import osa.projekat.payload.SignUpRequest;
import osa.projekat.payload.UserIdentityAvailability;
import osa.projekat.payload.UserSummary;
import osa.projekat.repository.CommentRepository;
import osa.projekat.repository.PostRepository;
import osa.projekat.repository.UserRepository;
import osa.projekat.security.CurrentUser;
import osa.projekat.security.JwtTokenProvider;
import osa.projekat.security.UserPrincipal;
import osa.projekat.service.PostService;
import osa.projekat.service.UserService;
import osa.projekat.util.AppConstants;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
    private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	private JwtTokenProvider tokenProvider;
	
	@Autowired 
	private CommentRepository commentRepository;
	
	@Autowired
	private PostService postService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser ) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		currentUser = (UserPrincipal) auth.getPrincipal();
	
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), currentUser.getPhoto());
        return userSummary;
    }
	
	@GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }
	
	@GetMapping("/users/{username}/posts")
    public PagedResponse<PostResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return postService.getPostsCreatedBy(username, currentUser, page, size);
    }
	
	@PutMapping("/users/{userId}")
	public void updateUser (@RequestBody SignUpRequest signUpRequest, @PathVariable Long userId) {
		
		User user = userRepository.findById(userId);
		user.setId(userId);
		user.setName(signUpRequest.getName());
		user.setUsername(signUpRequest.getUsername());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setPhoto(signUpRequest.getPhoto());
		userRepository.save(user);
		
		
		
	}
	
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	
	
	
	
	
	
}
