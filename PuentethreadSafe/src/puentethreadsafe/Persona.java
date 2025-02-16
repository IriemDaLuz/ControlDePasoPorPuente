package puentethreadsafe;

public class Persona implements Runnable {

    private final String idPersona;
    private final int tiempoPaso;
    private final int pesoPersona;
    private final int sentido;
    private final Puente puente;

    public Persona(String idPersona, int tiempoPaso, int pesoPersona, int sentido, Puente puente) {
        this.idPersona = idPersona;
        this.tiempoPaso = tiempoPaso;
        this.pesoPersona = pesoPersona;
        this.sentido = sentido;
        this.puente = puente;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public int getTiempoPaso() {
        return tiempoPaso;
    }

    public int getPesoPersona() {
        return pesoPersona;
    }

    public int getSentido() {
        return sentido;
    }
    
    
    @Override
    public void run() {
        System.out.printf("La %s con %d kilos quiere cruzar en %d segundos.\n" + "  Estado del puente: %d personas, %d kilos.\n",
                    idPersona, pesoPersona, tiempoPaso, puente.getNumeroPersonas(), puente.getPesoPersonas());
        
        // Entrar
        try {
            puente.entrar(this);
        } catch (InterruptedException ex) {
        }
        
        // Cruzar
        try {
            Thread.sleep(tiempoPaso * 100);
        } catch (InterruptedException e) {
        }
        
        // Salir
        puente.salir(this);
    }
    
}