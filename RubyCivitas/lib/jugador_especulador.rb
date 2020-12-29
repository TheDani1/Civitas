require_relative 'jugador'

module Civitas
  class Jugador_especulador < Jugador
    
    @@casas_max = 8
    @@casas_por_hotel = 4
    @@hoteles_max = 8
    
    def initialize(fianza)
      
      @fianza = fianza
      
    end
    
    def self.copia(otro, fianza)
      
      yo=new(fianza)
      yo.copia(otro)
      
      for i in otro.propiedades
        i.actualiza_propietario_por_conversion(yo)
      end
      
      yo
      
    end
    
    def to_string
      
      return super + "[JUGADOR ESPECULADOR]"
      
    end
    
    def paga_impuesto(cantidad)
      
      if @encarcelado
        
        return false
      else
        
        paga(cantidad/2)
        
      end
    end
    
    def construir_casa(ip)
      
      resultado = false
      
      if @encarcelado
        
        return resultado
        
      end
      
      if !@encarcelado
        
        
        existe = existe_la_propiedad(ip)
        
        
        if existe
          
          propiedad = @propiedades[ip]
          puedo_edificar = puedo_edificar_casa(propiedad)
          
          if puedo_edificar
            
            resultado = propiedad.construir_casa(self)
            
          end
          
        end
        
      end
      
      return resultado
    end
    
    def construir_hotel(ip)
      
      if !@encarcelado && existe_la_propiedad(ip)

        propiedad = @propiedades[ip]
        puedo_edificar_hotel = puedo_edificar_hotel(propiedad)

        if puedo_edificar_hotel

          result = propiedad.construir_hotel(self)

          casas_por_hotel = @@casas_por_hotel
          propiedad.derruir_casas(casas_por_hotel, self)

          Diario.instance.ocurre_evento("[DIARIO] El jugador " + @nombre + " construye un hotel " + "en " +
                                     ip + ", " + propiedad.nombre)
        end
      end

      return result
      
    end
    
    def encarcelar(num_casilla_carcel)
      
      if debe_ser_encarcelado

        mover_a_casilla(num_casilla_carcel)
        @encarcelado = true
        Diario.instance.ocurre_evento("[DIARIO] El jugador #{@nombre} ha sido encarcelado")
      end

      return @encarcelado
      
    end
    
    def debe_ser_encarcelado
      
      if @encarcelado
        return false
      else
        
        if tiene_salvoconducto
          
          perder_salvoconducto
          Diario.instance.ocurre_evento("[DIARIO] El jugador #{@nombre} ha usado SALVOCONDUCTO")
          return false
        else
          
          if @saldo > @fianza
            
            self.paga(@fianza)
            return false
            
          end
          
        end
        
      end
      
      return true
    end
    
    def salir_carcel_pagando
      
      resultado = false
      
      if puede_salir_carcel_pagando && @encarcelado
        
        paga(@fianza)
        @encarcelado = false
        Diario.instance.ocurre_evento("[DIARIO] El jugador #{@nombre} ha pagado, sale de la carcel")
        resultado = true
        
      end
      
      return resultado
      
    end
    
    attr_accessor :fianza
    private_class_method :new
        
  end
end
