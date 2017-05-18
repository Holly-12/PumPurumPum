package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    public static final int TILE_SIZE = 40;
    public static final int W = 400;
    public static final int H = 400;

    public static final int X_TILES = W / TILE_SIZE;
    public static final int Y_TILES = H / TILE_SIZE;

    public static final int MAX_STEP = 2;

    private Tile[][] grid;

    private ControllerGame controllerGame;

    private Scene scene;

    private Stage primaryStage;

    Label sumPlayer1 = new Label("0");
    Label sumPlayer2 = new Label("0");

    Label labelNamePlayer1 = new Label("Player1");
    Label labelNamePlayer2 = new Label("Player2");

    private Parent createContent(boolean flag) {
        Pane root = new Pane();
        root.setPrefSize(W, H);
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                grid[x][y] = new Tile(x, y, flag, controllerGame);
                root.getChildren().add(grid[x][y]);
            }
        }
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        scene = new Scene(createStage(false));

        this.primaryStage = primaryStage;

        primaryStage.getIcons().add(new Image("file:Img/Icon.png"));
        primaryStage.setTitle("Pum Purum Pum");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane createStage(boolean flag) {

        Pane root = new Pane();

        HBox hbox = new HBox();
        VBox vbox = new VBox();
        grid = new Tile[X_TILES][Y_TILES];
        labelNamePlayer1 = new Label("Player1");
        labelNamePlayer2 = new Label("Player2");

        sumPlayer1 = new Label("0");
        sumPlayer2 = new Label("0");

        sumPlayer1.setFont(new Font("Serif", 20));
        sumPlayer2.setFont(new Font("Serif", 20));

        sumPlayer1.setTextFill(Color.DARKRED);
        sumPlayer2.setTextFill(Color.DARKBLUE);

        hbox.getChildren().addAll(labelNamePlayer1, sumPlayer1, labelNamePlayer2, sumPlayer2);
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(40);
        hbox.setAlignment(Pos.CENTER);

        createMenu(vbox);

        if (!flag) {
            controllerGame = new ControllerGame(sumPlayer1, sumPlayer2, grid, labelNamePlayer1, labelNamePlayer2);

        }

        vbox.getChildren().addAll(hbox, createContent(flag));

        if (flag) {
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2);
            controllerGame.start();
        }
        root.getChildren().add(vbox);

        return root;
    }

    private void createMenu(VBox vbox) {
        MenuBar menuBar = new MenuBar();
        //menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        //root.setTop(menuBar);

        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New Player");
        //MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        newMenuItem.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog("Player1");
            dialog.setTitle("Pum Purum Pum");
            dialog.setHeaderText("Name Player");
            dialog.setContentText("Please enter your name:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                labelNamePlayer1.setText(result.get());
            }
        });

        fileMenu.getItems().addAll(newMenuItem, new SeparatorMenuItem(), exitMenuItem);

        Menu modeMenu = new Menu("Mode");
//        MenuItem PCvsPlayerMenuItem = new MenuItem("PC vs Player");
        MenuItem PCvsPCMenuItem = new MenuItem("PC vs PC");
        MenuItem playervsPCMenuItem = new MenuItem("Player vs PC");
//        MenuItem Player1vsPlayer2CMenuItem = new MenuItem("Player1 vs Player2");
        playervsPCMenuItem.setOnAction(event -> {
            scene = new Scene(createStage(false));
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        PCvsPCMenuItem.setOnAction(event -> {
            scene = new Scene(createStage(true));
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });

        modeMenu.getItems().addAll(PCvsPCMenuItem, playervsPCMenuItem);

        Menu aboutTheGameMenu = new Menu("About the game");
        MenuItem aboutTheGameMenuItem = new MenuItem("About the game");
        aboutTheGameMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pum Purum Pum");
            alert.setHeaderText("Legend Pum Purum Pum");
            alert.setContentText("The playing field is divided into 100 equal squares (10 x 10). " +
                    "The first player occupies the left upper cell, the second - the right lower cell.\n" +
                    "\n" +
                    "Players take turns in turn; The first player starts. Each move is a move of the player to " +
                    "any of adjacent cells, as well as to any of adjacent to adjacent (if there is currently no rival). " +
                    "If the cage into which the player comes is not previously visited either by him or his " +
                    "opponent (free cage), he is awarded points - the distance from the initial position to " +
                    "the occupied cell.\n" +
                    "\n" +
                    "Thus, the most valuable for each player are the cells near the starting position of the opponent.\n" +
                    "\n" +
                    "The game continues as long as there are free cells on the field.\n" +
                    "\n" +
                    "The winner is the player who scored more points." +
                    "\n" +
                    "By Valeriya Borodina IT-31");
            alert.showAndWait();
        });

        aboutTheGameMenu.getItems().add(aboutTheGameMenuItem);

        menuBar.getMenus().addAll(fileMenu, modeMenu, aboutTheGameMenu);

        vbox.getChildren().add(menuBar);
    }

    public static void main(String[] args) {
        launch(args);
    }
}