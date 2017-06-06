package sample.strategyComputer;

import javafx.scene.control.Label;
import sample.Tile;

import static sample.Main.*;

public abstract class ComputerStrategy {

    protected Tile[][] grid  = new Tile[X_TILES][Y_TILES];

    protected int sumPC;

    protected Label labelSumPC;

    protected int fixX;
    protected int fixY;

    protected boolean start;

    protected boolean isExit;

    public int getSumPC() {
        return sumPC;
    }

    public int getFixX() {
        return fixX;
    }
    public int getFixY() {
        return fixY;
    }
    public void setFixX(int fixX) {
        this.fixX = fixX;
    }
    public void setFixY(int fixY) {
        this.fixY = fixY;
    }
    public void setSumPC(int sumPC) {
        this.sumPC = sumPC;
    }

    protected ComputerStrategy(Tile[][] grid, Label labelSumPC, boolean start) {
        this.grid = grid;
        this.labelSumPC = labelSumPC;
        sumPC = 0;
        isExit = true;
        this.start = start;
        if(start) {
            fixX = 0;
            fixY = 0;
        }
        else {
            fixX = X_TILES - 1;
            fixY = Y_TILES - 1;
        }
    }

    public abstract void strategyComputer(int x, int y);

    private void clearWave() {
        for (int i = 0; i < X_TILES; i++) {
            for(int j = 0; j < Y_TILES; j++) {
                grid[i][j].isWave = false;
            }
        }
    }

    public boolean isSearchPath(int x, int y) {
        clearWave();
        int l = 0;
        grid[x][y].isWave = true;
        while(l < X_TILES*Y_TILES) {
            for(int i = 0; i < X_TILES; i++) {
                for(int j = 0; j < Y_TILES; j++) {
                    if(grid[i][j].isWave) {
                        waveCell(i, j);
                    }
                    if(grid[fixX][fixY].isWave) {
                        return true;
                    }
                }
            }
            l++;
        }
        return false;
    }

    private void waveCell(int x, int y) {
        for (int i = -MAX_STEP; i <= MAX_STEP; i++) {
            for (int j = -MAX_STEP; j <= MAX_STEP; j++) {
                if (checkOnBoundsCondition(x + i, y + j) && checkOnNotExistingCells(i, j)
                        && (!grid[x + i][y + j].isOpen)) {
                    grid[x + i][y + j].isWave = true;
                }
                if(x + i == fixX && y + j == fixY) {
                    grid[fixX][fixY].isWave = true;
                }
            }
        }
    }

    protected void checkOnPlaceStartOfTheGame(Tile tile) {
        if(start) {
            tile.open();
        }
        else {
            tile.openPC();
        }
    }

    protected int checkOnPlaceStartOfTheGame() {
        if(!start) {
            return (Math.abs(X_TILES - fixX)) * Math.abs(Y_TILES - fixY);
        }
        return (Math.abs(fixX + 1)) * Math.abs(fixY + 1);
    }

    public boolean checkOnExistenceOfTheCode(int x, int y) {
        for (int i = -MAX_STEP; i <= MAX_STEP; i++) {
            for (int j = -MAX_STEP; j <= MAX_STEP; j++) {
                if (checkOnBoundsCondition(x + i, y + j) && checkOnNotExistingCells(i, j)
                        && !grid[x + i][y + j].isOpen) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean checkOnBoundsCondition(int x, int y) {
        return x >= 0 && y >= 0 && x < X_TILES && y < Y_TILES;
    }

    protected boolean checkOnNotExistingCells(int x, int y) {
        return !(Math.abs(x) == 2 && Math.abs(y) == 1) && !(Math.abs(y) == 2 && Math.abs(x) == 1);
    }
}