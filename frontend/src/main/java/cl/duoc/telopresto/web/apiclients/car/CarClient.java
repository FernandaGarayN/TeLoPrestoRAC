package cl.duoc.telopresto.web.apiclients.car;

import cl.duoc.telopresto.web.config.feign.FeignCarConfig;
import cl.duoc.telopresto.web.services.Car;
import cl.duoc.telopresto.web.services.CarBrand;
import cl.duoc.telopresto.web.services.CarType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "car-client",
        url = "${spring.properties.feign.cars}",
        configuration = FeignCarConfig.class)
public interface CarClient {
    @GetMapping("/searching")
    List<Car> searchCars(
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "subsidiary", required = false) String subsidiary,
            @RequestParam(value = "price", required = false) Integer price);

    @GetMapping("/{id}")
    GetCarResponse findById(@PathVariable String id);

    @GetMapping("/years")
    List<Integer> getListOfYears();

    @GetMapping("/brands")
    List<CarBrand> getListOfBrands();

    @PostMapping("")
    Car save( @RequestBody Car newCar);

    @GetMapping()
    GetCarsResponse findAll();

    @PutMapping("/{id}")
    GetCarResponse edit(@PathVariable("id") String id, @RequestBody Car aCar);

    @DeleteMapping("{id}")
    GetCarResponse delete(@PathVariable String id);

    @GetMapping("/types")
    List<CarType> getTypes();
}
