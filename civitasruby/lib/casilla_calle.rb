# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
class CasillaCalle < Casilla
  attr_reader :tituloPropiedad
  
  def initialize(nombre,titulo)
    super(nombre)
    @tituloPropiedad=titulo
  end

  def recibe_jugador(actual,todos)
    if(jugador_correcto(actual, todos))
      super(actual,todos)
      jugador=todos[actual]
      
      if(!@tituloPropiedad.tiene_propietario)
        jugador.puede_comprar_casilla
      else
        @tituloPropiedad.tramitar_alquiler(jugador)
      end
    end
  end
  
  def to_string
    cadena="Casilla: #{@nombre}"  + @tituloPropiedad.to_string +  "\n"
    cadena+="\n"
    return cadena
  end
  public :to_string
end
end
