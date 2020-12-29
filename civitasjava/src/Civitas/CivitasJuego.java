package civitas;

import java.util.ArrayList;
import civitas.TituloPropiedad;
import java.util.Collections;

public class CivitasJuego {
    private int indiceJugadorActual;
    private EstadosJuego estado;
    private GestorEstados gestor;
    private Tablero tablero;
    private MazoSorpresas mazo;
    private ArrayList<Jugador> jugadores;
    private OperacionesJuego operacion;
    
    /**
     * Cambia de posicion al jugador
     */
    private void avanzaJugador(){
        Jugador jugadorActual = getJugadorActual();
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        this.contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        this.contabilizarPasosPorSalida(jugadorActual);
    };
	
    /**
     * Cancela la hipoteca de un jugador
     * @param ip : propiedad de la que se quiere cancelar la hipoteca
     * @return 
     */
    public boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
};

    /**
     * Constructor de la clase que:
     * 1) Inicializa el atributo jugadores creando y añadiendo un jugador por 
     *    cada nombre suministrado como parametro
     * 2) Crear el gestor de estados
     * 3) Inicializa el indice del jugador actual
     * 4) Crea el mazo de sorpresas
     * 5) Inicializa el tablero
     * 6) Inicializa el mazo de sorpresas
     * @param nombres : nombre de los jugadores que se quieren añadir
     */
    public CivitasJuego(ArrayList<String> nombres){
        jugadores = new ArrayList<>();
        for(int i=0; i<nombres.size(); i++){
            jugadores.add(new Jugador(nombres.get(i)));
        }
       
        gestor = new GestorEstados();
        gestor.estadoInicial();
		
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());

        estado = EstadosJuego.INICIO_TURNO;

        mazo = new MazoSorpresas(true);
        
        mazo = new MazoSorpresas(true);
        inicializarTablero(mazo);
        inicializarMazoSorpresas(tablero);
        
		
    };
    
    /**
     * Compra la propiedad de la casilla en la que se encuentra
     * @return res
     */
    public boolean comprar(){
        boolean res = false;
        
        Jugador jugadoractual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadoractual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = ((CasillaCalle)casilla).getTituloPropiedad();
        res = jugadoractual.comprar(titulo);
        
        return res;
    };
	

    /**
     * Delega en el metodo construirCasa(ip) de Jugador
     * @param ip : propiedad donde se quiere construir la casa
     * @return 
     */
    public boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }
	
    /**
     * Delega en el metodo construirHotel de Jugador
     * @param ip : propiedad donde se quiere construir el hotel
     * @return 
     */
    public boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    };

    /**
     * Mientras el método getPorSalida del tablero devuelva un valor mayor que 
     * 0 se llama al método pasaPorSalida del jugador pasado como parámetro. 
     * Al llamar a ese método el jugador actual cobra por todas las veces que 
     * ha pasado por la salida en su turno actual.
     * @param jugadorActual : jugador que va a cobrar la salida
     */
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        if(tablero.getPorSalida() > 0){
            jugadorActual.pasaPorSalida();
        }
    };

    /**
     * Devuelve true si alguno de los jugadores esta en bancarrota
     * @return fin
     */
    public boolean finalDelJuego(){
        boolean fin = false;

        for(int i=0; i<jugadores.size(); i++){
            
            if(jugadores.get(i).enBancarrota()){
                fin = true;

            }
        }

        return fin;
    };
	
    /**
     * Delega en el metodo getCasilla de Tablero
     * @return 
     */
    public Casilla getCasillaActual(){
        return tablero.getCasilla(getJugadorActual().getNumCasillaActual());
    };

    /**
     * Devuelve el jugador actual
     * @return 
     */
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    };

    /**
     * Delega en el metodo hipotecar(ip) de Jugador
     * @param ip : indice de la propiedad
     * @return 
     */
    public boolean hipotecar(int ip){
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    };

    public String infoJugadorTexto(){
	return (jugadores.get(indiceJugadorActual).toString() + "\n"
	+"Casilla actual: " +getCasillaActual().toString() +"\n--------------");
    };
	
    /**
     * Inicializa el mazo de sorpresas rellenando el mazosorpresas con sorpresas
     * @param tablero : 
     */
    private void inicializarMazoSorpresas(Tablero tablero){
        
      
        mazo.alMazo(new SorpresaPagarCobrar(250, "Cobras 250"));
        
        //mazo.alMazo(new SorpresaIrCasilla(tablero, 0, "Avanzas a la casilla de salida"));
        
        mazo.alMazo(new SorpresaPagarCobrar(-250, "Pagas 250"));
        
        mazo.alMazo(new SorpresaPorJugador(250, "Cobras 250"
                + "por cada jugador"));
        
        mazo.alMazo(new SorpresaSalirCarcel(mazo, "Obtienes la llave"));
        
        mazo.alMazo(new SorpresaPorCasaHotel(250, "Cobras 250"
                + "por cada hotel"));
    };

    /**
     * Se inicializa el tablero completando cada una de sus casillas
     * @param mazo :
     */
    private void inicializarTablero(MazoSorpresas mazo){
        
        tablero = new Tablero(5);
        this.mazo = mazo;
        
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Ogijares",110,
                (float) 1.29,680,1150,240)));

        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Armilla",110,
                (float) 1.29,680,1150,240)));
        
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Otura",110,
                (float) 1.29,680,1150,240)));

        tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa1"));

        tablero.añadeCasilla(new CasillaImpuesto((float)1200.0, "Multado"));
        
        tablero.añadeJuez();
        
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("La chana",110,
                (float) 1.29,680,1150,240)));

        tablero.añadeCasilla(new Casilla("Parking"));

        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Albolote", 150,
                (float) 1.45, 860, 1850, 320 )));
        
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Peligros",110,
                (float) 1.29,680,1150,240)));
        
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Pulianas",110,
                (float) 1.29,680,1150,240)));

        tablero.añadeCasilla(new CasillaSorpresa(mazo, "sorpresa 2"));

        tablero.añadeCasilla(new CasillaImpuesto((float)1235.0, "Multilla"));
    };
	
    /**
     * Se pasa el turno de un jugador al siguiente
     * Si es el ultimo se pasa al primero 
     */
    private void pasarTurno(){
        indiceJugadorActual++;
        if(indiceJugadorActual == jugadores.size())
            indiceJugadorActual = 0;
    };

    /**
     * Produce una lista ordenada de jugadores en funcion de su saldo
     * @return lista
     */
    public ArrayList<Jugador> ranking(){
        /*ArrayList<Jugador> ranking = new ArrayList<Jugador>();

        for(int i=0; i<jugadores.size(); i++){
            ranking.add(jugadores.get(i));
        }

        for(int i=0; i<ranking.size(); ++i){
            for(int j=i+1; j<ranking.size(); ++j){
                if(ranking.get(i).compareTo(ranking.get(j)) < 0){
                        Jugador aux = new Jugador(ranking.get(i));
                        ranking.set(i, ranking.get(j));
                        ranking.set(j,aux);
                }
            }
        }
        return ranking;*/
        ArrayList<Jugador> lista=jugadores;
        lista.sort((o1,o2) -> o1.compareTo(o2));
        Collections.reverse(lista);
        return lista;
    };
	
    /**
     * Se muestra el ranking de jugadores
     */
    public void mostrarRanking(){
        for(int i=0; i<ranking().size(); i++){
            System.out.println(i +".-" +ranking().get(i));
        }
    }

    /**
     * Delega em el metodo salircarcelpagando de Jugador
     * @return 
     */
    public boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    };

    /**
     * Delega en el metodo salircarceltirando de Jugador
     * @return 
     */
    public boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    };
    
    /**
     * Muestra cual es el siguiente paso elegido
     * @return operacion
     */
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestor.operacionesPermitidas(jugadorActual,
                estado);

        if(operacion == OperacionesJuego.PASAR_TURNO){
            this.pasarTurno();
            this.siguientePasoCompletado(operacion);
        }
        else if(operacion == OperacionesJuego.AVANZAR){
            this.avanzaJugador();
            this.siguientePasoCompletado(operacion);
        }

        return operacion;
    }

    /**
     * Se actualiza el estado del juego obteniendo el siguiente  estado del 
     * gestor de estados (método siguienteEstado).
     * Para ello es necesario obtener el jugador actual.
     * @param operacion 
     */
    public void siguientePasoCompletado(OperacionesJuego operacion){
	estado = gestor.siguienteEstado(jugadores.get(indiceJugadorActual),
                estado, operacion);
    }

    /**
    * Delega en el metodo vender(ip) de Jugador
    * @param ip : indice de la propiedad
    * @return 
    */
    public boolean vender(int ip){
        return jugadores.get(indiceJugadorActual).vender(ip);
    };
 
      
}

