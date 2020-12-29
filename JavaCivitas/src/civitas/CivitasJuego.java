/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;

public class CivitasJuego {
    
    //solo se ponen los que salen no los que la flecha termina en la clase
    private int indiceJugadorActual;
    
    //relacion a jugador
    private ArrayList<Jugador> jugadores;
    //relacion Estados juegos
    private EstadosJuego estado;
    //relacion GestorEstados
    private GestorEstados gestorEstados;
    //relacion a Tablero
    private Tablero tablero = new Tablero(6);
    //private Tablero tablero;
    //relacion MAZOSORPRESAS
    private MazoSorpresas mazo;
    
    
    //metodos
    
    public void avanzaJugador(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla;
        casilla = tablero.getCasilla(posicionNueva);
        this.contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        //System.out.print("ANTES DE CASILLA");
        casilla.recibeJugador(indiceJugadorActual,jugadores);
        this.contabilizarPasosPorSalida(jugadorActual);
    }
    
    
    public boolean cancelarHipoteca(int ip){
        Jugador actual = jugadores.get(indiceJugadorActual);
        return actual.cancelarHipoteca(ip);
    }
    
    //constructor CIVITAS
    
    public CivitasJuego(ArrayList<String> nombres){

        ArrayList<Jugador> players = new ArrayList();
        for(int i=0; i<nombres.size(); i++){
            //añade los jugadores que se han insertado
            players.add(new Jugador(nombres.get(i)));
        }
        jugadores = players;
        gestorEstados = new GestorEstados();
        estado = EstadosJuego.INICIO_TURNO;
        indiceJugadorActual = Dado.getInstance().quienEmpieza(nombres.size()-1);
        mazo = new MazoSorpresas(true);
        inicializarMazoSorpresas(tablero);
        inicializarTablero(mazo);
    }
    
    public boolean comprar(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = ((CasillaCalle)casilla).getTituloPropiedad();
        return jugadorActual.comprar(titulo);
    }
    
    public boolean construirCasa(int ip){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        return jugadorActual.construirCasa(ip);
    }
    
    public boolean construirHotel(int ip){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        return jugadorActual.construirHotel(ip);
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        while(tablero.getPorSalida() > 0){
            jugadorActual.pasaPorSalida();
        }
    }
    
    public boolean finalDelJuego(){
        boolean finDelJuego=false;
        for(int i =0; i<jugadores.size() && !jugadores.get(i).enBancarrota(); i++){
            if(jugadores.get(i).enBancarrota()){
                finDelJuego=true;
            }
        }
        return finDelJuego;
    }
    
