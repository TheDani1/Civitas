# encoding:utf-8
module Civitas
class Tablero
  require_relative ('casilla')
  attr_reader :numCasillaCarcel
  def initialize(indice)
    if(indice>=1)
      @numCasillaCarcel=indice
    else
      @numCasillaCarcel=1   
  end
  @casillas=Array.new
  @casillas<<Casilla.new("Salida")
 
  @porSalida=0
  @tieneJuez=false
  end


  def correcto(num_casilla=nil)
    
      if(num_casilla==nil)
       return @tieneJuez && @casillas.size > @numCasillaCarcel
      
      else
        if(@tieneJuez && @casillas.size > @numCasillaCarcel)
            return @casillas.size > num_casilla
          end   
      end
    end
 
  
  def  get_porsalida()
      aux=@porSalida
      if(@porSalida>0)
        @porSalida=@porSalida-1
        return aux
      else
        return @porSalida
      end  
  end
  
  def anade_casilla(casilla)
    if(@casillas.size==@numCasillaCarcel)
        @casillas<<Casilla.new("Cárcel")
        @casillas<<casilla
    else
      @casillas<<casilla
    end
  end
  
  def anade_juez()
    if(!@tieneJuez)
      @casillas<<CasillaJuez.new("Juez",@numCasillaCarcel)
      @tieneJuez=true
    end
  end
  
  
  def get_casilla(numCasilla)
    if(correcto(numCasilla))
      return @casillas[numCasilla]
    else
      return nil
    end
    
  end
  
  
  def nueva_posicion(actual,tirada)
    if(correcto())
      a=actual+tirada
      b=(actual+tirada)% @casillas.size
      if(a==b)
        return a
      else
        @poSalida=@porSalida+1
        return b
      end
    else
      return -1
    end
  end
  
  def calcular_tirada(origen,destino)
    a=destino-origen
    if(a<0)
      @porSalida=@porSalida+1
      return a+@casillas.size
    else
      return a
    end
    
  end
  
  
 
  
  private :correcto
  
  end
end