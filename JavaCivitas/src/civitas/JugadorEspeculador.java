package civitas;

public class JugadorEspeculador extends Jugador{

    private static int FactorEspeculador = 2;
    private int fianza;
    protected int CasasMax = 8;
    protected int CasasPorHotel = 4;
    protected int HotelesMax = 8;

    private void actualizaPropietarioPorConversion(Jugador jugador){

        for (TituloPropiedad cada : jugador.getPropiedades()){

            cada.actualiarPropietarioPorConversion(this);

        }

    }

    JugadorEspeculador(Jugador real_jugador, int fianza_a){

        super(real_jugador);
        fianza = fianza_a;
        actualizaPropietarioPorConversion(real_jugador);

    }

    @Override
    public String toString(){

        return super.toString() + "[JUGADOR ESPECULADOR]";

    }

    @Override
    boolean pagaImpuesto(float cantidad){

        if(encarcelado){

            return false;

        }else{

            return paga(cantidad/2);

        }

    }

    @Override
    protected boolean debeSerEncarcelado(){

        if (encarcelado){

            return false;

        }else{

            if(tieneSalvoconducto()){

                perderSalvoConducto();
                Diario.getInstance().ocurreEvento("El jugador "+ getNombre() +" ha usado SALVOCONDUCTO");
                return false;
            }else{

                if(saldo > fianza){

                    paga(fianza);
                    return false;

                }

            }

        }

        return true;

    }

    protected boolean puedoEdificarCasa(TituloPropiedad prop){

        return prop.getPropietario() == this && getSaldo() >= prop.getPrecioEdificar()
                && prop.getNumCasas() > CasasPorHotel && prop.getNumHoteles() < HotelesMax;

    }

}
