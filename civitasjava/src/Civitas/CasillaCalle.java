package civitas;

import java.util.ArrayList;


public class CasillaCalle extends Casilla {
    private TituloPropiedad titulo;
    
    CasillaCalle(TituloPropiedad titulo){
        super(titulo.getNombre());
        this.titulo = titulo;
    }
    
    @Override
    public void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            Jugador jugador = todos.get(actual);
           
            if(!titulo.tienePropietario()){
                jugador.puedeComprarCasilla();
            }else{
                titulo.tramitarAlquiler(jugador);
            }
        } 
    }



    TituloPropiedad getTituloPropiedad(){
        return titulo;
    }
    
    @Override
    public String toString(){
        return "CasillaCalle{" + "titulopropiedad: " +titulo.toString() + "}";
    }

}