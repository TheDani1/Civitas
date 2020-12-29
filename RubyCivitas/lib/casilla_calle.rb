require_relative 'casilla'
require_relative 'titulo_propiedad'
require_relative 'jugador'

module Civitas
  class Casilla_calle < Casilla
    
    def initialize(titulo)
      
      super(titulo.nombre)
      @titulo_propiedad = titulo
    end
    
    #Override
    def recibe_jugador(actual, todos)

      if jugador_correcto(actual, todos)

        informe(actual, todos)

        jugador = todos[actual]

        if @titulo_propiedad.tiene_propietario

          @titulo_propiedad.tramitar_alquiler(jugador)

        else

          todos[actual].puede_comprar_casilla

        end
      end
    end
    
    def to_string
      
      "[Casilla] -> #{@titulo_propiedad.nombre}\n[Tipo] -> Calle\n[Titulo] -> #{@titulo_propiedad.to_string}"
      
    end
    
    attr_reader :titulo_propiedad
    
  end
end

