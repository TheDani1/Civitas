require_relative ('tablero')
require_relative ('mazo_sorpresas')
require_relative ('casilla')
require_relative ('diario')
require_relative ('dado')
require_relative ('tipo_sorpresa')
require_relative ('tipo_casilla')
require_relative ('operaciones_juego')
require_relative ('estados_juego')
require_relative ('sorpresa')
require_relative ('civitas_juego')
require_relative ('gestor_estados') 
require_relative ('jugador')
require_relative ('vista_textual')
require_relative ('controlador')
require_relative ('operacion_inmobiliaria')
require_relative ('gestiones_inmobiliarias')
require_relative ('respuestas')
require_relative ('salidas_carcel')

module Civitas
class Test
 
=begin
 def self.main
   
    
    for i in 0...100
      puts "\n#{Dado.instance.quien_empieza(4)}"
      
    end 
       
  end
  
 def self.main2
   
   Dado.instance.set_debug(false)
   
   for i in 0...20
   puts "\n#{Dado.instance.tirar}"
   end
   
 end
 
 
 def self.main3
  
   Dado.instance.set_debug(false)
   
   for i in 0...100
 
   puts "\n#{Dado.instance.salgo_de_la_carcel}"
   end
  
  
  end
  
 def self.main4
   
   puts Operaciones_juego::COMPRAR
 end
 
 
 def self.main5
       a=Mazo_sorpresas.new
       b=Sorpresa.new
       c=Sorpresa.new
       a.al_mazo(b)
       a.al_mazo(c)
       a.siguiente
       a.inhabilitar_carta_especial(c)
       a.habilitar_carta_especial(c)
       puts Diario.instance.leer_evento
       puts Diario.instance.leer_evento
   
 end
 
 
 def self.main6
   
       a=Tablero.new(3);
       b=Casilla.new('Pablo')
       c=Casilla.new('Celia')
       d=Casilla.new('Juan')
       e=Casilla.new('Pedro');
       
      
           a.anade_casilla(b)
           a.anade_casilla(c);    
           a.anade_casilla(d);
           a.anade_casilla(e);
   
 end
=end
 def self.main
   vistatext=Vista_textual.new
   nombres=Array.new
   nombres<<"Pablo"
   nombres<<"Celia"
   Dado.instance.set_debug(true)
   juego=CivitasJuego.new(nombres)
   contr=Controlador.new(juego,vistatext)
   
   contr.juega
   
 end
   
 
  Test.main
end
  
end