package EjercicioMonjes;
public class Main {
    
    public static void main(String[] args) {
        Mesa m = new Mesa(5);
        for (int i = 1; i <= 5; i++) {
            Monje monje = new Monje(m, i);
            monje.start();
        }
    }
}
