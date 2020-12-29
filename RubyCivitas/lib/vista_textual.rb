#encoding:utf-8
require 'io/console'
require_relative "civitas_juego"
require_relative "controlador"

module Civitas

  class Vista_textual

    def initialize(civitas)
      @juego_model = civitas
      @i_gestion = -1
      @i_propiedad = -1
    end

    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end

    def menu(titulo, lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end

    
    def comprar

      lista = [TipoRespuestas::NO,TipoRespuestas::SI]
      eleccion=menu("¿Desea comprar la calle a la que ha llegado?",["No","Si"])
      return lista[eleccion]

    end

    def gestionar

      lista_s=["Vender","Hipotecar","Cancelar hipoteca","Construir casa","Construir hotel","Terminar"]
      lista_g=[TiposGestionesInmobiliarias::VENDER,TiposGestionesInmobiliarias::HIPOTECAR,TiposGestionesInmobiliarias::CANCELAR_HIPOTECA,TiposGestionesInmobiliarias::CONSTRUIR_CASA,TiposGestionesInmobiliarias::CONSTRUIR_HOTEL,TiposGestionesInmobiliarias::TERMINAR]

      seleccion= menu("¿Qué gestión desea realizar?",lista_s)
      @i_gestion=seleccion

      if @i_gestion != 5 && !@juego_model.get_jugador_actual.propiedades.empty?

        propiedades_nombre=[]
        propiedades_titulo=@juego_model.get_jugador_actual.propiedades

        for i in 0...propiedades_titulo.size() do
          propiedades_nombre.push(propiedades_titulo[i].nombre)
        end
        opcion = menu("Sobre qué propiedad?", propiedades_nombre)
        @i_propiedad=opcion
      end

    end

    def get_gestion
      @i_gestion
    end

    def get_propiedad
      @i_propiedad
    end

    def mostrar_siguiente_operacion(operacion)

      puts operacion.to_s

    end

    def mostrar_eventos

      while(Diario.instance.eventos_pendientes)

        puts Diario.instance.leer_evento

      end
    end

    def set_civitas_juego(civitas)
      @juego_model = civitas
      #self.actualizar_vista
    end

    def actualizar_vista
      
      puts @juego_model.get_jugador_actual.to_string
      puts @juego_model.get_casilla_actual.to_string

    end

    def salir_carcel
      titulo = "Elige la forma para intentar salir de la carcel"
      lista = [Salidas_carcel::PAGANDO, Salidas_carcel::TIRANDO]
      opcion = menu(titulo,["Pagando", "Tirando el dado"])
      return lista[opcion]
    end

    attr_reader :i_gestion, :i_propiedad
    public :mostrar_eventos, :gestionar

    
  end

end
