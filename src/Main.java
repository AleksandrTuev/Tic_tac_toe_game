    //игрок всегда ходит первым
    //игрок всегда ставит Х
    //бот всегда ставит О
    //бот выбирает случайную пустую клетку

    public class Main {
    private static int ROW_COUNT = 3;
    private static int COLUMNS_COUNT = 3;
    private static String CELL_STATE_EMPTY = "";
    private static String CELL_STATE_X = "X";
    private static String CELL_STATE_O = "O";

    public static void main(String[] args) {


    }

    public static void startGameRound(){ //начать раунд игры
        //create board
        String[][] board = createBoard();
        //startGameLoop
        startGameLoop(board);
    }
    
    public static String[][] createBoard(){
        String[][] board = new String[ROW_COUNT][COLUMNS_COUNT];

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int columns = 0; columns < COLUMNS_COUNT; columns++) {
                board[row][columns] = CELL_STATE_EMPTY;
            }
        }

        return board;
    }

    public static void startGameLoop(String[][] board){ //начать игровой цикл
        //while (gameNotOver)
        //playerTurn
        //botTurn
        //checkGameState (X_WON - крестики выиграли, O_WON - нолики выиграли, DRAW - ничья, GAME_NOT_OVER - игра не закончена)
    }

    public static void makePlayerTurn(){ //сделать ход игрока
        //get input - получить ввод
        //validate input - проверить ввод
        //place X on board - разместить Х на доске
    }

    public static void makeBotTurn(){ //сделать ход бота
        //get random empty cell - получить случайные координаты пустой ячейки
        //place O on board - разместить О на доске
    }

    public static void checkGameState(){ //проверка состояния игры
        //X - (1), O - (-1), empty - (0)
        //count sums rows, columns, diagonals - считаем сумму рядов, колонок, диагоналей

        //if sum.contains(3) -> X won (крестики выиграли)
        //if sum.contains(-3) -> O won (нолики выиграли)
        //if allCellsTaken() -> draw (если все ячейки заняты, то ничья
        //else -> game not over

    }
}