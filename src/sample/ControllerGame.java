package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import sample.strategyComputer.*;

import java.util.Random;

import static sample.Main.X_TILES;
import static sample.Main.Y_TILES;

class RandomComputerStrategyGenerator {
    private Random rand = new Random(47);

    public ComputerStrategy next(Tile[][] grid, Label sumPlayer, boolean start) {

        switch (rand.nextInt(4)) {
            default:
            case 0:
                return new AlongTheWallsComputerStrategy(grid, sumPlayer, start);
            case 1:
                return new MinimumComputerStrategy(grid, sumPlayer, start);
            case 2:
                return new MirrorComputerStrategy(grid, sumPlayer, start);
            case 3:
                return new MaximumComputerStrategy(grid, sumPlayer, start);
        }

    }
}

public class ControllerGame {

    private static RandomComputerStrategyGenerator randomComputerStrategyGenerator = new RandomComputerStrategyGenerator();
    private ComputerStrategy computerStrategyMin1;
    private ComputerStrategy computerStrategyMin2;

    private Tile[][] grid = new Tile[X_TILES][Y_TILES];

    private ComputerStrategy computerStrategy;
    private ComputerStrategy computerStrategy1;

    Label labelNamePlayer1;
    Label labelNamePlayer2;

    private int fixX;
    private int fixY;

    private int oldX = 0;
    private int oldY = 0;

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

    public ControllerGame(Tile[][] grid, Label sumPlayer1, Label sumPlayer2, Label labelNamePlayer1,
                          Label labelNamePlayer2, Main.StrategyPCvsPC strategyPCvsPC) {
        this.labelNamePlayer1 = labelNamePlayer1;
        this.labelNamePlayer2 = labelNamePlayer2;
        computerStrategyMin1 = new MinimumComputerStrategy(grid, sumPlayer1, true);
        computerStrategyMin2 = new MinimumComputerStrategy(grid, sumPlayer2, false);

        switch (strategyPCvsPC) {
            case MaximumvsMaximum:
                computerStrategy = new MaximumComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MaximumComputerStrategy(grid, sumPlayer2, false);
                break;
            case MaximumvsMinimum:
                computerStrategy = new MaximumComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MinimumComputerStrategy(grid, sumPlayer2, false);
                break;
            case MaximumvsMirror:
                computerStrategy = new MaximumComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MirrorComputerStrategy(grid, sumPlayer2, false);
                break;
            case MaximumvsAlongTheWalls:
                computerStrategy = new MaximumComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new AlongTheWallsComputerStrategy(grid, sumPlayer2, false);
                break;
            case MinimumvsMinimum:
                computerStrategy = new MinimumComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MinimumComputerStrategy(grid, sumPlayer2, false);
                break;
            case MinimumvsMaximum:
                computerStrategy = new MinimumComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MaximumComputerStrategy(grid, sumPlayer2, false);
                break;
            case MinimumvsMirror:
                computerStrategy = new MinimumComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MirrorComputerStrategy(grid, sumPlayer2, false);
                break;
            case MinimumvsAlongTheWalls:
                computerStrategy = new MinimumComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new AlongTheWallsComputerStrategy(grid, sumPlayer2, false);
                break;
            case AlongTheWallsvsAlongTheWalls:
                computerStrategy = new AlongTheWallsComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new AlongTheWallsComputerStrategy(grid, sumPlayer2, false);
                break;
            case AlongTheWallsvsMaximum:
                computerStrategy = new AlongTheWallsComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MaximumComputerStrategy(grid, sumPlayer2, false);
                break;
            case AlongTheWallsvsMinimum:
                computerStrategy = new AlongTheWallsComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MinimumComputerStrategy(grid, sumPlayer2, false);
                break;
            case AlongTheWallsvsMirror:
                computerStrategy = new AlongTheWallsComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MirrorComputerStrategy(grid, sumPlayer2, false);
                break;
        }
    }

