/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
/**
 *
 * @author emilio
 */
abstract public class Sorpresa {
    private String texto;

    Sorpresa(String texto_a){

        texto = texto_a;

    }
    
    public String toString(){

        return texto;
    }

    public boolean jugadorCorrecto(int actual, ArrayList <Jugador> todos){

        return actual < todos.size() && actual >= todos.indexOf(todos.get(0));
    }

    protected void informe(int actual, ArrayList <Jugador> todos){
        Diario.getInstance().ocurreEvento("\n[DIARIO] Se ha aplicado una sorpresa al jugador"+todos.get(actual).getNombre()+"\n");
    }
    
    abstract void aplicarAJugador(int actual, ArrayList <Jugador> todos);
    
}
