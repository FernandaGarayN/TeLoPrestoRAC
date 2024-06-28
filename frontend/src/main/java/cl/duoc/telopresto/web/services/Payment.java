package cl.duoc.telopresto.web.services;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Payment {
    private String reservation;
    private String amount;
    private String date;
    private String method;
    private String status;
}
