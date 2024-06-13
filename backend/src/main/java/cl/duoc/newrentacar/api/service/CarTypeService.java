package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.CarType;
import cl.duoc.newrentacar.api.repository.CarTypeFirebaseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CarTypeService {

  private CarTypeFirebaseRepository carTypeFirebaseRepository;

  @PostConstruct
  public void init() {
    carTypeFirebaseRepository = new CarTypeFirebaseRepository();
  }
  public List<CarType> getAllCarTypes(){
    return carTypeFirebaseRepository.getAllCarTypes();
  }
}
