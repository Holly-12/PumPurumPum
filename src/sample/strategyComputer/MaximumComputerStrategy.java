package sample.strategyComputer;

import javafx.scene.control.Label;
import sample.Tile;

import static sample.Main.MAX_STEP;
import static sample.Main.X_TILES;
import static sample.Main.Y_TILES;

public class MaximumComputerStrategy extends ComputerStrategy {
    public MaximumComputerStrategy(Tile[][] grid, Label labelSumPC, boolean start) {
        super(grid, labelSumPC, start);
    }

    public void strategyComputer(int x, int y) {
        if (isExit) {
            if (checkOnExistenceOfTheCode(fixX, fixY)) {
                seachMaxElement(fixX, fixY);
                Tile tile = grid[fixX][fixY];
                checkOnPlaceStartOfTheGame(tile);
                sumPC += checkOnPlaceStartOfTheGame();
                labelSumPC.setText(String.valueOf(sumPC));
            } else {
                isExit = false;
            }
        }
    }

    private void seachMaxElement(int x, int y) {
        int maxEl;
        if (start) {
            maxEl = (x - MAX_STEP + 1) * (y - MAX_STEP + 1);
        } else
            maxEl = (X_TILES - x - MAX_STEP - 1) * (Y_TILES - y - MAX_STEP - 1);
        for (int i = -MAX_STEP; i <= MAX_STEP; i++) {
            for (int j = -MAX_STEP; j <= MAX_STEP; j++) {
                if (checkOnBoundsCondition(x + i, y + j) && checkOnNotExistingCells(i, j) && !grid[x + i][y + j].isOpen) {
                    if (maxEl < countMultElement(x, y, i, j)) {
                        maxEl = countMultElement(x, y, i, j);
                        fixX = x + i;
                        fixY = y + j;
                    }
                }
            }
        }
    }

    private int countMultElement(int x, int y, int i, int j) {
        if (start) {
            return (x + i + 1) * (y + j + 1);
        }
        return (X_TILES - x - i) * (Y_TILES - y - j);
    }
}