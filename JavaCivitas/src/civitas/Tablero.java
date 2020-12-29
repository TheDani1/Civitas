package civitas;

import java.util.ArrayList;

/**
 *
 * @author Daniel
 */

public class Tablero {
    
    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private Boolean tieneJuez;
    
    // Constructor
    public Tablero(int indice_carcel){
        
        if (indice_carcel > 1 ) numCasillaCarcel = indice_carcel;
        else{
            
            numCasillaCarcel = 1;
            
        }

        casillas=new ArrayList<Casilla>();
        
        casillas.clear();

        casillas.add(new Casilla("Salida"));
        
        porSalida = 0;
        
        tieneJuez = false;         
    }
    
    private boolean correcto(){
        
        if(casillas.size() > numCasillaCarcel && tieneJuez){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean correcto(int numCasillas){
        if(correcto() && casillas.size() > numCasillas && numCasillas >= 0) {
            return true;
        }else{
            return false;
        }
    }
    
    int getCarcel(){
        
        return numCasillaCarcel;
        
    }
    
    int getPorSalida(){
        
        if(porSalida > 0){
            int salir=porSalida;
            porSalida--;
            
            return salir;
        } 
        
        return porSalida;      
        
    }
    
    void añadeCasilla(Casilla casilla){
        Casilla Carcel = new Casilla("Carcel");
        if(casillas.size() == numCasillaCarcel){
            
            casillas.add(Carcel);
            
        }
        
        casillas.add(casilla);
        
        if (casillas.size() == numCasillaCarcel){
            
            casillas.add(Carcel);
            
        }       
        
    }
    
    void añadeJuez(){
        
        if(!tieneJuez) {
            casillas.add(new CasillaJuez(numCasillaCarcel,"Juez") );
            tieneJuez = true;
        }
        
    }
    
    Casilla getCasilla(int numCasilla){
        
        if(correcto(numCasilla)) {

            return casillas.get(numCasilla);


        }


        return casillas.get(numCasilla);

    }
    
    int nuevaPosicion(int actual, int tirada){
        


            int pos_final = actual + tirada;

            if (pos_final >= casillas.size()) {

                porSalida++;
                pos_final = (pos_final % casillas.size()) ;

            }
            return pos_final;

        

        
    }
    
    int calcularTirada (int origen, int destino){
        
        int tirada_nec = destino - origen;
        
        if(tirada_nec < 0){
            tirada_nec = tirada_nec+casillas.size();
        }
        
        return tirada_nec;
        
    }    
}
