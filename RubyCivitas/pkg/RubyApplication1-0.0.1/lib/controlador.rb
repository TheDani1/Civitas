require_relative "gestor_estados"
require_relative "operaciones_juego"
require_relative "tipos"
require_relative "operacion_inmobiliaria"
require_relative "vista_textual"
require_relative "civitas_juego"
require_relative "diario"

module Civitas
  class Controlador

    def initialize(juego, vista)
      @juego = juego
      @vista = vista
    end

    def juega
      
      @vista.set_civitas_juego(@juego)

      until @juego.final_del_juego
        
        @vista.actualizar_vista
        @vista.pausa
        operacion = @juego.siguiente_paso
        
        @vista.mostrar_siguiente_operacion(operacion)
        if operacion != Operaciones_juego::PASAR_TURNO

          @vista.mostrar_eventos

        end

        if !@juego.final_del_juego && operacion != nil

          case operacion

          when Operaciones_juego::COMPRAR
            if @vista.comprar == TipoRespuestas::SI

              @juego.comprar

            end
            @juego.siguiente_paso_completo(Operaciones_juego::COMPRAR)

          when Operaciones_juego::GESTIONAR
            @vista.gestionar
            lista=[TiposGestionesInmobiliarias::VENDER,TiposGestionesInmobiliarias::HIPOTECAR,TiposGestionesInmobiliarias::CANCELAR_HIPOTECA,TiposGestionesInmobiliarias::CONSTRUIR_CASA,TiposGestionesInmobiliarias::CONSTRUIR_HOTEL,TiposGestionesInmobiliarias::TERMINAR]
            nuevo=OperacionInmobiliaria.new(lista[@vista.get_gestion],@vista.get_propiedad)
            
            case nuevo.gestion

            when TiposGestionesInmobiliarias::VENDER
              puts "------"+@vista.get_propiedad.to_s+"------"
              @juego.vender(@vista.i_propiedad)
            when TiposGestionesInmobiliarias::HIPOTECAR
              @juego.hipotecar(@vista.i_propiedad)
            when TiposGestionesInmobiliarias::CANCELAR_HIPOTECA
              @juego.cancelar_hipoteca(@vista.i_propiedad)
            when TiposGestionesInmobiliarias::CONSTRUIR_CASA
              @juego.construir_casa(@vista.i_propiedad)
            when TiposGestionesInmobiliarias::CONSTRUIR_HOTEL
              @juego.construir_hotel(@vista.i_propiedad)
            when TiposGestionesInmobiliarias::TERMINAR
              puts "---------TERMINAMOS---------"
              @juego.siguiente_paso_completo(operacion)

            end

          when Operaciones_juego::SALIR_CARCEL
            tipo_salida = @vista.salir_carcel

            if tipo_salida == TipoSalidasCarcel::PAGANDO
              @juego.salir_carcel_pagando
            else
              @juego.salir_carcel_tirando
            end

            @juego.siguiente_paso_completo(Operaciones_juego::SALIR_CARCEL)

          end

        end

      end

      @juego.ranking
    end
  end
end
