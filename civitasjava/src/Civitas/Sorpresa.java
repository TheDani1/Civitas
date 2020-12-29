package civitas;

import java.util.ArrayList;

public abstract class Sorpresa{
    private String texto;
    
    Sorpresa(String texto){
        this.texto = texto;
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        return actual <= todos.size();
    }
    
    
    protected void informe(int actual, ArrayList<Jugador> todos){
        String a = texto;
        String mensaje = "Se aplica una sorpresa " +a +" al jugador " 
                +todos.get(actual).getNombre();
        
        Diario.getInstance().ocurreEvento(mensaje);
    }
    
    public abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
    @Override
    public String toString(){
        return "Texto: " +texto + "}";
    }
}
