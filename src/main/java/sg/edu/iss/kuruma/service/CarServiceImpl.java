package sg.edu.iss.kuruma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {
	@Autowired
    CarRepository crepo;
    
    @Transactional
    public void saveCar(Car car) {        
            crepo.save(car);
    }

    @Transactional
    public void deleteCar(Car car) {        
            crepo.delete(car);
    }

    @Transactional
    public List<Car> listCar() {
        return crepo.findAll();
    }

    @Transactional
    public List<Car> findByBrand(String brand) {
        return crepo.findByBrand(brand);
    }

    @Transactional
    public List<Car> findByModel(String model) {
        return crepo.findByModel(model);
    }
    
    @Transactional
    public boolean saveFacility(Car car) {
        if(crepo.save(car)!=null) return true; 
        else return false;
    }

    @Override
    @Transactional
    public Car findById(Integer id) {    
        return crepo.findById(id).orElse(null);
    }

	@Override
	@Transactional
	public List<Car> findSearchByEntry(String entry) {
		return crepo.findSearch(entry);
	}

	@Override
	public List<Car> findAllCars() {
		return crepo.findAll();
	}
	
	@Override
	public List<Car> sortSearchByPrice(String entry) {
		return crepo.fndSearchSortByPrice(entry);
	}
}
