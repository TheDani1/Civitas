# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'civitas_juego'
require_relative 'operacion_inmobiliaria'
require_relative 'operaciones_juego'
require_relative 'respuestas'
require_relative 'gestiones_inmobiliarias'
require_relative 'salidas_carcel'
module Civitas

class Controlador
  def initialize(juego,vista)
    @juego=juego
    @vista=vista
  end
  
  def juega
    @vista.set_civitas_juego(@juego)
    while(!@juego.final_del_juego)
      @vista.actualizar_vista
      @vista.pausa
      op=@juego.siguiente_paso
      @vista.mostrar_siguiente_operacion(op)
      if(op!=Civitas::Operaciones_juego::PASAR_TURNO)
        @vista.mostrarEventos
      end
      
      if(!@juego.final_del_juego)
        case op
        when Operaciones_juego::COMPRAR
          res=@vista.comprar
          
          if(res==Civitas::Respuestas::SI)
            @juego.comprar
          end
           @juego.siguiente_paso_completado(op)
           
          
          
        when Operaciones_juego::GESTIONAR
          @vista.gestionar
          @vista.iGestion
          @vista.iPropiedad
          lista=[Civitas::GestionesInmobiliarias::VENDER,Civitas::GestionesInmobiliarias::HIPOTECAR,Civitas::GestionesInmobiliarias::CANCELAR_HIPOTECA,Civitas::GestionesInmobiliarias::CONSTRUIR_CASA,Civitas::GestionesInmobiliarias::CONSTRUIR_HOTEL,Civitas::GestionesInmobiliarias::TERMINAR]
          oi=OperacionInmobiliaria.new(lista[@vista.iGestion], @vista.iPropiedad)
          
          case lista[@vista.iGestion]
          when GestionesInmobiliarias::VENDER
            if(!@juego.get_jugador_actual.get_nombre_propiedades!=nil)
              @juego.vender(oi.numPropiedad)
            end
            
          when GestionesInmobiliarias::HIPOTECAR
            if(!@juego.get_jugador_actual.get_nombre_propiedades!=nil)
              @juego.hipotecar(oi.numPropiedad)
            end
            
          when GestionesInmobiliarias::CANCELAR_HIPOTECA
            if(!@juego.get_jugador_actual.get_nombre_propiedades!=nil)
              @juego.cancelar_hipoteca(oi.numPropiedad)
            end
            
          when GestionesInmobiliarias::CONSTRUIR_CASA
            @juego.construir_casa(oi.numPropiedad)
            
          when GestionesInmobiliarias::CONSTRUIR_HOTEL
            @juego.construir_hotel(oi.numPropiedad)
          else
            @juego.siguiente_paso_completado(op)
          end
            
          
        when   Operaciones_juego::SALIR_CARCEL
          salida=@vista.salir_carcel
          case salida
          when SalidasCarcel::PAGANDO
            @juego.salir_carcel_pagando
          when SalidasCarcel::TIRANDO
            @juego.salir_carcel_tirando
          end
          @juego.siguiente_paso_completado(op)
        end
        
        
      else 
        lista=Array.new
        lista=@juego.ranking
        lista.reverse
        for i in 0...lista.size
          puts lista[i].nombre+"\n"
        end
      end
         
        
         
      
    end
    
    
  end

  
  
  
  end
end