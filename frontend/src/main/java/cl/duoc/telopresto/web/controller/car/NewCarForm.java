package cl.duoc.telopresto.web.controller.car;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
@Setter
public class NewCarForm {
    private String plateCode;
    private String brand;
    private String model;
    private String subsidiaryId;
    private String color;
    private Integer factoryYear;
    private Integer capacity;
    private Integer dailyCost;
    private String type;
    private MultipartFile image;
}
