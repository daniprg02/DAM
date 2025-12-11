package Ejercicio2;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Profesor {
    @Id
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String email;
    private Date fechaContrato;
    @Column(name = "texto")
    private String especialidad;

    @OneToMany(mappedBy = "profesor")
    private List <Curso> cursos;


    public Profesor() {

    }
}
