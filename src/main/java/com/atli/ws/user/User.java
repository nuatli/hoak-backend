package com.atli.ws.user;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;


@Data
@Entity
public class User implements UserDetails{
	private static final long serialVersionUID = -404791862213939362L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "{hoaxify.constraint.username.NotNull.message}")
	@Size(min=4, max=255)
	//@Column(unique=true)
	@UniqueUsername
	public String username;
	
	@NotNull(message = "{hoaxify.constraint.displayname.NotNull.message}")
	@Size(min=4, max=255)
	private String displayName;
	
	@NotNull(message = "{hoaxify.constraint.password.NotNull.message}")
	//@Pattern(regexp = "^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})",message="{hoaxify.constraint.password.Pattern.message}")
	@Size(min=8, max=255)
	private String password;
	
	@Lob
	private String image;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
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
