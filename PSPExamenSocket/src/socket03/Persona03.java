package socket03;

import java.io.Serializable;

public class Persona03 implements Serializable {
    String id;
    String nombre;
    String horaActual;

    public Persona03(String id, String nombre, String horaActual) {
        this.id = id;
        this.nombre = nombre;
        this.horaActual = horaActual;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHoraActual() {
        return horaActual;
    }

    public void setHoraActual(String horaActual) {
        this.horaActual = horaActual;
    }

    @Override
    public String toString() {
        return "Persona03{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", horaActual='" + horaActual + '\'' +
                '}';
    }
}
