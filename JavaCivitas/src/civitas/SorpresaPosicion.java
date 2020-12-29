package civitas;

import java.util.ArrayList;

public class SorpresaPosicion extends SorpresaIrCasilla{

    private int pos;

    public Jugador getFran() {
        return fran;
    }

    public TituloPropiedad getTitulo() {
        return titulo;
    }

    private Jugador fran= new Jugador("Francisco");

    TituloPropiedad titulo = new TituloPropiedad("Sol",100,(float)1.3,200,300,50);

    Tablero tablero;

    public SorpresaPosicion(int valor_pos, Tablero tablero_a, int valor_a){

        super(tablero_a, valor_a, "SorpresaPosicion");

        if (valor_pos > 20){

            pos = 20;

        }else if(valor_pos < 1){

            pos = 1;

        }else{

            pos = valor_pos;

        }
    }

    // Funcion toString() omitida debido a herencia

    void aplicarAJugador(int actual, ArrayList<Jugador> todos){

        super.aplicarAJugador(actual, todos);

        System.out.println(getFran().toString());
        System.out.println(getTitulo().toString());

        System.out.println("Aplicando sorpresa del examen y tiro porque me toca");

        if(todos.get(actual).getNumCasillaActual() < pos){

            todos.get(actual).moverACasilla(pos);

        }

    }

}
