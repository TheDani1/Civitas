# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
class SorpresaPorJugador < Sorpresa
  def initialize(texto,valor)
    super(texto)
    @valor = valor
  end
  
  def aplicar_a_jugador(actual,todos)
     if(jugador_correcto(actual,todos))
        super(actual,todos)
        a=SorpresaPagarCobrar.new("Pagan todos los jugadores menos el no afectado ",@valor * -1)
        for i in 0...todos.size
          if(i!=actual)
            a.aplicar_a_jugador(i,todos)
          end
        end
        b=SorpresaPagarCobrar.new("Recibe dinero de todos los jugadores",@valor*(todos.size-1))
        b.aplicar_a_jugador(actual,todos)
    end
  end
  
  def to_string
    a = "Por Jugador"
    return a
  end
  public_class_method :new
end
end