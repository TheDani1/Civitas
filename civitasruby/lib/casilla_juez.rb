# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
class CasillaJuez < Casilla
  def initialize(nombre,carcel)
    super(nombre)
    @carcel = carcel
  end
  
  def recibe_jugador(actual,todos)
    if(jugador_correcto(actual,todos))
      super(actual,todos)
      todos[actual].encarcelar(@carcel)
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
