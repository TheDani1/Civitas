package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla{
    private Sorpresa sorpresa;
    private MazoSorpresas mazo;
    
    CasillaSorpresa(MazoSorpresas m, String n){
        super(n);
        this.mazo = m;
    }
    
    @Override
    public void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            sorpresa = mazo.siguiente();
            informe(actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
    
    @Override
    public String toString(){
        return "CasillaSorpresa{ Sorpresa: " +sorpresa + ", mazo: " + mazo +"}";
    }
}
