package Ejercicio1;

import jakarta.persistence.*;

@Entity
@Table(name = "departamentos")
public class Departamento1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_no", nullable = false)
    private Integer id;

    @Column(name = "dnombre", nullable = false, length = 15)
    private String dnombre;

    @Column(name = "loc", nullable = false, length = 15)
    private String loc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDnombre() {
        return dnombre;
    }

    public void setDnombre(String dnombre) {
        this.dnombre = dnombre;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

}