require_relative "diario"
require 'singleton'
module Civitas
  class Dado
    
    include Singleton

    @@SALIDACARCEL = 5

    def initialize
      @ultimo_resultado
      @debug = false
    end

    #@@instance = Dado.new

    def tirar

      @ultimo_resultado = rand(1..6)

      if @debug

        1

      end

    end

    def salgo_de_la_carcel

      @ultimo_resultado = rand(1..6)

      if @ultimo_resultado % 2 == 0

        false

      else

        true

      end
    end

    def quien_empieza(n)

      @ultimo_resultado = rand(0..(n-1))

    end

    def set_debug(d)

      @debug = d
      Diario.instance.ocurre_evento("[DIARIO] Cambia debug a " + d.to_s)

    end
    
    public

    attr_reader :ultimo_resultado
  end
end