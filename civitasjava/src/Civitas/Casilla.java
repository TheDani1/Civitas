package civitas;
import java.util.ArrayList;

public class Casilla {
    private String nombre;
    
    Casilla(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("El jugador "+
                todos.get(actual).getNombre()+ "ha caido en la casilla " 
                +toString());
    }
    
    @Override
    public String toString(){
        return "Casilla_Descanso{ " + "nombre: " +nombre + "}";
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        return actual <= todos.size();
    }
    
    public void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual,todos);
        }
    }

}

