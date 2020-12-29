package civitas;

import java.util.ArrayList;


public class SorpresaIrCarcel extends Sorpresa{
    private Tablero tablero;

    public SorpresaIrCarcel(Tablero t, String n){
        super(n);
        this.tablero = t;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    
    @Override
    public String toString(){
        return "SorpresaIrCarcel{ Tablero: " +tablero +super.toString() +"}";
    }
    
    
    
}
