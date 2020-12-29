
package civitas;

import java.util.Random;

/**
 *
 * @author Daniel
 * 
 */
public class Dado {
    
    // Atributos de instancia
    private Random random = new Random();
    private int ultimoResultado;
    private boolean debug;
    
    //Atributos de clase
    static final private Dado instance = new Dado();
    private final int SalidaCarcel = 5;
    
    private Dado(){
        debug = false;
        
        ultimoResultado = 0;
    }
    
    public static Dado getInstance(){
        return instance;
    }
    
    int tirar (){
        Random r1 = new Random ();
        
        ultimoResultado = 1;
        if (!debug)
            ultimoResultado = r1.nextInt(7);
        
        return ultimoResultado;
    }
    
    boolean salgoDeLaCarcel (){
        boolean salgo = false;
        
        if (tirar() >= 5)
                salgo = true;
        
        return salgo;
    }
    
    int quienEmpieza (int n){
        Random r = new Random();    
        int este_sale = r.nextInt(n) + 1;
        return este_sale;
    }
    
    public void setDebug (Boolean d){
        
        if (d){
            debug = true;
            Diario.getInstance().ocurreEvento("\n[DIARIO] Debug esta activado");
        }
        else {
            debug = false;
            Diario.getInstance().ocurreEvento("\n[DIARIO] Debug esta desactivado");

        }
        
    }
    int getUltimoResultado(){
        return ultimoResultado;
    }
    
    
}
