package sample.strategyComputer;

import javafx.scene.control.Label;
import sample.Tile;

import static sample.Main.*;

public class MinimumComputerStrategy extends ComputerStrategy {

    public MinimumComputerStrategy(Tile[][] grid, Label labelSumPC, boolean start) {

        super(grid, labelSumPC, start);
    }

    @Override
    public void strategyComputer(int x, int y) {
        if (isExit) {
            if (checkOnExistenceOfTheCode(fixX, fixY)) {
                seachMinElement(fixX, fixY);
                Tile tile = grid[fixX][fixY];
                CheckOnPlaceStartOfTheGame(tile);
                sumPC += CheckOnPlaceStartOfTheGame();
                labelSumPC.setText(String.valueOf(sumPC));
            } else {
                isExit = false;
            }
        }
    }

    private void seachMinElement(int x, int y) {
        int minEl = 0;
        for (int i = -MAX_STEP; i <= MAX_STEP; i++) {
            for (int j = -MAX_STEP; j <= MAX_STEP; j++) {
                if (checkOnBoundsCondition(x + i, y + j) && checkOnNotExistingCells(i, j) && !grid[x + i][y + j].isOpen) {
                    if (minEl < countMultElement(x, y, i, j)) {
                        minEl = countMultElement(x, y, i, j);
                        fixX = x + i;
                        fixY = y + j;
                    }
                }
            }
        }
    }

    private int countMultElement(int x, int y, int i, int j) {
        if (!start) {
            return (x + i + 1) * (y + j + 1);
        }
        return (X_TILES - x - i - 1) * (Y_TILES - y - j - 1);
    }
}
