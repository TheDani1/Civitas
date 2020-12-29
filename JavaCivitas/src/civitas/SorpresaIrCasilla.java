package civitas;

import java.util.ArrayList;

public class SorpresaIrCasilla extends Sorpresa{

    private Tablero tablero;
    private int valor;

    SorpresaIrCasilla(Tablero tablero_a, int valor_a, String texto){

        super(texto);
        tablero = tablero_a;
        valor = valor_a;

    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){

        if (jugadorCorrecto(actual, todos) && todos.get(actual) != null){
            super.informe(actual, todos);
            int casillaActual = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(casillaActual, valor);
            int nuevapos = tablero.nuevaPosicion(casillaActual, tirada);
            todos.get(actual).moverACasilla(nuevapos);
            tablero.getCasilla(nuevapos).recibeJugador(actual, todos);
        }

    }

}
