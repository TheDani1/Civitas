require_relative "tipo_sopresa"
require_relative "mazo_sorpresas"
require_relative "jugador"
require_relative "tablero"

module Civitas

  class Sorpresa

    def initialize (texto)
      @texto = texto
    end

    def jugador_correcto(actual, todos)
      
      return actual < todos.length && actual >= todos.find_index(todos.first)
      
    end

    def informe (actual, todos)

      Diario.instance.ocurre_evento("[DIARIO] Se esta aplicando una sorpresa a #{todos[actual]}")

    end

    def aplicar_al_jugador (actual, todos)
      
      informe(actual, todos)

    end

    def to_string

      return @texto

    end

  end
end
