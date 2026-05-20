package juego;

import java.awt.Color;

import entorno.Entorno;

public class Piso {

    
    // VARIABLES

    private int x;
    private int y;

    private int ancho;
    private int alto;

    
    // CONSTRUCTOR

    public Piso(int x, int y, int ancho, int alto) {

        this.x = x;
        this.y = y;

        this.ancho = ancho;
        this.alto = alto;
    }

    
    // DIBUJAR

    public void dibujar(Entorno e) {

        e.dibujarRectangulo(x, y, ancho, alto, 0, Color.WHITE);
    }

    
    // MOVER IZQUIERDA

    public void moverIzquierda(int valor) {

        x -= valor;
    }

    
    // GET X

    public int getX() {

        return x;
    }

    
    // GET Y

    public int getY() {

        return y;
    }

    
    // GET ANCHO

    public int getAncho() {

        return ancho;
    }

    
    // GET ALTO

    public int getAlto() {

        return alto;
    }
}