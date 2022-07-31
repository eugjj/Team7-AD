package sg.edu.iss.kuruma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import sg.edu.iss.kuruma.model.User;
import sg.edu.iss.kuruma.repository.UserRepository;

@Service
public class EmailNotificationService {
	@Autowired
	UserRepository urepo;
	@Autowired
	private JavaMailSender sender;
	
	public void sentEmailNotification(User user) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(user.getEmail());
		msg.setSubject("testing");
		msg.setText("testing testing");
		sender.send(msg);
	}

}
