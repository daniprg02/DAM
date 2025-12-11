package sistemaMatriculas;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.List;

/*
Entidad Curso (tabla: curso)
- id: Long, PK autogenerada (IDENTITY)
- nombre: String (máx. 100), no nulo
- creditos: Integer, no nulo, mínimo 1
- matriculas: List<Matricula> (OneToMany con Matricula)
 */
@Entity
@Table(name = "curso")  // Agrega esto
public class CursoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Agrega esto
    private Long id;
    @Column(length = 100, nullable = false)
    private String nombre;
    @Column(nullable = false)  // Quita columnDefinition, usa @Min para mínimo
    // Agrega validación
    @Min(value = 1, message = "El mínimo tiene que ser 1")
    private Integer creditos;

    // Agrega esta anotación para mapear la relación correctamente
    @OneToMany(mappedBy = "curso")
    private List<MatriculaJPA> matriculas;

    public CursoJPA() {

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

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public List<MatriculaJPA> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<MatriculaJPA> matriculas) {
        this.matriculas = matriculas;
    }


    @Override
    public String toString() {
        return "CursoJPA{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", creditos=" + creditos +
                ", matriculas=" + matriculas +
                '}';
    }
}
