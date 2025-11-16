import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
  private static final int WIDTH = 50;
  private static final int HEIGHT = 100;
  private static Cell[][] cells;
  private static Scanner in = new Scanner(System.in);

  public static synchronized void main(String[] args) throws InterruptedException {
    cells = new Cell[WIDTH][HEIGHT];

    for (int i = 0; i < cells.length; i++) {
      for (int k = 0; k < cells[i].length; k++) {
        cells[i][k] = new Cell(i, k);
      }
    }
      printState();


    System.out.println("Enter how many alive cells to be with: ");
    int cellAmount = in.nextInt();
    for (int i = 0; i < cellAmount; i++) {
      int xPos = (int) (Math.random() * WIDTH);
      int yPos = (int) (Math.random() * HEIGHT);
      if (cells[xPos][yPos].isAlive()) {
        i--;
        continue;
      }
      cells[xPos][yPos].setAlive(true);


    }

    printState();

    boolean isRepeating = false;
    while (true) {
      if(!isRepeating) {
        System.out.println("Press enter for the next simulation step, type e to exit, and r to repeat");
        char c = in.next().charAt(0);
      if (c == 'e') {
        break;
      }
      if(c == 'r')
        isRepeating = true;
      }
      simulateStep();
      clearConsole();
      printState();
      if(isRepeating) {
        Main.class.wait(100);
        System.out.println(" ");
      }

      boolean isMapAlive = false;
      for (int i = 0; i < cells.length; i++) {
        for (int i1 = 0; i1 < cells[i].length; i1++) {
          if(cells[i][i1].isAlive()) {
            isMapAlive = true;
            break;
          }
        }
        if(isMapAlive) break;
      }

      if(!isMapAlive) {
        System.out.println("Everyone has died");
        break;
      }
    }
  }

  public static void clearConsole() {
    System.out.print("\033[H\033[2J");

    System.out.flush();
  }

  private static void simulateStep() {
    for (int i = 0; i < cells.length; i++) {
      for (int k = 0; k < cells[i].length; k++) {
        cells[i][k].queueAlive(cells[i][k].isAlive());
      }
    }
    for (int i = 0; i < cells.length; i++) {
      for (int k = 0; k < cells[i].length; k++) {
        Cell cell = cells[i][k];
        int neighborCount = cell.aliveNeighborCount(cells);
//        System.out.println("("+i+", "+k+") Neighbors: " + neighborCount + " Alive: " + cell.isAlive());
        if (cell.isAlive() && (neighborCount < 2 || neighborCount > 3)) {
//          System.out.println("Alive, gonna DIE!@!");
          cell.queueAlive(false);
        }
        if (!cell.isAlive() && neighborCount == 3) {
//          System.out.println("Not alive, gonna be alive");
          cell.queueAlive(true);
        }
      }
    }

    for (int i = 0; i < cells.length; i++) {
      for (int k = 0; k < cells[i].length; k++) {
        Cell cell = cells[i][k];
        cell.applyQueue();
      }
    }
  }

  private static void printState() {
    for (int i = 0; i < cells.length; i++) {
      for (int k = 0; k < cells[i].length; k++) {
        Cell cell = cells[i][k];
        System.out.print(cell + " ");
      }
      System.out.print("\n");
    }
  }
}
