    import java.util.ArrayList;
    import java.util.List;
    import java.util.Random;
    import java.util.Scanner;

    public class Main {
    private static int ROW_COUNT = 3;
    private static int COLUMNS_COUNT = 3;
    private static String CELL_STATE_EMPTY = "";
    private static String CELL_STATE_X = "X";
    private static String CELL_STATE_O = "O";

    private static String GAME_STATE_X_WON = "Х победили!";
    private static String GAME_STATE_O_WON = "О победили!";
    private static String GAME_STATE_DRAW = "Ничья!";
    private static String GAME_STATE_NOT_FINISHED = "Игра не закончена";

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        while (true){
            startGameRound();
        }
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
        boolean playerTurn = true;

        do {
            if (playerTurn){
                makePlayerTurn(board);
                printBoardState(board);
            } else {
                makeBotTurn(board);
                printBoardState(board);
            }
            System.out.println("");
            playerTurn = !playerTurn;

            String gameState = checkGameState(board);
            if (gameState != GAME_STATE_NOT_FINISHED){
                System.out.println(gameState);
                return;
            }
        } while (true);
        //checkGameState (X_WON - крестики выиграли, O_WON - нолики выиграли, DRAW - ничья, GAME_NOT_OVER - игра не закончена)
    }

    public static void makePlayerTurn(String[][] board){ //сделать ход игрока
        //get input - получить ввод
        //validate input - проверить ввод
        int[] coordinates = inputCellCoordinates(board);
        //place X on board - разместить Х на доске
        board[coordinates[0]][coordinates[1]] = CELL_STATE_X;
    }

    public static int[] inputCellCoordinates(String[][] board) {
        System.out.println("Введите 2 числа (ряд и колонку) от 0 до 2 через пробел (0-2)");

        do {
            //допущение
            //не проверяем на наличие пробела и корректность цифр
            String[] input = scanner.nextLine().split(" ");

            int row = Integer.parseInt(input[0]);
            int col = Integer.parseInt(input[1]);

            if ((row < 0) || (row >= ROW_COUNT) || (col < 0) || (col >= COLUMNS_COUNT)){
                System.out.println("Некорректное значение! Введите 2 числа (ряд и колонку) от 0 до 2 через пробел:");
            } else if (board[row][col] != CELL_STATE_EMPTY) {
                System.out.println("Данная ячейка уже занята");
            } else {
                return new int[]{row, col};
            }
        } while (true);
    }

    public static void makeBotTurn(String[][] board){
        System.out.println("Ход бота");

        int[] coordinates = getRandomEmptyCellCoordinates(board);
        board[coordinates[0]][coordinates[1]] = CELL_STATE_O;
    }

    public static int[] getRandomEmptyCellCoordinates(String[][] board){
        do {
            int row = random.nextInt(ROW_COUNT);
            int col = random.nextInt(COLUMNS_COUNT);
            //empty --- проверяем на пусто
            //if not -> try again --- если нет, то пробуем ещё раз
            //if yes -> return --- если да, выходим

            if (board[row][col] == CELL_STATE_EMPTY) {
                return new int[]{row, col};
            }
        } while (true);
        //get random empty cell - получить случайные координаты пустой ячейки
        //place O on board - разместить О на доске
    }

    public static String checkGameState(String[][] board){ //проверка состояния игры
        List<Integer> sums = new ArrayList<>();

        //iterate rows - цикл по рядам
        for (int row = 0; row < ROW_COUNT; row++) {
            int rowSum = 0;
            for (int col = 0; col < COLUMNS_COUNT; col++) {
                rowSum += calculateNumValue(board[row][col]); //calculateNumValue - подсчёт числового значения
            }
            sums.add(rowSum);
        }

        //iterate columns - цикл по рядам
        for (int col = 0; col < COLUMNS_COUNT; col++) {
            int colSum = 0;
            for (int row = 0; row < ROW_COUNT; row++) {
                colSum += calculateNumValue(board[row][col]);
            }
            sums.add(colSum);
        }

        //diagonal from top-left to bottom-right
        int leftDiagonalSum = 0;
        for (int i = 0; i < ROW_COUNT; i++) {
            leftDiagonalSum += calculateNumValue(board[i][i]);
        }
        sums.add(leftDiagonalSum);

        //diagonal from top-right to bottom-left
        int rightDiagonalSum = 0;
        for (int i = 0; i < ROW_COUNT; i++) {
            rightDiagonalSum += calculateNumValue(board[i][(ROW_COUNT - 1) - i]);
        }
        sums.add(rightDiagonalSum);


        if (sums.contains(3)){
            return GAME_STATE_X_WON;
        } else if (sums.contains(-3)) {
            return GAME_STATE_O_WON;
        } else if (areAllCellsTaken(board)){
            return GAME_STATE_DRAW;
        } else {
            return GAME_STATE_NOT_FINISHED;
        }

        //X - (1), O - (-1), empty - (0)
        //count sums rows, columns, diagonals - считаем сумму рядов, колонок, диагоналей

        //if sum.contains(3) -> X won (крестики выиграли)
        //if sum.contains(-3) -> O won (нолики выиграли)
        //if allCellsTaken() -> draw (если все ячейки заняты, то ничья
        //else -> game not over
    }

    private static int calculateNumValue(String cellState){
        if (cellState.equals(CELL_STATE_X)){
            return 1;
        } else if (cellState.equals(CELL_STATE_O)) {
            return -1;
        } else {
            return 0;
        }
    }

    public static boolean areAllCellsTaken(String[][] board){ //все ли клетки заняты
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COLUMNS_COUNT; col++) {
                if (board[row][col] == CELL_STATE_EMPTY){
                    return false;
                }
            }
        }
        return true;
    }

    public static void printBoardState(String[][] board){
        System.out.println("---------");
        for (int row = 0; row < ROW_COUNT; row++) {
            String line = "| ";
            for (int col = 0; col < COLUMNS_COUNT; col++) {
                line += board[row][col] + " ";
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println("---------");
    }
}