require_relative "diario"
require_relative "titulo_propiedad"
module Civitas
  class Jugador

    @@casas_max = 4
    @@casas_por_hotel = 4
    @@hoteles_max = 4
    @@paso_por_salida = 1000
    @@precio_libertad = 200
    @@saldo_inicial = 99999999

    def initialize(nombre)
      @encarcelado = false
      @nombre = nombre
      @num_casilla_actual = 1
      @puede_comprar = true
      @saldo = @@saldo_inicial
      @salvoconducto = false
      @propiedades = Array.new
    end

    def debe_ser_encarcelado

      if !@encarcelado && !tiene_salvoconducto
        return true
      elsif !@encarcelado && tiene_salvoconducto
        perder_salvoconducto
        Diario.instance.ocurre_evento("[DIARIO] El jugador #{@nombre} se libra de la carcel.")
        return false
      else
        return true
      end
      return false
    end

    def encarcelar(num_casilla_carcel)

      if debe_ser_encarcelado

        mover_a_casilla(num_casilla_carcel)
        @encarcelado = true
        Diario.instance.ocurre_evento("[DIARIO] El jugador #{@nombre} ha sido encarcelado")
      end

      return @encarcelado
    end

    def existe_la_propiedad(ip)

      return (ip >= 0 && ip < @propiedades.size())

    end

    def obtener_salvoconducto(s)

      if @encarcelado
        return false
      else
        @salvoconducto = s
      end
    end

    def perder_salvoconducto

      @salvoconducto.usada
      @salvoconducto = nil

    end

    def tiene_salvoconducto

      @salvoconducto!=nil

    end

    def puede_comprar_casilla

      @puede_comprar = true

      if @encarcelado

        @puede_comprar = false

      end

      return @puede_comprar
    end

    def paga(cantidad)

      modificar_saldo(cantidad * -1)

    end

    def paga_impuesto(cantidad)

      if @encarcelado
        return false
      else
        paga(cantidad)
      end
    end

    def paga_alquiler(cantidad)

      if @encarcelado
        return false
      else
        paga(cantidad)
      end

    end

    def recibe(cantidad)

      if @encarcelado
        return false
      else
        modificar_saldo(cantidad)
      end
    end

    def modificar_saldo(cantidad)

      @saldo += cantidad
      Diario.instance.ocurre_evento("[DIARIO] El saldo del jugador ha sido incrementado en " + cantidad.to_s)
      return true
    end

    def mover_a_casilla(num_casilla)

      if @encarcelado

        return false
      else
        @num_casilla_actual = num_casilla
        @puede_comprar = false
        Diario.instance.ocurre_evento("[DIARIO] El jugador ha sido movido a la casilla " + num_casilla.to_s)
        return true
      end
    end

    def copia(otro)

      @encarcelado = otro.is_encarcelado
      @nombre = otro.nombre

      @num_casilla_actual = otro.num_casilla_actual
      @propiedades = otro.propiedades
      @puede_comprar = otro.puede_comprar
      @saldo = otro.saldo
      @salvoconducto = otro.tiene_salvoconducto

    end

    def puedo_gastar(precio)

      if @encarcelado
        return false
      else
        @saldo >= precio
      end

    end

    def vender(ip)

      if @encarcelado
        return false
      elsif existe_la_propiedad(ip) && @propiedades[ip].vender(self)

        @propiedades.delete(@propiedades[ip])
        Diario.instance.ocurre_evento("El jugador #{@nombre} ha vendido una propiedad")

        return true
      end
    end

    def tiene_algo_que_gestionar

      return @propiedades.size >= 1

    end

    def puede_salir_carcel_pagando

      @saldo >= @@precio_libertad

    end

    def salir_carcel_pagando

      if @encarcelado && puede_salir_carcel_pagando

        paga(@@precio_libertad)
        @encarcelado = false
        Diario.instance.ocurre_evento("[DIARIO] El jugador #{@nombre} ha salido de la carcel")
        true
      else
        false
      end

    end

    def salir_carcel_tirando

      Dado.instance.salgo_de_la_carcel

    end

    def pasa_por_salida

      modificar_saldo(@@paso_por_salida)
      Diario.instance.ocurre_evento("[DIARIO] El jugador #{@nombre} ha pasado por salida +#{@@paso_por_salida}")
      true

    end

    def puedo_edificar_hotel(propiedad)

      @propiedades.include?(propiedad) && puedo_gastar(propiedad.precio_edificar) &&
        (propiedad.num_casas >= @@casas_por_hotel) && (propiedad.num_hoteles < @@hoteles_max)

    end

    def puedo_edificar_casa(propiedad)

      @propiedades.include?(propiedad) && puedo_gastar(propiedad.precio_edificar) &&
        propiedad.num_casas < @@casas_max

    end

    def compare_to(otro = Jugador)

      @saldo <=> otro.saldo

    end

    def cancelar_hipoteca(ip)

      result = false

      if !@encarcelado && existe_la_propiedad(ip)

        propiedad = @propiedades[ip]
        cantidad = propiedad.get_importe_cancelar_hipoteca

        if puedo_gastar(cantidad)

          result = propiedad.cancelar_hipoteca(self)

        end

        if result

          Diario.instance.ocurre_evento("[DIARIO] El jugador " + @nombre + " cancela la hipoteca de la propiedad " + ip.to_s)

        end
      end

      result
    end

    def comprar(titulo)

      result = false;

      if !@encarcelado && @puede_comprar

        precio_compra = titulo.precio_compra

        if puedo_gastar(precio_compra)

          @propiedades.push(titulo)
          result = titulo.comprar(self)

          if result

            Diario.instance.ocurre_evento("[DIARIO] El jugador " + @nombre + " compra " + titulo.nombre)

          end

        end

        @puede_comprar = false

      end

      result

    end

    def construir_hotel(ip)

      if !@encarcelado && existe_la_propiedad(ip)

        propiedad = @propiedades[ip]
        puedo_edificar_hotel = puedo_edificar_hotel(propiedad)
        precio = propiedad.precio_edificar

        if puedo_edificar_hotel && puedo_gastar(precio)

          result = propiedad.construir_hotel(self)

          casas_por_hotel = @@casas_por_hotel
          propiedad.derruir_casas(casas_por_hotel, self)

          Diario.instance.ocurre_evento("[DIARIO] El jugador " + @nombre + " construye un hotel " + "en " +
                                     ip + ", " + propiedad.nombre)
        end
      end

      result
    end

    def construir_casa(ip)

      result = false

      if existe_la_propiedad(ip)

        propiedad = @propiedades[ip]

        if !@encarcelado

          puedo_edificar_casa = puedo_edificar_casa(propiedad)

          if (puedo_edificar_casa)

            result = propiedad.construir_casa(self)

          end
        end

        if result

          Diario.instance.ocurre_evento("[DIARIO] El jugador #{@nombre} construye una casa en la propiedad #{ip}")

        end

      end
    end

    def hipotecar(ip)

      result = false

      if !@encarcelado && existe_la_propiedad(ip)

        propiedad = @propiedades.at(ip)
        result = propiedad.hipotecar(self)

      end

      if result

        Diario.instance.ocurre_evento("[DIARIO] El jugador #{@nombre} hipoteca la propiedad #{ip}")

      end

      result
    end

    def is_encarcelado

      @encarcelado

    end

    def to_string

      "-----------[#{@nombre}]-------------\n[Saldo] -> #{@saldo}\n[Encarcelado] -> #{@encarcelado}\n[Numero de casilla actual] -> #{@num_casilla_actual}\n"

    end
    
    public

    attr_reader :casas_max, :casas_por_hotel, :hoteles_max, :nombre, :precio_libertad,
      :paso_por_salida, :propiedades, :puede_comprar, :saldo
    
    attr_accessor :num_casilla_actual

    protected :debe_ser_encarcelado

    private :puede_salir_carcel_pagando, :puedo_gastar, :perder_salvoconducto

    public :compare_to, :vender

  end
end
