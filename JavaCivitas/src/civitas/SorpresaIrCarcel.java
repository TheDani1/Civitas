package civitas;

import java.util.ArrayList;

public class SorpresaIrCarcel extends Sorpresa{

    private Tablero tablero;

    SorpresaIrCarcel(Tablero tablero_a, String texto){

        super(texto);
        tablero =  tablero_a;

    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){

        if (jugadorCorrecto(actual, todos) && todos.get(actual) != null){

            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());

        }

    }

}
