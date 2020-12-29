# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
class SorpresaIrCasilla < Sorpresa
  def initialize(texto,tablero,valor)
    super(texto)
    @tablero = tablero
    @valor = valor
  end
  
  def aplicar_a_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        super(actual,todos)
        casilla_actual=todos[actual].numCasillaActual
        tirada=@tablero.calcular_tirada(casilla_actual,@valor)
        nueva_pos=@tablero.nueva_posicion(casilla_actual,tirada)
        todos[actual].mover_a_casilla(nueva_pos)
        @tablero.get_casilla(nueva_pos).recibe_jugador(actual, todos)
      end
  end
  
  def to_string
    a = "Ir Casilla"
    return a
  end
  public_class_method :new
end
end