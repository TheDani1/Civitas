require_relative "diario"
require_relative "jugador"

module Civitas
  class Casilla

    def initialize(nombre)
      
      @nombre = nombre
      
    end
    
    def informe(actual, todos)

      Diario.instance.ocurre_evento("[DIARIO] El jugador #{todos[actual].nombre} ha caido en la casilla #{self.nombre.to_s}")

    end
    
    def jugador_correcto(actual, todos)

      correcto = false
      if actual < todos.size && actual >= 0
        correcto = true
      end

      return correcto
    end
    
    def recibe_jugador(actual, todos)
      informe(actual, todos)
    end
    
    #Override
    def to_string
      "[Casilla] -> #{@nombre}\n[Tipo] -> Descanso\n"
    end

    attr_reader :nombre
  end
end
