package sg.edu.iss.kuruma.model;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Session {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int userid;
	private String username;
	private Car lastViewCar;
	private List<Car> recList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Car getLastViewCar() {
		return lastViewCar;
	}
	public void setLastViewCar(Car lastViewCar) {
		this.lastViewCar = lastViewCar;
	}
	public List<Car> getRecList() {
		return recList;
	}
	public void setRecList(List<Car> recList) {
		this.recList = recList;
	}
	
	

}
