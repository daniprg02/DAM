package BibliotecaProfesor;



import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;


public class Book {

    private int id;

    boolean ocupado;

    public Book(int id, boolean ocupado) {

        this.id = id;
        this.ocupado = ocupado;
    }

    synchronized public void read(Student student) throws InterruptedException {

        while(this.ocupado) {
            wait();

        }

        System.out.println(student + " empieza a leer " + this);
        Thread.sleep(2000);
        this.ocupado = true;
        System.out.println(student + " ha finalizado de leer " + this);
    }
    synchronized public void dejarLeer(Student student) throws InterruptedException{
        this.ocupado = false;
        notifyAll();
    }

    public String toString() {
        return "Libro " + id;
    }

}