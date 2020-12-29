# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "civitas_juego"
require_relative "vista_textual"
require_relative "dado"

module Civitas
  
  jugadores = Array.new
  jugadores.push("Daniel")
  jugadores.push("Oscar")
  jugadores.push("Pablo")
  jugadores.push("Nuria")
  
  civitas = CivitasJuego.new(jugadores)
  
  Dado.instance.set_debug(true)

  vista = Vista_textual.new(civitas)
  
  controlador = Controlador.new(civitas, vista)
  
  controlador.juega
  
  
end

