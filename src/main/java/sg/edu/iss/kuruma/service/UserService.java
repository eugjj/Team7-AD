package sg.edu.iss.kuruma.service;

import java.util.List;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.model.User;

public interface UserService { 
	
	public User getUser(Integer id);
	public void addToWishlist(Car car, String username);
	public void removeFromWishlist(Car car, String username);
	public User findByUsername(String name);
	public User addUser(User user);
	public List<Integer> getWishlist(String username);
	public void save(User u);
	public List<User> showUsers();
	public void deleteUser(String username);
}
