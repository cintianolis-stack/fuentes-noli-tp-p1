package juego;

import entorno.Entorno;
import java.awt.Color;

public class Dibujar {

    public void rectangulo(
            Entorno entorno,
            int x,
            int y,
            int ancho,
            int alto,
            int angulo,
            Color color) {

        entorno.dibujarRectangulo(
                x,
                y,
                ancho,
                alto,
                angulo,
                color);
    }
}