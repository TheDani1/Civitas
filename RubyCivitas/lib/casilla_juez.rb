require_relative 'casilla'
require_relative 'jugador'

module Civitas
  class Casilla_juez < Casilla
    
    def initialize(nombre, num_casilla_carcel)
      
      super(nombre)
      @carcel = num_casilla_carcel
      
    end
    
    def recibe_jugador_juez(actual, todos)

      if jugador_correcto(actual, todos)
        informe(actual, todos)
        todos[actual].encarcelar(@carcel)
      end

    end
    
    def recibe_jugador(actual, todos)

      if jugador_correcto(actual, todos)
        informe(actual, todos)
        puts "El jugador se va a la carcel"
        todos[actual].encarcelar(@carcel)
      end
    end
    
    #Override
    def to_string
      
      "[Casilla] -> #{@nombre}\n[Tipo] -> Juez\n[Carcel] -> #{@carcel}\n"
      
    end
    
  
  end
end
