package osa.projekat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import osa.projekat.entity.Comment;
import osa.projekat.entity.Post;
import osa.projekat.entity.User;
import osa.projekat.payload.CommentRequest;
import osa.projekat.repository.PostRepository;
import osa.projekat.repository.UserRepository;
import osa.projekat.security.CurrentUser;
import osa.projekat.security.UserPrincipal;
import osa.projekat.service.CommentService;
@RequestMapping("/api")
@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/posts/{id}/comments")
	public List<Comment> getAllComments(@PathVariable Long id){
		return commentService.getAllComments(id);
	}
	
	@GetMapping("/posts/{postId}/comments/{id}")
	public Comment getComment(@PathVariable Long id) {
		return commentService.getComment(id);
	}
	
	@PostMapping("/posts/{postId}/comments")
	public void addComment(@RequestBody CommentRequest commentRequest, @PathVariable Long postId, @CurrentUser UserPrincipal currentUser) {
		Post post=postRepository.findById(postId);
		
		Comment comment = new Comment();
		comment.setTitle(commentRequest.getTitle());
		comment.setDescription(commentRequest.getDesc());
		comment.setPost(post);
		comment.setLikes(0);
		comment.setDislikes(0);
		comment.setCreatedBy(currentUser.getId());
		commentService.addComment(comment);
	}
	
	
	
//	@PutMapping("/posts/{postId}/comments/{id}")
//	public void updateComment(@RequestBody Comment comment, @PathVariable Integer postId, @PathVariable Integer id) {
//		comment.setPost(new Post(postId, "", "", "", "", "", "", "", "", 0, 0));
//		commentService.updateComment(comment);
//	}
	
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public void deleteComment(@PathVariable Long id) {
		commentService.deleteComment(id);
}
}
