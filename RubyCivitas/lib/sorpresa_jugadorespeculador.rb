require_relative 'jugador_especulador'
require_relative 'jugador'

module Civitas
  class Sorpresa_jugadorespeculador < Sorpresa
    
    def initialize(cantidad, texto)
      
      super(texto)
      @fianza = cantidad
      
    end
    
    def aplicar_jugador(actual, todos)
      
      if jugador_correcto(actual, todos)
        
        informe(actual, todos)
        
        jugadorespeculador = Jugador_especulador.copia(todos[actual], @fianza)
        
        todos[actual] = jugadorespeculador
        
      end
      
    end
  
  end 
end

