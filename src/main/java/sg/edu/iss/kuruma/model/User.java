package sg.edu.iss.kuruma.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {
	@Id	
	private int userid;
	private String username;
	public String email;
	private String password;	
	@OneToMany
	private List<Car> wishlist;
	
	public User(String username, String password) {
        super();
        this.userid = 1;
        this.username = username;
        this.password = password;       
             
    }
	public User(String email) {
		super();
		this.email = email;
	}
}
