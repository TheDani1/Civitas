package civitas;

import java.util.ArrayList;

public class SorpresaPagarCobrar extends Sorpresa{

    private int valor;

    SorpresaPagarCobrar(int valor_a, String texto){

        super(texto);
        valor = valor_a;

    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){

        if (jugadorCorrecto(actual, todos) && todos.get(actual) != null){
            super.informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }

    }

}
