package com.alkemy.ong.repository;

import java.util.List;
import java.util.Optional;

import com.alkemy.ong.dto.response.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
	
	Optional<User> findById(Long id);
	
	Optional<User> findByEmail(String email);

	List<UserResponseDto> findAllProjectedBy();
	
	boolean existsByEmail(String email);

	Optional<User> findByFirstName(String firstName);

}
