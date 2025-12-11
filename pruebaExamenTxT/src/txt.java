import java.io.*;

public class txt {
    public static void main(String[] args) {

        try(BufferedReader br = new BufferedReader(new FileReader("examen.txt"))) {
            String linea;
            int contParrafos = 0;
            int contParrafosTotal = 0;

            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                if(linea.isEmpty()) {
                    contParrafos++;
                }

            }

            System.out.println(contParrafos);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
