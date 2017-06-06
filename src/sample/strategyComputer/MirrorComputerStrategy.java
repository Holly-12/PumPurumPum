package sample.strategyComputer;
import javafx.scene.control.Label;
import sample.Tile;

import static sample.Main.X_TILES;
import static sample.Main.Y_TILES;

public class MirrorComputerStrategy extends ComputerStrategy {

    @Override
    public void strategyComputer(int x, int y) {
        if(checkOnExistenceOfTheCode(x, y)) {
            fixX = Math.abs(X_TILES - x - 1);
            fixY = Math.abs(Y_TILES - y - 1);
            Tile tile = grid[Math.abs(X_TILES - x - 1)][Math.abs(Y_TILES - y - 1)];
            tile.openPC();
            sumPC += (x + 1) * (y + 1);
            labelSumPC.setText(String.valueOf(sumPC));
        }
    }

    public MirrorComputerStrategy(Tile[][] grid, Label labelSumPc, boolean start) {

        super(grid, labelSumPc, start);
    }
}
