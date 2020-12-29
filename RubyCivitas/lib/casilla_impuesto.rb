require_relative 'casilla'
require_relative 'jugador'

module Civitas
  class Casilla_impuesto < Casilla
    
    def initialize(nombre,importe)
      
      super(nombre)
      @importe = importe
      
    end
    
    def recibe_jugador_impuesto(actual, todos)

      if jugador_correcto(actual,todos)
        informe(actual, todos)
        todos[actual].paga_impuesto(@importe)
      end
    end
    
    def to_string
      
      "[Casilla] -> #{@nombre}\n[Tipo] -> Impuesto\n[Importe] -> #{@importe}\n"
      
    end
    
    attr_reader :importe
  end
end

