package osa.projekat.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import osa.projekat.entity.Post;
import osa.projekat.payload.ApiResponse;
import osa.projekat.payload.PagedResponse;
import osa.projekat.payload.PostRequest;
import osa.projekat.payload.PostResponse;
import osa.projekat.repository.CommentRepository;
import osa.projekat.repository.PostRepository;
import osa.projekat.repository.UserRepository;
import osa.projekat.security.CurrentUser;
import osa.projekat.security.UserPrincipal;
import osa.projekat.service.PostService;
import osa.projekat.util.AppConstants;


@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	
	
	@GetMapping
	public PagedResponse<PostResponse> getPosts(@CurrentUser UserPrincipal currentUser,
												@RequestParam(value="page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
												@RequestParam(value="size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		
		return postService.getAllPosts(currentUser, page, size);
	}
	
	@GetMapping("/popularity")
	public PagedResponse<PostResponse> getAllPostsByPopularity(@CurrentUser UserPrincipal currentUser,
												@RequestParam(value="page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
												@RequestParam(value="size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		
		return postService.getAllPostsByPopularity(currentUser, page, size);
	}
	
	@GetMapping("/{postId}")
	public PostResponse  getPostById(@CurrentUser UserPrincipal currentUser,
									@PathVariable long postId) {
		return postService.getPostById(postId,currentUser);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('OBJAVLJIVAC') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest postRequest) {
		
		Post post = postService.createPost(postRequest);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath().path("/{postId}")
				.buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).body(new ApiResponse(true, "Post created successfully"));
	}
		
	
	@DeleteMapping("/{postId}")
	@PreAuthorize("hasRole('OBJAVLJIVAC') or hasRole('ADMINISTRATOR')")
	public void deletePost(@PathVariable long postId, @CurrentUser UserPrincipal currentUser) {
		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		currentUser = (UserPrincipal) auth.getPrincipal();
//		
//		Post post = postRepository.findById(postId);
		
		
		postRepository.deleteById(postId);
		
		
	}
	
	
	
	
	
	
}





