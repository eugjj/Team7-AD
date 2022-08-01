package sg.edu.iss.kuruma.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.kuruma.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
	@Query("SELECT c from Car c WHERE c.brand = :brand")
    ArrayList<Car> findByBrand(@Param("brand") String brand);
    
    @Query("SELECT c from Car c WHERE c.brand = :model")
    ArrayList<Car> findByModel(@Param("model") String model);
    
    @Query("SELECT c from Car c WHERE c.model LIKE %?1%" +
    				"OR c.brand LIKE %?1%" +
    				"OR c.price LIKE %?1%")
    List<Car> findSearch(String entry);
}
 
