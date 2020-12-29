# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
class CasillaSorpresa < Casilla
  def initialize(nombre,mazo)
    super(nombre)
    @mazo = mazo
  end
  
  def recibe_jugador(actual,todos)
    if(jugador_correcto(actual, todos))
      super(actual,todos)
      sorpresa1=@mazo.siguiente
      sorpresa1.aplicar_a_jugador(actual, todos)
    end
  end
   
  def to_string
    cadena="Casilla: #{@nombre} \n"
    cadena+="\n"
    return cadena
  end
   public :to_string
end
end
