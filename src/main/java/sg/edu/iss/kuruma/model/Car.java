package sg.edu.iss.kuruma.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Data
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String brand;
    private String model;
    private Double price;
    private String imgLink;    
    private String link;
    private Double coe;
    private Double mileageKm;
    private Double engineCapacity;
    private String regDate;
    private Integer manufacturedYear;
    private Integer numOfOwner;
    private Double predictedPrice;
    @ManyToMany(mappedBy="wishlist")
    private List<User> user;

	public Car(String brand, String model, Double price, String imgLink, String link, Double coe, Double mileageKm,
			Double engineCapacity, String regDate, Integer manufacturedYear, Integer numOfOwner,
			Double predictedPrice) {
		super();
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.imgLink = imgLink;
		this.link = link;
		this.coe = coe;
		this.mileageKm = mileageKm;
		this.engineCapacity = engineCapacity;
		this.regDate = regDate;
		this.manufacturedYear = manufacturedYear;
		this.numOfOwner = numOfOwner;
		this.predictedPrice = predictedPrice;
	}  

}
 
