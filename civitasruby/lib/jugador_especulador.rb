# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class JugadorEspeculador < Jugador
    
    @@FactorEspeculador = 2
    
    def initialize(jugador, fianza)
      jugador_copia(jugador)
      @fianza = fianza
    end
    
    def debe_ser_encarcelado
      res = false
      
      if (super)
        if(!puede_pagar_fianza)
          res = true
        end
      end
    return res
    end
    
    def puede_pagar_fianza
      puedepagar = false
      if(@saldo >= @fianza)
        modificar_saldo(-@fianza)
        puedepagar = true
      end
      return puedepagar
    end
    
    def puedo_edificar_casa(propiedad)
      if(@encarcelado)
        return false
      end
      
      if(propiedad.numCasas < (@@CasasMax * @@FactorEspeculador))
        return @saldo >= propiedad.precioEdificar
      else
        return false
      end
    end
    
    def puedo_edificar_hotel
      if(@encarcelado)
        return false
      end
      if(propiedad.numHoteles< (@@HotelesMax * @@FactorEspeculador) && propiedad.numCasas >=4)
        return @saldo >= propiedad.precioEdificar
      else
        return false
      end
    end
    
    def paga_impuesto(cantidad)
      if(@encarcelado)
        return false
      else
        return paga(cantidad/@@FactorEspeculador)
      end
    end
    
    def get_casas_max
      return (@@CasasMax * @@FactorEspeculador)
    end
    
    def get_hoteles_max
      return (@@HotelesMax * @@FactorEspeciulador)
    end
    
    def to_string
      a= "JugadorEspeculador{fianza= #{@fianza} , nombre= #{@nombre}, saldo= #{@saldo},encarcelado = #{@encarcelado}, numCasillaActual=  #{@numCasillaActual}, puedeComprar= #{@puedeComprar}, salvoconducto= #{@salvoconducto}, propiedades= "
      for i in 0...@propiedades.size
        a = a + @propiedades[i].nombre
      end
      a = a + "}\n"
      return a
    end
    
    def construir_casa(ip)
      res = false
      puedoedificarcasa = false
      if(@encarcelado)
        return res
      else
        existe = existe_la_propiedad(ip)
        if(existe)
          propiedad = @propiedades[ip]
          puedoedificarcasa = self.puedo_edificar_casa(propiedad)
          if(puedoedificarcasa)
            res = propiedad.construir_casa(self)
            if(res)
              Diario.instance.ocurre_evento("El jugador #{@nombre} construye una
                 casa en la propiedad "+ @propiedades[ip].nombre)
            end
          end
        end
      end
      return res
    end
    
    def construir_hotel(ip)
      result = false
      if(@encarcelado)
        return result
      end
      existe=existe_la_propiedad(ip);
      if(existe)
        propiedad = @propiedades[ip]
        puedoEdificarHotel = self.puedo_edificar_hotel(propiedad)
        precio = propiedad.precioEdificar
        if(puedo_gastar(precio) && propiedad.numHoteles < @@HotelesMax && propiedad.numCasas >= @@CasasPorHotel)
          puedoEdificarHotel = true
        end
        if(puedoEdificarHotel)
          result = propiedad.construir_hotel(self)
          propiedad.derruir_casas(@@CasasPorHotel,self)
          Diario.instance.ocurre_evento("El jugador #{@nombre} construye hotel en la propiedad "+ @propiedades[ip].nombre)
      end
    end
    
  end
    
    
  end
end
