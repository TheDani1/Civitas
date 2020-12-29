package juegoTexto;

import civitas.CivitasJuego;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.GestionesInmobiliarias;
import civitas.SalidasCarcel;
import java.util.ArrayList;
import civitas.Jugador;

class Controlador {
    private CivitasJuego juego;
    private VistaTextual vista;
    
    /**
     * Constructor
     * @param juego : instancia de CivitasJuego
     * @param vista : instancia de VistaTextual
     */
    Controlador(CivitasJuego juego, VistaTextual vista){
        this.juego = juego;
        this.vista = vista;
    }
    
    /**
     * Unico metodo de esta clase
     * Indica a la vista que muestre el estado del juego actualizado
     * Mientras no se haya producido el final del juego:
     *  - Muestra el estado del juego en cada momento (actualizarVista)
     *  - Indica a la vista que haga una pausa (interaccion del usario entre
     *    turno y turno
     *  - Indica a la vista que muestre la siguiente operacion que se va a 
     *    realizar (siguientePaso)
     *  - Si la operacion anterior no es pasar de turno, indica a la vista que
     *    muestre los eventos pendientes en el diario
     *  - Pregunta al modelo si se ha llegado al final del juego
     *  - Si no ha terminado, dependiendo del valor "operacion" del primer paso
     *    se realiza lo siguiente:
     *          + Si es comprar: ejecuta el metodo asociado a la compra y si
     *            la respuesta obtenida es SI, ejecuta el metodo asociado a la
     *            compra.
     *            Metodo siguientePasoCompletado
     *          + Si es gestionar: ejecuta el metodo gestionar y se consultan 
     *            los inices de gestion y propiedad seleccionados.
     *            Se crea un objeto OperacionInmoviliaria y se llama al metodo
     *            correspondiente a la gestion pasando el ip como parametro
     *            Si la gestion es TERMINAR se llama siguientePasoCompletado
     *          + Si es intentar salir de la carcel: ejecuta el metodo asociado 
     *            a la eleccion de metodo de salida y en funcion de la opcion
     *            se llama al metodo adecuado del modelo.
     *            Metodo siguientePasoCompletado
     *  - Cuando se produzca el final del juego se muestra un ranking 
     *    de jugadores
     */
    /*void juega(){
        vista.setCivitasJuego(juego);
        
        while(vista.juegoModel.finalDelJuego() == false){
            
            System.out.println("");
            vista.actualizarVista();
            
            vista.pausa();
            
            OperacionesJuego siguiente = vista.juegoModel.siguientePaso();
            vista.mostrarSiguienteOperacion(siguiente);

            if(siguiente != OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            ;
            boolean fin = vista.juegoModel.finalDelJuego();

            if(fin == false){
                if(siguiente == OperacionesJuego.COMPRAR){
                    Respuestas respuesta = vista.comprar();
                    
                    if(respuesta == Respuestas.SI){
                        vista.juegoModel.comprar();
                        vista.juegoModel.siguientePasoCompletado(OperacionesJuego.COMPRAR);        
                    }
                    else{
                        vista.juegoModel.siguientePasoCompletado(OperacionesJuego.COMPRAR);
                    }
                }
                else if(siguiente == OperacionesJuego.GESTIONAR){
                    vista.gestionar();
                    OperacionInmobiliaria operacion = new 
                        OperacionInmobiliaria(GestionesInmobiliarias.values()[vista.getGestion()], vista.getPropiedad());

                        System.out.println(operacion.getNumPropiedad() + "es la propiedad");

                        if(operacion.getGestion() == GestionesInmobiliarias.CANCELAR_HIPOTECA){
                            vista.juegoModel.cancelarHipoteca(operacion.getNumPropiedad());
                        }
                        else if(operacion.getGestion() == GestionesInmobiliarias.CONSTRUIR_CASA){
                            vista.juegoModel.construirCasa(operacion.getNumPropiedad());
                        }
                        else if(operacion.getGestion() == GestionesInmobiliarias.CONSTRUIR_HOTEL){
                            vista.juegoModel.construirHotel(operacion.getNumPropiedad());
                        }
                        else if(operacion.getGestion() == GestionesInmobiliarias.HIPOTECAR){
                            vista.juegoModel.hipotecar(operacion.getNumPropiedad());
                        }
                        else if(operacion.getGestion() == GestionesInmobiliarias.VENDER){
                            vista.juegoModel.vender(operacion.getNumPropiedad());
                        }
                        else if(operacion.getGestion() == GestionesInmobiliarias.TERMINAR){
                            vista.juegoModel.siguientePasoCompletado(OperacionesJuego.GESTIONAR);
                        }
                }

                else if(siguiente == OperacionesJuego.SALIR_CARCEL){
                    SalidasCarcel salida = vista.salirCarcel();
                    if(salida == SalidasCarcel.PAGANDO){
						vista.juegoModel.salirCarcelPagando();
					}
					else{
						vista.juegoModel.salirCarcelTirando();
					}
					vista.juegoModel.siguientePasoCompletado(OperacionesJuego.SALIR_CARCEL);
                }
            }
        }
		
		vista.juegoModel.mostrarRanking();
	}*/
    
    public void juega(){
        
        vista.setCivitasJuego(juego);
        
        while(!juego.finalDelJuego()){
            OperacionesJuego operacion = juego.siguientePaso();
            
            vista.actualizarVista();

            vista.mostrarSiguienteOperacion(operacion);

            
            if(operacion != civitas.OperacionesJuego.PASAR_TURNO)
                vista.mostrarEventos();
            
            
            if(!juego.finalDelJuego()){
                switch(operacion){
                    case COMPRAR:
                        Respuestas r = vista.comprar();
                        if(r == Respuestas.SI)
                            juego.comprar();
                        juego.siguientePasoCompletado(operacion);
                        break;
                    case GESTIONAR:
                        vista.gestionar();
                        vista.getGestion();
                        vista.getPropiedad();
                        OperacionInmobiliaria op = new OperacionInmobiliaria
                        (GestionesInmobiliarias.values()[vista.getGestion()],
                        vista.getPropiedad());
                        
                        switch(GestionesInmobiliarias.values()
                                [vista.getGestion()]){
                            case VENDER:
                                if(juego.getJugadorActual().getNombrePropiedades()
                                        !=null){
                                    juego.vender(op.getNumPropiedad());
                                }
                                break;
                            case HIPOTECAR:
                                if(juego.getJugadorActual().getNombrePropiedades()
                                        !=null){
                                    juego.hipotecar(op.getNumPropiedad());
                                }
                                break;
                            case CONSTRUIR_CASA:
                                juego.construirCasa(op.getNumPropiedad());
                                break;
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(op.getNumPropiedad());
                            default:
                                juego.siguientePasoCompletado(operacion);
                            break;
                        }
                    break;
                    
                    case SALIR_CARCEL:
                        SalidasCarcel salida = vista.salirCarcel();
                        switch(salida){
                            case PAGANDO: 
                                juego.salirCarcelPagando();
                                break;
                            case TIRANDO:
                                juego.salirCarcelTirando();
                                break;
                        }
                        
                }
                
            }
            
            else{
                ArrayList<Jugador> ranking = juego.ranking();
                for(int i=0; i<ranking.size(); i++){
                    System.out.println(ranking.get(i).getNombre() + "\n");
                }
                
                vista.actualizarVista();
            }
        }
    }
}

