package cl.duoc.telopresto.web.services;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String id;
    private String plateCode;
    private String brand;
    private String model;
    private String subsidiary;
    private String color;
    private Integer year;
    private Integer capacity;
    private Integer dailyCost;
    private String type;
    private String image;
    private String imageUrl;
    private String mimeType;
    private String extension;
    private List<CarComment> comments;
}
