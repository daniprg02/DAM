package sistemaMatriculas;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
/*
Entidad Matricula (tabla: matricula)
- id: Long, PK autogenerada (IDENTITY)
- alumno: Alumno (ManyToOne, FK alumno_id)
- curso: Curso (ManyToOne, FK curso_id)
- fechaAlta: LocalDate, no nulo
- nota: Double, puede ser null, rango 0–10 si no es nulo
 */
@Entity
@Table(name = "matricula")  // Agrega esto
public class MatriculaJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Agrega esto para ID
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private AlumnoJPA alumno;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoJPA curso;

    @Column(nullable = false)  // Agrega para no nulo
    private LocalDate fechaAlta;

    @Min(value = 0, message = "La nota debe ser al menos 0")  // Agrega validaciones
    @Max(value = 10, message = "La nota no debe exceder 10")
    private Double nota;

    public MatriculaJPA() {
    }

    public CursoJPA getCurso() {
        return curso;
    }

    public void setCurso(CursoJPA curso) {
        this.curso = curso;
    }

    public AlumnoJPA getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoJPA alumno) {
        this.alumno = alumno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "MatriculaJPA{" +
                "id=" + id +
                ", alumno=" + alumno +
                ", curso=" + curso +
                ", fechaAlta=" + fechaAlta +
                ", nota=" + nota +
                '}';
    }
}
