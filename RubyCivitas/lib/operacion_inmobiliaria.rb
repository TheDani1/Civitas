module Civitas
  class OperacionInmobiliaria

    def initialize(gest = TiposGestionesInmobiliarias, ip)

      @gestion = gest

      @num_propiedad = ip

    end

    attr_reader :gestion, :num_propiedad



  end

  #[TiposGestionesInmobiliarias::VENDER,
  #                  TiposGestionesInmobiliarias::HIPOTECAR,
  #                  TiposGestionesInmobiliarias::CANCELAR_HIPOTECA,
  #                  TiposGestionesInmobiliarias::CONSTRUIR_CASA,
  #                  TiposGestionesInmobiliarias::CONSTRUIR_HOTEL,
  #                  TiposGestionesInmobiliarias::TERMINAR]


end
