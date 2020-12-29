# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.




require_relative ('sorpresa')
require_relative 'jugador'



module Civitas
class Sorpresa
=begin
  def init
    @valor=-1
    @mazo=nil
    @tablero=nil
  end
=end
  
  def initialize(texto)
    #@valor=valor
    #@mazo=mazo
    #@tablero=tablero
    @texto=texto
    #@tipo=tipo
  end
  
  def jugador_correcto(actual,todos)
    return actual<=todos.size
  end

  def aplicar_a_jugador(actual,todos)
    informe(actual,todos)
  end
  
  def informe(actual,todos)
    mensaje="Se esta aplicando una sorpresa  al jugador " + todos[actual].nombre+"\n"
    puts mensaje
  end
  
  
      
  private_class_method :new
  private :informe
  
  
end
end
