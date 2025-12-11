package ProductorConsumidor;
public class Consumidor extends Thread{
	private Cola cola;
	
	public Consumidor (Cola c) {
		cola = c;
		
	}
	
	public void run() {
		int valor = 0;
		for (int i = 0; i<5;i++) {
			valor = cola.get();
			System.out.println(i+ " Consumidor consume "+ valor);
		}
	}

}
