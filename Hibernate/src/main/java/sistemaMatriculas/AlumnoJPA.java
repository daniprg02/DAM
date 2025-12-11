package sistemaMatriculas;

import jakarta.persistence.*;

import java.util.List;

/*
Entidad Alumno (tabla: alumno)
- id: Long, PK autogenerada (IDENTITY)
- nombre: String (máx. 100), no nulo
- email: String (máx. 150), no nulo, único
- matriculas: List<Matricula> (relación OneToMany bidireccional con cascade ALL y
orphanRemoval)
 */
@Entity
@Table(name = "alumno")  // Agrega esto para el nombre de la tabla
public class AlumnoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Agrega esto para autogeneración
    private Long id;
    @Column(length = 100, nullable = false)
    private String nombre;
    @Column(length = 150, nullable = false, unique = true)
    private String email;
    // Cambia nombre del campo para consistencia (opcional, pero recomendado)
    @OneToMany(mappedBy = "alumno", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<MatriculaJPA> matriculas;  // Cambia "matricula" a "matriculas"

    public AlumnoJPA() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Cambia el nombre del getter para consistencia (de getMatricula a getMatriculas)
    public List<MatriculaJPA> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<MatriculaJPA> matriculas) {
        this.matriculas = matriculas;
    }

    @Override
    public String toString() {
        return "AlumnoJPA{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", matriculas=" + matriculas +
                '}';
    }
}
