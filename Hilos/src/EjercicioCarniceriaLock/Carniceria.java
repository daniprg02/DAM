package EjercicioCarniceriaLock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Carniceria {
	
	private int id;
	private Lock lock;

	public Carniceria(int id) {
		this.lock = new ReentrantLock();
		this.id = id;
	}
	
	
	public void pedirCarne(Cliente cliente) throws InterruptedException {
		lock.tryLock(10, TimeUnit.MINUTES);
		System.out.println("El cliente "+cliente.getNombre()+ " est� pidiendo 2 kg de chuletas.");
		Thread.sleep(2000);
		lock.unlock();
		System.out.println("El cliente "+cliente.getNombre()+ " ha dejado de pedir.");
		
	}
	
	public void pagar(Cliente cliente) throws InterruptedException {
		lock.tryLock(10, TimeUnit.MINUTES);
		System.out.println("El cliente "+cliente.getNombre()+ " est� pagando su pedido.");
		Thread.sleep(2000);
		lock.unlock();
		System.out.println("El cliente "+cliente.getNombre()+ " ha dejado de pagar.");
	}

	public void despedirse(Cliente cliente) throws InterruptedException {
		lock.tryLock(10, TimeUnit.MINUTES);
		System.out.println("El cliente "+cliente.getNombre()+ " est� dejando la carnicer�a.");
		Thread.sleep(2000);
		lock.unlock();
		System.out.println("El cliente "+cliente.getNombre()+ " ha dejado la carnicer�a.");
	}
}
