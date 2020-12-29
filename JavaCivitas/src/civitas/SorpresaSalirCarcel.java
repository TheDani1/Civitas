package civitas;

import java.util.ArrayList;

public class SorpresaSalirCarcel extends Sorpresa{

    private MazoSorpresas mazo;

    SorpresaSalirCarcel(MazoSorpresas mazo_a, String texto){

        super(texto);
        mazo = mazo_a;

    }

    void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }

    void usada(){
        mazo.habilitarCartaEspecial(this);
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){

        if (jugadorCorrecto(actual, todos) && todos.get(actual) != null){

            super.informe(actual, todos);
            boolean salvoconducto = false;

            for(Jugador cada_j : todos){

                if(cada_j.tieneSalvoconducto()){

                    salvoconducto = true;

                }

            }
            if(!salvoconducto){

                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo();

            }

        }

    }

}
