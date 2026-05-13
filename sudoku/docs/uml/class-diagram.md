# UML — Class Diagram

```mermaid
classDiagram
    class SudokuBoard {
        -int[][] board
        +getValue(row, col)
        +setValue(row, col, value)
    }

    class SudokuGenerator {
        +generateBoard()
        -solveBoard()
        -isValid()
    }

    class SudokuSolver {
        +solve(board)
        -isValid()
    }

    class SudokuValidator {
        +isValidMove(board, row, col, number)
    }

    class GameManager {
        -int errors
        -int maxErrors
        -boolean gameOver
        -boolean victory
        +addError()
        +isGameOver()
        +isVictory()
        +reset()
    }

    class BoardManager {
        +copy(board)
        +isComplete(board)
    }

    class MainWindow {
        -JTextField[][] cells
        -Timer timer
        -int[][] solutionBoard
        -int[][] visibleBoard
        +resetGame()
        +handleCellInput()
    }

    SudokuGenerator --> SudokuBoard
    SudokuSolver --> SudokuBoard
    SudokuValidator --> SudokuBoard
    MainWindow --> GameManager
    MainWindow --> BoardManager
