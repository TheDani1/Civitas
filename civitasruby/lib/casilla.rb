# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.



module Civitas
class Casilla
  attr_reader :nombre,:tituloPropiedad
  
  
  
  def initialize(nombre)
      @nombre= nombre
  end
  
  def recibe_jugador(actual,todos)
      informe(actual,todos)
  end
  
  def informe(actual,todos)
    Diario.instance.ocurre_evento("El jugador "+todos[actual].nombre+" ha caido en la casilla "+to_string)
  end
  
  def jugador_correcto(actual,todos)
    return actual<=todos.size
  end
  

  
  def to_string
    cadena="Casilla: #{@nombre} \n"
    cadena+="\n"
    return cadena
  end
  
  
  
  
  
  
  private :informe
  end 
end