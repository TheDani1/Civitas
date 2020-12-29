package civitas;

import java.util.ArrayList;


public class SorpresaIrCasilla extends Casilla{
    private int valor;
    private Tablero tablero;

    public SorpresaIrCasilla(Tablero tablero, int valor, String texto){
        super(texto);
        this.valor = valor;
        this.tablero = tablero;
    }
    
    
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            int a = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(a,valor);
            int nuevaPos = tablero.nuevaPosicion(a, tirada);
            todos.get(actual).moverACasilla(nuevaPos);
            tablero.getCasilla(nuevaPos).recibeJugador(actual, todos);
        }
    }
    
    @Override
    public String toString(){
        return "SorpresaIrCasilla{ valor: " +valor +". tablero: " +tablero
                +super.toString() +"}";
    }
    
    
}
