require_relative "diario"
require_relative "sorpresa"

module Civitas
  class MazoSorpresas

    def init

      @sorpresas.clear
      @cartas_especiales.clear
      @barajada = false
      @usadas = 0

    end

    def initialize(d = false)
      @sorpresas = Array.new { Sorpresa }
      @barajada = false
      @usadas = nil
      @debug = d
      @cartas_especiales = Array.new { Sorpresa }
      @ultima_sorpresa = nil
      init
    end

    def al_mazo(s)

      unless @barajada

        @sorpresas.push(s)

      end

    end

    def siguiente

      if !@barajada || @usadas == @sorpresas.size && !@debug

        @usadas = 0
        @barajada = true

      end

      @usadas += 1
      first = @sorpresas.at(0)
      @sorpresas.delete_at(0)
      @sorpresas.push(first)
      @ultima_sorpresa = first

      @sorpresas.shuffle

      first

    end

    def inhabilitar_carta_especial(s)

      if @sorpresas.include?(s)

        card = @sorpresas.at(@sorpresas.find_index(s))
        @sorpresas.delete(s)
        @cartas_especiales.push(card)

        Diario.instance.ocurre_evento("[DIARIO] Inhabilitacion carta especial")

      end

    end

    def habilitar_carta_especial(s)

      if @cartas_especiales.include?(s)

        card = @cartas_especiales.at(@cartas_especiales.find_index(s))
        @cartas_especiales.delete(s)
        @sorpresas.push(card)

        Diario.instance.ocurre_evento("[DIARIO] Habilitacion carta especial")

      end

    end
  end
end
