package sg.edu.iss.kuruma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.model.User;
import sg.edu.iss.kuruma.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository urepo;

	@Transactional
    @Override
    public User getUser(Integer id) {        
            return urepo.findById(id).orElse(null);
    } 
    @Transactional
    @Override
    public void removeFromWishlist(Car car) {        
    	User user = urepo.findById(1).get();
    	List<Car> wishlist = user.getWishlist();	   	
		wishlist.remove(car);
    	user.setWishlist(wishlist);
    	urepo.saveAndFlush(user);
    }   
    
    @Transactional
	@Override
	public void addToWishlist(Car car) {
    	User user = urepo.findById(1).get();
    	List<Car> wishlist = user.getWishlist();	   	
		wishlist.add(car);
    	user.setWishlist(wishlist);
    	urepo.saveAndFlush(user);
    }		
    
    @Override
	public User findUserByUsername(String username) {
		return urepo.findUserByUsername(username);
	}

	@Override
	public User findUserById(int userid) {
		return urepo.findById(userid).get();
	}

}