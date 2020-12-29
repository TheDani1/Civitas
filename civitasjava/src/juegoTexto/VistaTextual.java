package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.Respuestas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


class VistaTextual {
	CivitasJuego juegoModel;
	int iGestion = -1;
	int iPropiedad = -1;
	private static String separador = "==================";
	
	private Scanner in;

	public VistaTextual(){
		in = new Scanner(System.in);
	}
	
	void mostrarEstado(String estado){
		System.out.println(estado);
	}
	
	void pausa(){
		System.out.println("Pulsa una tecla: ");
		in.nextLine();
	}
	
	int leeEntero(int max, String msg1, String msg2){
		boolean hecho;
		String cad;
		int num=-1;
		do{
			System.out.println(msg1);
			cad = in.nextLine();
			try{
				num=Integer.parseInt(cad);
				hecho = true;
			}
			catch(NumberFormatException e){ //Entero no introducido
				System.out.println(msg2);
				hecho = false;
			}
			if(hecho && (num<0 || num>=max)){
				System.out.println(msg2);
				hecho = false;
			}			
		}while(!hecho);
		
		return num;	
	}
	
	
	int menu(String titulo, ArrayList<String> lista){
		String tab = "	";
		int opcion;
		System.out.println(titulo);
		for(int i=0; i<lista.size(); i++){
			System.out.println(tab + i + "-" + lista.get(i));
		}
		
		opcion = leeEntero(lista.size(), "\n" + tab + "Elige una opcion: ",
					tab + "Valor erroneo");
		return opcion;
	}
	
	
	SalidasCarcel salirCarcel(){
		int opcion = menu("Elige la forma para intentar salir de la carcel", 
				new ArrayList<> (Arrays.asList("Pagando", "Tirando el dado")));
		
		return (SalidasCarcel.values()[opcion]);
	}
	
	
	Respuestas comprar(){
		int opcion = menu ("¿Quiere comprar la casilla en la que ha caido?",
				new ArrayList<>(Arrays.asList("SI", "NO")));
		
		return(Respuestas.values()[opcion]);
	}
	
	
	void gestionar(){
		int opcion =  menu("¿Que numero de gestion inmobiliaria quiere realizar?",
                        new ArrayList<>(Arrays.asList("Vender", "Hipotecar", 
                                "Cancelar hipoteca", "Construir casa",
                                "Construir hotel", "Terminar")));
		iGestion = opcion;
		
		ArrayList<String> calles = new ArrayList<String>();
		
		for(int i=0; i<juegoModel.getJugadorActual().getPropiedades().size(); i++){
			calles.add(juegoModel.getJugadorActual().
                                getPropiedades().get(i).getNombre());
		}
		
		iPropiedad = menu("¿Sobre que casilla quiere realizar la"
                        + " operacion?", calles);
	}
	
	public int getGestion(){
		return iGestion;
	}
	
	public int getPropiedad(){
		return iPropiedad;
	}
	
	
	void mostrarSiguienteOperacion(OperacionesJuego operacion){
		if(operacion == OperacionesJuego.AVANZAR){
			System.out.println("La siguiente operacion es avanzar");
		}
		else if(operacion == OperacionesJuego.COMPRAR){
			System.out.println("La siguiente operacion es comprar");
		}
		else if(operacion == OperacionesJuego.GESTIONAR){
			System.out.println("La siguiente operacion es gestionar");
		}
		else if(operacion == OperacionesJuego.PASAR_TURNO){
			System.out.println("La siguiente operacion es pasar turno");
		}
		else{
			System.out.println("La siguiente operacion es salir de la carcel");
		}
	}
	
	
	
	void mostrarEventos(){
		while(Diario.getInstance().eventosPendientes()){
			System.out.println(Diario.getInstance().leerEvento());
		}	
	}
	
	public void setCivitasJuego(CivitasJuego civitas){
		juegoModel = civitas;
	}
	
	void actualizarVista(){
            System.out.println(juegoModel.infoJugadorTexto());
        }
	
}