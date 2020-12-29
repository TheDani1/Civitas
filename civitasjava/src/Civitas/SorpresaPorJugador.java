/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author info
 */
public class SorpresaPorJugador extends Sorpresa{
    private int valor;
    
    SorpresaPorJugador(int valor, String texto){
        super(texto);
        this.valor = valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            SorpresaPagarCobrar a = new SorpresaPagarCobrar(valor * -1, 
            "Pagan todos menos el que no es afectado");
            
            for(int i=0; i<todos.size(); i++){
                if(i!=actual)
                    a.aplicarAJugador(i, todos);
            }
            
            SorpresaPagarCobrar b = new SorpresaPagarCobrar(
                    valor*(todos.size()-1), "Recibe dinero de todos");
            }
    }
    
    @Override
    public String toString(){
        return "SorpresaAPlicarAJugador{ valor: " +valor +super.toString() +"}";
    }
    
    
    
}
