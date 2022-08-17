package sg.edu.iss.kuruma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.model.User;
import sg.edu.iss.kuruma.repository.UserRepository;

@Service
public class EmailNotificationService {
	@Autowired
	UserRepository urepo;
	@Autowired
	private JavaMailSender sender;
	private final String kurumaUrl ="http://localhost:8080/cardetail/";
	
	public void sentEmailNotification(Pair<User,Car[]> pair) {
		SimpleMailMessage msg = new SimpleMailMessage();
		User user = pair.getFirst();
		Car newCar = pair.getSecond()[0];
		Car dbCar = pair.getSecond()[1];
		String carLink = kurumaUrl+dbCar.getId();
		
		msg.setTo(user.getEmail());
		msg.setSubject(String.format("Kuruma - Check out this NEW %1s listing", newCar.getModel()));
		msg.setText(String.format("You've received this email because you're subscribed to Kuruma's wishlist notification.\n\n"+
				"------------ NEW listing posted is priced LOWER than your wishlist ------------"+
				"\nModel - %1s"+
				"\nListing price - $%2s"+
				"\nClick on LINK for more details: %3s"+
				"\n\nHope you enjoy using Kuruma service!"+
				"\nKuruma Team7"+
				"\n-------------------------------------------------------------------------------------------"+
				"\n*This e-mail is an automated notification. Please do not reply to this message.", newCar.getModel(), newCar.getPrice().toString(), carLink));
		sender.send(msg);
	}
	
	public Pair<User,Car[]> userWithcarPriceLessThanWishlist(Car car) {
		for (User u : urepo.findAll()) {
			List<Car> wishlistCars = u.getWishlist();
			for (Car c : wishlistCars) {
				if (getModelString(c).equals(getModelString(car)) && car.getPrice().compareTo(c.getPrice()) == -1) {
					Car[] carArr = {car, c};
					return Pair.of(u, carArr);
				}
			}
		}
		return null;
	}
	
	public String getModelString(Car cardetails) {
		String carModel = cardetails.getModel();
		String [] strList = carModel.split(" ");
		return strList[0] + " " + strList[1];
	}

}
