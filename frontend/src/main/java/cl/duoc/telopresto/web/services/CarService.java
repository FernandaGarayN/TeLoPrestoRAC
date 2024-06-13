package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.car.CarClient;
import cl.duoc.telopresto.web.controller.car.CarSearchForm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import cl.duoc.telopresto.web.controller.car.EditCarForm;
import cl.duoc.telopresto.web.controller.car.NewCarForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class CarService {
    private final CarClient carClient;

    public List<Integer> getListOfYears() {
        return carClient.getListOfYears();
    }

    public List<String> getListOfBrands() {
        return carClient.getListOfBrands();
    }

    public List<Car> searchCars(CarSearchForm carSearchForm) {
        return carClient.searchCars(
                carSearchForm.getBrand(),
                carSearchForm.getModel(),
                carSearchForm.getColor(),
                carSearchForm.getType(),
                carSearchForm.getYear(),
                carSearchForm.getSubsidiary(),
                carSearchForm.getPrice());
    }

    public Car findById(String id) {
        return carClient.findById(id).getCar();
    }

    public Car save(NewCarForm form) {
        Car newCar = new Car();
        newCar.setBrand(form.getBrand());
        newCar.setCapacity(form.getCapacity());
        newCar.setDailyCost(form.getDailyCost());
        newCar.setColor(form.getColor());
        newCar.setModel(form.getModel());
        newCar.setPlateCode(form.getPlateCode());
        newCar.setSubsidiary(form.getSubsidiaryId());
        newCar.setType(form.getType());
        newCar.setYear(form.getFactoryYear());
        MultipartFile file = form.getImage();
        try {
            byte[] fileContent = file.getBytes();
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            newCar.setImage(encodedString);

            // Get file extension
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            newCar.setExtension(extension);

            // Get MIME type
            String mimeType = file.getContentType();
            newCar.setMimeType(mimeType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return carClient.save(newCar);
    }

    public Car edit(String id, EditCarForm form) {
        Car newCar = new Car();
        newCar.setBrand(form.getBrand());
        newCar.setCapacity(form.getCapacity());
        newCar.setDailyCost(form.getDailyCost());
        newCar.setColor(form.getColor());
        newCar.setModel(form.getModel());
        newCar.setPlateCode(form.getPlateCode());
        newCar.setSubsidiary(form.getSubsidiaryId());
        newCar.setType(form.getType());
        newCar.setYear(form.getFactoryYear());
        MultipartFile file = form.getImage();
        if (file != null && !file.isEmpty()) {
            try {
                byte[] fileContent = file.getBytes();
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                newCar.setImage(encodedString);

                String originalFilename = file.getOriginalFilename();
                assert originalFilename != null;
                String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                newCar.setExtension(extension);

                String mimeType = file.getContentType();
                newCar.setMimeType(mimeType);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            newCar.setImage(null);
        }
        return carClient.edit(id, newCar).getCar();
    }

    public List<Car> findAll() {
        return carClient.findAll().getCars();
    }

    public void deleteCar(String id) {
        carClient.delete(id);
    }

    public List<Map<String, String>> getListOfCarTypes() {
        return carClient.getTypes().stream().map(type -> Map.of("id", type.getId(), "name", type.getName())).toList();
    }
}
