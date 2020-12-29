# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
class SorpresaPorCasaHotel < Sorpresa
  def initialize(texto,valor)
    super(texto)
    @valor = valor
  end
  
  def aplicar_a_jugador(actual,todos)
    if(jugador_correcto(actual,todos))
        super(actual,todos)
        todos[actual].modificar_saldo(@valor*todos[actual].cantidad_casas_hoteles)
    end
  end
  
  def to_string
    a = "Por Casa-Hotel"
    return a
  end
  public_class_method :new
end
end