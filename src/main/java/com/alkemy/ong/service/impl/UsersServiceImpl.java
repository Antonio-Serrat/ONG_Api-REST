package com.alkemy.ong.service.impl;

import javax.json.JsonPatch;
import javax.persistence.EntityNotFoundException;


import com.alkemy.ong.Enum.ERole;
import com.alkemy.ong.dto.response.UserResponseDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RolRepository;

import com.alkemy.ong.dto.request.LoginUsersDto;
import com.alkemy.ong.exception.NotRegisteredException;
import com.alkemy.ong.service.Interface.IFileStore;
import com.alkemy.ong.util.PatchHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alkemy.ong.dto.request.UsersCreationDto;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UsersRepository;
import com.alkemy.ong.security.JwtProvider;
import com.alkemy.ong.service.Interface.IUsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

import java.util.HashSet;

import java.util.List;

import java.util.Locale;
import java.util.Set;

@Service
public class UsersServiceImpl implements IUsersService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper mapper;
	private final MessageSource messageSource;
	private final PatchHelper patchHelper;
	private final RolRepository rolRepository;
	private final JwtProvider jwtProvider;
	private final EmailServiceImpl emailService;
	private final ProjectionFactory projectionFactory;
	private final IFileStore fileStore;

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, ModelMapper mapper, MessageSource messageSource, PatchHelper patchHelper, RolRepository rolRepository, JwtProvider jwtProvider, EmailServiceImpl emailService, ProjectionFactory projectionFactory, IFileStore fileStore) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.mapper = mapper;
		this.messageSource = messageSource;
		this.patchHelper = patchHelper;
		this.rolRepository = rolRepository;
		this.jwtProvider = jwtProvider;
		this.emailService = emailService;
		this.projectionFactory = projectionFactory;
		this.fileStore = fileStore;
	}


	@Override
	public UserResponseDto createUser(UsersCreationDto user) throws IOException {

		if(usersRepository.findByEmail(user.getEmail()).isPresent())
			throw new RuntimeException(messageSource.getMessage("user.error.email.registered", null, Locale.getDefault()));

		User userEntity = User.builder()
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.password(passwordEncoder.encode(user.getPassword()))
				.build();

		Set<Role> roles = new HashSet<>();
		roles.add(rolRepository.findByRoleName(ERole.ROLE_USER).get());
		userEntity.setRoles(roles);
		emailService.registerEmail(user.getEmail());

		User userCreated = usersRepository.save(userEntity);
		
		if(!user.getPhoto().isEmpty())
			userCreated.setPhoto(fileStore.save(userCreated, user.getPhoto()));
		
		return projectionFactory.createProjection(UserResponseDto.class, usersRepository.save(userCreated));
	}

	@Override
	public String loginUser(LoginUsersDto user) throws NotRegisteredException {
		boolean isRegistered = loadUserByUsername(user.getEmail()).getUsername().equals(user.getEmail());
		if(isRegistered)
			return jwtProvider.generatedToken((User) loadUserByUsername(user.getEmail()));
		else
			throw new NotRegisteredException(messageSource.getMessage("login.error.email.not.registered", null, Locale.getDefault()));
	}

	@Override
	public User getUser(String email) {
		User usr = usersRepository.findByEmail(email).orElseThrow(
				() -> new UsernameNotFoundException(
						messageSource.getMessage("user.error.email.not.found", null, Locale.getDefault())
				)
		);
		return usr;
	}

	@Override
	public UserResponseDto updateUser(Long id, UsersCreationDto user) {
		User userEntity = getUserById(id);
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

		if(!user.getPhoto().isEmpty())
			userEntity.setPhoto(fileStore.save(userEntity, user.getPhoto()));

		return projectionFactory.createProjection(UserResponseDto.class, usersRepository.save(userEntity));
	}

	@Override
	public void deleteUser(Long id) {
		User userEntity = getUserById(id);
		fileStore.deleteFilesFromS3Bucket(userEntity);
		usersRepository.delete(userEntity);
	}

	@Override
	public User getUserById(Long id) {
		return usersRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(messageSource.getMessage("user.error.not.found", null, Locale.getDefault()))
		);
	}

	//https://cassiomolin.com/2019/06/10/using-http-patch-in-spring/
	@Override
	public UserResponseDto patchUpdate(Long id, JsonPatch patchDocument) {
		User user = getUserById(id);
		User userPatched = patchHelper.patch(patchDocument, user, User.class);
		userPatched.setEdited(new Date());
		return projectionFactory.createProjection(UserResponseDto.class, userPatched);
	}

	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = usersRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));
		return User.build(user);
	}

	@Override
	public List<UserResponseDto> showAllUsers() {
		return usersRepository.findAllProjectedBy();
	}


}