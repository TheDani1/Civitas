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

  def informe(actual,todos)
    mensaje="Se esta aplicando una sorpresa  al jugador " + todos[actual].nombre+"\n"
    puts mensaje
  end
  
  def aplicar_a_jugador(actual,todos)
    informe(actual,todos)
  end
  
=begin  
  def self.constructor1(tipo,tablero)
    new(nil,nil,tablero,nil,tipo)
  end

  def self.constructor2(tipo,tablero,valor,texto)
    new(valor,nil,tablero,texto,tipo)
  end
  
  def self.constructor3(tipo,mazo)
    new(nil,mazo,nil,nil,tipo)
  end
  
  def self.constructor4(tipo,valor,texto)
    new(valor,nil,nil,texto,tipo)
  end
=end  
  
  
=begin  
  def usada
    if(@tipo==Civitas::TipoSorpresa::SALIR_CARCEL)
      @mazo.habilitar_carta_especial(self)
    end
  end
  
  def aplicar_a_jugador(actual,todos)
    puts @tipo.inspect
    if(@tipo==Civitas::TipoSorpresa::IR_CASILLA)
      aplicar_a_jugador_ir_acasilla(actual,todos)
    end
    if(@tipo==Civitas::TipoSorpresa::IR_CARCEL)
      aplicar_a_jugador_ir_carcel(actual,todos)
    end
    if(@tipo==Civitas::TipoSorpresa::PAGAR_COBRAR)
       aplicar_a_jugador_pagar_cobrar(actual,todos)
    end
    if(@tipo==Civitas::TipoSorpresa::POR_CASA_HOTEL)
      aplicar_a_jugador_por_casa_hotel(actual,todos)
    end
    if(@tipo==Civitas::TipoSorpresa::POR_JUGADOR)
      aplicar_a_jugador_por_jugador(actual,todos)
    end
    if(@tipo==Civitas::TipoSorpresa::SALIR_CARCEL)
      aplicar_a_jugador_salir_carcel(actual,todos)
    end
  end

  
  
  def aplicar_a_jugador_ir_carcel(actual,todos)
    if(jugador_correcto(actual,todos))
      informe(actual,todos)
      todos[actual].encarcelar(4) # no me deja poner tablero.numcasillacarcel
    end
  end
    
  
  def aplicar_a_jugador_ir_acasilla(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        casilla_actual=todos[actual].numCasillaActual
        tirada=@tablero.calcular_tirada(casilla_actual,@valor)
        nueva_pos=@tablero.nueva_posicion(casilla_actual,tirada)
        todos[actual].mover_a_casilla(nueva_pos)
        @tablero.get_casilla(nueva_pos).recibe_jugador(actual, todos)
      end
  end
    
  def aplicar_a_jugador_pagar_cobrar(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        todos[actual].modificar_saldo(@valor)
      end
  end
  
  
  def aplicar_a_jugador_por_casa_hotel(actual,todos)
    if(jugador_correcto(actual,todos))
        informe(actual,todos)
        todos[actual].modificar_saldo(@valor*todos[actual].cantidad_casas_hoteles)
    end
  end
    
  def aplicar_a_jugador_por_jugador(actual,todos)
     if(jugador_correcto(actual,todos))
        informe(actual,todos)
        a=Sorpresa.constructor4(Civitas::TipoSorpresa::PAGAR_COBRAR,@valor*-1,"Pagan todos los jugadores menos el no afectado ")
        for i in 0...todos.size
          if(i!=actual)
            a.aplicar_a_jugador_pagar_cobrar(i,todos)
          end
        end
        b=Sorpresa.constructor4(Civitas::TipoSorpresa::PAGAR_COBRAR,@valor*(todos.size-1),"Recibe dinero de todos los jugadores")
        b.aplicar_a_jugador_pagar_cobrar(actual,todos)
    end
  end
  
  
  def aplicar_a_jugador_salir_carcel(actual,todos)
     if(jugador_correcto(actual,todos))
        informe(actual,todos)
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
  
  
  def salir_del_mazo
    if(@tipo==Civitas::TipoSorpresa::SALIR_CARCEL)
      @mazo.inhabilitar_carta_especial(self)
    end
    
  end
  
  
  def to_string
    a=@tipo.to_s
    return a
  end
  
=end
    
    
  private_class_method :new
  private :informe
  
  
end
end
