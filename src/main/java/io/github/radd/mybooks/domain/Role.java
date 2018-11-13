package io.github.radd.mybooks.domain;


import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "role")
public class Role {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
    private Long id;
 
    private String name;
	
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

	public Role() { }

    public Role(final String name) {
        this.name = name;
    }

	public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(final Collection<User> users) {
        this.users = users;
    }

}