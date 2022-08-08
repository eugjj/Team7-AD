package sg.edu.iss.kuruma.service;

import java.util.ArrayList;
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
    public void removeFromWishlist(Car car, String username) {        
    	User user = findByUsername(username);
    	List<Car> wishlist = user.getWishlist();	   	
		wishlist.remove(car);
    	user.setWishlist(wishlist);
    	urepo.saveAndFlush(user);
    }   
    
    @Transactional
	@Override
	public void addToWishlist(Car car, String username) {
    	User user = findByUsername(username);
    	List<Car> wishlist = user.getWishlist();	   	
		wishlist.add(car);
    	user.setWishlist(wishlist);
    	urepo.saveAndFlush(user);
    }		
    
    @Transactional
    @Override
	public User findByUsername(String username) {
		return urepo.findByUsername(username);
	}

    @Transactional
	@Override
	public List<Integer> getWishlist(String username) {
    	List<Integer> wishList = new ArrayList<Integer>();
    	for(Car c:findByUsername(username).getWishlist())
    	{wishList.add(c.getId());}
		return wishList;
	}
	
	@Transactional
	@Override
	public User addUser(User user) {
		return urepo.saveAndFlush(user);
	}

	@Transactional
	@Override
	public void save(User u) {
		urepo.save(u);
	}


}