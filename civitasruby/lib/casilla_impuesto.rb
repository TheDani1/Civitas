# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
class CasillaImpuesto < Casilla
  def initialize(nombre,valor)
    super(nombre)
    @importe = valor
  end
  
  def recibe_jugador(actual,todos)
    if(jugador_correcto(actual,todos))
      super(actual,todos)
      todos[actual].paga_impuesto(@importe)
    end
  end
  
  def to_string
    cadena="Casilla: #{@nombre}  Importe: #{@importe} \n"
    cadena+="\n"
    return cadena
  end
  public :to_string
end
end
