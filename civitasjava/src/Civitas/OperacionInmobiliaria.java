package civitas;

public class OperacionInmobiliaria {
	private int numPropiedad;
	private GestionesInmobiliarias gestion;
	
        /**
         * Consultor GestionesInmoviliarias
         * @return gestion
         */
	public GestionesInmobiliarias getGestion(){
		return gestion;
	}
	
        /**
         * Consultor numPropiedad
         * @return numPropiedad
         */
	public int getNumPropiedad(){
		return numPropiedad;
	}
	
        /**
         * Constructor por parametros
         * @param gest : gestion
         * @param ip : indice de la propiedad
         */
	public OperacionInmobiliaria(GestionesInmobiliarias gest, int ip){
		gestion = gest;
		numPropiedad = ip;
	}
}