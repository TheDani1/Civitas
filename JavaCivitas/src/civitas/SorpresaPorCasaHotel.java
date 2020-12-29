package civitas;

import java.util.ArrayList;

public class SorpresaPorCasaHotel extends Sorpresa{

    private int valor;

    SorpresaPorCasaHotel(int valor_a, String texto){

        super(texto);
        valor = valor_a;

    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){

        if (jugadorCorrecto(actual, todos) && todos.get(actual) != null){
            super.informe(actual, todos);
            todos.get(actual).modificarSaldo(valor * todos.get(actual).cantidadCasasHoteles());
        }

    }

}
