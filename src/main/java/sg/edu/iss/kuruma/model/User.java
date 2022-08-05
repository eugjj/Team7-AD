package sg.edu.iss.kuruma.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userid;
	private String username;
	public String email;
	private String password;	
	@OneToMany
	private List<Car> wishlist;
	
	public User(String username, String password, String email) {
        super();
        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        this.username = username;
        this.password = hashedPassword;       
        this.email = email;
    }
	
	public User(String email) {
		super();
		this.email = email;
	}
}
