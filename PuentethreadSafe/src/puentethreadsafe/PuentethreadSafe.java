package puentethreadsafe;

import java.util.Random;

public class PuentethreadSafe {

    public static void main(String[] args) {
        final int MIN_TIEMPO_LLEGADA = 1;
        final int MAX_TIEMPO_LLEGADA = 30;

        final int MIN_TIEMPO_PASO = 10;
        final int MAX_TIEMPO_PASO = 50;

        final int MIN_PESO_PERSONA = 40;
        final int MAX_PESO_PERSONA = 120;

        final Puente puente = new Puente();
        String idPersona;
        int tiempoLlegada;
        int tiempoPaso;
        int pesoPersona;
        // sentido puede ser 0 o 1
        int sentido;

        int numeroPersona = 0;
        while (true) {
            numeroPersona++;
            idPersona = "Persona " + numeroPersona;
            tiempoLlegada = numeroAleatorio(MIN_TIEMPO_LLEGADA, MAX_TIEMPO_LLEGADA);
            tiempoPaso = numeroAleatorio(MIN_TIEMPO_PASO, MAX_TIEMPO_PASO);
            pesoPersona = numeroAleatorio(MIN_PESO_PERSONA, MAX_PESO_PERSONA);
            sentido = numeroAleatorio(0, 1); 

            System.out.printf("La %s llegará en %d segundos, pesa %d kilos y tardará %d segundos en cruzar en sentido %d.\n",
                    idPersona, tiempoLlegada, pesoPersona, tiempoPaso, sentido);

            Thread hiloPersona = new Thread(new Persona(idPersona, tiempoPaso, pesoPersona, sentido, puente));
            
            try {
                Thread.sleep(tiempoLlegada * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            hiloPersona.start();
        }
    }

    public static int numeroAleatorio(int valorMin, int valorMax) {
        Random r = new Random();
        return valorMin + r.nextInt(valorMax - valorMin + 1);
    }
}