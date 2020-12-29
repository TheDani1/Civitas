package civitas;

import java.util.ArrayList;

public class CasillaCalle extends Casilla{

    private TituloPropiedad tituloPropiedad;

    CasillaCalle (TituloPropiedad tituloPropiedad_a){

        super(tituloPropiedad_a.getNombre() );
        tituloPropiedad=tituloPropiedad_a;

    }

    @Override
    public String toString(){

        return "\n[Casilla] -> " + getTituloPropiedad().getNombre() + "\n[Tipo] -> Calle\n[Titulo] -> " + getTituloPropiedad().toString();

    }

    @Override
    public void recibeJugador(int iactual, ArrayList<Jugador> todos){

        if(jugadorCorrecto(iactual,todos)){

            super.informe(iactual, todos);
            Jugador nuevo_j = todos.get(iactual);

            if(!tituloPropiedad.tienePropietario()){

                nuevo_j.puedeComprarCasilla();

            }else{

                tituloPropiedad.tramitarAlquiler(nuevo_j);

            }
        }
    }

    public TituloPropiedad getTituloPropiedad() {
        return tituloPropiedad;
    }

}
