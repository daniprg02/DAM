package EjercicioCarniceriaLock;
public class Cliente extends Thread{

	private String nombre;
	private Carniceria carniceria;
	

	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Cliente(String nombre, Carniceria c) {
		
		this.nombre = nombre;
		this.carniceria = c;
	}



	@Override
	public void run() {
		System.out.println("El cliente "+ this.getNombre()+ " entra en la carnicer�a.");
		try {
			carniceria.pedirCarne(this);
			carniceria.pagar(this);
			carniceria.despedirse(this);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
