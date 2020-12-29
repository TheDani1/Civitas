/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.GestionesInmobiliarias;
import civitas.Respuestas;
import civitas.OperacionInmobiliaria;

public class Controlador {
    
    CivitasJuego juego;
    VistaTextual vista;

    Controlador(CivitasJuego juego, VistaTextual vista){
            this.juego=juego;
            this.vista = vista;
    }
    
    void juega() {

        vista.setCivitasJuego(juego);

        while (!juego.finalDelJuego()) {

            vista.actualizarVista();
            vista.pausa();

            OperacionesJuego operacion = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(operacion);

            if (operacion != OperacionesJuego.PASAR_TURNO) {
                vista.mostrarEventos();;
            }

            if (!juego.finalDelJuego()) {
                //metodo comprar

                if ( operacion == OperacionesJuego.COMPRAR ) {

                    if (vista.comprar() == Respuestas.SI) {
                        juego.comprar();
                    }
                    juego.siguientePasoCompletado(operacion);

                } else if (operacion == OperacionesJuego.GESTIONAR) {
                    vista.gestionar();
                    OperacionInmobiliaria ig = new OperacionInmobiliaria(GestionesInmobiliarias.values()[vista.getGestion()],vista.getPropiedad());

                    switch (ig.getGestion()) {
                        case VENDER:
                            juego.vender(vista.getPropiedad());
                            break;
                        case HIPOTECAR:
                            juego.hipotecar(vista.getPropiedad());
                            break;
                        case CANCELAR_HIPOTECA:
                            juego.cancelarHipoteca(vista.getPropiedad());
                            break;
                        case CONSTRUIR_CASA:
                            juego.construirCasa(vista.getPropiedad());
                            break;
                        case CONSTRUIR_HOTEL:
                            juego.construirHotel(vista.getPropiedad());
                            break;
                        case TERMINAR:
                            juego.siguientePasoCompletado(operacion);
                            break;

                    }

                } else if (operacion == OperacionesJuego.SALIR_CARCEL) {
                    SalidasCarcel salida;
                    salida = vista.salirCarcel();
                    if (salida == SalidasCarcel.PAGANDO) {
                        juego.salirCarcelPagando();
                    } else {
                        juego.salirCarcelTirando();
                    }
                    juego.siguientePasoCompletado(OperacionesJuego.SALIR_CARCEL);

                }

                if(juego.finalDelJuego()) {

                    System.out.println("El juego ha finalizado debido a que un jugador ha caido en bancarrota. Ranking: ");
                    for (int i = 0; i < juego.ranking().size(); i++) {

                        System.out.println(juego.ranking().get(i));
                    }
                }
            }
        }
    }
}