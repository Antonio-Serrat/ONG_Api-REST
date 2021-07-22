package com.alkemy.ong.model;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "users")
@Getter @Setter
@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
@NoArgsConstructor
@ApiModel(description = "Detalles sobre usuario")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Identificación única del usuario.")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(notes = "Nombre del usuario.")
	@NotBlank(message = "El Nombre es requerido.")
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@ApiModelProperty(notes = "Apellido del usuario.")
	@NotBlank(message = "El Apellido es requerido.")
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@ApiModelProperty(notes = "Email del usuario.")
	@NotBlank(message = "El Email es requerido.")
	@Email(message = "Email invalido.")
	@Column(nullable = false)
	private String email;

	@ApiModelProperty(notes = "Password del usuario.")
	@Column(nullable = false)
	@NotBlank(message = "La contraseña es requerida.")
	private String password;

	@ApiModelProperty(notes = "Foto del usuario.")
	private String photo;

	@Column(name = "create_date", updatable = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name = "edited_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date edited;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles;

	private Boolean deleted = Boolean.FALSE;

	@ElementCollection(targetClass=GrantedAuthority.class)
	private Collection<? extends GrantedAuthority> authorities;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "news")
	private List<Comment> comments = new ArrayList<>();


	@Builder
	public User(String firstName, String lastName, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.created = new Date();
	}

	public static User build(User user) {
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getRoleName().name()))
				.collect(Collectors.toList());

		return new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), authorities);
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
