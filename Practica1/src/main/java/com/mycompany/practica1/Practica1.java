/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.practica1;
import java.util.Random;
import java.util.Scanner;

public class Practica1 {
    // Definición de variables globales
    static char[][] board; // El tablero del juego
    static int pacmanRow, pacmanCol; // Posición de Pac-Man
    static int score = 0; // Puntaje
    static int lives = 3; // Vidas
    static int totalRows, totalCols; // Dimensiones del tablero

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        // Menú de inicio
        System.out.println("==== MENÚ DE INICIO ====");
        System.out.println("1. Iniciar Juego");
        System.out.println("2. Historial de partidas");
        System.out.println("3. Salir");
        int option = sc.nextInt();

        if (option == 1) {
            iniciarJuego(sc, rand);
        } else if (option == 3) {
            System.out.println("¡Gracias por jugar!");
        } else {
            System.out.println("Opción no válida. Saliendo...");
        }

        sc.close();
    }

    // Función para iniciar el juego
    public static void iniciarJuego(Scanner sc, Random rand) {
        // Configuración inicial
        System.out.println("Ingrese su nombre de usuario: ");
        String username = sc.next();
        System.out.println("Bienvenido, " + username + "!");
        
        System.out.println("Seleccionar tamaño de tablero: 'P' para Pequeño o 'G' para Grande");
        char boardType = sc.next().charAt(0);

        if (boardType == 'P') {
            totalRows = 5;
            totalCols = 6;
        } else {
            totalRows = 10;
            totalCols = 10;
        }

        board = new char[totalRows][totalCols];
        llenarTablero(rand);

        // Mostrar el tablero inicial
        mostrarTablero();

        // Lógica del juego
        while (lives > 0 && score < (totalRows * totalCols)) {
            System.out.println("Puntaje: " + score + " | Vidas: " + lives);
            System.out.println("Ingrese movimiento (8=Arriba, 5=Abajo, 4=Izquierda, 6=Derecha): ");
            int move = sc.nextInt();

            // Mover Pac-Man según la tecla presionada
            if (move == 8) {
                moverPacMan(-1, 0); // Arriba
            } else if (move == 5) {
                moverPacMan(1, 0);  // Abajo
            } else if (move == 4) {
                moverPacMan(0, -1); // Izquierda
            } else if (move == 6) {
                moverPacMan(0, 1);  // Derecha
            }

            // Verificar colisiones y actualizar el tablero
            if (board[pacmanRow][pacmanCol] == '@') { // Fantasma
                lives--; // Pac-Man pierde una vida
                System.out.println("¡Has perdido una vida por un fantasma!");
                board[pacmanRow][pacmanCol] = ' ';  // Eliminar el fantasma
            } else if (board[pacmanRow][pacmanCol] == '0') { // Premio simple
                score += 10;
                board[pacmanRow][pacmanCol] = ' ';  // Eliminar premio simple
            } else if (board[pacmanRow][pacmanCol] == '$') { // Premio especial
                score += 15;
                board[pacmanRow][pacmanCol] = ' ';  // Eliminar premio especial
            }

            // Mostrar el tablero después de cada movimiento
            mostrarTablero();
        }

        // Mensaje final
        if (lives == 0) {
            System.out.println("¡Has perdido todas tus vidas! El juego ha terminado.");
        } else {
            System.out.println("¡Felicidades, ganaste!");
        }
    }

    // Llenar el tablero con elementos aleatorios
    public static void llenarTablero(Random rand) {
        // Llenar el tablero con espacios vacíos
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                board[i][j] = ' ';
            }
        }

        // Colocar paredes, premios y fantasmas aleatorios
        colocarElementos(rand, '@', 0.2); // Fantasmas
        colocarElementos(rand, '0', 0.4); // Premios simples
        colocarElementos(rand, '$', 0.4); // Premios especiales
        colocarElementos(rand, 'X', 0.2); // Paredes

        // Colocar Pac-Man en una posición aleatoria vacía
        pacmanRow = rand.nextInt(totalRows);
        pacmanCol = rand.nextInt(totalCols);
        while (board[pacmanRow][pacmanCol] != ' ') {
            pacmanRow = rand.nextInt(totalRows);
            pacmanCol = rand.nextInt(totalCols);
        }
        board[pacmanRow][pacmanCol] = '<'; // Representación de Pac-Man
    }

    // Colocar un tipo de elemento (fantasma, premio, pared) aleatoriamente en el tablero
    public static void colocarElementos(Random rand, char element, double percentage) {
        int totalElements = (int) (totalRows * totalCols * percentage);
        for (int i = 0; i < totalElements; i++) {
            int row = rand.nextInt(totalRows);
            int col = rand.nextInt(totalCols);
            if (board[row][col] == ' ') {
                board[row][col] = element;
            }
        }
    }

    // Mover Pac-Man
    public static void moverPacMan(int deltaRow, int deltaCol) {
        int newRow = pacmanRow + deltaRow;
        int newCol = pacmanCol + deltaCol;

        if (newRow < 0) newRow = totalRows - 1; // Bordes infinitos
        if (newRow >= totalRows) newRow = 0;
        if (newCol < 0) newCol = totalCols - 1;
        if (newCol >= totalCols) newCol = 0;

        if (board[newRow][newCol] != 'X') { // No puede moverse por las paredes
            // Borrar la antigua posición de Pac-Man
            board[pacmanRow][pacmanCol] = ' ';
            pacmanRow = newRow;
            pacmanCol = newCol;
            // Colocar Pac-Man en la nueva posición
            board[pacmanRow][pacmanCol] = '<';
        }
    }

    // Mostrar el tablero
    public static void mostrarTablero() {
        System.out.println("\nTablero:");
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
