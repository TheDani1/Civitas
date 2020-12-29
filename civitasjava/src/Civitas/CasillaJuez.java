package civitas;

import java.util.ArrayList;


public class CasillaJuez extends Casilla{
    private static int carcel;
    
    CasillaJuez(int numCasillaCarcel, String n){
        super(n);
        carcel = numCasillaCarcel;
    }
    
    @Override
    public void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }
    
    @Override
    public String toString(){
        return "CasillaJuez{ " + "nombre: " +this.getNombre() + "}";
    }
}
