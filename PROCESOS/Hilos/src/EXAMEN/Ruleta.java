package EXAMEN;

public class Ruleta extends Thread {
    int numeroGanador;
    Banca banca;


    public Ruleta() {
        numeroGanador = (int) (Math.random()*37+1);
        this.banca = banca;
    }

    public int girarRuleta(){
        notifyAll();
        int nuevoNumeroGanador = (int) (Math.random()*37+1);
        setNumeroGanador(nuevoNumeroGanador);
        return nuevoNumeroGanador;
    }

    public int getNumeroGanador() {
        return numeroGanador;
    }

    public void setNumeroGanador(int numeroGanador) {
        this.numeroGanador = numeroGanador;
    }


}
