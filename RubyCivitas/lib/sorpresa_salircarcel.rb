module Civitas
  class Sorpresa_salircarcel < Sorpresa
    
    def initialize(mazo)
      
      @mazo = mazo
      
    end
    
    def salir_del_mazo

      @mazo.inhabilitar_carta_especial(self)
      
    end
    
    def usada
      
      @mazo.habilitar_carta_especial(self)
      
    end
    
    def aplicar_a_jugador(actual, todos)

      if jugador_correcto(actual, todos)

        informe(actual, todos)

        salvoconducto = false

        todos.each { |items|

          if items.tiene_salvoconducto

            salvoconducto = true

          end
        }

        unless salvoconducto

          todos[actual].obtener_salvoconducto(self)
          salir_del_mazo

        end
      end
    end
  
  end  
end
