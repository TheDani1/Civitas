require_relative 'casilla'
require_relative 'sorpresa'

module Civitas
  class Casilla_sorpresa < Casilla
    
    def initialize(mazo, nombre)
      
      super(nombre)
      @mazo = mazo
      
    end
    
    #Override
    def recibe_jugador(actual, todos)

      if jugador_correcto(actual, todos)

        @sorpresa = @mazo.siguiente
        
        informe(actual, todos)
        
        #puts @sorpresa
        @sorpresa.aplicar_al_jugador(actual, todos)

      end
    end
    
    #Override
    def to_string
      
      "[Casilla] -> #{@nombre}\n[Tipo] -> Sorpresa\n[Sorpresa] -> #{@sorpresa.to_string}"
      
    end
   
  end
end
