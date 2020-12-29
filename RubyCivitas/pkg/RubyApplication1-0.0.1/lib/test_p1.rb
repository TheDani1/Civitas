require_relative "civitas_juego"
require_relative "dado"
require_relative "controlador"
require "minitest/reporters"
require "minitest"
require "minitest/unit"

MiniTest::Unit.runner = MiniTest::SuiteRunner.new
if ENV["RM_INFO"] || ENV["TEAMCITY_VERSION"]
  MiniTest::Unit.runner.reporters << MiniTest::Reporters::RubyMineReporter.new
elsif ENV['TM_PID']
  MiniTest::Unit.runner.reporters << MiniTest::Reporters::RubyMateReporter.new
else
  MiniTest::Unit.runner.reporters << MiniTest::Reporters::ProgressReporter.new
end

module Civitas

  class Test

    def test_hola

      civita = CivitasJuego.new("Emilio", "Miguel", "Juan", "Daniel")

      vista = Vista_textual.new(civita)

      dado = Dado.new
      dado.set_debug(true)

      controlador = Controlador.new(civita, vista)


      controlador.juega
    end

  end
end