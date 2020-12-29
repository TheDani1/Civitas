module Civitas
  class Sorpresa_porjugador < Sorpresa
    
    def initialize(valor, texto)
      
      @valor = valor
      super(texto)
      
    end
    
    def aplicar_a_jugador(actual, todos)

      if jugador_correcto(actual, todos)
        informe(actual, todos)
        sorpresa_c = Sorpresa_pagarcobrar.new(@valor*(-1),"Decrementa saldo")
        sorpresa_p = Sorpresa_pagarcobrar.new(@valor*todos.size,"Incrementa saldo")

        for i in 0...todos.size
          if actual!=i
            sorpresa_c.aplicar_a_jugador(i,todos)
          else
            sorpresa_p.aplicar_a_jugador(i,todos)
          end
        end
        
      end
    end
    
  end
end
