package osa.projekat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import osa.projekat.entity.Comment;
import osa.projekat.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	List<Comment> comments = new ArrayList<>();
	
	public List<Comment> getAllComments(Long postId) {
		List<Comment> comments = new ArrayList<>();
		commentRepository.findByPostId(postId)
		.forEach(comments::add);
		return comments;
	}
	
	public Comment getComment(Long id) {
		
		return commentRepository.findOne(id);
	}
	
	public void addComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public void updateComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public void deleteComment(Long id) {
		commentRepository.delete(id);
	}
	
	
	
	
	
	
	
}
