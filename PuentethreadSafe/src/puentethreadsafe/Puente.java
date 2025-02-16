package puentethreadsafe;

public class Puente {
    // Constantes
    private static final int MAXIMO_PERSONAS_TOTAL = 4;
    private static final int MAXIMO_PERSONAS_SENTIDO = 3;
    private static final int MAXIMO_PESO = 300;
    
    // Variables
    private int numeroPersonas = 0;
    private int pesoPersonas = 0;
    private int personasSentido0 = 0;
    private int personasSentido1 = 0;

    // Constructor
    public Puente() {
    }

    // Getters
    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public int getPesoPersonas() {
        return pesoPersonas;
    }

    // Entrar
    public void entrar(Persona persona) throws InterruptedException {
        synchronized(this) {
            int sentido = persona.getSentido();
            int pesoPersona = persona.getPesoPersona();

            while (
                (numeroPersonas + 1 > MAXIMO_PERSONAS_TOTAL) || (pesoPersonas + pesoPersona > MAXIMO_PESO) ||
                (sentido == 0 && personasSentido0 + 1 > MAXIMO_PERSONAS_SENTIDO) || (sentido == 1 && personasSentido1 + 1 > MAXIMO_PERSONAS_SENTIDO)
            ) {
                System.out.printf("La %s debe esperar.\n", persona.getIdPersona());
                this.wait();
            }

            // Actualizar el estado del puente
            numeroPersonas++;
            pesoPersonas += pesoPersona;

            if (sentido == 0) {
                personasSentido0++;
            } else {
                personasSentido1++;
            }

            System.out.printf("La %s entra en sentido %d. Estado del puente: %d personas, %d kilos.\n",
                    persona.getIdPersona(), sentido, numeroPersonas, pesoPersonas);
        }
    }

    // Salir
    public void salir(Persona persona) {
        synchronized(this) {
            int sentido = persona.getSentido();

            numeroPersonas--;
            pesoPersonas -= persona.getPesoPersona();

            if (sentido == 0) {
                personasSentido0--;
            } else {
                personasSentido1--;
            }

            this.notifyAll();
            System.out.printf("La %s sale. Estado del puente: %d personas, %d kilos.\n",
                    persona.getIdPersona(), numeroPersonas, pesoPersonas);
        }
    }
}