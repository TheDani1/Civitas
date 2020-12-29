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
public class Jugador implements Comparable<Jugador> {
    
    private ArrayList<TituloPropiedad> propiedades;

    protected Sorpresa salvoconducto;
    
    static protected int CasasMax = 4;
    static protected int CasasPorHotel = 4;
    static protected int HotelesMax = 4;
    static protected float PasoPorSalida = 1000;
    static protected float PrecioLibertad = 200;
    
    protected boolean encarcelado = false;
    private String nombre;
    private int numCasillaActual;
    private boolean puedeComprar;
    protected float saldo;
    
    static private float SaldoInicial=7500f;
    
    boolean cancelarHipoteca(int ip){
        boolean cancelar = false;

        if(encarcelado){
            return false;
        }
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            float cantidad = propiedad.getImporteCancelarHipoteca();
            boolean puedoGastar = puedoGastar(cantidad);
            if(puedoGastar){
                Diario.getInstance().ocurreEvento("\n[DIARIO] El jugador "+nombre+ "cancela la hipoteca de la propiedad "+ip);
                cancelar = propiedad.cancelarHipoteca(this);
            }
            if(cancelar && propiedad.getHipotecado()){
                if(propiedad.esEsteElPropietario(this)){
                   paga(propiedad.getImporteCancelarHipoteca());
                    propiedad.cancelarHipoteca(this);
                    cancelar=true;

                }
            }
            
        }
        return cancelar;
    }
    
    
    int cantidadCasasHoteles(){
        int cantidad=0;
        for(int i =0; i< propiedades.size();i++){
            cantidad += propiedades.get(i).cantidadCasasHoteles();
        }
        return cantidad;
    }
    
    public int compareTo(Jugador otro){
        int compara = Float.compare(saldo, otro.saldo);
        return compara;
    }
    
    boolean comprar(TituloPropiedad titulo) {
        boolean result = false;
        if (encarcelado) {
            return result;
        }
        if (puedeComprar) {
            float precio = titulo.getPrecioCompra();

            if (puedoGastar(precio)) {

                result = titulo.comprar(this);

                if (result) {

                    propiedades.add(titulo);
                    Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador " + this + " ha comprado la propiedad " + titulo.toString());

                }

                //puedeComprar = false;
            }
        }
        return result;
    }
    
    boolean construirCasa(int ip){
        boolean construir = false;
        if(encarcelado){
            return construir;
        }else{
            if(this.existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                boolean edificarCasa=this.puedoEdificarCasa(propiedad);
                float precio = propiedad.getPrecioEdificar();
                if(this.puedoGastar(precio) && propiedad.getNumCasas() <= getCasasMax() && edificarCasa){
                    construir = propiedad.construirCasa(this);
                    if(construir){
                        Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre +" construye una casa en la propiedad "+ip);
                    }
                }
            }
        }
        return construir;
    }
    
    boolean construirHotel(int ip){
        boolean construir = false;
        if(encarcelado){
            return construir;
        }else{
            if(this.existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                boolean edificarHotel=this.puedoEdificarHotel(propiedad);
                float precio = propiedad.getPrecioEdificar();
                if(this.puedoGastar(precio) && propiedad.getNumHoteles() <=getHotelesMax()){
                    construir = propiedad.construirHotel(this);
                    
                }
                if(edificarHotel){
                    construir = propiedad.construirHotel(this);
                    int casasPorHotel = getCasasPorHotel();
                    propiedad.derruirCasas(casasPorHotel, this);
                }
                if(construir){
                        Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre +" construye una casa en la propiedad "+ip);
                    }
            }
        }
        return construir;
    }
    
    protected boolean debeSerEncarcelado(){
        boolean envioCarcel = false;
        if(isEncarcelado()){
            envioCarcel = false;
        }else{
            if(!tieneSalvoconducto()){
                envioCarcel=true;
            }else{
                perderSalvoConducto();
                Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre+ " pierde salvoconducto y sale de la carcel");
                envioCarcel = false;
            }
        }
        return envioCarcel;
    }
    
    boolean enBancarrota(){
        if(saldo <= 0){
            return true;
        }else{
            return false;
        }
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado()){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre+ " ha sido enviado a la carcel.");
        }
        return encarcelado;
    }
    
    
    private boolean existeLaPropiedad(int ip){
        return (ip >= 0 && ip < propiedades.size() );
    }
    
    
    private int getCasasMax(){
        return CasasMax;
    }
    
    private int getCasasPorHotel(){
        return CasasPorHotel;
    }
    
    private int getHotelesMax(){
        return HotelesMax;
    }
    
    protected String getNombre(){
        return nombre;
    }
    
    public int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    private float getPrecioLibertad(){
        return PrecioLibertad;
    }
    
    private float getPremioPasoSalida(){
        return PasoPorSalida;
    }
    
    protected ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }
    
    boolean getPuedeComprar(){
        return puedeComprar;
    }

    protected float getSaldo(){
        return saldo;
    }
    
    boolean hipotecar(int ip){
        boolean result = false;

        if(encarcelado){

            return result;

        }

        if(existeLaPropiedad(ip)){

            TituloPropiedad propiedad = propiedades.get(ip);
            result = propiedad.hipotecar(this);

        }

        if(result){

            Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre+ " ha hipotecado la propiedad "+ip);

        }

        return result;
    }
    
    
    public boolean isEncarcelado(){
        return encarcelado;
    }
    
    Jugador(String nombre){

        this.nombre=nombre;
        this.saldo += SaldoInicial;
        encarcelado=false;
        numCasillaActual=0;
        puedeComprar=true;
        propiedades= new ArrayList<TituloPropiedad>();
        salvoconducto=null;

    }


    protected Jugador(Jugador otro){
        nombre= otro.nombre;
        propiedades=otro.propiedades;
        salvoconducto= otro.salvoconducto;
        encarcelado = otro.encarcelado;
        numCasillaActual = otro.numCasillaActual;
        puedeComprar = otro.puedeComprar;
        saldo= otro.saldo;
    }
    
    boolean modificarSaldo(float cantidad){
        saldo +=cantidad;
        Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre+ "ha modificado el saldo "+String.valueOf(cantidad));
        return true;
    }
    
    boolean moverACasilla(int numCasilla){
        if(!encarcelado){
            numCasillaActual = numCasilla;
            Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre+ " se ha movido a la casilla "+String.valueOf(numCasilla));
            return true;
        }else{
            return false;
        }
   
    }
    
    boolean obtenerSalvoconducto(Sorpresa sorpresa){
        if(!isEncarcelado()){
            salvoconducto = sorpresa;
            return true;
            
        }else{
            return false;
        }
    }
    
    boolean paga(float cantidad){
        return modificarSaldo(cantidad * -1);
        //modificarSaldo ya escribe en diario
    }
    
    boolean pagaAlquiler(float cantidad){
        if(!isEncarcelado()){
            return paga(cantidad);
        }else{
            return false;
        }
    }
    
    boolean pagaImpuesto(float cantidad){
        if(!isEncarcelado()){
            return paga(cantidad);
        }else{
            return false;
        }
    }
    
    boolean pasaPorSalida(){
        Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre+ " ha pasado por la casilla de salida, aumenta su saldo en "+PasoPorSalida);
        return modificarSaldo(PasoPorSalida);
    }
    
    protected void perderSalvoConducto(){
        ((SorpresaSalirCarcel)salvoconducto).usada();
        salvoconducto = null;
    }
    
    boolean puedeComprarCasilla(){
        if(!encarcelado){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean puedeSalirCarcelPagando(){
        if(saldo >= PrecioLibertad){
            return true;
        }else{
            return false;
        }
    }
    //metodo sin implementar
    private boolean puedoEdificarCasa(TituloPropiedad propiedad){
        return false;
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad propiedad){
        return false;
    }
    
    private boolean puedoGastar(float precio){

        if(!isEncarcelado() && saldo >= precio){
            return true;
        }else{
            return false;
        }
    }
    
    boolean recibe(float cantidad){
        if(!isEncarcelado()){
            return modificarSaldo(cantidad);
        }else{
            return false;
        }
    }
    
    boolean salirCarcelPagando(){
        if(isEncarcelado() && puedeSalirCarcelPagando()){
            paga(PrecioLibertad);
            encarcelado=false;
            Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre+ " sale de la carcel pagando");
            return true;
        }else{
            return false;
        }
    }
    
    boolean salirCarcelTirando(){
        if(Dado.getInstance().salgoDeLaCarcel()){
            encarcelado=false;
            Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre+" sale de la carcel tirando los dados");
            return true;
        }else{
            return false;
        }
    }
    
    boolean tieneAlgoQueGestionar(){
        if(propiedades.size() != 0){
            return true;
        }else{
            return false;
        }
    }
    
    boolean tieneSalvoconducto(){
        if(salvoconducto == null){
            return false;
        }else {
            return true;
        }
    }
    
    @Override
    public String toString(){
        String cadenaString = "\n-----------["+ nombre +"]------------\n[Saldo] -> "+ saldo +"\n[Encarcelado] -> "+encarcelado+"\n[Numero de casilla actual] -> "+numCasillaActual+"\n";
        return cadenaString;
    }
    
    boolean vender(int ip){
        if(!isEncarcelado()){
            if(existeLaPropiedad(ip)){
                if(propiedades.get(ip).vender(this)){
                    propiedades.remove(ip);
                    Diario.getInstance().ocurreEvento("\n[DIARIO] Jugador "+nombre+" ha vendido la propiedad "+String.valueOf(ip));
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
