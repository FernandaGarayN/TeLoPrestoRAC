package cl.duoc.telopresto.web.controller.car;

import cl.duoc.telopresto.web.services.*;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final SubsidiaryService subsidiaryService;
    private final ReservationService reservationService;
    private List<Integer> listOfYear;
    private List<Map<String, String>> listOfBrands;
    private List<Map<String, String>> listOfSubsidiaries;
    private List<Map<String, String>> listOfCarTypes;

    @PostConstruct
    public void init() {
        listOfCarTypes = carService.getListOfCarTypes();
        listOfSubsidiaries = subsidiaryService.getListOfSubsidiaries();
        listOfBrands = carService.getListOfBrands();
        listOfYear = carService.getListOfYears();
    }

    @GetMapping({"/", "/index"})
    public String showVehicleTypes(Model model) {
        model.addAttribute("carTypes", carService.getTypes());
        model.addAttribute("carSearchForm", CarSearchForm.builder().build());
        return "index";
    }

    @GetMapping("/mantenedor-vehiculos")
    public String getAllCars(ModelMap model) {
        List<Car> cars = carService.findAll();
        cars.forEach(
                car -> {

                    listOfCarTypes.stream()
                            .filter(type -> type.get("id").equals(car.getType()))
                            .findFirst()
                            .ifPresent(
                                    type -> car.setType(type.get("name"))
                            );

                    listOfSubsidiaries.stream()
                            .filter(subsidiary -> subsidiary.get("id").equals(car.getSubsidiary()))
                            .findFirst()
                            .ifPresent(
                                    subsidiary -> car.setSubsidiary(subsidiary.get("name"))
                            );

                    listOfBrands.stream()
                            .filter(brand -> brand.get("id").equals(car.getBrand()))
                            .findFirst()
                            .ifPresent(
                                    brand -> car.setBrand(brand.get("name"))
                            );
                }
        );
        model.addAttribute("cars", cars);
        return "mantenedor-vehiculos";
    }

    @GetMapping("/busqueda-vehiculos")
    public String getBusquedaVehiculos(ModelMap model) {
        model.addAttribute("listOfYears", listOfYear);
        model.addAttribute("listOfBrands", listOfBrands);
        model.addAttribute("listOfSubsidiaries", listOfSubsidiaries);
        model.addAttribute("listOfCarTypes", listOfCarTypes);
        model.addAttribute("carSearchForm", CarSearchForm.builder().build());
        return "busqueda-vehiculos";
    }

    @PostMapping("/busqueda-vehiculos")
    public String postBusquedaVehiculos(ModelMap model,
                                        @Valid @ModelAttribute("carSearchForm") CarSearchForm carSearchForm,
                                        BindingResult bindingResult) {
        model.addAttribute("listOfYears", listOfYear);
        model.addAttribute("listOfBrands", listOfBrands);
        model.addAttribute("listOfSubsidiaries", listOfSubsidiaries);
        model.addAttribute("listOfCarTypes", listOfCarTypes);
        model.addAttribute("carSearchForm", carSearchForm);
        if (!bindingResult.hasErrors()) {
            List<Car> cars = carService.searchCars(carSearchForm);
            cars.forEach(
                    car -> {

                        listOfCarTypes.stream()
                                .filter(type -> type.get("id").equals(car.getType()))
                                .findFirst()
                                .ifPresent(
                                        type -> car.setType(type.get("name"))
                                );

                        listOfSubsidiaries.stream()
                                .filter(subsidiary -> subsidiary.get("id").equals(car.getSubsidiary()))
                                .findFirst()
                                .ifPresent(
                                        subsidiary -> car.setSubsidiary(subsidiary.get("name"))
                                );

                        listOfBrands.stream()
                                .filter(brand -> brand.get("id").equals(car.getBrand()))
                                .findFirst()
                                .ifPresent(
                                        brand -> car.setBrand(brand.get("name"))
                                );
                    }
            );
            model.addAttribute("results", cars);
        }

        return "busqueda-vehiculos";
    }

    @GetMapping("/detalle-vehiculo")
    public String getDetalleVehiculo(ModelMap model, @RequestParam("idVehiculo") String idVehiculo) {
        Car car = carService.findById(idVehiculo);
        List<Reservation> currentByCarId = reservationService.getCurrentByCarId(idVehiculo);
        List<Reservation> reservations = reservationService.findByCarId(idVehiculo);
        reservations.forEach(Reservation::calculateTotal);

        List<CarComment> carComments = reservations.stream()
                .map(Reservation::getComment)
                .filter(Objects::nonNull)
                .toList();

        model.addAttribute("carComments", carComments);
        model.addAttribute("reservations", reservations);
        model.addAttribute("isReserved", !currentByCarId.isEmpty());

        listOfCarTypes.stream()
                .filter(type -> type.get("id").equals(car.getType()))
                .findFirst()
                .ifPresent(
                        type -> car.setType(type.get("name"))
                );

        listOfSubsidiaries.stream()
                .filter(subsidiary -> subsidiary.get("id").equals(car.getSubsidiary()))
                .findFirst()
                .ifPresent(
                        subsidiary -> car.setSubsidiary(subsidiary.get("name"))
                );

        listOfBrands.stream()
                .filter(brand -> brand.get("id").equals(car.getBrand()))
                .findFirst()
                .ifPresent(
                        brand -> car.setBrand(brand.get("name"))
                );

        model.addAttribute("car", car);
        return "detalle-vehiculo";
    }

    @GetMapping("/nuevo-vehiculo")
    public String getNewCar(ModelMap model) {
        model.addAttribute("listOfBrands", listOfBrands);
        model.addAttribute("listOfSubsidiaries", listOfSubsidiaries);
        model.addAttribute("listOfCarTypes", listOfCarTypes);
        model.addAttribute("newCarForm", NewCarForm.builder().build());
        return "nuevo-vehiculo";
    }

    @GetMapping("/editar-vehiculo/{id}")
    public String getEditCar(ModelMap model, @PathVariable("id") String id) {
        Car car = carService.findById(id);
        EditCarForm form = EditCarForm.builder()
                .brand(car.getBrand())
                .model(car.getModel())
                .factoryYear(car.getYear())
                .plateCode(car.getPlateCode())
                .subsidiaryId(car.getSubsidiary())
                .dailyCost(car.getDailyCost())
                .type(car.getType())
                .capacity(car.getCapacity())
                .color(car.getColor())
                .imageUrl(car.getImageUrl())
                .build();
        model.addAttribute("listOfBrands", listOfBrands);
        model.addAttribute("listOfSubsidiaries", listOfSubsidiaries);
        model.addAttribute("listOfCarTypes", listOfCarTypes);
        model.addAttribute("editCarForm", form);
        model.addAttribute("id", id);
        return "editar-vehiculo";
    }

    @PostMapping("/nuevo-vehiculo")
    public String postNewCar(ModelMap model,
                             @Valid @ModelAttribute("newCarForm") NewCarForm newCarForm,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listOfBrands", listOfBrands);
            model.addAttribute("listOfCarTypes", listOfCarTypes);
            model.addAttribute("listOfSubsidiaries", listOfSubsidiaries);
            model.addAttribute("newCarForm", newCarForm);
            return "nuevo-vehiculo";
        }
        carService.save(newCarForm);
        redirectAttributes.addFlashAttribute("successMessage", "El vehículo ha sido guardado exitosamente.");
        return "redirect:/mantenedor-vehiculos";
    }

    @PostMapping("/editar-vehiculo/{id}")
    public String postEditCar(ModelMap model, @PathVariable("id") String id,
                              @Valid @ModelAttribute("editCarForm") EditCarForm editCarForm,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listOfBrands", listOfBrands);
            model.addAttribute("listOfSubsidiaries", listOfSubsidiaries);
            model.addAttribute("listOfCarTypes", listOfCarTypes);
            model.addAttribute("editCarForm", editCarForm);
            return "editar-vehiculo";
        }
        carService.edit(id, editCarForm);
        redirectAttributes.addFlashAttribute("successMessage", "El vehículo ha sido modificado exitosamente.");
        return "redirect:/mantenedor-vehiculos";
    }

    @GetMapping("/eliminar-vehiculo/{id}")
    public String deleteCar(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        carService.deleteCar(id);
        redirectAttributes.addFlashAttribute("successMessage", "El vehículo ha sido eliminado exitosamente.");
        return "redirect:/mantenedor-vehiculos";
    }

    @GetMapping("/cambiar-a-en-mantencion/{id}")
    public String changeToMaintenance(@PathVariable("id") String id, RedirectAttributes redirectAttributes, ModelMap model) {
        carService.changeCarStatusEnMantencion(id);
        redirectAttributes.addFlashAttribute("successMessage", "El vehículo ha sido cambiado a estado de mantención.");
        List<Car> cars = carService.findAll();
        cars.forEach(
                car -> {

                    listOfCarTypes.stream()
                            .filter(type -> type.get("id").equals(car.getType()))
                            .findFirst()
                            .ifPresent(
                                    type -> car.setType(type.get("name"))
                            );

                    listOfSubsidiaries.stream()
                            .filter(subsidiary -> subsidiary.get("id").equals(car.getSubsidiary()))
                            .findFirst()
                            .ifPresent(
                                    subsidiary -> car.setSubsidiary(subsidiary.get("name"))
                            );

                    listOfBrands.stream()
                            .filter(brand -> brand.get("id").equals(car.getBrand()))
                            .findFirst()
                            .ifPresent(
                                    brand -> car.setBrand(brand.get("name"))
                            );
                }
        );
        model.addAttribute("cars", cars);
        return "redirect:/mantenedor-vehiculos";
    }
    @GetMapping("/cambiar-a-disponible/{id}")
    public String changeToAvailable(@PathVariable("id") String id, RedirectAttributes redirectAttributes, ModelMap model) {
        carService.changeCarStatusDisponible(id);
        redirectAttributes.addFlashAttribute("successMessage", "El vehículo ha sido cambiado a estado disponible.");
        List<Car> cars = carService.findAll();
        cars.forEach(
                car -> {

                    listOfCarTypes.stream()
                            .filter(type -> type.get("id").equals(car.getType()))
                            .findFirst()
                            .ifPresent(
                                    type -> car.setType(type.get("name"))
                            );

                    listOfSubsidiaries.stream()
                            .filter(subsidiary -> subsidiary.get("id").equals(car.getSubsidiary()))
                            .findFirst()
                            .ifPresent(
                                    subsidiary -> car.setSubsidiary(subsidiary.get("name"))
                            );

                    listOfBrands.stream()
                            .filter(brand -> brand.get("id").equals(car.getBrand()))
                            .findFirst()
                            .ifPresent(
                                    brand -> car.setBrand(brand.get("name"))
                            );
                }
        );
        model.addAttribute("cars", cars);
        return "redirect:/mantenedor-vehiculos";
    }
}
