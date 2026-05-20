package juego;

import java.awt.Color;

import entorno.Entorno;

public class Proyectil {

    private double x;
    private double y;

    private double dx;
    private double dy;

    
    // CONSTRUCTOR

    public Proyectil(
            int xInicial,
            int yInicial,
            int mouseX,
            int mouseY) {

        x = xInicial;
        y = yInicial;

        double distancia = Math.sqrt(
                Math.pow(mouseX - x, 2) +
                Math.pow(mouseY - y, 2));

        
        // evitar división por 0

        if (distancia == 0) {

            distancia = 1;
        }

        
        // velocidad proyectil

        dx = (mouseX - x) / distancia * 8;
        dy = (mouseY - y) / distancia * 8;
    }

    
    // MOVER

    public void mover() {

        x += dx;
        y += dy;
    }

    
    // MOVER IZQUIERDA

    public void moverIzquierda(int valor) {

        x -= valor;
    }

    
    // DIBUJAR

    public void dibujar(Entorno e) {

        e.dibujarCirculo(
                (int)x,
                (int)y,
                8,
                Color.YELLOW);
    }

    
    // COLISION ENEMIGO

    public boolean colisiona(Enemigo enemigo) {

        return Math.abs(x - enemigo.getX()) < 20 &&
               Math.abs(y - enemigo.getY()) < 20;
    }

    
    // FUERA PANTALLA

    public boolean fueraPantalla() {

        return x < 0 ||
               x > 800 ||
               y < 0 ||
               y > 600;
    }
}