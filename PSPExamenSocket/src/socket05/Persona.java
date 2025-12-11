package socket05;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int edad;
    private String descripcion;
    private LocalDateTime horaRegistro;

    public Persona(String nombre, int edad, String descripcion) {
        this.nombre = nombre;
        this.edad = edad;
        this.descripcion = descripcion;
        this.horaRegistro = LocalDateTime.now();
    }

    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getHoraRegistro() { return horaRegistro; }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", descripcion='" + descripcion + '\'' +
                ", horaRegistro=" + horaRegistro +
                '}';
    }
}
