package Ejercicio1;

import jakarta.persistence.*;

@Entity
@Table(name = "tarjeta_acceso")
public class TarjetaAcceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String codigoAcceso;
    
    private boolean activa;

    @OneToOne
    @JoinColumn(name = "empleado_id", unique = true)
    private Empleado empleado;

    public TarjetaAcceso(String codigoAcceso, boolean activa) {
        this.codigoAcceso = codigoAcceso;
        this.activa = activa;
    }

    public TarjetaAcceso() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
