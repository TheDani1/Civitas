# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
class Dado
   include Singleton
   
  attr_reader :ultimoResultado
  def initialize
    @ultimoResultado=1
    @debug=false
    @@SalidaCarcel=5
  end
  
  def tirar
    if(!@debug)
      @ultimoResultado=1+rand(6).to_i 
    else
      @ultimoResultado=1
    end
    
    return @ultimoResultado
  end
  
  def salgo_de_la_carcel
    a=tirar
    return a>=5
  end
  
  def quien_empieza(n)
      
      numero=rand(n).to_i
      
    return numero
   
  end
  
  def set_debug(d)
    if(d)
      Diario.instance.ocurre_evento('Debug on(avanza de unidad en unidad)')
      @debug=d
    else
      @debug=d
       Diario.instance.ocurre_evento('Debug off(avanza segun dado')
    end
  end
  
  
  
  end

end