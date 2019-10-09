package osa.projekat.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import osa.projekat.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByIdIn(List<Long> userIds);
	
	Post findById(Long postId);
	
	Page<Post> findByCreatedBy(Long userId, Pageable pageable);
	
	List<Post> findByIdIn(List<Long> postIds, Sort sort);
	
	long countCreatedBy(Long userId);
	
	

	Page<Post> findAllByOrderByLikesDesc(Pageable pageable);

	@Transactional
	void deleteById(Long postId);

	
}
