module Civitas
  class Sorpresa_ircarcel < Sorpresa
    
    def initialize(tablero)
      
      @texto = "Sorpresa para ir a la carcel"
      @tablero = tablero
      
    end
    
    def aplicar_a_jugador(actual, todos)

      if jugador_correcto(actual, todos)

        informe(actual, todos)
        todos[actual].encarcelar(@tablero.num_casilla_carcel)

      end

    end
    
    
  end
end