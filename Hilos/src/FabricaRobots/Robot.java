package FabricaRobots;

public class Robot extends Thread{
    int id;
    Fabrica fabrica;

    public Robot(int id, Fabrica fabrica) {
        this.id = id;
        this.fabrica = fabrica;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "id=" + id +
                '}';
    }

    @Override
    public void run() {
        try {
            fabrica.ensamblar(this);
            fabrica.empaquetar(this);
            fabrica.pintar(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
