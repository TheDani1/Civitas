/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;
import civitas.CivitasJuego;
import java.util.ArrayList;
import civitas.Dado;

public class prueba {

    public static void main(String[] args) {
        // TODO code application logic here
        VistaTextual vista = new VistaTextual();
        
        //int num_jugadores = 4;
        ArrayList <String> jugadores = new ArrayList<String>();
        
        jugadores.add("Daniel");
        jugadores.add("Emilio");
        jugadores.add("Jose");
        jugadores.add("Nuria");
        
        CivitasJuego civita = new CivitasJuego(jugadores);
        
        Dado.getInstance().setDebug(true);
        
        Controlador controlador = new Controlador(civita, vista);
        
        controlador.juega();
    }
    
}
