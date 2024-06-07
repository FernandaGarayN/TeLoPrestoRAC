package cl.duoc.telopresto.web.apiclients.car;

import cl.duoc.telopresto.web.services.Car;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCarsResponse {
    private List<Car> cars;
}
