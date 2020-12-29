package civitas;


public class TituloPropiedad {
    private float alquilerBase, precioCompra, precioEdificar, hipotecaBase,
            factorRevalorizacion;
    private static float factorInteresesHipoteca = 1.1f;
    private boolean hipotecado;
    private String nombre;
    private int numCasas, numHoteles;
    
    private Jugador propietario;
    private int ip;
    
    /**
     * Constructor por parametros
     * @param nombre : nombre de la propiedad
     * @param alquilerBase : cantidad de alquiler base
     * @param factorRevalorizacion : revalorizacion
     * @param hipotecaBase : hipoteca base
     * @param precioCompra : precio al comprar
     * @param precioEdificar : precio por edificar
     */
    public TituloPropiedad(String nombre, float alquilerBase, 
        float factorRevalorizacion, float hipotecaBase, float precioCompra, 
        float precioEdificar){
            this.alquilerBase = alquilerBase;
            this.factorRevalorizacion = factorRevalorizacion;
            this.hipotecaBase = hipotecaBase;
            this.nombre = nombre;
            this.precioCompra = precioCompra;
            this.precioEdificar = precioEdificar;
            propietario = null;          //Comienzan sin propietario
            hipotecado = false;          //Comienzan sin nada hipotecado
            numCasas = 0;                //Comienzan sin casas
            numHoteles = 0;              //Comienzan sin hoteles
    }
    
    /**
     * Constructor por parametros
     * @param hipotecado 
     * @param nhoteles : numero de hoteles que hay
     * @param ncasas : numero de casas que hay
     * @param n : nombre
     * @param pcompra : precio de compra
     * @param pedificar : precio por edificar
     * @param pbasealquiler : precio del alquiler base
     * @param pbasehipoteca : precio de la hipoteca base
     * @param frev : factor de revalorizacion
     */
    public TituloPropiedad(boolean hipotecado, int nhoteles, int ncasas,
            String n, float pcompra, float pedificar, float pbasealquiler,
            float pbasehipoteca, float frev){
        this.hipotecado = hipotecado;
        this.numCasas = ncasas;
        this.numHoteles = nhoteles;
        
        this.nombre = n;
        this.precioCompra = pcompra;
        this.precioEdificar = pedificar;
        this.alquilerBase = pbasealquiler;
        this.hipotecaBase = pbasehipoteca;
        this.factorRevalorizacion = frev;
    }
    
    /**
     * Consultor de la ip del titulopropiedad
     * @return ip
     */
    int getIp(){
        return ip;
    }
    
    /**
     * Actualiza el propietario a uno nuevo 
     * @param jugador : nuevo propietario
     */
    void actualizaPropietarioPorConversion(Jugador jugador){
        propietario = jugador;
    };

    /**
     * Cancela la hipotea de una propiedad
     * @param jugador : jugador actual
     * @return res
     */
    boolean cancelarHipoteca(Jugador jugador){
        boolean res = false;
        if(esEsteElPropietario(jugador) && hipotecado){
            propietario.paga(getImporteCancelarHipoteca());
            hipotecado = false;
            res = true;
        }
        return res;
    }

    /**
     * Consultor del numero de casas y hoteles construidos
     * @return
     */
    int cantidadCasasHoteles(){
        return (numCasas + numHoteles);
    }
    
    /**
     * Actualiza el propietario haciendo que el nuevo tenga que pagar el precio
     * @param jugador : jugador actual
     * @return res
     */
    boolean comprar(Jugador jugador){
        boolean res = false;
        if(tienePropietario() == false){
            actualizaPropietarioPorConversion(jugador);
            propietario.paga(precioCompra);
            res = true;
        }
        return res;
    }
    
    /**
     * Construye una casa
     * @param jugador : jugador actual
     * @return construido
     */
    boolean construirCasa(Jugador jugador){
        boolean construido = false;
        if(esEsteElPropietario(jugador) && numCasas <= jugador.getCasasMax()){
            jugador.paga(precioEdificar);
            numCasas++;
            construido = true;    
        }
        return construido;
    }
    
    /**
     * Construye un hotel
     * @param jugador : jugador actual
     * @return construido
     */
    boolean construirHotel(Jugador jugador){
        boolean construido = false;
        
        if(esEsteElPropietario(jugador) && numCasas>=jugador.getCasasMax()
                && numHoteles<jugador.getHotelesMax()){
            
            jugador.paga(precioEdificar*5);
            numHoteles++;
            construido = true;
            
        }
        return construido;
    };
    
    /**
     * Elimina casa
     * @param n : numero de casas
     * @param jugador : jugador actual
     * @return derruida
     */
    boolean derruirCasas(int n, Jugador jugador){
        boolean derruida = false;
        
        if(esEsteElPropietario(jugador) && numCasas>=n){
            numCasas -= n;
            derruida = true;
        }
        return derruida;
    };
    
    /**
     * Elimina hotel
     * @param n : numero de hoteles
     * @param jugador : jugador actual
     * @return derruido
     */
    boolean derruirHoteles(int n, Jugador jugador){
        boolean derruido = false;
        
        if(esEsteElPropietario(jugador) && numHoteles>=n){
            numHoteles -= n;
            derruido = true;
        }
        return derruido;
    }
    
