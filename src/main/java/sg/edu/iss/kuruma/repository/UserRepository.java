package sg.edu.iss.kuruma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.kuruma.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findUserByUsername(String username);
}
