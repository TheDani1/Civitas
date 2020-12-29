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
public class MazoSorpresas {
    
    private ArrayList<Sorpresa> sorpresas = new ArrayList<Sorpresa>();
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales = new ArrayList<Sorpresa>();
    private Sorpresa ultimaSorpresa;
    
    
    
    void init(){
        sorpresas.clear();
        cartasEspeciales.clear();
        barajada = false;
        usadas = 0;
    }
    
    MazoSorpresas(boolean debugger){
        debug = debugger;
        init();
        if(debugger){
            Diario.getInstance().ocurreEvento("\n[DIARIO] Modo DEBUG activado");
        }
        
    }
    
    MazoSorpresas(){
        init();
        debug=false;
    }
    
    
    //metodos visibilidad de paquete
    
    void alMazo(Sorpresa s){
        if(!barajada){
            sorpresas.add(s);
        }
    }
    
    Sorpresa siguiente(){
        if(!barajada || usadas == sorpresas.size()){
            if(!debug){
                barajada=true;
                //falta poner el metodo para barajar
                usadas=0;
            }
            usadas++;
            Sorpresa s = sorpresas.get(0);
            sorpresas.add(s);
            ultimaSorpresa = s;
            sorpresas.remove(0);
            
        }
        return ultimaSorpresa;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        if(cartasEspeciales.contains(sorpresa)){
            sorpresas.add(sorpresa);
            cartasEspeciales.remove(sorpresa);
            Diario.getInstance().ocurreEvento("\n[DIARIO]Carta especial inhabilitada.");
        }
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa){
        if(cartasEspeciales.contains(sorpresa)){
            sorpresas.add(sorpresa);
            cartasEspeciales.remove(sorpresa);
            Diario.getInstance().ocurreEvento("\n[DIARIO] Carta especial habilitada.");
            
        }
    }
    
    Sorpresa getUltimaSorpresa(){
        return sorpresas.get(sorpresas.size()-1);
    }
    
}
