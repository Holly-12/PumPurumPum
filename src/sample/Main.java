package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    public static final int TILE_SIZE = 40;
    public static final int W = 400;
    public static final int H = 400;

    public enum StrategyPCvsPC {MaximumvsMaximum, MaximumvsMinimum, MaximumvsMirror, MaximumvsAlongTheWalls,
        MinimumvsMinimum, MinimumvsMaximum, MinimumvsMirror, MinimumvsAlongTheWalls, AlongTheWallsvsAlongTheWalls,
        AlongTheWallsvsMinimum, AlongTheWallsvsMirror, AlongTheWallsvsMaximum};

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

    String namePlayer = "";

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
        if (namePlayer.isEmpty()) {
            labelNamePlayer1 = new Label("Player1");
        } else labelNamePlayer1 = new Label(namePlayer);

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

        root.getChildren().add(vbox);

        return root;
    }

    private void createMenu(VBox vbox) {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createMenuFileItem(), createMenuPCvsPCItem(), createMenuPlayervsPCItem(),
                createMenuAboutTheGameItem());
        vbox.getChildren().add(menuBar);
    }

    private Menu createMenuFileItem() {
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New Player");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        newMenuItem.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog("Player1");
            dialog.setTitle("Pum Purum Pum");
            dialog.setHeaderText("Name Player");
            dialog.setContentText("Please enter your name:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                namePlayer = result.get();
                labelNamePlayer1.setText(result.get());
            }
        });
        fileMenu.getItems().addAll(newMenuItem, new SeparatorMenuItem(), exitMenuItem);
        return fileMenu;
    }

    private MenuItem createMaximumvsMaximumMenuItem() {
        MenuItem MaximumvsMaximumMenuItem = new MenuItem("Maximum vs Maximum");
        MaximumvsMaximumMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.MaximumvsMaximum);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return  MaximumvsMaximumMenuItem;
    }

    private MenuItem createMaximumvsMinimumMenuItem() {
        MenuItem MaximumvsMinimumMenuItem = new MenuItem("Maximum vs Minimum");
        MaximumvsMinimumMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.MaximumvsMinimum);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });

        return MaximumvsMinimumMenuItem;
    }

    private MenuItem createMaximumvsMirrorMenuItem() {
        MenuItem MaximumvsMirrorMenuItem = new MenuItem("Maximum vs Mirror");
        MaximumvsMirrorMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.MaximumvsMirror);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return MaximumvsMirrorMenuItem;
    }

    private MenuItem createMaximumvsAlongTheWallsMenuItem() {
        MenuItem MaximumvsAlongTheWallsMenuItem = new MenuItem("Maximum vs AlongTheWalls");
        MaximumvsAlongTheWallsMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.MaximumvsAlongTheWalls);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return MaximumvsAlongTheWallsMenuItem;
    }

    private MenuItem createMinimumvsMinimumMenuItem() {
        MenuItem MinimumvsMinimumMenuItem = new MenuItem("Minimum vs Minimum");
        MinimumvsMinimumMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.MinimumvsMinimum);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return MinimumvsMinimumMenuItem;
    }

    private MenuItem createMinimumvsMaximumMenuItem() {
        MenuItem MinimumvsMaximumMenuItem = new MenuItem("Minimum vs Maximum");
        MinimumvsMaximumMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.MinimumvsMaximum);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return MinimumvsMaximumMenuItem;
    }

    private MenuItem createMinimumvsMirrorMenuItem() {
        MenuItem MinimumvsMirrorMenuItem = new MenuItem("Minimum vs Mirror");
        MinimumvsMirrorMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.MinimumvsMirror);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return MinimumvsMirrorMenuItem;
    }

    private MenuItem createMinimumvsAlongTheWallsMenuItem() {
        MenuItem MinimumvsAlongTheWallsMenuItem = new MenuItem("Minimum vs AlongTheWalls");
        MinimumvsAlongTheWallsMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.MinimumvsAlongTheWalls);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return MinimumvsAlongTheWallsMenuItem;
    }

    private MenuItem createAlongTheWallsvsAlongTheWallsMenuItem() {
        MenuItem AlongTheWallsvsAlongTheWallsMenuItem = new MenuItem("AlongTheWalls vs AlongTheWalls");
        AlongTheWallsvsAlongTheWallsMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.AlongTheWallsvsAlongTheWalls);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return AlongTheWallsvsAlongTheWallsMenuItem;
    }

    private MenuItem createAlongTheWallsvsMinimumMenuItem() {
        MenuItem AlongTheWallsvsMinimumMenuItem = new MenuItem("AlongTheWalls vs Minimum");
        AlongTheWallsvsMinimumMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.AlongTheWallsvsMinimum);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return AlongTheWallsvsMinimumMenuItem;
    }

    private MenuItem createAlongTheWallsvsMirrorMenuItem() {
        MenuItem AlongTheWallsvsMirrorMenuItem = new MenuItem("AlongTheWalls vs Mirror");
        AlongTheWallsvsMirrorMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.AlongTheWallsvsMirror);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return AlongTheWallsvsMirrorMenuItem;
    }

    private MenuItem createAlongTheWallsvsMaximumMenuItem() {
        MenuItem AlongTheWallsvsMaximumMenuItem = new MenuItem("AlongTheWalls vs Maximum");
        AlongTheWallsvsMaximumMenuItem.setOnAction(event -> {
            namePlayer = "";
            scene = new Scene(createStage(true));
            controllerGame = new ControllerGame(grid, sumPlayer1, sumPlayer2, labelNamePlayer1, labelNamePlayer2,
                    StrategyPCvsPC.AlongTheWallsvsMaximum);
            controllerGame.start();
            primaryStage.setScene(scene);
            primaryStage.show();
            controllerGame.searchWinnerPCvsPC();
        });
        return AlongTheWallsvsMaximumMenuItem;
    }

    private Menu createMenuPCvsPCItem() {
        Menu PCvsPCMenu = new Menu("PC vs PC");

        PCvsPCMenu.getItems().addAll(createMaximumvsMaximumMenuItem(), createMaximumvsMinimumMenuItem(),
                createMaximumvsMirrorMenuItem(), createMaximumvsAlongTheWallsMenuItem(), createMinimumvsMinimumMenuItem(),
                createMinimumvsMaximumMenuItem(), createMinimumvsMirrorMenuItem(), createMinimumvsAlongTheWallsMenuItem(),
                createAlongTheWallsvsAlongTheWallsMenuItem(), createAlongTheWallsvsMinimumMenuItem(),
                createAlongTheWallsvsMirrorMenuItem(), createAlongTheWallsvsMaximumMenuItem());
        return PCvsPCMenu;
    }

    private Menu createMenuPlayervsPCItem() {
        Menu playervsPCMenu = new Menu("Player vs PC");
        MenuItem playervsPCMenuItem = new MenuItem("Player vs PC");
        playervsPCMenuItem.setOnAction(event -> {
            scene = new Scene(createStage(false));
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        playervsPCMenu.getItems().addAll(playervsPCMenuItem);
        return playervsPCMenu;
    }

    private Menu createMenuAboutTheGameItem() {
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

        return aboutTheGameMenu;
    }

    public static void main(String[] args) {
        launch(args);
    }
}