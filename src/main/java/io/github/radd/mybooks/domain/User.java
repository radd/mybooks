package io.github.radd.mybooks.domain;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user")
public class User {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
    private Long id;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "last_name")
    private String lastName;
	
    private String email;
	
    private String password;
 
    @ManyToMany
    @JoinTable( 
        name = "user_role", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "user_id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "role_id")) 
    private Collection<Role> roles;
	
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
}