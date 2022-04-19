package com.example.rp2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class HelloApplication extends Application {
    static Pane pane = new Pane();
    static VBox vbox = new VBox();
    static HBox hbox = new HBox();
    static Label skorel = new Label();
    static int skore = 0;
    static Random rand = new Random();
    static int x = 500;
    static int y = 500;
    static Label DP = new Label("Dobra prace");

    @Override
    public void start(Stage stage) throws IOException {

        Scene menu = new Scene(vbox, x, y);
        Scene hra = new Scene(pane, x, y);
        Scene hra2 = new Scene(hbox, x, y);

        pane.getChildren().add(skorel);

        ObservableList<String> obtiznosti =
                FXCollections.observableArrayList(
                        "Hard",
                        "Medium",
                        "Easy"
                );
        ComboBox comboBox = new ComboBox(obtiznosti);
        comboBox.setPromptText("Obtiznost");

        Button obtiznosti1 = new Button("Play");
        obtiznosti1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                stage.setScene(hra);
                StartTimer((String) comboBox.getValue());
            }
        });

        Label nadpis = new Label("Zmackni pole");

        vbox.getChildren().addAll(nadpis, comboBox, obtiznosti1);
        vbox.setAlignment(Pos.CENTER);

        String css = this.getClass().getResource("styly.css").toExternalForm();
        hra.getStylesheets().add(css);

        stage.setTitle("Menu");
        stage.setScene(menu);

        skorel.setLayoutX(x / 2);
        skorel.setLayoutY(y / 2);

        skorel.getStyleClass().add("skore");

        skorel.setAlignment(Pos.CENTER);

        DP.setAlignment(Pos.CENTER);

        Button zapsatSe = new Button();

        hbox.getChildren().addAll(zapsatSe);

         /*if (skore >= 1) {
            System.out.println("hvno");
            stage.setScene(hra2);
            zapsatSe.setOnAction(event -> stage.setScene(hra2));
            {;
            };
        }
*/

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    public static void novyButton() {
        Button btn = new Button("");
        pane.getChildren().add(btn);
        btn.setLayoutX(rand.nextInt(x - 50));
        btn.setLayoutY(rand.nextInt(y - 50));
        btn.setPrefWidth(50);
        btn.setPrefHeight(50);
        btn.getStyleClass().add("batn");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                pane.getChildren().remove(btn);
                skore++;
            }

        });
    }

    public static void StartTimer(String obtiznost) {
        final long startNanoTime = System.nanoTime();
        double kolikrat = 0;
        if (obtiznost == "Hard") kolikrat = 2;
        else if (obtiznost == "Medium") kolikrat = 1;
        else if (obtiznost == "Easy") kolikrat = 0.5;
        double finalKolikrat = kolikrat;
        new AnimationTimer() {
            int i = 0;

            public void handle(long currentNanoTime) {
                File file = new File("C:\\Users\\honza\\IdeaProjects\\RP2\\src\\main\\resources\\com\\example\\rp2\\Highscore.txt"); // napr. soubor.txt
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                skorel.setText(Integer.toString(skore));

                if (i < 60 / finalKolikrat) i++;
                else {
                    i = 0;
                    novyButton();
                }
                if (skore == 30){
                    System.exit(0);
                    try {
                        FileReader reader = new FileReader(file, StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        if (skore == 30) {
                            try {
                                FileInputStream fis=new FileInputStream(file);
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                           // e.printStackTrace();
                        }
                    }

                }
            }
        }.start();
    }
}