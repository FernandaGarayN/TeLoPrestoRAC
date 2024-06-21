package cl.duoc.newrentacar.api.endpoint.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String method;
    private Integer amount;
    private String date;
}
