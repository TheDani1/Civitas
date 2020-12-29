package civitas;

import java.util.ArrayList;

public class Tablero {
    
    private int numCasillaCarcel; //NUMERO DE LA CASILLA DONDE SE ENCUENTRA LA 
                                  //CARCEL
    
    private ArrayList<Casilla> casillas; //CONTIENE LAS CASILLAS DEL JUEGO
    
    private int porSalida;//NUMERO DE VECES QUE SE HA PASADO POR LA SALIDA EN
                          //UN TURNO
    
    private boolean tieneJuez;//REPRESENTA SI EL TABLERO TIENE CASILLA DE JUEZ
    
    /**
     * Constructor de tablero
     * @param indCarcel : indice de la carcel
     */
    public Tablero(int indCarcel){           
        numCasillaCarcel = 1;
        if(indCarcel >= 1){                   //Guarda el valor en indCarcel si
            numCasillaCarcel = indCarcel;     //es >= 1
        }
        else{
            numCasillaCarcel = 1;
        }
        
        casillas = new ArrayList<>();        //Inicializa casillas a ArrayList 
                                             //que esta vacio
                                             
        casillas.add(new Casilla("Salida")); //Añade una casilla nueva llamada
                                             //"Salida"
        
        this.porSalida = 0;                  //Inicializa porSalida a 0
        
        this.tieneJuez = false;              //Inicializa tieneJuez en false
      
    }
    /**
     * Devuelve true si el tablero es correcto 
     * (nº elementos casillas > numCasillaCarcel y hay casilla tipo Juez)
     * @return 
     */
    boolean correcto(){
        return (casillas.size()>numCasillaCarcel && tieneJuez == true);
    }
    
    
    /**
     * Devuelve true si el numero de la casilla es correcto
     * @param numCasilla : numero de la casilla a comprobar
     * @return 
     */
    boolean correcto(int numCasilla){
        return (correcto() && numCasilla >= 0 && 
                numCasilla<casillas.size());
    }
    
    
    /**
     * Consultor de numCasillaCarcel
     * @return 
     */
    int getCarcel(){
        return numCasillaCarcel;
    }
    
    /**
     * Decrementa 1 unidad el valor de porSalida si este es > 0
     * si no devuelve el mismo valor
     * @return resultado
     */
    int getPorSalida(){
        int resultado = porSalida;     
        
        if(porSalida > 0){              
                porSalida--;           
        }
        return resultado;               
    }                                   
    
    /**
     * Añade una casilla nueva comprobando si es la de la carcel o no
     * @param casilla : casilla a añadir
     */
    void añadeCasilla(Casilla casilla){
        if(casillas.size() == numCasillaCarcel){                  
            casillas.add(new Casilla("Carcel"));
        }
        
        casillas.add(casilla);            
    }                                     
    
    void añadeCasilla(Casilla casilla, int pos){
        if(casillas.size() == numCasillaCarcel){
            casillas.add(new Casilla("Carcel"));
        }
        
        casillas.add(pos, casilla);
    }
    /**
     * Añade la casilla juez al tablero si aun no ha sido añadida
     * Se actualiza tieneJuez y se evita que haya varias casillas de este tipo
     */
    void añadeJuez(){
        if(casillas.size() == numCasillaCarcel){
            casillas.add(new Casilla("Carcel"));
        }
        
        casillas.add(new Casilla("Juez"));
        tieneJuez = true;
    }
    
    /**
     * Devuelve la casilla en la posicion numCasilla o null si el indice
     * no es valido
     * @param numCasilla : numero de casilla
     * @return 
     */
    public Casilla getCasilla(int numCasilla){
        if(correcto(numCasilla)){
            return casillas.get(numCasilla);
        }else{
            return null;       
        }
    }
    
    /**
     * Devuelve la nueva posicion al avanzar "tirada" casillas 
     * @param actual : indice del jugador actual
     * @param tirada : numero de casillas que avanza
     * @return res
     */
    int nuevaPosicion(int actual, int tirada){
        int res = 0;
        if(correcto() == false){         
            return -1;
        }else{
            if(actual+tirada < casillas.size()){
                res = actual + tirada;
            }else{                            
                res = (actual + tirada) % casillas.size();
                porSalida++;       //Se ha dado una vuelta al tablero y se
            }                      //ha pasado por la salida
            return res;
        }       
    }
    
    /**
     * Calcula la tirada que se tendria que obtener para ir de origen a destino
     * @param origen : casilla de origen
     * @param destino : casilla de destino
     * @return tirada
     */
    int calcularTirada(int origen, int destino){
        int tirada = 0;
        if(destino - origen >= 0){
            tirada = destino - origen;
        }else{
            tirada = destino - origen + casillas.size();  //Por si se pasa por 
        }                                                 //la salida
        return tirada;        
    }
    
    /**
     * Consulta del numero de casillas
     * @return 
     */
    int getCantidadCasillas(){
        return casillas.size();
    }
    
    
}