    public Casilla getCasillaActual(){
        int jugadorActual = jugadores.get(indiceJugadorActual).getNumCasillaActual();
        return tablero.getCasilla(jugadorActual);
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
    
    public boolean hipotecar(int ip){
        Jugador jugadorActual=jugadores.get(indiceJugadorActual);
        return jugadorActual.hipotecar(ip);
    }
    
    public String infoJugadorTexto(){
        return jugadores.get(indiceJugadorActual).toString();
    }
    
    private void inicializarMazoSorpresas(Tablero tablero){

        Sorpresa posicion = new SorpresaPosicion(4,tablero,25);
        mazo.alMazo(posicion);

        Sorpresa Especulador = new SorpresaEspeculador(1500,"[SORPRESA] Conversion ESPECULADOR");
        mazo.alMazo(Especulador);

        Sorpresa irACarcel = new SorpresaIrCarcel(tablero, "[SORPRESA] Ir Carcel");
        mazo.alMazo(irACarcel);

        Sorpresa irACasilla = new SorpresaIrCasilla(tablero,3,"[SORPRESA] Vete a la casilla 3");
        mazo.alMazo(irACasilla);

        Sorpresa irACasillaN2 = new SorpresaIrCasilla(tablero,5,"[SORPRESA] Vete a la casilla 5");
        mazo.alMazo(irACasillaN2);

        Sorpresa salirCarcel = new SorpresaSalirCarcel(mazo, "[SORPRESA] Salvoconducto obtenido");
        mazo.alMazo(salirCarcel);

        Sorpresa pagar = new SorpresaPagarCobrar(-50,"[SORPRESA] Tienes que pagar 50");
        mazo.alMazo(pagar);

        Sorpresa cobrar = new SorpresaPagarCobrar(50,"[SORPRESA] Tienes que cobrar 50");
        mazo.alMazo(cobrar);

        Sorpresa porCasaHotel = new SorpresaPorCasaHotel(20,"[SORPRESA] Pagas 100 por cada casa y hotel");
        mazo.alMazo(porCasaHotel);

        Sorpresa porJugador1 = new SorpresaPorJugador(30,"[SORPRESA] Cada jugador te paga 30");
        mazo.alMazo(porJugador1);

        Sorpresa porJugador2 = new SorpresaPorJugador(-30,"[SORPRESA] Se te quitan 30 para pagar a un jugador");
        mazo.alMazo(porJugador2);
    }
    
    public void inicializarTablero(MazoSorpresas mazo){

        tablero = new Tablero(6);

        // CASILLA 1
        TituloPropiedad titulo1 = new TituloPropiedad("Camino Purchil",100,(float)1.3,200,300,50); //Calle
        CasillaCalle casilla1=new CasillaCalle(titulo1);
        tablero.añadeCasilla(casilla1);

        // CASILLA 2
        TituloPropiedad titulo2 = new TituloPropiedad("Genil",85,(float)1.2,150,250,50);
        CasillaCalle casilla2=new CasillaCalle(titulo2);
        tablero.añadeCasilla(casilla2);

        // CASILLA 3
        TituloPropiedad titulo3 = new TituloPropiedad("Avenida Madrid",150,(float)1.4,250,350,75);
        CasillaCalle casilla3=new CasillaCalle(titulo3);
        tablero.añadeCasilla(casilla3);

        // CASILLA 4
        TituloPropiedad titulo4 = new TituloPropiedad("Camino Ronda",200,(float)1,100,440,55);
        CasillaCalle casilla4=new CasillaCalle(titulo4);
        tablero.añadeCasilla(casilla4);

        // CASILLA 5
        TituloPropiedad titulo5 = new TituloPropiedad("Reyes Catolicos",95,(float)1.9,120,490,20);
        CasillaCalle casilla5=new CasillaCalle(titulo5);
        tablero.añadeCasilla(casilla5);

        // CASILLA IMPUESTO
        CasillaImpuesto impuesto = new CasillaImpuesto(100,"Factura Endesa: 100"); //Impuesto
        tablero.añadeCasilla(impuesto);

        // CASILLA SORPRESA
        CasillaSorpresa sorpresa1 = new CasillaSorpresa(mazo,"Sorpresas");
        tablero.añadeCasilla(sorpresa1);

        // CASILLA 6
        TituloPropiedad titulo6 = new TituloPropiedad("Marbella",300,(float)1.5,1500,3500,980);
        CasillaCalle casilla6=new CasillaCalle(titulo6);
        tablero.añadeCasilla(casilla6);
        TituloPropiedad titulo7 = new TituloPropiedad("Caceres",400,(float)1.6,900,1500,150);
        CasillaCalle casilla7=new CasillaCalle(titulo7);
        tablero.añadeCasilla(casilla7);
        TituloPropiedad titulo8 = new TituloPropiedad("Gonzalo Gallas",500,(float)1.7,600,200,250);
        CasillaCalle casilla8=new CasillaCalle(titulo8);
        tablero.añadeCasilla(casilla8);
        TituloPropiedad titulo9 = new TituloPropiedad("Plaza Einstein",550,(float)1.8,3000,1200,500);
        CasillaCalle casilla9=new CasillaCalle(titulo9);
        tablero.añadeCasilla(casilla9);

        CasillaSorpresa sorpresa2 = new CasillaSorpresa(mazo, "Que Sera");
        tablero.añadeCasilla(sorpresa2);

        CasillaSorpresa sorpresa3 = new CasillaSorpresa(mazo, "Otra vez");
        tablero.añadeCasilla(sorpresa3);

        TituloPropiedad titulo10 = new TituloPropiedad("California",700,1.1f,1800,2500,1000);
        CasillaCalle casilla10=new CasillaCalle(titulo10);
        tablero.añadeCasilla(casilla10);
        TituloPropiedad titulo11 = new TituloPropiedad("Casablanca",500,1.3f,1200,1600,800);
        CasillaCalle casilla11=new CasillaCalle(titulo11);
        tablero.añadeCasilla(casilla11);
        TituloPropiedad titulo12 = new TituloPropiedad("Wellington",1200,1.4f,2000,2300,1000);
        CasillaCalle casilla12=new CasillaCalle(titulo12);
        tablero.añadeCasilla(casilla12);

        Casilla parking = new Casilla("Parking");
        tablero.añadeCasilla(parking);

        tablero.añadeJuez();
    }
    
    public void pasarTurno(){
        indiceJugadorActual = (indiceJugadorActual+1)% jugadores.size();
    }

    
    public ArrayList<Jugador> ranking(){
        ArrayList<Jugador> devolver = new ArrayList();
        ArrayList<Jugador> rank = new ArrayList(jugadores);
        
        Jugador jugadorTop;
        
        for(int i=0; i< jugadores.size(); i++){
            Jugador max = jugadores.get(1);
            for(int j=0; j< rank.size();j++){
                jugadorTop = jugadores.get(i);
                if(max.compareTo(jugadorTop) == 1){
                    max = jugadorTop;
                }
            }
            rank.remove(max);
            devolver.add(max);
        }
        return devolver;
    }
    
    public boolean salirCarcelPagando(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        return jugadorActual.salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        return jugadorActual.salirCarcelTirando();
    }
    
    public OperacionesJuego siguientePaso(){
       Jugador jugadorActual = jugadores.get(indiceJugadorActual);
       OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
       if(operacion == OperacionesJuego.PASAR_TURNO){
           this.pasarTurno();
           this.siguientePasoCompletado(operacion);
       }
       if(operacion == OperacionesJuego.AVANZAR){
           this.avanzaJugador();
           this.siguientePasoCompletado(operacion);
       }
       contabilizarPasosPorSalida(jugadorActual);
       return operacion;
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        Jugador jugador = jugadores.get(indiceJugadorActual);
        gestorEstados.siguienteEstado(jugador,estado,operacion);
    }
    
    public boolean vender(int ip ){
        Jugador actual = jugadores.get(indiceJugadorActual);
        return actual.vender(ip);
    }
    
    
    public void actualizarInfo(){
       
        System.out.println(this.infoJugadorTexto());
        
        for (int i = 0; i < jugadores.size() -1 && !jugadores.get(i).enBancarrota(); i++){
            if (jugadores.get(i+1).enBancarrota())
                ranking();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
}
