# encoding:utf-8
module Civitas
class Mazo_sorpresas
   
  def init
    @sorpresas=Array.new
    @cartasEspeciales=Array.new
    @barajada=false
    @usadas=0
    @ultimaSorpresa=nil
  end
  
  def initialize(d=nil)
    if(d==nil)
    init 
    @debug=false
    else
      @debug=d
     init
      if(d)
        Diario.instance.ocurre_evento('Debug ON(mazo no se baraja)')
      end
    end  
  end
  
  def al_mazo(s)
    if(!@barajada)
      @sorpresas<<s  
    end
  end
  
  def siguiente 
    if((!@barajada)||(@usadas == @sorpresas.size)&&(@debug = false))
      @sorpresas.shuffle
      @usadas = 0
      @barajada = true
    end
    @usadas = @usadas + 1
    @ultimaSorpresa = @sorpresas[0]
    @sorpresas.delete_at(0)
    @sorpresas<<@ultimaSorpresa
    return @ultimaSorpresa
  end
  
  
  def inhabilitar_carta_especial(sorpresa)
    for i in 0...@sorpresas.size
      if(@sorpresas[i]==sorpresa)
          a=@sorpresas[i]
          @sorpresas.delete_at(i)
          @cartasEspeciales<<a
          Diario.instance.ocurre_evento('Se ha inhabilitado una carta del mazo sorpresas y se ha añadido al de cartas especiales')
      end
    end
  end
  
  def habilitar_carta_especial(sorpresa)
    for i in 0...@cartasEspeciales.size
      if(@cartasEspeciales[i]==sorpresa)
          a=@cartasEspeciales[i]
          @cartasEspeciales.delete_at(i)
          @sorpresas<<a
          Diario.instance.ocurre_evento('Se ha habilitado una carta al mazo sorpresas proveniente de las cartas especiales')
      end
    end  
  end
  
  
 
  private :init
  
  end
  
end