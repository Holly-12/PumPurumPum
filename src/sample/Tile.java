package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static sample.Main.*;

public class Tile extends StackPane {

    private int x, y;

    public boolean isOpen = false;

    private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);

    private boolean flag;
    public boolean isWave;

    private ControllerGame controllerGame;

    private Text text = new Text();

    public Tile(int x, int y, boolean flag, ControllerGame controllerGame) {
        this.controllerGame = controllerGame;
        this.flag = flag;
        this.x = x;
        this.y = y;

        if (x == 0 && y == 0)
            open();
        if (x == 9 && y == 9)
            openPC();

        border.setStroke(Color.LIGHTGRAY);

        text.setFill(Color.WHITE);
        text.setFont(Font.font(18));
        text.setText(String.valueOf((x + 1) * (y + 1)));

        getChildren().addAll(border, text);

        setTranslateX(x * TILE_SIZE);
        setTranslateY(y * TILE_SIZE);

        if (!flag) {
            setOnMouseClicked(event -> open());
        }
    }

    public void openPC() {
        if(flag) {
            setDarkBlue();
        }else {
            setBlue();
        }
    }

    public void open() {
        if (flag) {
            if (isOpen)
                return;
            setDarkRed();
        } else if (!flag) {
            if (checkCriterion(controllerGame.getFixX(), controllerGame.getFixY()) && !isOpen) {
                setRed();
                controllerGame.setFixX(x);
                controllerGame.setFixY(y);
                if (x == 0 && y == 0) {
                    return;
                } else {
                    controllerGame.start(x, y);
                }
            }
        }
    }

    public void setDarkRed() {
        isOpen = true;
        text.setVisible(false);
        border.setFill(Color.DARKRED);
    }

    public void setDarkBlue() {
        isOpen = true;
        text.setVisible(false);
        border.setFill(Color.DARKBLUE);
    }

    private void setRed() {
        isOpen = true;
        text.setVisible(false);
        border.setFill(Color.DARKORANGE);
    }

    private void setBlue() {
        isOpen = true;
        text.setVisible(false);
        border.setFill(Color.BLUE);
    }

    public boolean checkCriterion(int fixX, int fixY) {
        for (int i = -MAX_STEP; i <= MAX_STEP; i++) {
            for (int j = -MAX_STEP; j <= MAX_STEP; j++) {
                if (checkOnBoundsCondition(x + i, y + j) && checkOnNotExistingCells(i, j)) {
                    if (x + i == fixX && y + j == fixY) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkOnBoundsCondition(int x, int y) {
        return x >= 0 && y >= 0 && x < X_TILES && y < Y_TILES;
    }

    protected boolean checkOnNotExistingCells(int x, int y) {
        return !(Math.abs(x) == 2 && Math.abs(y) == 1) && !(Math.abs(y) == 2 && Math.abs(x) == 1);
    }
}