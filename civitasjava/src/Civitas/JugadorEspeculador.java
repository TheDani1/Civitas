/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author info
 */
public class JugadorEspeculador extends Jugador{
    private static int FactorEspeculador = 2;
    private int fianza;
    
    JugadorEspeculador(Jugador j, int f){
        super(j);
        especulador = true;
        fianza = f;
        this.actualizaPropietarioPorConversion();
    }
    
    
    @Override
    protected boolean debeSerEncarcelado(){
        boolean res = false;
        
        if(super.debeSerEncarcelado()){
            if(!puedePagarFianza())
                res = true;
        }
        
        return res;
    }
    
    
    private boolean puedePagarFianza(){
        boolean res = false;
        
        if(saldo >= fianza){
            modificarSaldo(-(fianza));
            res = true;
        }
        
        return res;
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad titulo){
        if(encarcelado)
            return false;
        else{
            if(titulo.getNumHoteles()<getHotelesMax() && 
                    titulo.getNumCasas()>=4)
                return saldo >= titulo.getPrecioEdificar();
            else
                return false;
        }
    }
    
    @Override
    boolean pagaImpuesto(float cantidad){
        if(encarcelado)
            return false;
        else
            return paga(cantidad/FactorEspeculador);
    }
    
    private void actualizaPropietarioPorConversion(){
        for(int i=0; i<super.propiedades.size(); i++){
            propiedades.get(i).setPropietario(this);
        }
    }
    
    @Override
    protected int getCasasMax(){
        return CasasMax*FactorEspeculador;
    }
    
    
    @Override
    protected int getHotelesMax(){
        return HotelesMax*FactorEspeculador;
    }
    
    @Override
    public String toString(){
        return "JugadorEspeculador{ Fianza: " +fianza +" Encarcelado: " 
                +super.encarcelado + ", nombre=" + this.getNombre() + 
                ", numCasillaActual=" + this.getNumCasillaActual() + 
                ", puedeComprar=" + this.getPuedeComprar() + ", saldo=" + 
                saldo + ", propiedades=" + propiedades.toString() +
                ", salvoconducto=" + super.salvoconducto + '}'+"\n";
    }
    
    
    
    
    
    
}
