require_relative "sorpresa"
require_relative "casilla"
module Civitas
  class Tablero

    def initialize(casilla_carcel = 1)
      @num_casilla_carcel = casilla_carcel # Numero casilla de la carcel
      @casillas = Array.new() {Sorpresa} # Contenedor de las casillas del juego
      @por_salida = 0 # Numero de veces que se ha pasado por salida
      @tiene_juez = false # Representa si el tablero dispone de la casilla Juez

      if (casilla_carcel >= 1)

        @num_casilla_carcel = casilla_carcel

      end

      @casillas.push(Casilla.new("Salida"))
    end

    private

    # private methods
    def correcto
      @casillas.length > @num_casilla_carcel && @tiene_juez
    end

    def correcto?(num_casilla)
      correcto && num_casilla <= @casillas.length
    end

    public # package methods

    

    def get_por_salida

      valor_d = @por_salida

      if (@por_salida > 0)

        @por_salida -= 1

      end

      valor_d
    end

    def aniade_casilla(casilla)

      if (@casillas.length == @num_casilla_carcel)

        @casillas.push(Casilla.new("Carcel"))

      end

      @casillas.push(casilla)

      if @casillas.size == @num_casilla_carcel

        @casillas.push(Casilla.new("Carcel"))

      end

    end

    def aniade_juez

      if @tiene_juez == false

        @casillas.push(Casilla_juez.new("Juez",@num_casilla_carcel))
        @tiene_juez = true

      end

    end

    def get_casillla(num_casilla)

      casilla = null

      if (correcto?num_casilla)

        casilla = @casillas.at(num_casilla)

      end

      casilla
    end

    def nueva_posicion(actual, tirada)

      resultado = -1

      if(correcto)

        resultado = actual+tirada

        if(resultado == @casillas.length)

          @por_salida += 1

        end

      end

      resultado
    end

    def calcular_tirada(origen, destino)

      resultado = origen - destino

      if resultado < 0
        resultado += @casillas.length
      end

      resultado
    end

    def get_casilla(num_cas)

      @casillas[num_cas]

    end

    attr_accessor :casillas
    attr_reader :num_casilla_carcel

  end
end
