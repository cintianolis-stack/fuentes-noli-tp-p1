package juego;

import java.awt.Color;

import entorno.Entorno;

public class Item {

    private int x;
    private int y;

    public Item(int x, int y) {

        this.x = x;
        this.y = y;
    }

    // DIBUJAR

    public void dibujar(Entorno e) {

        e.dibujarCirculo(x, y, 12, Color.GREEN);
    }

    // MOVER IZQUIERDA

    public void moverIzquierda(int valor) {

        x -= valor;
    }

    // COLISION PERSONAJE

    public boolean colisiona(Personaje p) {

        return Math.abs(x - p.getX()) < 25 &&
               Math.abs(y - p.getY()) < 25;
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