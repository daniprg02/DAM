package PreExamen01;

public class Persona {
    String id ;
    String nombre;
    String hora;

    public Persona(String id, String nombre,  String hora) {
        this.id = id;
        this.nombre = nombre;
        this.hora = hora;

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

    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Persona{" + "id=" + id + ", nombre=" + nombre + ", hora=" + hora + '}';
    }
}
