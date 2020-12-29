package civitas;

import static java.lang.Float.compare;
import java.util.ArrayList;
import civitas.TituloPropiedad;
import civitas.Casilla;

import java.lang.Comparable;

public class Jugador implements Comparable<Jugador>{
   
    static protected int CasasMax = 4;
    
    static protected int CasasPorHotel = 4;
    
    protected boolean encarcelado;
    
    static protected int HotelesMax = 4;
    
    private String nombre;
    
    private int numCasillaActual;
    
    static protected float PasoPorSalida = 1000f;
    
    static protected float PrecioLibertad = 200f;
    
    private boolean puedeComprar;
    
    protected float saldo;
    
    static float SaldoInicial = 7500f;

    protected SorpresaSalirCarcel salvoconducto = null;
    
    protected ArrayList<TituloPropiedad> propiedades;
    
    protected Boolean especulador;
    
    
    
    public Boolean getEspeculador(){
        return especulador;
    }
    
    @Override
    public String toString(){
        return "Jugador{ Encarcelado: " +encarcelado +", nombre: " +nombre 
                +", numCasillaActual: " +numCasillaActual+ ", puedeComprar=" + 
                puedeComprar + ", saldo=" + saldo + ", propiedades=" + 
                propiedades.toString() + ", salvoconducto=" + salvoconducto 
                + '}'+"\n";
    }
    /**
     * Sirve para cancelar la hipoteca de la propiedad pasada como argumento
     * @param ip : indice de la propiedad
     * @return res
     */
    boolean cancelarHipoteca(int ip) {
        boolean res = false;
        if(encarcelado == true){
            return res;
        }
        
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            float cantidad = propiedad.getImporteCancelarHipoteca();
            boolean puedoGastar = puedoGastar(cantidad);
        
            if(puedoGastar){
                res = propiedad.cancelarHipoteca(this);
                if(res){
                    Diario.getInstance().ocurreEvento("El jugador " +nombre+ 
                        "cancela la hipoteca de la propiedad " +ip);
                }
            }
        }
        return res;
    }
    
    /**
     * Calcula la cantidad de casas y hoteles
     * @return cantidad
     */
    int cantidadCasasHoteles() {
        int cantidad = 0;
        
        for(int i=0; i<propiedades.size(); i++){
            cantidad+=propiedades.get(i).getNumCasas();
            cantidad+=propiedades.get(i).getNumHoteles();
        }
        return cantidad;
    }
    
    /**
     * Compara dos jugadores por su saldo
     * @param otro : otro jugador para comparar
     * @return 
     */
    
    @Override
    public int compareTo(Jugador otro) {
        return compare(saldo, otro.getSaldo());
    }
    
    /**
     * Compra una propiedad
     * @param titulo : titulo de la propiedad
     * @return res
     */
    boolean comprar(TituloPropiedad titulo) {
        boolean res = false;
        
        if(encarcelado){
            return res;
        }
        
        if(puedeComprar){
            float precio = titulo.getPrecioCompra();
            
            if(puedoGastar(precio)){
                res = titulo.comprar(this);
                
                if(res){
                    propiedades.add(titulo);
                    Diario.getInstance().ocurreEvento("El jugador "+this 
                    +" compra la propiedad " +titulo.toString());
                    
                }
                puedeComprar = false;
            }
        }
        return res;
    }
    
    /**
     * Construye casa en la propiedad indicada como argumento
     * @param ip : indice de la propiedad
     * @return res
     */
    boolean construirCasa(int ip) {
        boolean res = false;
        
        if(encarcelado){
            return res;
        }
        else{
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                boolean puedoEdificarCasa = puedoEdificarCasa(propiedad);
                
                if(puedoEdificarCasa){
                    res = propiedad.construirCasa(this);
                    if(res){
                        Diario.getInstance().ocurreEvento("El jugador "+nombre 
                        +" construye casa en la propiedad " +
                                propiedades.get(ip).getNombre());
                    }
                }   
            }
        }
        
        return res;
    }
    
    /**
     * Construye hotel en la porpiedad indicada como argumento
     * @param ip : indice de la propiedad
     * @return res
     */
    boolean construirHotel(int ip) {
        boolean res = false;
        if(encarcelado){
            return res;
        }
        
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);
            float precio = propiedad.getPrecioEdificar();
            
            if(puedoGastar(precio) && propiedad.getNumHoteles()<getHotelesMax()
                    && propiedad.getNumCasas() >= getCasasPorHotel()){
                puedoEdificarHotel = true;
            }
            
            if(puedoEdificarHotel){
                res = propiedad.construirHotel(this);
                propiedad.derruirCasas(CasasPorHotel, this);
            }
            
            if(res){
                Diario.getInstance().ocurreEvento("El jugador " +nombre 
                +"construye hotel en la propiedad " +
                        propiedades.get(ip).getNombre());
            }
        }
        
        return res;
    }
    
    /**
     * Devuelve true si el jugador debe ser encarcelado
     * @return res
     */
    protected boolean debeSerEncarcelado() {
        boolean res;
        if(isEncarcelado()){
            res = false;
        }else if(tieneSalvoconducto()){
            perderSalvoconducto();
            res = false;
            Diario.getInstance().ocurreEvento("El jugador" +nombre +"ha perdido"
                + " salvoconducto");
        }else{
            res = true;
        }
        
        return res;
    }
    
    /**
     * Muestra los nombres de las propiedades
     * @return nombres
     */
    public ArrayList<String> getNombrePropiedades(){
        ArrayList<String> nombres = new ArrayList<>();      
        for(int i=0;i<propiedades.size();i++)
            nombres.add(propiedades.get(i).getNombre());
        return nombres;
       
    }
    
    
    /**
     * Devuelve true si el jugador esta en bancarrota
     * @return saldo
     */
    boolean enBancarrota() {
        return saldo <= 0;
    }
    
    /**
     * Encarcela al jugador si debe ser encarcelado y se informa
     * @param numCasillaCarcel : casilla carcel
     * @return encarcelado
     */
    boolean encarcelar(int numCasillaCarcel) {
        if(debeSerEncarcelado()){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
        }
        return encarcelado;
    }
    
    protected boolean existeLaPropiedad(int ip) {
        return (ip >= 0 && ip < propiedades.size());
    }
    
    protected int getCasasMax() {
        return CasasMax;
    }
    
    int getCasasPorHotel() {
        return CasasPorHotel;
    }
    
    protected int getHotelesMax() {
        return HotelesMax;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    int getNumCasillaActual() {
        return numCasillaActual;
    }
    
    private float getPrecioLibertad() {
        return PrecioLibertad;
    }
    
    private float getPrecioPasoSalida() {
        return PasoPorSalida;
    }
    
    public ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }
    
    boolean getPuedeComprar() {
        return puedeComprar;
    }
    
    public float getSaldo() {
        return saldo;
    }
    
    Sorpresa getSalvoconducto(){
        return salvoconducto;
    }
    
    /**
     * Hipoteca la propiedad pasada como parametro
     * @param ip : indice de la propiedad
     * @return res
     */
    boolean hipotecar(int ip) {
        boolean res = false;
        
        if(encarcelado){
            return res;
        }
        
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            res = propiedad.hipotecar(this);
        }
        
        if(res){
            Diario.getInstance().ocurreEvento("El jugador " +nombre 
            +"hipoteca la propiedad " +ip);
        }
        return res;
    }
    
    public Boolean isEncarcelado() {
        return encarcelado;
    }
    
    Jugador(String nombre) {
        this.nombre = nombre;
        propiedades = new ArrayList<>();
        saldo = SaldoInicial;
        numCasillaActual = 0;
        puedeComprar = true;
    }
    
    protected Jugador(Jugador otro) {
        encarcelado = otro.isEncarcelado();
        nombre = otro.getNombre();
        numCasillaActual = otro.getNumCasillaActual();
        puedeComprar = otro.getPuedeComprar();
        saldo = otro.getSaldo();
        salvoconducto = otro.salvoconducto;
        propiedades = otro.getPropiedades();
    }
    
    /**
     * Modifica el saldo del jugador sumandole (cantidad)
     * @param cantidad : cantidad a incrementar
     * @return 
     */
    Boolean modificarSaldo(float cantidad) {
        saldo += (float)(cantidad);
        Diario.getInstance().ocurreEvento("Se ha modificado el saldo del "
                + "jugador " +nombre);
        return true;
    }
    
    /**
     * Se mueve el jugador a la casilla pasada como parametro 
     * si no esta encarcelado
     * @param numCasilla : casilla a la que se mueve
     * @return res
     */
    boolean moverACasilla(int numCasilla) {
        boolean res;
        if(isEncarcelado()){
            res = false;
        }
        else{
            numCasillaActual = numCasilla;
            puedeComprar = false;
            Diario.getInstance().ocurreEvento("El jugador " +nombre 
                    +" se mueve de la casilla " +numCasillaActual +
                    " a la " +numCasilla);
            res = true;
        }
        
        return res;
    }
    
    /** 
     * Si el jugador no esta encarcelado se devuelve true
     * @param sorpresa :  
     * @return res
     */
    boolean obtenerSalvoconducto(SorpresaSalirCarcel sorpresa) {
        boolean res;
        if(encarcelado){
            res = false;
        }
        else{
            salvoconducto = sorpresa;
            res = true;
        }
        return res;
    }
    
    /**
     * El jugador paga una cantidad
     * @param cantidad : cantidad que tiene que pagar
     * @return
     */
    boolean paga(float cantidad) {
        return modificarSaldo(cantidad*(-1));
    }
    
    /**
     * El jugador pague el alquiler si no esta encarcelado
     * @param cantidad : cantidad que tiene que pagar
     * @return res
     */
    boolean pagaAlquiler(float cantidad) {
        boolean res = false;
        if(encarcelado){
            res = false;
        }
        else{
            res = paga(cantidad);
        }
        
        return res;
    }

    /**
     * Metodo que hace que el jugador pague impuesto si no esta encarcelado
     * @param cantidad : cantidad que tiene que pagar
     * @return res
     */
    boolean pagaImpuesto(float cantidad) {
        boolean res;
        
        if(encarcelado){
            res = false;
        }
        else{
            res = paga(cantidad);
        }
        
        return res;
    }
    /**
     * Jugador pasa por la salida y se le cobra. Se informa en el Diario
     * @return
     */
    boolean pasaPorSalida() {
        modificarSaldo(PasoPorSalida);
        Diario.getInstance().ocurreEvento("El jugador " +nombre +"ha pasado por"
                + "la salida");
        return true;
    }
    
    /**
     * El jugador pierde salvoconducto
     */
    private void perderSalvoconducto() {
        salvoconducto.usada();
        salvoconducto = null;
    }
    
    /**
     * Consulta si el jugador puede comprar unacasilla
     * @return 
     */
    boolean puedeComprarCasilla() {
        puedeComprar = true;
        if(encarcelado){
            puedeComprar = false;
        }
        
        return puedeComprar;
    }
    
    /**
     * Si el jugador esta en la carcel y puede salir pagando, se paga el precio,
     * deja de estar encarcelado y se informa al diario
     * @return resultado
     */
    boolean salirCarcelPagando() {
        boolean resultado = false;

        if(encarcelado && puedesalirCarcelPagando()){
            paga(PrecioLibertad);
            encarcelado = false;
            Diario.getInstance().ocurreEvento(nombre +" ha pagado para salir "
                    + "de la carcel");
            resultado = true;
        }

        return resultado;
    }
    
    /**
     * Informa de si se puede edificar y en caso de que se puede cual seria
     * el precio de esto
     * @param propiedad : propiedad en la que se quiere edificar
     * @return res
     */
    private boolean puedoEdificarCasa(TituloPropiedad propiedad) {
        boolean res = false;
        if(encarcelado == false){
            if(propiedad.getNumCasas()<=CasasMax){
                res = saldo >= propiedad.getPrecioEdificar();
            }
        }
        return res;
    }
    
    /**
     * Informa de si se puede edificar un hotel y en caso de que se pueda
     * cual seria el precio de este
     * @param propiedad : propiedad en la que se quiere edificar
     * @return res
     */
    private boolean puedoEdificarHotel(TituloPropiedad propiedad) {
        boolean res = false;
        if(encarcelado == false){
            if(propiedad.getNumCasas()<=HotelesMax && 
                    propiedad.getNumCasas()==CasasMax){
                res = saldo >= propiedad.getPrecioEdificar();
            }
        }
        
        return res;
    
    }
    
    /**
     * Consulta si el jugador puede gastarse ese dinero
     * @param precio : cantidad que quiero comprobar
     * @return res
     */
    protected boolean puedoGastar(float precio) {
        boolean res = false;
        if(encarcelado){
            res = false;
        }else{
            if(saldo >= precio){
                res = true;
            }
        }
        
        return res;
    }
    
    /**
     * Hace recibir al jugador una cantidad de dinero
     * @param cantidad : cantidad de dinero que recibe
     * @return res
     */
    boolean recibe(float cantidad) {
        boolean res;
        if(encarcelado){
            res = false;
        }else{
            res = modificarSaldo(cantidad);
        }
        
        return res;
    }
    
    /**
     * Informa si el saldo del jugador es mayor al precio de salir de la carcel
     * @return 
     */
    boolean puedesalirCarcelPagando() {
        return (saldo >= PrecioLibertad);        
    }
    
    /**
     * Se pregunta al dado si el jugador debe salir de la carcel
     * @return res
     */
    boolean salirCarcelTirando() {
        boolean res = false;
        
        if(Dado.getInstance().salgoDeLaCarcel()){
            encarcelado = false;
            Diario.getInstance().ocurreEvento(nombre +"ha tirado para salir "
                    + "de la carcel"); 
            res = true;
        }
        return res;
    }
    
    /**
     * Indica si el jugador tiene propiedades
     * @return 
     */
    boolean tieneAlgoQueGestionar() {
        return (propiedades!=null);
    }
    /**
     * Indica si el jugador tiene un Salvoconducto
     * @return 
     */
    boolean tieneSalvoconducto() {
        return (salvoconducto!=null);
    }
    
    /**
     * Muestra informacion
     * @return resultado
     */
    /*public String toString() {
        String resultado ="";
        String listapropiedades = "";
        String aux;
        
        for(int i=0; i<propiedades.size(); i++){
            aux = propiedades.get(i).getNombre();
            listapropiedades += " ";
            listapropiedades += aux;
        }
        
        resultado = "" +nombre +"\nEncarcelado: " +encarcelado + 
                "\nPuedecomprar: " +puedeComprar +"\nSaldo: " +saldo
                +"\nNº propiedades: " +propiedades.size()+ "\nPropiedades" 
                +propiedades +"\nNº casilla actual: " +numCasillaActual;
        
        return resultado;
    };*/
    
    /**
     * Se vende la propiedad (ip)
     * @param ip : propiedad que se quiere vender
     * @return resultado
     */
    boolean vender(int ip){
        boolean resultado = false;
        if(isEncarcelado()){
            resultado = false;
        }
        else{
            if(existeLaPropiedad(ip)){
                resultado = propiedades.get(ip).vender(this);
                if(resultado){
                    Diario.getInstance().ocurreEvento(nombre +"ha vendido la"
                        +" casilla " +propiedades.get(ip));
                    propiedades.remove(ip);
                }                
            }
        }
        
        return resultado;
    }

    void setPuedeComprar(boolean d){
        puedeComprar = d;
    }
}