package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.Respuestas;
import civitas.GestionesInmobiliarias;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.Jugador;
import civitas.TituloPropiedad;

public class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("\nPulsa una tecla\n");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("\nElige la forma para intentar salir de la carcel", new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  Respuestas comprar() {

    ArrayList<String> lista = new ArrayList<>();
    lista.add("SI");
    lista.add("NO");

    int eleccion=menu("\n¿Desea comprar la calle a la que ha llegado?",lista);
    if(eleccion == 0){

      return Respuestas.SI;

    }else{

      return Respuestas.NO;

    }

  }

  void gestionar () {
      Jugador jugador = juegoModel.getJugadorActual();
      int opcion = menu ("¿Que deseas gestionar?",
       new ArrayList<> (Arrays.asList("VENDER", "HIPOTECAR", "CANCELAR_HIPOTECA", "CONSTRUIR_CASA", "CONSTRUIR_HOTEL", "TERMINAR")));
      iGestion = opcion;
      iPropiedad=jugador.getNumCasillaActual();
      //funcion getNumCasillaActual de jugador cambiada a publica.
  }
  
  public int getGestion(){
      return iGestion;
  }
  
  public int getPropiedad(){
      return iPropiedad;
  }
    

  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
      System.out.print(operacion);
  }


  void mostrarEventos() {
      //cambiado el Diario leer eventos pendientes
      if(Diario.getInstance().eventosPendientes()){
        String cad = Diario.getInstance().leerEvento();
        System.out.print(cad);
      }
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        //this.actualizarVista();

    }
  
  void actualizarVista(){

      juegoModel.getJugadorActual().toString();
      System.out.println(juegoModel.getCasillaActual());
  } 
}
