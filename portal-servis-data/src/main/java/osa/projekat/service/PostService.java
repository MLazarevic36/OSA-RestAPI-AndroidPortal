package osa.projekat.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import osa.projekat.util.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import osa.projekat.entity.Comment;
import osa.projekat.entity.Post;
import osa.projekat.entity.User;
import osa.projekat.exception.BadRequestException;
import osa.projekat.exception.ResourceNotFoundException;
import osa.projekat.payload.CommentRequest;
import osa.projekat.payload.PagedResponse;
import osa.projekat.payload.PostRequest;
import osa.projekat.payload.PostResponse;
import osa.projekat.repository.CommentRepository;
import osa.projekat.repository.PostRepository;
import osa.projekat.repository.UserRepository;
import osa.projekat.security.UserPrincipal;
import osa.projekat.util.ModelMapper;


@Service
public class PostService {
	
	@Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
	
	public PagedResponse<PostResponse> getAllPosts(UserPrincipal currentUser, int page, int size) {
		
		validatePageNumberAndSize(page, size);
		
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createdAt");
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		if(posts.getNumberOfElements() == 0) {
			return new PagedResponse<> (Collections.emptyList(), posts.getNumber(),
					posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
		}
		
		List<Long> postIds = posts.map(Post::getId).getContent();
//		Map<Long, Long> postUserCommentMap = getPostUserCommentMap(currentUser, postIds);
		Map<Long, User> creatorMap = getPostCreatorMap(posts.getContent());
		
		List<PostResponse> postResponses = posts.map(post ->  {
			return ModelMapper.mapPostToPostResponse(post, creatorMap.get(post.getCreatedBy()));
		}).getContent();
		
		return new PagedResponse<>(postResponses, posts.getNumber(),
				posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());

		
	}
	

	public Post createPost(PostRequest postRequest) {
		Post post = new Post();
		post.setTitle(postRequest.getTitle());
		post.setDescription(postRequest.getDescription());
		post.setLikes(0);
		post.setDislikes(0);
		post.setLocation("Novi Sad");
		post.setPhoto(postRequest.getPhoto());
		post.setTags("tagovi");
		post.setComments(null);
		
		return postRepository.save(post);
	}
	
	public PostResponse getPostById(Long postId, UserPrincipal currentUser) {
		Post post = postRepository.findById(postId);
		
		User creator = userRepository.findById(post.getCreatedBy());
		
		Comment userComment = null;
		
		
		
		if(currentUser != null) {
		//	userComment = commentRepository.findByUserIdAndPostId(currentUser.getId(), postId);
		}
		
		return ModelMapper.mapPostToPostResponse(post, creator);
		
		
	}
	
	
	public PagedResponse<PostResponse> getPostsCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
		validatePageNumberAndSize(page, size);
		
		User user = userRepository.findByUsername(username);
		
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createdAt");
		Page<Post> posts = postRepository.findByCreatedBy(user.getId(), pageable);
		
		if(posts.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), posts.getNumber(),
					posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
		}
		
		List<Long> postIds = posts.map(Post::getId).getContent();
		
		List<PostResponse> postResponses = posts.map(post -> {
			return ModelMapper.mapPostToPostResponse(post, user);
		}).getContent();
		
		return new PagedResponse<>(postResponses, posts.getNumber(),
				posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
	}
	
	
	public PostResponse castCommentAndGetUpdatedPost(Long postId, CommentRequest commentRequest, UserPrincipal currentUser){
		
		Post post = postRepository.findById(postId);
		
		User user = userRepository.getOne(currentUser.getId());
		
		Comment comment = new Comment();
	
		comment.setPost(post);
		comment.setTitle(commentRequest.getTitle());
		comment.setDescription(commentRequest.getDesc());
		comment.setCreatedBy(user.getId());;
		post.addComment(comment);
		
		comment = commentRepository.save(comment);
		
		User creator = userRepository.findById(post.getCreatedBy());
		
		return ModelMapper.mapPostToPostResponse(post, creator);
		
		
		
	}
	
	
	
	
	
	
	
	
	private void validatePageNumberAndSize(int page, int size) {
		if(page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}
		
		if(size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

	
	
	 private Map<Long, Long> getPostUserCommentMap(UserPrincipal currentUser, List<Long> postIds) {
	        // Retrieve Votes done by the logged in user to the given pollIds
	        Map<Long, Long> postUserCommentMap = null;
	        if(currentUser != null) {
	            //List<Comment> userComments = commentRepository.findByUserIdAndPostIdIn(currentUser.getId(), postIds);

	            //postUserCommentMap = userComments.stream()
	              //      .collect(Collectors.toMap(comment -> comment.getPost().getId(), comment -> comment.getId()));
	        }
	        return postUserCommentMap;
	    }
	 
	 
	 Map<Long, User> getPostCreatorMap(List<Post> posts) {
	        // Get Poll Creator details of the given list of polls
	        List<Long> creatorIds = posts.stream()
	                .map(Post::getCreatedBy)
	                .distinct()
	                .collect(Collectors.toList());

	        List<User> creators = userRepository.findByIdIn(creatorIds);
	        Map<Long, User> creatorMap = creators.stream()
	                .collect(Collectors.toMap(User::getId, Function.identity()));

	        return creatorMap;
	    }
	 
	 public PagedResponse<PostResponse> getAllPostsByPopularity(UserPrincipal currentUser, int page, int size) {
			
			validatePageNumberAndSize(page, size);
			
			Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createdAt");
			
			Page<Post> posts = postRepository.findAllByOrderByLikesDesc(pageable);
			
			if(posts.getNumberOfElements() == 0) {
				return new PagedResponse<> (Collections.emptyList(), posts.getNumber(),
						posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
			}
			
			List<Long> postIds = posts.map(Post::getId).getContent();
//			Map<Long, Long> postUserCommentMap = getPostUserCommentMap(currentUser, postIds);
			Map<Long, User> creatorMap = getPostCreatorMap(posts.getContent());
			
			List<PostResponse> postResponses = posts.map(post ->  {
				return ModelMapper.mapPostToPostResponse(post, creatorMap.get(post.getCreatedBy()));
			}).getContent();
			
			return new PagedResponse<>(postResponses, posts.getNumber(),
					posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());

			
		}
	
}
