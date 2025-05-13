package mx.uady.sicei.kardex_service.repositories;

import lombok.Data;

@Data
public class Student {
    private String enrollmentId;
    private String name;
    private String lastName;
    private String secondLastName;
    private String email;
}
