package sg.edu.iss.kuruma.service;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.model.User;

public interface UserService { 
	
	public User getUser(Integer id);
	public void addToWishlist(Car car);
	public void removeFromWishlist(Car car);
	public User findUserByUsername(String name);
	public User findUserById(int userid);

}
