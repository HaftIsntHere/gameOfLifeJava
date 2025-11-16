public class Cell {
  private boolean isAlive;
  private boolean nextState;
  int x;
  int y;

  public Cell(int x, int y) {
    isAlive = false;
    this.x = x;
    this.y = y;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public void setAlive(boolean alive) {
    isAlive = alive;
  }

  public int aliveNeighborCount(Cell[][] cells) {
    int counter = 0;

    for (int dx = -1; dx <= 1; dx++) {
      for (int dy = -1; dy <= 1; dy++) {
        if (dx == 0 && dy == 0) continue;

        int nx = x + dx;
        int ny = y + dy;

        if (nx >= 0 && nx < cells.length &&
            ny >= 0 && ny < cells[0].length) {
          if (cells[nx][ny].isAlive()) counter++;
        }
      }
    }

    return counter;
  }


  public void queueAlive(boolean alive) {
    nextState = alive;
  }

  public void applyQueue() {
    isAlive = nextState;
  }

  @Override
  public String toString() {
    return isAlive ?  "⬛" : "⬜";
  }
}
