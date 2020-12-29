# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative ('tablero')
require_relative ('casilla')
require_relative ('gestor_estados')
require_relative ('titulo_propiedad')
require_relative ('jugador')
require_relative 'operaciones_juego'
require_relative 'casilla_calle'
require_relative 'casilla_juez'
require_relative 'casilla_impuesto'
require_relative 'casilla_sorpresa'
require_relative 'sorpresa'
require_relative 'sorpresa_ir_carcel'
require_relative 'sorpresa_ir_casilla'
require_relative 'sorpresa_pagar_cobrar'
require_relative 'sorpresa_por_casa_hotel'
require_relative 'sorpresa_por_jugador'
require_relative 'sorpresa_salir_carcel'
require_relative 'sorpresa_jugador_especulador'




module Civitas 
class CivitasJuego
  
  def initialize(nombres)
    @jugadores=Array.new
    for i in 0...nombres.size
      @jugadores<<Jugador.new(nombres[i])
    end
    @indiceJugadorActual=Dado.instance.quien_empieza(@jugadores.size)
    @gestorEstados=Gestor_estados.new
    @estado=@gestorEstados.estado_inicial
    @mazo=Mazo_sorpresas.new(false)
    inicializa_tablero(@mazo)
    inicializa_mazo_sorpresas(@tablero)
  end
  
  def inicializa_tablero(mazo)
    @tablero=Tablero.new(4)
    @mazo=mazo
    
    @tablero.anade_casilla(CasillaCalle.new("Albolotee",TituloPropiedad.new("Albolote",80,1.20,610,880,200)))
    @tablero.anade_casilla(CasillaSorpresa.new("Sorpresa1",@mazo))
    @tablero.anade_juez
    @tablero.anade_casilla(CasillaCalle.new("Ogijares",TituloPropiedad.new("Ogijares",70,1.20,590,840,185)))
    @tablero.anade_casilla(CasillaCalle.new("Armilla",TituloPropiedad.new("Armilla",60,1.17,565,800,170)))
    @tablero.anade_casilla(CasillaSorpresa.new("Sorpresa2",@mazo))
    
    @tablero.anade_casilla(CasillaCalle.new("Otura",TituloPropiedad.new("Otura",110,1.29,680,1150,240)))
    @tablero.anade_casilla(CasillaCalle.new("Chana",TituloPropiedad.new("Chana",100,1.26,650,950,230)))
    @tablero.anade_casilla(Casilla.new("Descanso"))
    @tablero.anade_casilla(CasillaCalle.new("Peligros",TituloPropiedad.new("Peligros",90,1.23,630,920,215)))
    @tablero.anade_casilla(CasillaSorpresa.new("Sorpresa3",@mazo))
    @tablero.anade_casilla(CasillaCalle.new("Churriana",TituloPropiedad.new("Churriana",170,1.44,830,1650,310)))
    @tablero.anade_casilla(CasillaCalle.new("Santa fe",TituloPropiedad.new("Santa fe",140,1.37,770,1500,280)))
    
    @tablero.anade_casilla(CasillaCalle.new("Baza",TituloPropiedad.new("Baza",130,1.34,740,1300,270)))
    @tablero.anade_casilla(CasillaSorpresa.new("Sorpresa4",@mazo))
    @tablero.anade_casilla(CasillaCalle.new("Cenes",TituloPropiedad.new("Cenes",210,1.53,950,2200,360)))
    @tablero.anade_casilla(CasillaCalle.new("Cullar vega",TituloPropiedad.new("Cullar vega",200,1.5,920,2000,350)))
    @tablero.anade_casilla(CasillaImpuesto.new("Multa",1000))
    @tablero.anade_casilla(CasillaCalle.new("Motril",TituloPropiedad.new("Motril",180,1.45,860,1850,320)))
   
    
  end
  
  
  def inicializa_mazo_sorpresas(tablero)
    @mazo.al_mazo(SorpresaJugadorEspeculador.new("200 de fianza", 200))
    @mazo.al_mazo(SorpresaPorJugador.new("Pagas 200 a cada jugador ", -200))
    @mazo.al_mazo(SorpresaSalirCarcel.new("Sorpresa salir carcel", @mazo))
    @mazo.al_mazo(SorpresaPagarCobrar.new("Cobra 500 euros",+500))
    @mazo.al_mazo(SorpresaPagarCobrar.new("Pagas 500 euros",-500))
    @mazo.al_mazo(SorpresaIrCasilla.new("Avanza hasta la casilla Salida", tablero, 0))
    @mazo.al_mazo(SorpresaIrCasilla.new("Vas a la carcel", tablero, 4))
    @mazo.al_mazo(SorpresaIrCasilla.new("Ve a la casilla de descanso", tablero,8)) 
    @mazo.al_mazo(SorpresaPorCasaHotel.new("Recibes 200 por cada casa y hotel", +200))
    @mazo.al_mazo(SorpresaPorCasaHotel.new("Pagas 200 por cada casa y hotel", -200))
    @mazo.al_mazo(SorpresaPorJugador.new("Recibes 200 de cada jugador ", +200))
    
    @mazo.al_mazo(SorpresaIrCarcel.new("Vas a la carcel",tablero))
  end
  
  def contabilizar_pasos_por_salida(jugadorActual)
    while(@tablero.get_porsalida>0)
      jugadorActual.pasa_por_salida
    end
  end
  
  def pasar_turno
    @indiceJugadorActual= @indiceJugadorActual+1
    if(@indiceJugadorActual==@jugadores.size)
      @indiceJugadorActual=0
    end
  end
  
   def siguiente_paso_completado(operacion)
     @estado=@gestorEstados.siguiente_estado(@jugadores[@indiceJugadorActual],
       @estado, operacion)
   end
   
   def vender(ip)
     return @jugadores[@indiceJugadorActual].vender(ip)
   end
   
   def salir_carcel_pagando
    return @jugadores[@indiceJugadorActual].salir_carcel_pagando
   end
   
   def salir_carcel_tirando
     return @jugadores[@indiceJugadorActual].salir_carcel_tirando
   end
   
   def final_del_juego
     ok=false
     for i in 0...@jugadores.size 
       if(@jugadores[i].en_bancarrota)
         ok=true
       end
     end
     return ok
   end
   
   def get_casilla_actual
     return @tablero.get_casilla(@jugadores[@indiceJugadorActual].numCasillaActual)
   end
   
   def get_jugador_actual
     return @jugadores[@indiceJugadorActual]
   end
   
   def ranking
    ordenados=[]
    for i in 0...@jugadores.size
    ordenados<<@jugadores[i]
    end
    ordenados.sort
    return ordenados
  end
   
   
   def avanza_jugador
     jugador_actual=@jugadores[@indiceJugadorActual]
     posicion_actual=jugador_actual.numCasillaActual
     tirada=Dado.instance.tirar
     posicion_nueva=@tablero.nueva_posicion(posicion_actual, tirada)
     casilla=@tablero.get_casilla(posicion_nueva)
     contabilizar_pasos_por_salida(jugador_actual)
     jugador_actual.mover_a_casilla(posicion_nueva)
     casilla.recibe_jugador(@indiceJugadorActual,@jugadores)
     contabilizar_pasos_por_salida(jugador_actual)
   end
   
   
   def siguiente_paso
     jugador_actual=@jugadores[@indiceJugadorActual]
     operacion=@gestorEstados.operaciones_permitidas(jugador_actual,@estado)
     if(operacion==Civitas::Operaciones_juego::PASAR_TURNO)
       pasar_turno
       siguiente_paso_completado(operacion)
     else if(operacion==Civitas::Operaciones_juego::AVANZAR)
        avanza_jugador
        siguiente_paso_completado(operacion)
     end
    end
    
    return operacion
   end
   
   
   
   def comprar
     jugador_actual=@jugadores[@indiceJugadorActual]
     num_casilla_actual=jugador_actual.numCasillaActual
     casilla=@tablero.get_casilla(num_casilla_actual)
     titulo=casilla.tituloPropiedad
     res=jugador_actual.comprar(titulo)
     return res
   end
   
   def info_jugador_texto
     return @jugadores[@indiceJugadorActual].to_string
   end
   
   
   def hipotecar(ip)
     return @jugadores[@indiceJugadorActual].hipotecar(ip)
   end
   
   def cancelar_hipoteca(ip)
     return @jugadores[@indiceJugadorActual].cancelar_hipoteca(ip)
   end
   
   def construir_casa(ip)
     return @jugadores[@indiceJugadorActual].construir_casa(ip)
   end
   
   def construir_hotel(ip)
     return  @jugadores[@indiceJugadorActual].construir_hotel(ip)
   end
   
   
   
   
  
  private :inicializa_tablero,:inicializa_mazo_sorpresas,:contabilizar_pasos_por_salida,:pasar_turno,
          :avanza_jugador
  
  end
end