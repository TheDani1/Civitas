# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
class SorpresaSalirCarcel < Sorpresa
  def initialize(texto,mazo)
    super(texto)
    @mazo = mazo
  end
  
  def aplicar_a_jugador(actual,todos)
     if(jugador_correcto(actual,todos))
        super(actual,todos)
        ok=false
        for i in 0...todos.size
          if(todos[i].tiene_salvoconducto)
            ok=true
          end
        end
        if(!ok)
          todos[actual].obtener_salvoconducto(self)
          salir_del_mazo
        end
     end
  end
  
  def usada 
      @mazo.habilitar_carta_especial(self)
  end
  
  def salir_del_mazo
      @mazo.inhabilitar_carta_especial(self)
  end
  
  def to_string
    a = "Salir Carcel"
    return a
  end
  public_class_method :new
end
end