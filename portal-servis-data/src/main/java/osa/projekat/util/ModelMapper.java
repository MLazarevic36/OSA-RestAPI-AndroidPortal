package osa.projekat.util;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import osa.projekat.entity.Post;
import osa.projekat.entity.User;
import osa.projekat.payload.PostResponse;
import osa.projekat.payload.UserSummary;
import osa.projekat.repository.UserRepository;
import osa.projekat.security.UserPrincipal;
import osa.projekat.service.UserService;
import osa.projekat.payload.CommentResponse;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class ModelMapper {
	
	
	public static PostResponse mapPostToPostResponse(Post post, User creator){
		
		
		
		
		PostResponse postResponse = new PostResponse();
		postResponse.setId(post.getId());
		postResponse.setTitle(post.getTitle());
		postResponse.setDesc(post.getDescription());
		postResponse.setPhoto(post.getPhoto());
		postResponse.setCreationDateTime(post.getCreatedAt());
		postResponse.setLikes(post.getLikes());
		postResponse.setDislikes(post.getDislikes());
		postResponse.setTags(post.getTags());
		postResponse.setLocation(post.getLocation());
		
		List<CommentResponse> commentResponses = post.getComments().stream().map(comment -> {
			CommentResponse commentResponse = new CommentResponse();
			commentResponse.setId(comment.getId());
			commentResponse.setTitle(comment.getTitle());
			commentResponse.setDesc(comment.getDescription());
			commentResponse.setCreationDateTime(comment.getCreatedAt());
			
			UserSummary commentCreator = new UserSummary(comment.getCreatedBy(), "", "", "");
			commentResponse.setCreatedBy(commentCreator);
			commentResponse.setLikes(comment.getLikes());
			commentResponse.setDislikes(comment.getDislikes());
			
			return commentResponse;
		}).collect(Collectors.toList());
			
			
		postResponse.setComments(commentResponses);
		
		UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName(), creator.getPhoto());
        postResponse.setCreatedBy(creatorSummary);
        
        

			
		
		
		return postResponse;
	
	
	}

	
	
}
