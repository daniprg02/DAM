package Ejercicio1;

import jakarta.persistence.*;

@Entity
@Table(name = "direccion")
public class Direccion {
    @Id
    @Column(name = "idPersona", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPersona", nullable = false)
    private Personaaa personaaa;

    @Column(name = "id")
    private Integer id1;

    @Column(name = "calle")
    private String calle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Personaaa getPersonaaa() {
        return personaaa;
    }

    public void setPersonaaa(Personaaa personaaa) {
        this.personaaa = personaaa;
    }

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

}