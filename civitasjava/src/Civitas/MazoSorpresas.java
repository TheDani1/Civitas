package civitas;

import java.util.ArrayList;
import java.util.Collections;

public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas;  //Almacena las cartas Sorpresa
    private boolean barajada;               //Indica si se ha barajado
    private int usadas;                     //Numero de cartas ya usadas
    private boolean debug; 
    private ArrayList<Sorpresa> cartasEspeciales;//Almacena la carta SALIRCARCEL
    private Sorpresa ultimaSorpresa;        //Ultima sorpresa que ha salido
    
    /**
     * Inicializa sorpresas,cartasEspeciales,barajada y usadas
     */
    private void init(){
        barajada = false;
        usadas = 0;
        cartasEspeciales = new ArrayList<>();
        sorpresas = new ArrayList<>();
    }
    /**
     * Constructor sin parametros
     */
    MazoSorpresas(){
        init();
        debug = false;
    }
    /**
     * Constructor por parametros
     * @param d 
     */
    MazoSorpresas(boolean d){
        debug = d;
        init();
        if(debug){
            Diario.getInstance().ocurreEvento("Debug true");
        }
    }
    
    /**
     * Añade la Sorpresa del parametro al final
     * @param s : sorprese que se quiere añadir
     */
    void alMazo(Sorpresa s){
        if(barajada == false){
            sorpresas.add(s);
        }
    }
    
    /**
     * Si cumple las condiciones se baraja el mazo, la primera sorpresa pasa a 
     * ser la ultima y se devuelve por referencia
     * @return 
     */
    Sorpresa siguiente(){
        if(barajada == false || usadas == sorpresas.size() && debug == false){
            Collections.shuffle(sorpresas);
            usadas = 0;
            barajada = true;
        }
                
        usadas++;

        Sorpresa aux = sorpresas.get(0);
        sorpresas.add(sorpresas.size()-1, aux);
        sorpresas.remove(0);

        ultimaSorpresa = aux;
        
        return ultimaSorpresa;
    }
    
    /**
     * Si sorpresa es una carta especial y esta en el mazo, se 
     * quita y se añade a cartasEspeciales
     * @param sorpresa : sorpresa que se quiere inhabilitar
     */
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        Sorpresa aux;

        for(int i=0; i<sorpresas.size(); i++){
            if(sorpresas.get(i) == sorpresa){

                aux = sorpresas.get(i);
                sorpresas.remove(i);
                cartasEspeciales.add(aux);
                Diario.getInstance().ocurreEvento("Una carta movida de "
                        + "sorpresa a especiales");
            }
        }
    }
    
    /**
     * Si sorpresa esta en cartasEspeciales, se saca y se añade a la coleccion
     * de sorpresas para habilitarla
     * @param sorpresa : sorpresa que se quiere habilitar
     */
    void habilitarCartaEspecial(Sorpresa sorpresa){
        Sorpresa aux;

        for(int i=0; i<cartasEspeciales.size(); i++){
            if(cartasEspeciales.get(i) == sorpresa){
                aux = cartasEspeciales.get(i);
                cartasEspeciales.remove(i);
                sorpresas.add(aux);
                Diario.getInstance().ocurreEvento("Una carta movida de "
                        + "especiales a sorpresa");
            }
        }
    }
	
    Sorpresa getUltimaSorpresa(){
        return ultimaSorpresa;
    }

    Sorpresa getSorpresas(int i){
        return sorpresas.get(i);
    }

    int getTamSorpresas(){
        return sorpresas.size();
    }
    
}
    
    

