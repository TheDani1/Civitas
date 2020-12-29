module Civitas
  class Sorpresa_jugadoriracasilla < Sorpresa
    
    def initialize(tablero_s, valor, texto)
      
      @tablero = tablero_s
      @valor = valor
      super(texto)
      
    end
    
    def aplicar_a_jugador(actual, todos)
      
      puts todos[actual]

      if jugador_correcto(actual, todos)

        informe(actual, todos)
        casilla = todos[actual].num_casilla_actual
        tirada = @tablero.calcular_tirada(casilla, @valor)
        posicion = @tablero.nueva_posicion(casilla, tirada)

        todos[actual].mover_a_casilla(posicion)
        @tablero.casillas[posicion].recibe_jugador(todos[actual],todos)

      end

    end
 
  end
end
