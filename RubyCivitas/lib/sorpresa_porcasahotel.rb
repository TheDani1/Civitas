module Civitas
  class Sorpresa_porcasahotel < Sorpresa
    
    def initialize(valor, texto)
      
      super(texto)
      @valor = valor
      
    end
    
    def aplicar_a_jugador(actual, todos)

      if jugador_correcto(actual, todos)

        informe(actual, todos)
        todos[actual].modificar_saldo(@valor*(todos[actual].cantidad_casas_hoteles))

      end
    end
  
  end
end
