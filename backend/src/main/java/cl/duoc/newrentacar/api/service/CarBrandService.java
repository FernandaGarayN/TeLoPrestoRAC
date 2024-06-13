package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.CarBrand;
import cl.duoc.newrentacar.api.repository.CarBrandFirebaseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CarBrandService {
  private CarBrandFirebaseRepository carBrandFirebaseRepository;

  @PostConstruct
  public void init() {
    carBrandFirebaseRepository = new CarBrandFirebaseRepository();
  }

  public List<CarBrand> getAllBrands() {
    return carBrandFirebaseRepository.getAllBrands();
  }
}
