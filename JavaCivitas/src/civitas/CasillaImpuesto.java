package civitas;

import java.util.ArrayList;

public class CasillaImpuesto extends Casilla{

    private int importe;

    CasillaImpuesto(int cantidad, String nom){

        super(nom);
        importe = cantidad;

    }

    @Override
    public String toString(){

        return "[Casilla] -> " + super.getNombre() +"\n[Tipo] -> Impuesto\n[Importe] -> " + importe + "\n";

    }

    @Override
    public void recibeJugador(int iactual, ArrayList<Jugador> todos){

        if(jugadorCorrecto(iactual,todos)){
            todos.get(iactual).pagaImpuesto(importe);
            super.informe(iactual,todos);
        }

    }

}
