package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import sample.strategyComputer.ComputerStrategy;
import sample.strategyComputer.MaximumComputerStrategy;
import sample.strategyComputer.MinimumComputerStrategy;
import sample.strategyComputer.MirrorComputerStrategy;

import java.util.Random;

import static sample.Main.*;

class RandomComputerStrategyGenerator {
    private Random rand = new Random(47);
    public ComputerStrategy next(Tile[][] grid, Label sumPlayer, boolean start) {
        if(start) {
            switch (rand.nextInt(2)) {
                default:
                case 0:
                    return new MaximumComputerStrategy(grid, sumPlayer, start);
                case 1:
                    return new MinimumComputerStrategy(grid, sumPlayer, start);
            }
        }
        else {
            switch (rand.nextInt(3)) {
                default:
                case 0:
                    return new MaximumComputerStrategy(grid, sumPlayer, start);
                case 1:
                    return new MinimumComputerStrategy(grid, sumPlayer, start);
                case 2:
                    return new MirrorComputerStrategy(grid, sumPlayer, start);
            }
        }
    }
}

public class ControllerGame {

    private static RandomComputerStrategyGenerator randomComputerStrategyGenerator = new RandomComputerStrategyGenerator();

    private Tile[][] grid = new Tile[X_TILES][Y_TILES];

    private ComputerStrategy computerStrategy;
    private ComputerStrategy computerStrategy1;

    Label labelNamePlayer1;
    Label labelNamePlayer2;

    private int fixX;
    private int fixY;

    private int sum = 0;

    Label sumPlayer;

    public void setFixX(int fixX) {
        this.fixX = fixX;
    }

    public void setFixY(int fixY) {
        this.fixY = fixY;
    }

    public int getFixX() {
        return fixX;
    }

    public int getFixY() {
        return fixY;
    }

    public ControllerGame(Tile[][] grid, Label sumPlayer1, Label sumPlayer2, Label labelNamePlayer1, Label labelNamePlayer2) {
        this.labelNamePlayer1 = labelNamePlayer1;
        this.labelNamePlayer2 = labelNamePlayer2;
        this.grid = grid;
        computerStrategy = randomComputerStrategyGenerator.next(grid, sumPlayer1, true);
        computerStrategy1 = randomComputerStrategyGenerator.next(grid, sumPlayer2, false);
    }

    public ControllerGame(Label sumPlayer1, Label sumPlayer2, Tile[][] grid, Label labelNamePlayer1, Label labelNamePlayer2) {
        this.labelNamePlayer1 = labelNamePlayer1;
        this.labelNamePlayer2 = labelNamePlayer2;
        this.grid = grid;
        this.sumPlayer = sumPlayer1;
        fixX = 0;
        fixY = 0;
        computerStrategy = randomComputerStrategyGenerator.next(grid, sumPlayer2, false);
    }

    private void searchWinner() {
        if(sum > computerStrategy.getSumPC()) {
            printAlertWinner(labelNamePlayer1.getText());
        }
        else if(sum == computerStrategy.getSumPC()) {
            printAlertWinner("Friendship");
        }
        else
            printAlertWinner(labelNamePlayer2.getText());
    }

    private void printAlertWinner(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pum Purum Pum");
        alert.setHeaderText("Winner is " + str);
        alert.showAndWait();
    }

    public void searchWinnerPCvsPC() {
        if(computerStrategy.getSumPC() > computerStrategy1.getSumPC()) {
            printAlertWinner(labelNamePlayer1.getText());
        }
        else if(computerStrategy.getSumPC() == computerStrategy1.getSumPC()) {
            printAlertWinner("Friendship");
        }
        else
            printAlertWinner(labelNamePlayer2.getText());
    }

    public void start() {
        for (int i = 0; i < 50; i++) {
            controllerGame(0, 0);
        }
    }

    public void start(int x, int y) {
        if (computerStrategy.checkOnExistenceOfTheCode(x, y)) {
            sum += (x + 1) * (y + 1);
            sumPlayer.setText(String.valueOf(sum));
            computerStrategy.strategyComputer(x, y);
        }
        else {
            startPC(x, y);
            searchWinner();
        }
    }

    private void startPC(int x, int y){
        for(int i = 0; i < 50; i++) {
            computerStrategy.strategyComputer(x, y);
        }
    }

    public void controllerGame(int x, int y) {
        computerStrategy.strategyComputer(x, y);
        computerStrategy1.strategyComputer(computerStrategy.getFixX(), computerStrategy.getFixY());
    }
}