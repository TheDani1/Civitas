package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla{

    private MazoSorpresas mazo;

    CasillaSorpresa(MazoSorpresas mazo_a, String nom){

        super(nom);
        mazo = mazo_a;

    }

    @Override
    public void recibeJugador(int iactual, ArrayList<Jugador> todos){

        if(jugadorCorrecto(iactual, todos)){
            Sorpresa sorpresa = mazo.siguiente();
            sorpresa.aplicarAJugador(iactual,todos);
            super.informe(iactual,todos);
        }

    }

}
