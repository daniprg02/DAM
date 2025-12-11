package Ejercicio2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Check;

import java.util.Date;

@Entity
@Check(constraints = "fechaInicio < fechaFin")
public class Curso {
    @Id
    private Long id;
    String titulo;
    String descripcion;
    Date fechaInicio;
    Date fechaFin;


}
