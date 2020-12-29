require_relative "gestor_estados"
require_relative "jugador"
require_relative "mazo_sorpresas"
require_relative "sorpresa"
require_relative "tablero"
require_relative "titulo_propiedad"
require_relative 'jugador_especulador'
require_relative 'sorpresa_ircarcel.rb'
require_relative 'sorpresa_jugadoriracasilla.rb'
require_relative 'sorpresa_pagarcobrar.rb'
require_relative 'sorpresa_porcasahotel.rb'
require_relative 'sorpresa_porjugador.rb'
require_relative 'sorpresa_salircarcel.rb'
require_relative 'sorpresa_jugadorespeculador.rb'
require_relative 'casilla_calle.rb'
require_relative 'casilla_sorpresa.rb'
require_relative 'casilla_impuesto.rb'
require_relative 'casilla_juez.rb'
require_relative 'casilla.rb'
require_relative 'jugador_especulador'
require_relative 'jugador'
require "./dado"
module Civitas
  class CivitasJuego

    def initialize(nombre_jugadores)
      
      @jugadores = Array.new
      for i in 0...nombre_jugadores.size
        nuevo = Jugador.new(nombre_jugadores[i])
        jugadorespeculador = Jugador_especulador.copia(nuevo, 1500)
        @jugadores.push(jugadorespeculador)
        
      end
      
      nuevo = Jugador.new("Daniel")
      @jugadores.push(nuevo)

      # Modificación: todos son especuladores menos 1
      
      @gestor_estados = Gestor_estados.new
      @estado = @gestor_estados.estado_inicial
      @indice_jugador_actual = Dado.instance.quien_empieza(@jugadores.length)
      @mazo = MazoSorpresas.new
      @tablero = Tablero.new
      inicializa_mazo_sorpresas(@tablero)
      inicializa_tablero(@mazo)

    end

    def inicializa_tablero(mazo_s)

      @tablero = Tablero.new(9);
      
      # CASILLA 1
      propiedad_n1 = TituloPropiedad.new("Reyes Catolicos",25,50,1000,2000,500)
      casilla_n1 = Casilla_calle.new(propiedad_n1)
      @tablero.aniade_casilla(casilla_n1)
      
      # CASILLA 2
      propiedad_n2 = TituloPropiedad.new("Callao", 25, 50, 1000, 2000, 500)
      casilla_n2 = Casilla_calle.new(propiedad_n2)
      @tablero.aniade_casilla(casilla_n2)
      
      # SORPRESA 1
      sorpresa_n1 = Casilla_sorpresa.new(mazo_s, "Casilla sorpresa n1")
      @tablero.aniade_casilla(sorpresa_n1)
      
      # CASILLA 3
      propiedad_n3 = TituloPropiedad.new("Pedro Antonio",25,50,1000,2000,500)
      casilla_n3 = Casilla_calle.new(propiedad_n3)
      @tablero.aniade_casilla(casilla_n3)
      
      # CASILLA 4
      propiedad_n4 = TituloPropiedad.new("Catedral",50.0,100,2000,4000,1000)
      casilla_n4 = Casilla_calle.new(propiedad_n4)
      @tablero.aniade_casilla(casilla_n4)
      
      # CASILLA 5
      propiedad_n5 = TituloPropiedad.new("Camino Ronda",50.0,100,2000,4000,1000)
      casilla_n5 = Casilla_calle.new(propiedad_n5)
      @tablero.aniade_casilla(casilla_n5)
      
      # CASILLA 6
      propiedad_n6 = TituloPropiedad.new("Malagueta",50.0,100,2000,4000,1000)
      casilla_n6 = Casilla_calle.new(propiedad_n6)
      @tablero.aniade_casilla(casilla_n6)
      
      # CASILLA 7
      propiedad_n7 = TituloPropiedad.new("Alicante",50.0,100,2000,4000,1000)
      casilla_n7 = Casilla_calle.new(propiedad_n7)
      @tablero.aniade_casilla(casilla_n7)
      
      # CASILLA 8
      propiedad_n8 = TituloPropiedad.new("Xeraco",50.0,100,2000,4000,1000)
      casilla_n8 = Casilla_calle.new(propiedad_n8)
      @tablero.aniade_casilla(casilla_n8)
      
      # CASILLA 9
      propiedad_n9 = TituloPropiedad.new("Albaicin",50.0,100,2000,4000,1000)
      casilla_n9 = Casilla_calle.new(propiedad_n9)
      @tablero.aniade_casilla(casilla_n9)
      
      # CASILLA 10
      propiedad_n10 = TituloPropiedad.new("La Zubia",50.0,100,2000,4000,1000)
      casilla_n10 = Casilla_calle.new(propiedad_n10)
      @tablero.aniade_casilla(casilla_n10)
      
      # CASILLA 11
      propiedad_n11 = TituloPropiedad.new("Zaidin",50.0,100,2000,4000,1000)
      casilla_n11 = Casilla_calle.new(propiedad_n11)
      @tablero.aniade_casilla(casilla_n11)
      
      # CASILLA 12
      propiedad_n12 = TituloPropiedad.new("Palacio Deportes",50.0,100,2000,4000,1000)
      casilla_n12 = Casilla_calle.new(propiedad_n12)
      @tablero.aniade_casilla(casilla_n12)
      
      # IMPUESTO 1
      hacienda = Casilla_impuesto.new("Hacienda!",1000)
      @tablero.aniade_casilla(hacienda)
      
      # DESCANSO 1
      la_nada = Casilla.new("Nada")
      @tablero.aniade_casilla(la_nada)
      
      # SORPRESA 2
      sorpresan2 = Casilla_sorpresa.new(mazo_s,"Casilla sorpresa n2")
      @tablero.aniade_casilla(sorpresan2)
      
      # SORPESA 3
      sorpresan3 = Casilla_sorpresa.new(mazo_s,"Casilla sorpresa n3")
      @tablero.aniade_casilla(sorpresan3)

      @tablero.aniade_juez

    end

    def inicializa_mazo_sorpresas(tablero)
      
      especulador = Sorpresa_jugadorespeculador.new(1500,"[SORPRESA] Conversion ESPECULADOR")
      @mazo.al_mazo(especulador)

      ir_a_carcel = Sorpresa_ircarcel.new(tablero);
      @mazo.al_mazo(ir_a_carcel);

      ir_a_casilla = Sorpresa_jugadoriracasilla.new(tablero,3,"[SORPRESA] Vete a la casilla 3");
      @mazo.al_mazo(ir_a_casilla);

      ir_a_casilla_n2 = Sorpresa_jugadoriracasilla.new(tablero,5,"[SORPRESA] Vete a la casilla 5");
      @mazo.al_mazo(ir_a_casilla_n2);

      salir_carcel = Sorpresa_salircarcel.new(@mazo);
      @mazo.al_mazo(salir_carcel);

      pagar = Sorpresa_pagarcobrar.new(-50,"[SORPRESA] Tienes que pagar 50")
      @mazo.al_mazo(pagar);

      cobrar = Sorpresa_pagarcobrar.new(50,"[SORPRESA] Tienes que cobrar 50");
      @mazo.al_mazo(cobrar);

      por_casa_hotel = Sorpresa_porcasahotel.new(20,"[SORPRESA] Pagas 100 por cada casa y hotel");
      @mazo.al_mazo(por_casa_hotel);

      por_jugador_n1 = Sorpresa_porjugador.new(30,"[SORPRESA] Cada jugador te paga 30");
      @mazo.al_mazo(por_jugador_n1);

      por_jugador_n2 = Sorpresa_porjugador.new(-30,"[SORPRESA] Se te quitan 30 para pagar a un jugador");
      @mazo.al_mazo(por_jugador_n2);

    end

    def contabilizar_pasos_por_salida(jugador_actual)

      while(@tablero.get_por_salida>0)

        jugador_actual.pasa_por_salida

      end
    end

    def pasar_turno

      @indice_jugador_actual = (@indice_jugador_actual + 1 ) % @jugadores.size

    end

    def siguiente_paso_completo(operacion)

      @estado = @gestor_estados.siguiente_estado(@jugadores.at(@indice_jugador_actual), @estado, operacion);

    end

    def construir_casa(ip)

      @jugadores.at(@indice_jugador_actual).construir_casa(ip);

    end

    def construir_hotel(ip)

      @jugadores.at(@indice_jugador_actual).construir_hotel(ip)

    end

    def vender(ip)

      @jugadores.at(@indice_jugador_actual).vender(ip);

    end

    def hipotecar(ip)

      @jugadores.at(@indice_jugador_actual).hipotecar(ip);

    end

    def cancelar_hipoteca(ip)

      @jugadores.at(@indice_jugador_actual).cancelar_hipoteca(ip);

    end

    def salir_carcel_pagando

      @jugadores.at(@indice_jugador_actual).salir_carcel_pagando

    end

    def salir_carcel_tirando

      @jugadores.at(@indice_jugador_actual).salir_carcel_tirando

    end

    def final_del_juego

      for jugador in @jugadores

        if jugador.saldo == 0

          return true

        end
      end

      return false

    end

    def ranking

      ranking = Array.new {Jugador}

      @jugadores.each { |jugadores|

        ranking.push(jugadores)

      }

      ranking.sort.reverse!

    end

    def avanza_jugador

      # jugadorActual y posicion_actual
      jugador_actual = @jugadores.at(@indice_jugador_actual)
      posicion_actual = jugador_actual.num_casilla_actual

      # tirada y posicionNueva

      tirada = Dado.instance.tirar
      posicion_nueva = @tablero.nueva_posicion(posicion_actual, tirada)

      # casilla
      casilla = @tablero.get_casilla(posicion_nueva)

      # NOTA
      contabilizar_pasos_por_salida(jugador_actual)

      jugador_actual.mover_a_casilla(posicion_nueva)
      casilla.recibe_jugador(@indice_jugador_actual, @jugadores)

      # Por si acaso, pasa por salida
      contabilizar_pasos_por_salida(jugador_actual)

    end

    def get_jugador_actual

      @jugadores[@indice_jugador_actual]

    end

    def get_casilla_actual

      @tablero.get_casilla(@jugadores[@indice_jugador_actual].num_casilla_actual)

    end

    def siguiente_paso

      jugador_actual = @jugadores.at(@indice_jugador_actual)
      
      operacion = @gestor_estados.operaciones_permitidas(jugador_actual, @estado)

      if(operacion == Operaciones_juego::PASAR_TURNO)

        pasar_turno
        siguiente_paso_completo(operacion)
      elsif (operacion == Operaciones_juego::AVANZAR)

        avanza_jugador
        siguiente_paso_completo(operacion)

      end

      operacion

    end

    def comprar

      jugador_actual = get_jugador_actual
      casilla = get_casilla_actual
      titulo = casilla.titulo_propiedad

      res = jugador_actual.comprar(titulo)

    end

  end
end
