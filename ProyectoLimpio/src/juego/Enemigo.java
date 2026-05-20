package juego;

import java.awt.Color;

import entorno.Entorno;

public class Enemigo {

    private int x;
    private int y;

    private int velocidad;

    public Enemigo(int x, int y, int velocidad) {

        this.x = x;
        this.y = y;

        this.velocidad = velocidad;
    }

    
    // MOVER

    public void mover() {

        x += velocidad;
    }

    
    // MOVER IZQUIERDA

    public void moverIzquierda(int valor) {

        x -= valor;
    }

    
    // DIBUJAR

    public void dibujar(Entorno e) {

        e.dibujarCirculo(x, y, 20, Color.RED);
    }

    
    // COLISION PERSONAJE

    public boolean colisiona(Personaje p) {

        return Math.abs(x - p.getX()) < 30 &&
               Math.abs(y - p.getY()) < 40;
    }

    
    // FUERA PANTALLA

    public boolean fueraPantalla() {

        return x < -100 || x > 900;
    }

    
    // GET X

    public int getX() {

        return x;
    }

    
    // GET Y

    public int getY() {

        return y;
    }
}