package sg.edu.iss.kuruma.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    
    public Car(String brand, String model, Double price, String imgLink, String link) {
        super();
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.imgLink = imgLink;
        this.link = link;
    }

 

        
    

 

}
