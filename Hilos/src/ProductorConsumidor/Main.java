package ProductorConsumidor;
public class Main {

	public static void main(String[] args) {
		
		Cola cola = new Cola();
		Productor p = new Productor (cola);
		p.start();
		Consumidor c = new Consumidor (cola);
		c.start();

	}

}
