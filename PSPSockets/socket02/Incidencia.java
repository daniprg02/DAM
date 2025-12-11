package socket02;

import java.util.Date;

public class Incidencia {
    private Empleado empleado;
    private String titulo;
    private int prioridad;
    private Date fecha;
    private String estado;

    public Incidencia(Empleado empleado, String titulo, int prioridad, Date fecha, String estado) {
        this.empleado = empleado;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "empleado=" + empleado +
                ", titulo='" + titulo + '\'' +
                ", prioridad=" + prioridad +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                '}';
    }
}
