package civitas;

public class TituloPropiedad {
    
    private float alquilerBase;
    private static float factorInteresesHipoteca = (float) 1.1;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    
    
    //añadido propietario
    private Jugador propietario;
    
    void actualiarPropietarioPorConversion(Jugador jugador){
        propietario = jugador;
    }
    
    boolean cancelarHipoteca(Jugador jugador){
        if(hipotecado && esEsteElPropietario(jugador)){
            if(jugador.paga(getImporteCancelarHipoteca())){
                hipotecado = false;
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public float getPrecioEdificar() {
        return precioEdificar;
    }

    public String getNombre() {
        return nombre;
    }

    public TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe){

        nombre = nom;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;
        hipotecado=false;
        numCasas=0;
        numHoteles=0;

    }
    @Override
    public String toString(){

        return "\n----------[PROPIEDAD]----------\n[Nombre] -> " + nombre
        + "\n[Alquiler base] -> " + alquilerBase + "\n[Factor InteresesHipoteca] -> " + factorInteresesHipoteca
        + "\n[Factor Revalorizacion] -> " + factorRevalorizacion + "\n[Hipoteca base] -> " + hipotecaBase
        + "\n[Hipotecado] -> " + hipotecado + "\n[Numero casas] -> " + numCasas + "\n[Numero Hoteles] -> " + numHoteles
        + "\n[Precio compra] -> " + precioCompra + "\n[Precio edificacion] ->  " + precioEdificar + "\n-------------------------------";

    }

    public float getPrecioAlquiler(){

        if(hipotecado || propietarioEncarcelado()){// añadido propietario es encarcelado

            return 0;

        }else{

            return (float) (alquilerBase*(1+(numCasas*0.5) + (numHoteles*2.5)));

        }
    }
    
    private float getImporteHipoteca(){   
        return (float) (hipotecaBase*(1+(numCasas*0.5)+(numHoteles*2.5)));
    }

    float getImporteCancelarHipoteca(){

        // (getImporteHipoteca() *factorInteresesHipoteca);
        
        return (getImporteHipoteca() * factorInteresesHipoteca);
        

    }

    void tramitarAlquiler(Jugador jugador){

        if((getPropietario() != null) && (propietario != jugador)){

            //float alquiler = jugador.pagaAlquiler();
            //getPropietario().recibe(alquiler);
            
            jugador.pagaAlquiler(alquilerBase);
            propietario.recibe(getPrecioAlquiler());
            

        }

    }

    private boolean propietarioEncarcelado(){

        if(tienePropietario()){

            if(propietario.isEncarcelado()){
                return true;
            }else{
                return false;
            }

        }else{
            return false;
        }

    }

    int cantidadCasasHoteles(){

        return numHoteles+numCasas;

    }

    private float getPrecioVenta(){

        return precioCompra + (precioEdificar*numCasas) + (precioEdificar*numHoteles*factorRevalorizacion);

    }
    int getNumCasas(){
        return numCasas;
    }
    int getNumHoteles(){
        return numHoteles;
    }
    
    float getPrecioCompra(){
        return precioCompra;
    }
    
    boolean comprar(Jugador jugador){

        if (propietario == null){
            propietario = jugador;
            propietario.paga(precioCompra);
            return true;

        }

        return false;
    }
    boolean construirCasa(Jugador jugador){
        if (esEsteElPropietario(jugador)){
            if(jugador.paga(getPrecioEdificar())){
                numCasas++;
                return true; 
            }else{
                return false;
            } 
        }else{
            return false;
        }
            
    }
    boolean construirHotel(Jugador jugador){
        if (esEsteElPropietario(jugador)){
            if(jugador.paga(getPrecioEdificar())){
                numHoteles++;
                numCasas = 0;
                return true; 
            }else{
                return false;
            }   
        }else{
            return false;
        }             
    }

    boolean derruirCasas(int n, Jugador jugador){

        if(jugador == getPropietario() && numCasas >= n){

            numCasas -= n;
            return true;

        }else{
            return false;
        }

    }
    boolean esEsteElPropietario(Jugador jugador){
        if (jugador == propietario){
            return true;
        }else{
            return false;
        }
    }

    boolean vender(Jugador jugador){

        if(getPropietario() == jugador && !hipotecado){
            if(propietario.recibe(getPrecioVenta())){
                propietario=jugador;
                numCasas = 0;
                numHoteles = 0;
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    
    
    
    
    Jugador getPropietario(){
        return propietario;
    }
    
    public boolean getHipotecado(){
        return this.hipotecado;
    }
    boolean tienePropietario(){
        if(hipotecado){
            return true;
        }else{
            return false;
        }
    }
    
    boolean hipotecar(Jugador jugador){
        if (!hipotecado && esEsteElPropietario(jugador)){
            if(jugador.recibe(getImporteHipoteca())){
            hipotecado= true;
            return true;
            }else{
                return false;
            }
        }else{
            return false;
        }       
    }
    
    



}
