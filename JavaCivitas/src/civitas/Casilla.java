package civitas;

import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class Casilla {
    
    public String nombre;

    Casilla(String in_nombre){

        nombre = in_nombre;
    }

    public void informe(int actual, ArrayList<Jugador> todos){

        Diario.getInstance().ocurreEvento("\n[DIARIO] El jugador "+String.valueOf(actual)+ " ha caido en "+this.toString());

    }

    void recibeJugador(int iactual, ArrayList<Jugador> todos){

        informe(iactual, todos);

    }

    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){

        // VERIFICAR SI EL INDICE DEL JUGADOR ES CORRECTO
        // comprueba si el primer
        // parámetro es un índice válido para acceder a los elementos del segundo parámetro.

        return actual >= 0 && actual < todos.size();
    }

    @Override
    public String toString(){

        return "\n[Casilla] -> "+ nombre + "\n[Tipo] -> Descanso\n";
    }
    
    public String getNombre(){
        
        return nombre;
        
    }
}
