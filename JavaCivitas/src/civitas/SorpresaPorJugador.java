package civitas;

import java.util.ArrayList;

public class SorpresaPorJugador extends Sorpresa{

    private int valor;

    SorpresaPorJugador(int valor_a, String texto){

        super(texto);
        valor = valor_a;

    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){

        if (jugadorCorrecto(actual, todos) && todos.get(actual) != null){
            super.informe(actual, todos);
            Sorpresa tipo1=new SorpresaPagarCobrar(valor*-1,"Decrementa saldo");
            Sorpresa tipo2=new SorpresaPagarCobrar(valor*todos.size(),"Incrementa saldo");
            for(int i=0;i<todos.size();i++){
                if(actual!=i){
                    tipo1.aplicarAJugador(i, todos);
                } else {
                    tipo2.aplicarAJugador(i, todos);
                }
            }
        }

    }

}