    public ControllerGame(Label sumPlayer1, Label sumPlayer2, Tile[][] grid, Label labelNamePlayer1,
                          Label labelNamePlayer2) {
        this.labelNamePlayer1 = labelNamePlayer1;
        this.labelNamePlayer2 = labelNamePlayer2;
        this.grid = grid;
        this.sumPlayer = sumPlayer1;
        fixX = 0;
        fixY = 0;
        computerStrategy = new AlongTheWallsComputerStrategy(grid, sumPlayer2, false);
                //randomComputerStrategyGenerator.next(grid, sumPlayer2, false);
        computerStrategyMin1 = new MinimumComputerStrategy(grid, sumPlayer2, false);
    }

    public void searchWinner() {
        if (sum > computerStrategyMin1.getSumPC()) {
            printAlertWinner(labelNamePlayer1.getText());
        } else if (sum == computerStrategyMin1.getSumPC()) {
            printAlertWinner("Friendship");
        } else
            printAlertWinner(labelNamePlayer2.getText());
    }

    private void printAlertWinner(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pum Purum Pum");
        alert.setHeaderText("Winner is " + str);
        alert.showAndWait();
    }

    public void searchWinnerPCvsPC() {
        if (computerStrategyMin1.getSumPC() > computerStrategyMin2.getSumPC()) {
            printAlertWinner(labelNamePlayer1.getText());
        } else if (computerStrategyMin1.getSumPC() == computerStrategyMin2.getSumPC()) {
            printAlertWinner("Friendship");
        } else
            printAlertWinner(labelNamePlayer2.getText());
    }

    public void start() {
        for (int i = 0; i < 60; i++) {
            if (computerStrategy1.isSearchPath(computerStrategy.getFixX(), computerStrategy.getFixY())) {
                computerStrategy.strategyComputer(0, 0);
                computerStrategyMin1.setFixX(computerStrategy.getFixX());
                computerStrategyMin1.setFixY(computerStrategy.getFixY());
                computerStrategyMin1.setSumPC(computerStrategy.getSumPC());
                computerStrategy1.strategyComputer(computerStrategy.getFixX(), computerStrategy.getFixY());
                computerStrategyMin2.setFixX(computerStrategy1.getFixX());
                computerStrategyMin2.setFixY(computerStrategy1.getFixY());
                computerStrategyMin2.setSumPC(computerStrategy1.getSumPC());
            } else {
                computerStrategyMin1.strategyComputer(computerStrategy.getFixX(), computerStrategy.getFixY());
                computerStrategyMin2.strategyComputer(computerStrategyMin1.getFixX(), computerStrategyMin1.getFixY());
            }
        }
    }

    public void start(int x, int y) {
        grid[oldX][oldY].setDarkRed();
        oldX = fixX;
        oldY = fixY;
        sum += (x + 1) * (y + 1);
        sumPlayer.setText(String.valueOf(sum));
        if (computerStrategy.isSearchPath(x, y)) {
            grid[computerStrategy.getFixX()][computerStrategy.getFixY()].setDarkBlue();
            computerStrategy.strategyComputer(x, y);
            computerStrategyMin1.setFixX(computerStrategy.getFixX());
            computerStrategyMin1.setFixY(computerStrategy.getFixY());
            computerStrategyMin1.setSumPC(computerStrategy.getSumPC());
            if (!computerStrategy.checkOnExistenceOfTheCode(x, y)) {
                startPC(x, y);
                searchWinner();

            }
        } else {
            grid[computerStrategyMin1.getFixX()][computerStrategyMin1.getFixY()].setDarkBlue();
            computerStrategyMin1.strategyComputer(x, y);
            if (!computerStrategyMin1.checkOnExistenceOfTheCode(x, y)) {
                startPC(x, y);
                searchWinner();

            }
        }
    }

    private void startPC(int x, int y) {
        for (int i = 0; i < 100; i++) {
            grid[computerStrategyMin1.getFixX()][computerStrategyMin1.getFixY()].setDarkBlue();
            computerStrategyMin1.strategyComputer(x, y);
        }
    }
}