# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
class TituloPropiedad
  
  attr_reader :nombre,:numCasas,:numHoteles,:hipotecado,:propietario,:precioCompra,:precioEdificar
  
  def initialize(n,pba,fr,pbh,pc,pe)
    @nombre=n
    @alquilerBase=pba
    @factorRevalorizacion=fr
    @hipotecaBase=pbh
    @precioCompra=pc
    @precioEdificar=pe
    @propietario=nil
    @numCasas=0
    @numHoteles=0
    @hipotecado=false
    @@factorInteresesHipoteca=1.1
  end
  
  
  def to_string
 devolver = "
 Nombre: #{@nombre}
 Alquiler base: #{@alquilerBase}
 Número casas: #{ @numCasas}
 Número hoteles: #{@numHoteles}
 Precio compra: #{@precioCompra}"
 return devolver
 end
  
  def tiene_propietario
    return @propietario != nil
  end
  
  def es_este_el_propietario(jugador)
    return @propietario.equal?(jugador) 
  end
  
  def propietario_encarcelado
    return !(!@propietario.is_encarcelado||!tiene_propietario)
  end
  
  def get_precio_alquiler
    if(@hipotecado||propietario_encarcelado)
      var=0
    else
      var=(@alquilerBase *(1+(@numCasas*0.5)+(@numHoteles*2.5)));
    end
    return var
  end
  
  
  def get_importe_hipoteca
    cantidad_recibida=(@hipotecaBase*(1+(@numCasas*0.5)+(@numHoteles*2.5)))
    return cantidad_recibida
  end
  
  def get_importe_cancelar_hipoteca
    return get_importe_hipoteca*@@factorInteresesHipoteca
  end
  
  def  cancelar_hipoteca(jugador)
    if(@hipotecado && es_este_el_propietario(jugador) )
      @propietario.paga(get_importe_cancelar_hipoteca)
      @hipotecado=false
      return true
    end
      return false
  end
  
  
  def hipotecar(jugador)
    if(!@hipotecado && es_este_el_propietario(jugador))
      @propietario.recibe(get_importe_hipoteca)
      @hipotecado=true
      return true
    else 
      return false
    end
  end
  
  def tramitar_alquiler(jugador)
     if(tiene_propietario && !es_este_el_propietario(jugador) )
       puts "adios"
       jugador.paga_alquiler(get_precio_alquiler)
       @propietario.recibe(get_precio_alquiler)
       
     end
  end
  
  
  def cantidad_casas_hoteles
    return @numCasas+@numHoteles
  end
  
  def derruir_casas(n,jugador)
    if(es_este_el_propietario(jugador)&&@numCasas>=n)
      @numCasas=@numCasas-n
      return true
    else
      return false
    end
    
  end
  
  def get_precio_venta
    return @precioCompra+(@numCasas+5*@numHoteles)*@precioEdificar*@factorRevalorizacion;
  end
  
  def construir_casa(jugador)
    ok=false
    if(es_este_el_propietario(jugador) && @numCasas < jugador.get_casas_max)
      jugador.paga(@precioEdificar)
      @numCasas=@numCasas+1
      ok=true
    end
    return ok
  end
  
  
  def construir_hotel(jugador)
    ok=false
    if(es_este_el_propietario(jugador)&& @numHoteles < jugador.get_hoteles_max)
      jugador.paga(@precioEdificar*5)
      @numHoteles=@numHoteles+1
      ok=true
    end
    return ok
  end
  
  
  def comprar(jugador)
    if(tiene_propietario)
      return false
    else
      @propietario=jugador
      @propietario.paga(@precioCompra)
      return true
    end
  end
  
  def vender(jugador)
      if(es_este_el_propietario(jugador))
        @propietario.recibe(get_precio_venta)
        @propietario=nil
        @numCasas=0
        @numHoteles=0
        return true
      else
        return false
      end
  end
  
  def actualiza_propietario_por_conversion(jugador)
    @propietario = jugador
  end
  
  def set_propietario(jugador)
    @propietario = jugador
  end
  
  private :get_precio_alquiler,:propietario_encarcelado,:es_este_el_propietario,:get_precio_venta
  
  end
end