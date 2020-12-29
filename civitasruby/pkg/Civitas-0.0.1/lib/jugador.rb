# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
class Jugador
  include Comparable
  
  attr_reader :nombre,:numCasillaActual,:CasasMax,:CasasPorHotel,:propiedades,:puedeComprar,:saldo,:encarcelado,:valor,:salvoconducto,:especulador
  
  
  
  def initialize(n)
    @nombre=n
    @encarcelado=false
    @numCasillaActual=0
    @puedeComprar=true
    @saldo=7500
    @propiedades=Array.new
    @salvoconducto=nil
    @@CasasMax=4
    @@CasasPorHotel=4
    @@HotelesMax=4
    @@PasoPorSalida=1000
    @@PrecioLibertad=200
    @@SaldoInicial=7500
    @especulador = false
  end
  
  def jugador_copia(otro)
      @nombre = otro.nombre
      @encarcelado=otro.encarcelado
      @numCasillaActual=otro.numCasillaActual
      @puedeComprar=otro.puedeComprar
      @saldo=otro.saldo
      @salvoconducto= otro.salvoconducto
      @propiedades=otro.propiedades
      for i in @propiedades
        i.actualiza_propietario_por_conversion(self)  
      end
      @especulador = otro.especulador
  end
  
  def self.copia(otro)
      @nombre = otro.nombre
      @encarcelado=otro.encarcelado
      @numCasillaActual=otro.numCasillaActual
      @puedeComprar=otro.puedeComprar
      @saldo=otro.saldo
      @salvoconducto= otro.salvoconducto
      @propiedades=otro.propiedades    
  end
  
  def to_string
    mensaje1="Nombre= "+ @nombre + " saldo inicial= "+@@SaldoInicial.to_s+"  saldo actual= "+@saldo.to_s+"  encarcelado= "+@encarcelado.to_s+
      "  numero de la casilla actual= "+@numCasillaActual.to_s+"  puede comprar= "+@puedeComprar.to_s+" salvoconducto= "+tiene_salvoconducto.to_s+" "
    
    mensaje2="Propiedades= "
    for i in 0...@propiedades.size
      mensaje2=mensaje2+@propiedades[i].nombre
    end
    
    return mensaje1+mensaje2
  end
  
  
  
  def is_encarcelado
    return @encarleado
  end
  
  def modificar_saldo(cantidad)
    @saldo=@saldo+cantidad
    Diario.instance.ocurre_evento('Se ha modificado el saldo del jugador')
    return true
  end
  
  def paga(cantidad)
    ok=modificar_saldo(cantidad * -1)
    return ok
  end
  
  def recibe(cantidad)
    if(@encarcelado)
      return false
    else
      ok=modificar_saldo(cantidad)
      return ok
    end
  end
  
  def paga_impuesto(cantidad)
    if(@encarcelado)
      return false
    else
      ok=paga(cantidad)
      return ok
    end
  end
  
  
  def paga_alquiler(cantidad)
    if(@encarcelado)
      return false
    else
      ok=paga(cantidad)
      return ok
    end
  end
  
  
  
  def obtener_salvoconducto(s)
    if(@encarcelado)
      return false
    else
      @salvoconducto=s
      return true
    end
  end
  
  
  
  
  def tiene_salvoconducto
    return @salvoconducto!=nil
  end
  
 
  
  
  def perder_salvoconducto
    @salvoconducto.usada
    @salvoconducto=nil
  end
  
  def debe_ser_encarcelado
    if(@encarcelado)
      return false
    else
      if (!tiene_salvoconducto)
      return true;
      else
        perder_salvoconducto
        Diario.instance.ocurre_evento("El jugador "+@nombre+" se libra de la carcel")
        return false
      end
   end
  end
  
  
  def mover_a_casilla(numCasilla)
    if(@encarcelado)
      return false
    else
      @numCasillaActual=numCasilla
      @puedeComprar=false
      Diario.instance.ocurre_evento("EL jugador "+@nombre+" se ha movido  a la casilla "+ @numCasillaActual.to_s)
      return true
    end
  end
  
  
  
  def encarcelar(numCasillaCarcel)
    if(debe_ser_encarcelado)
        mover_a_casilla(numCasillaCarcel)
        @encarcelado=true
        Diario.instance.ocurre_evento("Se ha encarcelado al jugador "+@nombre)
        return @encarcelado
    end
  end

  def cantidad_casas_hoteles()
    cont=0
    for i in 0...@propiedades.size
      cont=cont+propiedades[i].cantidad_casas_hoteles
    end
    return cont
  end
  
  
  def puede_comprar_casilla
    @puedeComprar= !@encarcelado
    
    return @puedeComprar
  end
  
  def puedo_gastar(precio)
    if(@encarcelado)
      return false
    else
      return @saldo>=precio
    end
  end
  
  def existe_la_propiedad(ip)
    return ip<=@propiedades.size
  end
  
  
  def vender(ip)
    if(@encarcelado)
      return false
    else
      if(existe_la_propiedad(ip))
        ok=@propiedades[ip].vender(self)
      
      if(ok)
        Diario.instance.ocurre_evento("La propiedad "+@propiedades[ip].nombre+" ha sido vendida por el jugador #{@nombre}")
        @propiedades.delete_at(ip)
        return true
       else
      return false
      end
      else
        return false
    end
    end 
  end 
  
  
  def tiene_algo_que_gestionar
    return @propiedades!=nil
  end
  
  def puede_salir_carcel_pagando
    return @saldo>=@@PrecioLibertad
  end
  
  def salir_carcel_pagando
      if(@encarcelado && puede_salir_carcel_pagando)
        paga(@@PrecioLibertad)
        @encarcelado=false
        a=@@PrecioLibertad.to_s
        Diario.instance.ocurre_evento("El jugador "+@nombre+" ha salido de la carcel pagando "+a+ " euros")
        return true
      else
        return false
      end
  end
  
  def en_bancarrota
    return @saldo<=0
  end
  
  def salir_carcel_tirando
    if(Dado.instance.salgo_de_la_carcel)
      @encarcelado=false
      Diario.instance.ocurre_evento("El jugador "+@nombre+" ha salido de la carcel mediante tirada")
      return true
    else
      return false
    end
    
  end
  
  def pasa_por_salida
    modificar_saldo(@@PasoPorSalida)
    a=@@PasoPorSalida.to_s
    Diario.instance.ocurre_evento("El jugador "+@nombre+" ha recibido "+a+" por pasar por la casilla Salida")
    return true
  end
  
  def <=>(otro)
    @valor<=>otro.valor
  end
  
  
  
  def puedo_edificar_casa(propiedad)
    if(@encarcelado)
      return false
    else
      if(propiedad.numCasas<=@@CasasMax)
        return @saldo>=propiedad.precioEdificar
      else
        return false
      end
      
    end
    
  end
  
  
  def puedo_edificar_hotel(propiedad)
    if(@encarcelado)
      return false
    else
      if(propiedad.numHoteles<=@@HotelesMax && propiedad.numCasas==4)
        return @saldo>=propiedad.precioEdificar
      else
        return false
      end
    end
  end
  
  
  def cancelar_hipoteca(ip)
    result=false
    if(@encarcelado)
      return result
    end
    
    if(existe_la_propiedad(ip))
      propiedad=@propiedades[ip]
      cantidad=propiedad.get_importe_cancelar_hipoteca
      puedo_gastar=puedo_gastar(cantidad)
      
      if(puedo_gastar)
        result=propiedad.cancelar_hipoteca(self)
        if(result)
          Diario.instance.ocurre_evento("El jugador "+@nombre+" cancela la hipoteca de la propiedad "+ip.to_s)
        end
      end
    end
    
    return result
    
  end
  
  
  def comprar(titulo)
    result=false
    if(@encarcelado)
      return result
    end
    
    if(@puedeComprar)
      precio=titulo.precioCompra
      if(puedo_gastar(precio))
        result=titulo.comprar(self)
        if(result)
          @propiedades<<titulo
          Diario.instance.ocurre_evento("El jugador "+@nombre+" compra la propiedad "+titulo.to_string+"\n")
        end
        @puedeComprar=false
      end
    end
    return result
  end
  
  
  
  
  def construir_hotel(ip)
    result=false
    if(@encarcelado)
      return result
    end
    
    if(existe_la_propiedad(ip))
      propiedad=@propiedades[ip]
      puedo_edificar_hotel=puedo_edificar_hotel(propiedad)
      precio=propiedad.precioEdificar
      
      if(puedo_gastar(precio) && propiedad.numHoteles<@@HotelesMax && propiedad.numCasas>=@@CasasPorHotel )
        puedo_edificar_hotel=true
      end
      
      if(puedo_edificar_hotel)
        result=propiedad.construir_hotel(self)
        propiedad.derruir_casas( @@CasasPorHotel, self)
        Diario.instance.ocurre_evento("El jugador "+@nombre+" construye hotel en la propiedad "+ip.to_s)
      end
      
    end
    
    return result
    
  end
  
  def hipotecar(ip)
    result=false
    if(@encarcelado)
      return result
    end
    
    if(existe_la_propiedad(ip))
      propiedad=@propiedades[ip]
      result=propiedad.hipotecar(self)
    end
    
    if(result)
      Diario.instance.ocurre_evento("El jugador "+@nombre+" hipoteca la propiedad "+ip.to_s)
    end
    
    return result
  end
  
  
  def construir_casa(ip)
    result=false
    puedo_edificar_casa=false
    if(@encarcelado)
      return result
    else
      existe=existe_la_propiedad(ip)
      if(existe)
        propiedad=@propiedades[ip]
        puedo_edificar_casa=puedo_edificar_casa(propiedad)
        if(puedo_edificar_casa)
          result=propiedad.construir_casa(self)
          if(result)
            Diario.instance.ocurre_evento("El jugador "+@nombre+" construye casa en la propiedad"+ip.to_s)
          end
        end
        
      end
    end
    return result
  end
  
  def get_nombre_propiedades
    nombres=Array.new
    for i in 0...@propiedades.size
      nombres<<@propiedades[i].nombre
    end
    return nombres
  end
  
  def get_casas_max 
    return @@CasasMax
  end
  
  def get_hoteles_max
    return @@HotelesMax
  end
  
  
  
 
  private :perder_salvoconducto,:puedo_gastar,:existe_la_propiedad,:puede_salir_carcel_pagando,:puedo_edificar_casa,:puedo_edificar_hotel,
          :CasasPorHotel
end

end