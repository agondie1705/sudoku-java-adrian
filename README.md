# sudoku-java-adrian
## 📌 UML — Class Diagram

El diagrama completo está en `docs/uml/class-diagram.md`, pero aquí tienes una vista rápida:

```mermaid
classDiagram
    class SudokuBoard {
        -int[][] board
        +getValue(row, col)
        +setValue(row, col, value)
    }

    class SudokuGenerator {
        +generateBoard()
    }

    class SudokuSolver {
        +solve(board)
    }

    class GameManager {
        +addError()
        +isGameOver()
    }
```
