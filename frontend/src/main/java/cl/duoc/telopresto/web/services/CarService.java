package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.car.CarClient;
import cl.duoc.telopresto.web.controller.car.CarSearchForm;
import java.util.List;

import cl.duoc.telopresto.web.controller.car.NewCarForm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CarService {
  private final CarClient carClient;

 public List<Integer> getListOfYears(){
   return carClient.getListOfYears();
 }
    public List<String> getListOfBrands(){
        return carClient.getListOfBrands();
    }

  public List<Car> searchCars(CarSearchForm carSearchForm) {
    return carClient.searchCars(
        carSearchForm.getBrand(),
        carSearchForm.getModel(),
        carSearchForm.getColor(),
        carSearchForm.getYear(),
        carSearchForm.getSubsidiary(),
        carSearchForm.getPrice());
  }

  public Car findById(Integer id){
    return carClient.findById(id).getCar();
  }
  public Car save(NewCarForm form){
     Car newCar = new Car();
     newCar.setBrand(form.getBrand());
     newCar.setCapacity(form.getCapacity());
     newCar.setCost(form.getDailyCost());
     newCar.setColor(form.getColor());
     newCar.setModel(form.getModel());
     newCar.setPlateCode(form.getPlateCode());
     newCar.setSubsidiaryId(form.getSubsidiaryId());
     newCar.setType(form.getType());
     newCar.setYear(form.getFactoryYear());
     newCar.setImage(form.getImage());
     return carClient.save(newCar);
  }

    public List<Car> findAll() {
        return carClient.findAll().getCars();
    }
}
