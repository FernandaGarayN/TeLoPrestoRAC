package cl.duoc.telopresto.web.controller.car;

import cl.duoc.telopresto.web.services.Car;
import cl.duoc.telopresto.web.services.CarService;
import cl.duoc.telopresto.web.services.SubsidiaryService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final SubsidiaryService subsidiaryService;
    private List<Integer> listOfYear;
    private List<String> listOfBrands;
    private List<String> listOfSubsidiaries;

    @PostConstruct
    public void init() {
        listOfSubsidiaries = subsidiaryService.getListOfSubsidiaries();
        listOfBrands = carService.getListOfBrands();
        listOfYear = carService.getListOfYears();
    }

    @GetMapping("/mantenedor-vehiculos")
    public String getAllCars(ModelMap model) {
        List<Car> cars = carService.findAll();
        model.addAttribute("cars", cars);
        return "mantenedor-vehiculos";
    }

    @GetMapping("/busqueda-vehiculos")
    public String getBusquedaVehiculos(ModelMap model) {
        model.addAttribute("listOfYears", listOfYear);
        model.addAttribute("listOfBrands", listOfBrands);
        model.addAttribute("listOfSubsidiaries", listOfSubsidiaries);
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
        model.addAttribute("carSearchForm", carSearchForm);
        if (!bindingResult.hasErrors()) {
            List<Car> cars = carService.searchCars(carSearchForm);
            model.addAttribute("results", cars);
        }

        return "busqueda-vehiculos";
    }

    @GetMapping("/detalle-vehiculo")
    public String getDetalleVehiculo(ModelMap model, @RequestParam("idVehiculo") String idVehiculo) {
        Car car = carService.findById(idVehiculo);
        model.addAttribute("car", car);
        return "detalle-vehiculo";
    }

    @GetMapping("/nuevo-vehiculo")
    public String getNewCar(ModelMap model) {
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
                //.subsidiaryId(car.getSubsidiary())
                .dailyCost(car.getDailyCost())
                .type(car.getType())
                .capacity(car.getCapacity())
                .color(car.getColor())
                .build();
        model.addAttribute("editCarForm", form);
        model.addAttribute("id", id);
        return "editar-vehiculo";
    }

    @PostMapping("/nuevo-vehiculo")
    public String postNewCar(ModelMap model,
                             @Valid @ModelAttribute("newCarForm") NewCarForm newCarForm,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
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
}
