package civitas;

import java.util.ArrayList;

public class SorpresaEspeculador extends Sorpresa{

    private int fianza;

    SorpresaEspeculador(int fianza_a, String texto){

        super(texto);
        fianza = fianza_a;

    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){

        if (jugadorCorrecto(actual, todos) && todos.get(actual) != null){

            super.informe(actual, todos);
            JugadorEspeculador espc = new JugadorEspeculador(todos.get(actual), fianza);
            todos.remove(actual);
            todos.add(espc);

        }

    }

}
