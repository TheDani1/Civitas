package civitas;

import java.util.Random;

public class Dado {
    private Random random;                     //Genera numeros aleatorios
    private int ultimoResultado;
    private boolean debug = true;
    private static Dado instance = new Dado(); //Unica instancia de Dado que 
                                               //existe en el juego
    private final int SalidaCarcel = 5;
    
    static public Dado getInstance() {         //Consultor asociado a instance
        return instance;
    }
    
    /**
     * Constructor privado y sin argumentos
     * Inicializa todos los atributos de instancia
     */
    private Dado(){
       this.ultimoResultado = 0;
       this.debug = false;
       random = new Random();
    }
    
    /**
     * Devuelve el resultado de tirar un dado
     * @return tirada
     */
    int tirar(){
        int tirada;
        if(debug){
            tirada = 1;    
        }
        else{
            tirada = random.nextInt(6) + 1;
        }
        ultimoResultado = tirada;
        return tirada;
    }
    
    /**
     * Tira el dado y devuelve true si con ese numero se puede salir de 
     * la carel (numero==SalidaCarcel)
     * @return 
     */
    boolean salgoDeLaCarcel(){
        return tirar() == SalidaCarcel;   
    }
    
    /**
     * Devuelve que jugador empieza
     * @param n : 
     * @return a
     */
    int quienEmpieza(int n){
        int a = random.nextInt(n);
        return a;
    }
    /**
     * Cambia de modo el debug y deja constancia en el Diario
     * @param d : 
     */
    void setDebug(boolean d){
        this.debug = d; 
        if(debug){
            Diario.getInstance().ocurreEvento("debug del dado true");
        }
        else{
            Diario.getInstance().ocurreEvento("debug del dado false");
        }
    }
    
    /**
     * Consultor de UltimoResultado
     * @return 
     */
    int getUltimoResultado(){
        return this.ultimoResultado;
    }
}
