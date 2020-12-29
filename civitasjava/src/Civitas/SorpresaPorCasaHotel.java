package civitas;

import java.util.ArrayList;


public class SorpresaPorCasaHotel extends Sorpresa{
    private int valor;
    
    SorpresaPorCasaHotel(int valor, String texto){
        super(texto);
        this.valor = valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor * 
                    todos.get(actual).cantidadCasasHoteles());
        }
    }
    
    @Override
    public String toString(){
        return "SorpresaPorCasaHotel{ Valor: " +valor +super.toString() + "}";
    }
}
