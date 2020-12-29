package civitas;

import java.util.ArrayList;


public class CasillaImpuesto extends Casilla{
    private float importe;
    
    CasillaImpuesto(float c, String n){
        super(n);
        importe = c;
    }
    
    @Override
    public void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }
    
    @Override
    public String toString(){
        return "Casilla impuesto{ Nombre: "+this.getNombre() +"importe: " 
                +importe + "}";
    }
}
