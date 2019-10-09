package osa.projekat.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import osa.projekat.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	public List<Comment> findByPostId(Long postId);
	
	
//    List<Comment> findByUserIdAndPostIdIn(@Param("userId") Long userId, @Param("pollIds") List<Long> pollIds);
//	
//	
//	
//	Page<Long> findCommentedPostIdsByUserId(@Param("userId") Long userId, Pageable pageable);
//
//	
//	Comment findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

}