    /**
     * Consultor de propietario
     * @param jugador : jugador a comprobar
     * @return 
     */
    boolean esEsteElPropietario(Jugador jugador){
        return (propietario == jugador);
    };
    
    /**
     * Consultor atributo hipotecado
     * @return hipotecado
     */
    public boolean getHipotecado(){
        return hipotecado;
    };

    /**
     * Consultor importe de la hipoteca
     * @return cantidadrecibida
     */
    float getImporteHipoteca(){
        
        float cantidadrecibida = (float)(hipotecaBase * 
                (1 + (numCasas * 0.5) + (numHoteles * 2.5)));
        return cantidadrecibida;
        
    };

    /**
     * Devuelve el importe que se obtiene al hipotecar el
     * título multiplicado por factorInteresesHipoteca(10%)
     * @return importe
     */
    float getImporteCancelarHipoteca(){
        float importe = (float)(factorInteresesHipoteca * getImporteHipoteca());
        return importe;
    }


    /**
     * Consultor de nombre
     * @return nombre
     */
    public String getNombre(){
        return nombre;
    };
    
    /**
     * Consultor del numero de casas
     * @return numCasas
     */
    int getNumCasas(){
        return numCasas;
    };
    
    /**
     * Consultor del numero de hoteles
     * @return numHoteles
     */
    int getNumHoteles(){
        return numHoteles;
    };
    
    /**
     * Devuelve el precio del alquiler calculado según las reglas del juego
     * Si el titulo esta hipotecado o el propietario esta en la carcel el
     * precio del alquiler es 0
     * @return precioalquiler
     */
    float getPrecioAlquiler(){
        float precioalquiler = (float) ((1 + (numCasas * 0.5) + 
                (numHoteles * 2.5)) * alquilerBase);
        return precioalquiler;
    };
    
    /**
     * Consultor del precio de compra
     * @return precioCompra
     */
    float getPrecioCompra(){
        return precioCompra;
    };
    
    /**
     * Consultor del precio por edificar
     * @return precioEdificar
     */
    float getPrecioEdificar(){
        return precioEdificar;
    };
    
    /**
     * Devuelve la suma del precio de compra con el precio de edificar las
     * casas y hoteles que tenga, multiplicado éste último por el factor 
     * de revalorización
     * @return precio
     */
    private float getPrecioVenta(){
        float precio;
        precio = (float)((precioCompra + (numCasas + 5 * numHoteles) * 
                precioEdificar) * factorRevalorizacion);
        return precio;
    }
    
    
    Jugador getPropietario(){
        return propietario;
    };
    
    /**
     * Se hipoteca una propiedad del jugador
     * @param jugador : jugador actual
     * @return res
     */
    boolean hipotecar(Jugador jugador){
        boolean res = false;
        
        if(hipotecado == false && esEsteElPropietario(jugador)){
            propietario.recibe(getImporteHipoteca());
            hipotecado = true;
            res = true;
        }
        return res;
    }
    
    /**
     * Devuelve true si el propietario se encuentra en la carcel
     * @return res
     */
    private boolean propietarioEncarcelado(){
        boolean res = false;
        
        if(propietario.isEncarcelado()){
            res = true;
        }
        
        return res;
        
    };
    /**
     * Devuelve true si tiene propietario
     * @return
     */
    boolean tienePropietario(){
        return (propietario!=null);
    };
    
    /**
     * Proporciona una representacion en forma de caracteres del estado completo
     * del objeto
     * @return devolver
     */
    public String toString(){
        String devolver;
        String aux = " ";
        if(propietario == null){
            aux = "no tiene propietario";
        }
        else{
            aux = propietario.getNombre();
        }
        
        devolver = "\nNombre: " + nombre + "\nPrecio compra: " + precioCompra
            + "\nPropietario: " + aux + "\nFactor revalorización: " + 
            factorRevalorizacion + "\nAlquilerbase: " + alquilerBase
            + "\nFactor intereses hipoteca: " + factorInteresesHipoteca 
            + "\nNumero casas: "+ numCasas + "\nNumero hoteles: " + numHoteles
            + "\nPrecio edificar: " + precioEdificar + "\nHipoteca base: "
                + hipotecaBase + "\nHipotecada: " + hipotecado;
            
            return devolver;
    }



    /**
     * Si el titulo tiene propietario y jugador no es el propietario, este 
     * paga el alquiler y el propietario lo recibe
     * @param jugador : jugador actual
     */
    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario() && esEsteElPropietario(jugador) == false){
            float precio = getPrecioAlquiler();
            jugador.pagaAlquiler(precio);
            propietario.recibe(precio);
        }
    }
    
    /**
     * Se vende una propiedad de jugador
     * @param jugador : jugador actual
     * @return res
     */
    boolean vender(Jugador jugador){
        boolean res = false;
        
        if(esEsteElPropietario(jugador)){
            propietario.recibe(getPrecioVenta());
            propietario = null;
            numCasas = 0;
            numHoteles = 0;
            res = true;
        }
        return res;
    };
    
    
    public void setPropietario(JugadorEspeculador j){
        propietario = j;
    }
}
