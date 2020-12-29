require_relative "jugador"
module Civitas
  class TituloPropiedad

    @@factor_intereses_hipoteca = 1.1

    def initialize(nom, ab, fr, hb, pc, pe)
      @alquiler_base = ab
      @factor_revalorizacion = fr
      @hipoteca_base = hb
      @hipotecado = false
      @nombre = nom
      @num_casas = 0
      @num_hoteles = 0
      @precio_compra = pc
      @precio_edificar = pe
      @propietario = nil
    end

    def to_string

      return "\n----------[PROPIEDAD]----------\n[Nombre] -> "+ @nombre +
        "\n[Alquiler base] -> "+@alquiler_base.to_s+"\n[Factor InteresesHipoteca] -> "+@@factor_intereses_hipoteca.to_s+
        "\n[Factor Revalorizacion] -> "+@factor_revalorizacion.to_s+"\n[Hipoteca base] -> "+@hipoteca_base.to_s+
        "\n[Hipotecado] -> "+@hipotecado.to_s+"\n[Numero casas] -> "+@num_casas.to_s+"\n[Numero Hoteles] -> "+@num_hoteles.to_s+
        "\n[Precio compra] -> "+@precio_compra.to_s+"\n[Precio edificacion] ->  "+@precio_edificar.to_s+
        "\n[Propietario] ->  "+@propietario.to_s+
        "\n-------------------------------"

    end

    def get_precio_alquiler

      if @hipotecado || propietario_encarcelado
        return 0
      else
        return (@alquiler_base * (1 + (@num_casas * 0.5) + (@num_hoteles * 2.5)))

      end
    end

    def tiene_propietario

      @propietario!=nil

    end

    def get_importe_hipoteca

      @hipoteca_base*((@num_casas*0.5) + 1 + (2.5*@num_hoteles))

    end

    def es_este_el_propietario(jugador)

      @propietario == jugador

    end

    def get_importe_cancelar_hipoteca

      self.get_importe_hipoteca * @@factor_intereses_hipoteca
    end

    def cancelar_hipoteca(jugador)

      op = false

      if @hipotecado && es_este_el_propietario(jugador)

        op = true
        jugador.paga(get_importe_cancelar_hipoteca)
        @hipotecado = false

      end

      return op

    end

    def tramitar_alquiler(jugador)

      if es_este_el_propietario(jugador) && tiene_propietario

        jugador.paga_alquiler(self.get_precio_alquiler)
        @propietario.recibe(self.get_precio_alquiler)

      end
    end

    def propietario_encarcelado
      tiene_propietario && @propietario.is_encarcelado
    end

    def cantidad_casas_hoteles

      @num_hoteles + @num_casas

    end

    def get_precio_venta

      @precio_compra + (@precio_edificar * @num_casas) + (@precio_edificar * @num_hoteles * @factor_revalorizacion)

    end

    def derruir_casas(n, jugador)

      op = false

      if es_este_el_propietario(jugador) && @num_casas >= n
        op = true
        @num_casas -= n
      end
      return op
    end

    def vender(jugador)

      op = false

      if es_este_el_propietario(jugador) && !@hipotecado
        op = true
        jugador.recibe(self.get_precio_venta)
        @propietario = nil
        @num_casas = 0
        @num_hoteles = 0
      end

      return op
    end
    
    def comprar(jugador)
      
      final = false
      if @propietario == nil
        @propietario = jugador
        @propietario.paga(@precio_compra)
        final = true
      end
      
      return final
      
    end

    def construir_hotel(jugador)

      construido = false

      if es_este_el_propietario(jugador)

        jugador.paga(@precio_edificar)
        @num_hoteles += 1
        @num_casas -=4
        construido = true
      end

      return construido
    end

    def construir_casa(jugador)

      decision = false

      if es_este_el_propietario(jugador)

        jugador.paga(@precio_edificar)
        @num_casas += 1
        decision = true

      end

      return decision
    end

    def hipotecar(jugador)

      decision = false

      if !@hipotecado && es_este_el_propietario(jugador)

        jugador.recibe(self.get_importe_hipoteca)
        @hipotecado = true
        decision = true

      end

      return decision
    end

    attr_reader :hipotecado, :hipoteca_base, :nombre, :num_casas, :num_hoteles,
      :alquiler_base, :precio_compra, :precio_edificar

  end
end
