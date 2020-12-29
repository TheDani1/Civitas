package civitas;

import java.util.ArrayList;

public class SorpresaEspeculador extends Sorpresa{
    private int fianza;
    
    SorpresaEspeculador(int fianza, String texto){
        super(texto);
        this.fianza = fianza;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            JugadorEspeculador j = new JugadorEspeculador(todos.get(actual),
                    fianza);
            todos.set(actual, j);
        }
    }
    
    @Override
    public String toString(){
        return "SorpresaEspeculador{ Fianza: " +fianza +super.toString() + "}";
    }
}
