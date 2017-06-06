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
        if (start) {
            switch (rand.nextInt(2)) {
                default:
                case 0:
                    return new AlongTheWallsComputerStrategy(grid, sumPlayer, start);
                case 1:
                    return new MinimumComputerStrategy(grid, sumPlayer, start);
            }
        } else {
            switch (rand.nextInt(3)) {
                default:
                case 0:
                    return new AlongTheWallsComputerStrategy(grid, sumPlayer, start);
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
    private MaximumComputerStrategy computerStrategyMax;

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
                vsMinimum:
                computerStrategy = new AlongTheWallsComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new AlongTheWallsComputerStrategy(grid, sumPlayer2, false);
                break;
            case AlongTheWallsvsMaximum:
                vsMinimum:
                computerStrategy = new AlongTheWallsComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MaximumComputerStrategy(grid, sumPlayer2, false);
                break;
            case AlongTheWallsvsMinimum:
                vsMinimum:
                computerStrategy = new AlongTheWallsComputerStrategy(grid, sumPlayer1, true);
                computerStrategy1 = new MinimumComputerStrategy(grid, sumPlayer2, false);
                break;
            case AlongTheWallsvsMirror:
                vsMinimum:
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
        computerStrategy = randomComputerStrategyGenerator.next(grid, sumPlayer2, false);
        computerStrategyMax = new MaximumComputerStrategy(grid, sumPlayer2, false);
    }

    public void searchWinner() {
        if (sum > computerStrategy.getSumPC()) {
            printAlertWinner(labelNamePlayer1.getText());
        } else if (sum == computerStrategy.getSumPC()) {
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
        if (computerStrategy.getSumPC() > computerStrategy1.getSumPC()) {
            printAlertWinner(labelNamePlayer1.getText());
        } else if (computerStrategy.getSumPC() == computerStrategy1.getSumPC()) {
            printAlertWinner("Friendship");
        } else
            printAlertWinner(labelNamePlayer2.getText());
    }

    public void start() {
        for (int i = 0; i < 50; i++) {
            computerStrategy.strategyComputer(0, 0);
            computerStrategy1.strategyComputer(computerStrategy.getFixX(), computerStrategy.getFixY());
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
            computerStrategyMax.setFixX(computerStrategy.getFixX());
            computerStrategyMax.setFixY(computerStrategy.getFixY());
            computerStrategyMax.setSumPC(computerStrategy.getSumPC());
            if (!computerStrategy.checkOnExistenceOfTheCode(x, y)) {
                if (startPC(x, y)) {
                    searchWinner();
                }
            }
        } else {
            grid[computerStrategyMax.getFixX()][computerStrategyMax.getFixY()].setDarkBlue();
            computerStrategyMax.strategyComputer(x, y);
            if (!computerStrategyMax.checkOnExistenceOfTheCode(x, y)) {
                if (startPC(x, y)) {
                    searchWinner();
                }
            }
        }
    }

    private boolean startPC(int x, int y) {
        for (int i = 0; i < 50; i++) {
            computerStrategy.strategyComputer(x, y);
        }
        return true;
    }
}