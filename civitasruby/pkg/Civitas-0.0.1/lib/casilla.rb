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
  
  def jugador_correcto(actual,todos)
    return actual<=todos.size
  end
  
  def informe(actual,todos)
    Diario.instance.ocurre_evento("El jugador "+todos[actual].nombre+" ha caido en la casilla "+to_string)
  end
  
  def to_string
    cadena="Casilla: #{@nombre} \n"
    cadena+="\n"
    return cadena
  end
  
  
=begin 
  def  init
       @carcel=0
       @importe=0
       @nombre= "Casilla"
       @mazo=Mazo_sorpresas.new 
       @tipo=Civitas::TipoCasilla::CALLE
       @tituloPropiedad=nil
       @sorpresa=nil
  end    

  
  def self.constructor1(nombre)
    @tipo=Civitas::TipoCasilla::DESCANSO
    new(nil,nil,nombre,nil,@tipo,nil,nil)  
  end
  
  def self.constructor2(titulo)
    @tipo=Civitas::TipoCasilla::CALLE
    new(nil,nil,nil,nil,@tipo,titulo,nil)  
  end
  
  def self.constructor3(cantidad,nombre)
    @tipo=Civitas::TipoCasilla::IMPUESTO
    new(nil,cantidad,nombre,nil,@tipo,nil,nil)
    
  end
  
  def self.constructor4(numCasillaCarcel,nombre)
    @tipo=Civitas::TipoCasilla::JUEZ
    new(numCasillaCarcel,nil,nombre,nil,@tipo,nil,nil)  
    
  end
  
  def self.constructor5(mazo,nombre)
    @tipo=Civitas::TipoCasilla::SORPRESA
    new(nil,nil,nombre,mazo,@tipo,nil,nil)
    
  end
=end
  
=begin  
  def to_string
      cadena="Casilla: #{@nombre}   \nTipo: "+ @tipo.to_s + "\n"
      if(@tipo==TipoCasilla::CALLE)
        cadena += @tituloPropiedad.to_string + "\n"
      end
        if(@tipo==TipoCasilla::IMPUESTO)
          cadena += "Importe: #{@importe}\n"
        end
     cadena+="\n"
     return cadena
  end
=end  
  
  
=begin
  def recibe_jugador_impuesto(actual,todos)
    if(jugador_correcto(actual,todos))
      informe(actual,todos)
      todos[actual].paga_impuesto(@importe)
    end
  end
  
  def recibe_jugador_juez(actual,todos)
    if(jugador_correcto(actual,todos))
      informe(actual,todos)
      todos[actual].encarcelar(@carcel)
    end
  end
  
  
  def recibe_jugador_calle(actual,todos)
    if(jugador_correcto(actual, todos))
      informe(actual,todos)
      jugador=todos[actual]
      
      if(!@tituloPropiedad.tiene_propietario)
        jugador.puede_comprar_casilla
      else
        puts "hola"
        @tituloPropiedad.tramitar_alquiler(jugador)
      end
    end
  end
  
  
  def recibe_jugador_sorpresa(actual,todos)
    if(jugador_correcto(actual, todos))
      sorpresa1=@mazo.siguiente
      sorpresa1.aplicar_a_jugador(actual, todos)
    end
  end
=end 
  
  
  
  
  
  
  private :informe
  end 
end