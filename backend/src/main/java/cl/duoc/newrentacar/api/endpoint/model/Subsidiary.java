package cl.duoc.newrentacar.api.endpoint.model;

import com.google.firebase.database.PropertyName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subsidiary {
    private String id;
    private String name;
    private String address;
    private int phoneNumber;
}
