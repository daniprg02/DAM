package CombateBoxeo;

import java.util.concurrent.ThreadLocalRandom;

public class Ring {
    Boxeador boxeador;
    Boxeador boxeador2;
    Arbitro arbitro;
    Boolean combateComenzado;
    Boolean combateFinalizado;
    String ganador;

    public Ring (Boxeador boxeador, Boxeador boxeador2, Arbitro arbitro){
        this.boxeador = boxeador;
        this.boxeador2 = boxeador2;
        this.arbitro = arbitro;
        combateComenzado = false;
        combateFinalizado = false;
    }

    public synchronized void arbitroInicia (){
        combateComenzado = true;
        System.out.println("Combate comenzado!!!");
        notifyAll();
    }

    public synchronized void arbitroFinaliza () throws InterruptedException {
        while(!combateFinalizado){
            wait();
        }

        System.out.println("El ganador del combate es: " + ganador);

    }


    public synchronized void comenzarCombate(Boxeador atacante, Boxeador oponente) throws InterruptedException {
        while (!combateComenzado) {
            System.out.println("Esperando a comenzar el combate");
            wait();

        }

        while(!combateFinalizado) {
            //int golpe = (int) (Math.random() * 10 + 1);
            // Evitar condiciones de carrera con sincronización interna
            int golpe = ThreadLocalRandom.current().nextInt(1, 11);

            System.out.println("El golpe que lanza " + atacante.getNombre() + " se aplica con una fuerza de " + golpe);

            if (oponente.getSalud() > 0 && atacante.getSalud() > 0) {
                oponente.setSalud(oponente.getSalud() - golpe);
                System.out.println("Al boxeador " + oponente.getNombre() + " le queda " + oponente.getSalud() + " puntos de vida");
            }
            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 500));

            if (oponente.getSalud() <= 0 || atacante.getSalud() <= 0) {

                combateFinalizado = true;
                if (atacante.getSalud() > oponente.getSalud()) {
                    ganador = atacante.getNombre();
                } else {
                    ganador = oponente.getNombre();
                }
                notifyAll();
            }
            if(combateFinalizado){
                break;
            }

            // 🔹 ⚙️ Aquí podrías escalar a varios combates o más boxeadores
            /*
             * 🔸 Si quisieras ampliar este ring a más de 2 boxeadores:
             *
             * private List<Boxeador> boxeadores;
             *
             * - Podrías crear un bucle que empareje de dos en dos:
             *   for (int i = 0; i < boxeadores.size(); i += 2) {
             *       Boxeador a = boxeadores.get(i);
             *       Boxeador b = boxeadores.get(i + 1);
             *       iniciarCombate(a, b); // metodo auxiliar que use esta misma lógica
             *   }
             *
             * 🔸 O podrías usar un Semaphore para limitar el nº de combates simultáneos.
             *
             * 🔸 O incluso una clase "Torneo" que tenga varios rings en paralelo.
             */
        }

    }
}

