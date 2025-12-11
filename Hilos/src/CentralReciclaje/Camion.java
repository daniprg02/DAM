package CentralReciclaje;

public class Camion implements Runnable {
    String matricula;
    Central central;

    public Camion(String matricula, Central homero) {
        this.matricula = matricula;
        this.central = homero;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }


    @Override
    public String toString() {
        return "Camion{" +
                "matricula='" + matricula + '\'' +
                '}';
    }

    @Override
    public void run() {
        try {
            while (true) {
                central.descargarResiduos(this);
                central.clasificarResiduos(this);
                central.compactarResiduos(this);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
