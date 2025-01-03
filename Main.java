import Controller.GameController;
import Models.*;
import Strategies.WinningStrategies.ColWinningStrategy;
import Strategies.WinningStrategies.DiagonalWinningStrategy;
import Strategies.WinningStrategies.RowWinningStrategy;
import Strategies.WinningStrategies.WinningStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        GameController gameController = new GameController();
        try {
            int dimensionOfGame = 3;
            List<Player> playerList = new ArrayList<>();
            playerList.add(new Player(new Symbol('X'), "Rohit",1L , PlayerType.HUMAN));
            playerList.add(new Bot(new Symbol('O'),"GPT", 2L, BotDifficultyLevel.EASY));

            List<WinningStrategy> winningStrategyList = new ArrayList<>();
            winningStrategyList.add(new RowWinningStrategy());
            winningStrategyList.add(new ColWinningStrategy());
            winningStrategyList.add(new DiagonalWinningStrategy());
            Game game = gameController.startGame(dimensionOfGame, playerList, winningStrategyList);

            while(gameController.checkState(game).equals(GameState.IN_PROGRESS)){
                gameController.printBoard(game);

                System.out.println("Does anyone want to undo? (y/n)");
                String undoAns = scn.next();

                if(undoAns.equals("y")){
                    gameController.undo(game);
                    continue;
                }

                gameController.makeMove(game);
            }

            gameController.printBoard(game);
            System.out.println("Game is finished");
            GameState gameState = gameController.checkState(game);

            if(gameState == GameState.WIN){
                System.out.println("Game is won by - " + game.getWinner().getName());
            }else{
                System.out.println("Game is a draw");
            }
            scn.close();
        } catch (Exception ex) {
            System.out.println("Game did not work");
        }
    }
}
