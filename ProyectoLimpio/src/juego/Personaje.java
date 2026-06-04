package juego;

import java.awt.Color;

import entorno.Entorno;

public class Personaje {

   
    // VARIABLES
 

    private int x;
    private int y;

    private int ancho;
    private int alto;

    // posición anterior
    private int yAnterior;

    // velocidad vertical
    private double velocidadY = 0;

    // detectar si está en pisos
    private boolean enPiso = false;

    // gravedad
    private final double GRAVEDAD = 0.6;

    // fuerza salto
    private final double SALTO = -17;

    // velocidad horizontal
    private final int VELOCIDAD = 5;

    private int vidas = 3;

   
    // CONSTRUCTOR
   

    public Personaje(int x, int y, int ancho, int alto) {

        this.x = x;
        this.y = y;

        this.ancho = ancho;
        this.alto = alto;
    }


    // DIBUJAR
   
    public void dibujar(Entorno e) {

        e.dibujarRectangulo(x, y, ancho, alto, 0, Color.WHITE);
    }

   
    // MOVIMIENTO DERECHA
   

    public void moverDerecha() {

        x += VELOCIDAD;
    }


    // MOVIMIENTO IZQUIERDA
   

    public void moverIzquierda() {

        x -= VELOCIDAD;
    }


    // SALTO

    public void saltar() {

        if (enPiso) {

            velocidadY = SALTO;

            enPiso = false;
        }
    }

    
    // GRAVEDAD
  
    public void aplicarGravedad() {

        // guardar posición anterior
        yAnterior = y;

        // aplicar gravedad
        velocidadY += GRAVEDAD;

        // mover personaje
        y += (int) velocidadY;

        // asumir que está en el aire
        enPiso = false;
    }

    
    // COLISION PISO
  

    public void EnPiso(Piso piso) {

        // bordes personaje
        int izquierdaPersonaje = x - ancho / 2;
        int derechaPersonaje = x + ancho / 2;

        int abajoPersonaje = y + alto / 2;

        // bordes piso
        int izquierdaPiso = piso.getX() - piso.getAncho() / 2;
        int derechaPiso = piso.getX() + piso.getAncho() / 2;

        int arribaPiso = piso.getY() - piso.getAlto() / 2;

        // colision horizontal
        boolean colisionX =

                derechaPersonaje >= izquierdaPiso &&
                izquierdaPersonaje <= derechaPiso;

        // colision vertical
        boolean colisionY =

                yAnterior + alto / 2 <= arribaPiso &&
                abajoPersonaje >= arribaPiso;

        // solo si cae
        if (colisionX && colisionY && velocidadY >= 0) {

            // apoyar arriba
            y = arribaPiso - alto / 2;

            // detener caída
            velocidadY = 0;

            // está sobre piso
            enPiso = true;
        }
    }

 
    // COLISION COSTADOS

    public void EnCostado(Piso piso) {

        // bordes personaje
        int izquierdaPersonaje = x - ancho / 2;
        int derechaPersonaje = x + ancho / 2;

        int arribaPersonaje = y - alto / 2;
        int abajoPersonaje = y + alto / 2;

        // bordes piso
        int izquierdaPiso = piso.getX() - piso.getAncho() / 2;
        int derechaPiso = piso.getX() + piso.getAncho() / 2;

        int arribaPiso = piso.getY() - piso.getAlto() / 2;
        int abajoPiso = piso.getY() + piso.getAlto() / 2;

        // verificar misma altura
        boolean colisionY =

                abajoPersonaje > arribaPiso &&
                arribaPersonaje < abajoPiso;

      
        // CHOQUE IZQUIERDA
     
        if (colisionY &&
            derechaPersonaje >= izquierdaPiso &&
            izquierdaPersonaje < izquierdaPiso &&
            x < piso.getX()) {

            x = izquierdaPiso - ancho / 2;
        }

     
        // CHOQUE DERECHA
      
        if (colisionY &&
            izquierdaPersonaje <= derechaPiso &&
            derechaPersonaje > derechaPiso &&
            x > piso.getX()) {

            x = derechaPiso + ancho / 2;
        }
    }


    // COLISION TECHO

    public void EnTecho(Piso piso) {

        // bordes personaje
        int izquierdaPersonaje = x - ancho / 2;
        int derechaPersonaje = x + ancho / 2;

        int arribaPersonaje = y - alto / 2;

        // bordes piso
        int izquierdaPiso = piso.getX() - piso.getAncho() / 2;
        int derechaPiso = piso.getX() + piso.getAncho() / 2;

        int abajoPiso = piso.getY() + piso.getAlto() / 2;

        // colision horizontal
        boolean colisionX =

                derechaPersonaje >= izquierdaPiso &&
                izquierdaPersonaje <= derechaPiso;

        // golpea techo
        if (colisionX &&
            velocidadY < 0 &&
            arribaPersonaje <= abajoPiso &&
            yAnterior - alto / 2 >= abajoPiso) {

            // acomodar debajo
            y = abajoPiso + alto / 2;

            // detener salto
            velocidadY = 0;
        }
    }

   
    // LIMITAR PANTALLA
    
    public void limitarPantalla(Entorno e) {

        // izquierda
        if (x - ancho / 2 < 0) {

            x = ancho / 2;
        }

        // derecha
        if (x + ancho / 2 > e.ancho()) {

            x = e.ancho() - ancho / 2;
        }

        // abajo pantalla
        if (y + alto / 2 > e.alto()) {

            vidas--;

            x = 400;
            y = 100;

            velocidadY = 0;
        }
    }

    
    // GET X

    public int getX() {

        return x;
    }

    
    // GET Y

    public int getY() {

        return y;
    }

    
    // GET VIDAS

    public int getVidas() {

        return vidas;
    }

    
    // PERDER VIDA

    public void perderVida() {

        if (vidas > 0) {

            vidas--;
        }
    }

    
    // GANAR VIDA

    public void ganarVida() {

        vidas++;
    }
}