package osa.projekat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import osa.projekat.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	

    List<User> findByIdIn(List<Long> userIds);

    User findByUsername(String username);
    

    Boolean existsByUsername(String username);

	User findById(Long id);


}
