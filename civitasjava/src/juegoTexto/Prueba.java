package juegoTexto;

import civitas.CivitasJuego;
import java.util.ArrayList;
import civitas.Dado;
import java.lang.NullPointerException;
import civitas.Tablero;
import civitas.Casilla;

public class Prueba {

    public static void main(String[] args) {
        VistaTextual vista = new VistaTextual();
        ArrayList<String> nombres = new ArrayList<>();
        
        nombres.add("Jugador 1");
        nombres.add("Jugador 2");
        
       
        CivitasJuego juego = new CivitasJuego(nombres);
        
        Controlador controlador = new Controlador(juego, vista);
        
        controlador.juega();
    }
    
}
