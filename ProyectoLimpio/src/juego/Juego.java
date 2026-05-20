package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
    // El objeto Entorno que controla el tiempo y otros

    private Entorno entorno;

    // Variables y métodos propios de cada grupo
    // ...

    private Personaje personaje;
    private Piso[] pisos;
    private Enemigo[] enemigos;
    private Proyectil disparo;
    private Castillo castillo;
    private Item item;

    private boolean gano = false;
    private boolean perdio = false;

    Juego()
    {
        // Inicializa el objeto entorno

        this.entorno = new Entorno(this,"Proyecto para TP",800,600);

        // Inicializar lo que haga falta para el juego
        // ...

        // PERSONAJE

        personaje = new Personaje(400,100,30,50);

        // CREAR ISLAS

        pisos = new Piso[12];

        // piso inferior izquierdo
        pisos[0] = new Piso(150,550,250,30);

        // piso inferior derecho
        pisos[1] = new Piso(500,550,250,30);

        // isla media izquierda
        pisos[2] = new Piso(250,430,220,30);

        // isla media derecha
        pisos[3] = new Piso(600,430,220,30);

        // isla superior
        pisos[4] = new Piso(430,310,220,30);

        // NUEVAS ISLAS

        // camino hacia el castillo

        pisos[5] = new Piso(900,520,220,30);

        pisos[6] = new Piso(1200,430,220,30);

        pisos[7] = new Piso(1450,340,220,30);

        pisos[8] = new Piso(1700,500,220,30);

        pisos[9] = new Piso(2000,420,220,30);

        pisos[10] = new Piso(2300,320,220,30);

        pisos[11] = new Piso(2600,520,300,30);

        // ENEMIGOS

        enemigos = new Enemigo[10];

        // CASTILLO

        castillo = new Castillo(2900,470,120,180);

        // Inicia el juego!

        this.entorno.iniciar();
    }

    /**
     * Durante el juego, el método tick() será ejecutado en cada instante y
     * por lo tanto es el método más importante de esta clase. Aquí se debe
     * actualizar el estado interno del juego para simular el paso del tiempo
     * (ver el enunciado del TP para mayor detalle).
     */

    public void tick()
    {
        // Procesamiento de un instante de tiempo
        // ...

        // GANAR

        if (gano) {

            entorno.cambiarFont("Arial",40,Color.GREEN);

            entorno.escribirTexto("GANASTE",300,300);

            return;
        }

        // PERDER

        if (perdio) {

            entorno.cambiarFont("Arial",40,Color.RED);

            entorno.escribirTexto("PERDISTE",300,300);

            return;
        }

        // DIBUJAR FONDO NEGRO

        entorno.dibujarRectangulo(
                entorno.ancho() / 2,
                entorno.alto() / 2,
                entorno.ancho(),
                entorno.alto(),
                0,
                Color.BLACK);

        // MOVIMIENTO DERECHA

        if (entorno.estaPresionada('d') ||
            entorno.estaPresionada(entorno.TECLA_DERECHA)) {

            // personaje libre al inicio

            if (personaje.getX() < 400) {

                personaje.moverDerecha();

            } else {

                // mover pisos

                for (int i = 0; i < pisos.length; i++) {

                    pisos[i].moverIzquierda(5);
                }

                // mover castillo

                castillo.moverIzquierda(5);

                // mover enemigos

                for (int i = 0; i < enemigos.length; i++) {

                    if (enemigos[i] != null) {

                        enemigos[i].moverIzquierda(5);
                    }
                }

                // mover disparo

                if (disparo != null) {

                    disparo.moverIzquierda(5);
                }

                // mover item

                if (item != null) {

                    item.moverIzquierda(5);
                }
            }
        }

        // MOVIMIENTO IZQUIERDA

        if (entorno.estaPresionada('a') ||
            entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {

            personaje.moverIzquierda();
        }

        // SALTO

        if (entorno.sePresiono('w') ||
            entorno.sePresiono(entorno.TECLA_ARRIBA)) {

            personaje.saltar();
        }

        // DISPARO

        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) &&
            disparo == null) {

            disparo = new Proyectil(
                    personaje.getX(),
                    personaje.getY(),
                    entorno.mouseX(),
                    entorno.mouseY());
        }

        // GRAVEDAD

        personaje.aplicarGravedad();

        // COLISIONES

        for (int i = 0; i < pisos.length; i++) {

            // colision arriba

            personaje.tocarPiso(pisos[i]);

            // colision costados

            personaje.tocarCostado(pisos[i]);

            // colision techo

            personaje.tocarTecho(pisos[i]);
        }

        // LIMITES PANTALLA

        personaje.limitarPantalla(entorno);

        // DIBUJAR PISOS

        for (int i = 0; i < pisos.length; i++) {

            pisos[i].dibujar(entorno);
        }

        // GENERAR ENEMIGOS

        generarEnemigos();

        // ENEMIGOS

        for (int i = 0; i < enemigos.length; i++) {

            if (enemigos[i] != null) {

                enemigos[i].mover();

                enemigos[i].dibujar(entorno);

                // choque personaje

                if (enemigos[i].colisiona(personaje)) {

                    personaje.perderVida();

                    enemigos[i] = null;
                }

                // choque disparo

                if (enemigos[i] != null &&
                    disparo != null &&
                    disparo.colisiona(enemigos[i])) {

                    enemigos[i] = null;

                    disparo = null;

                    // crear item aleatorio

                    if (Math.random() < 0.3) {

                        item = new Item(
                                personaje.getX(),
                                personaje.getY());
                    }
                }

                // fuera pantalla

                if (enemigos[i] != null &&
                    enemigos[i].fueraPantalla()) {

                    enemigos[i] = null;
                }
            }
        }

        // PROYECTIL

        if (disparo != null) {

            disparo.mover();

            disparo.dibujar(entorno);

            // fuera pantalla

            if (disparo.fueraPantalla()) {

                disparo = null;
            }
        }

        // CASTILLO

        castillo.dibujar(entorno);

        if (castillo.colisiona(personaje)) {

            gano = true;
        }

        // VIDAS

        for (int i = 0; i < personaje.getVidas(); i++) {

            entorno.dibujarCirculo(
                    30 + (i * 40),
                    30,
                    15,
                    Color.RED);
        }

        if (personaje.getVidas() <= 0) {

            perdio = true;
        }

        // ITEM

        if (item != null) {

            item.dibujar(entorno);

            if (item.colisiona(personaje)) {

                personaje.ganarVida();

                item = null;
            }
        }

        // DIBUJAR PERSONAJE

        personaje.dibujar(entorno);
    }

    // GENERAR ENEMIGOS

    private void generarEnemigos()
    {
        int vivos = 0;

        // contar enemigos vivos

        for (int i = 0; i < enemigos.length; i++) {

            if (enemigos[i] != null) {

                vivos++;
            }
        }

        // mantener enemigos vivos

        if (vivos < 5) {

            for (int i = 0; i < enemigos.length; i++) {

                if (enemigos[i] == null) {

                    // elegir isla aleatoria

                    int pisoRandom =
                            (int)(Math.random() * pisos.length);

                    Piso piso = pisos[pisoRandom];

                    // posición aleatoria sobre isla

                    int xEnemigo =
                            piso.getX()
                            - piso.getAncho() / 2
                            + (int)(Math.random() * piso.getAncho());

                    // arriba del piso

                    int yEnemigo =
                            piso.getY()
                            - piso.getAlto() / 2
                            - 20;

                    // dirección aleatoria

                    int velocidad;

                    if (Math.random() < 0.5) {

                        velocidad = 2;

                    } else {

                        velocidad = -2;
                    }

                    // crear enemigo

                    enemigos[i] =
                            new Enemigo(
                                    xEnemigo,
                                    yEnemigo,
                                    velocidad);

                    break;
                }
            }
        }
    }

    @SuppressWarnings("unused")

    public static void main(String[] args)
    {
        Juego juego = new Juego();
    }
}