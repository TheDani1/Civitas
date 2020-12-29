package civitas;

import java.util.ArrayList;

public class CasillaJuez extends Casilla{

    private static int carcel;

    CasillaJuez(int numCasillaCarcel, String nom){

        super(nom);
        carcel = numCasillaCarcel;

    }

    @Override
    public void recibeJugador(int iactual, ArrayList<Jugador> todos){

        if(jugadorCorrecto(iactual, todos)){
            todos.get(iactual).encarcelar(carcel);
            super.informe(iactual,todos);
            System.out.println("El jugador se va a la carcel");
        }

    }

    @Override
    public String toString(){

        return "\n[Casilla] -> " + super.getNombre() +"\n[Tipo] -> Juez\n[Carcel] -> " + carcel +"\n";

    }

}
