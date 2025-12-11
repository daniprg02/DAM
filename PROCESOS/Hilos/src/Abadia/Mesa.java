package Abadia;

import java.util.Arrays;

public class Mesa {

    boolean [] ocupado;
    boolean comer;


    public Mesa() {
        this.ocupado = new boolean[]{true, true, true, true, true};
        this.comer = false;

    }

    public boolean[] getOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean[] ocupado) {
        this.ocupado = ocupado;
    }

    public boolean isComer() {
        return comer;
    }

    public void setComer(boolean comer) {
        this.comer = comer;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "rezar=" + Arrays.toString(ocupado) +
                ", comer=" + comer +
                '}';
    }
}
