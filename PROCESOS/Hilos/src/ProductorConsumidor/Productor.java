package ProductorConsumidor;
public class Productor extends Thread{

	private final Cola cola;
	
		public Productor (Cola c) {
			cola = c;
		}
		public void run() {
			for (int i = 0;i<5;i++) {
				cola.put(i); //pone el n�mero en la cola
				System.out.println(i+ "=>Productor produce "+ i);
				try {
					sleep(100);
				}catch(InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
			}
		}
		
}
