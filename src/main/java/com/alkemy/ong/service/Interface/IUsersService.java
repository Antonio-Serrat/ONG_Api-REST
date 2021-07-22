package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.request.LoginUsersDto;
import com.alkemy.ong.dto.request.UsersCreationDto;
import com.alkemy.ong.dto.response.UserResponseDto;
import com.alkemy.ong.exception.NotRegisteredException;
import com.alkemy.ong.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.json.JsonPatch;
import java.io.IOException;
import java.util.List;


public interface IUsersService extends UserDetailsService {

	UserResponseDto createUser(UsersCreationDto user) throws IOException;

	User getUser(String email);

	UserResponseDto updateUser(Long id, UsersCreationDto user);

	void deleteUser(Long id);

	User getUserById(Long id);

	UserResponseDto patchUpdate(Long id, JsonPatch patchDocument);

	UserDetails loadUserByUsername(String email);

	String loginUser(LoginUsersDto user) throws NotRegisteredException;

	List<UserResponseDto> showAllUsers();


}
