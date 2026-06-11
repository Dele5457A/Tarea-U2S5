package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImcApp extends Application {

    private TextField txtNombre;
    private TextField txtPeso;
    private TextField txtAltura;

    private Label lblImc;
    private Label lblCategoria;

    private ProgressBar pbImc;

    @Override
    public void start(Stage stage) {

        Label titulo = new Label("Calculadora de IMC — UTNG");
        titulo.getStyleClass().add("lbl-titulo");

        txtNombre = new TextField();
        txtPeso = new TextField();
        txtAltura = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);

        grid.add(new Label("Peso (kg):"), 0, 1);
        grid.add(txtPeso, 1, 1);

        grid.add(new Label("Altura (m):"), 0, 2);
        grid.add(txtAltura, 1, 2);

        Button btnCalcular = new Button("Calcular IMC");
        btnCalcular.setMaxWidth(Double.MAX_VALUE);
        btnCalcular.getStyleClass().add("btn-primary");

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setMaxWidth(Double.MAX_VALUE);
        btnLimpiar.getStyleClass().add("btn-secondary");

        HBox botones = new HBox(8, btnCalcular, btnLimpiar);

        lblImc = new Label("IMC: --");
        lblImc.getStyleClass().add("lbl-resultado");

        lblCategoria = new Label("Categoria: --");

        pbImc = new ProgressBar(0);
        pbImc.setMaxWidth(Double.MAX_VALUE); 
        btnCalcular.setOnAction(e -> calcularImc());
        btnLimpiar.setOnAction(e -> limpiarCampos());

        VBox root = new VBox(
                12,
                titulo,
                grid,
                botones,
                lblImc,
                lblCategoria,
                pbImc
        );

        root.setPadding(new Insets(24));

        Scene scene = new Scene(root, 400, 480);

        scene.getStylesheets().add(
                getClass()
                        .getResource("styles.css")
                        .toExternalForm()
        );

        stage.setTitle("UTNG — Calculadora IMC");
        stage.setScene(scene);
        stage.show();
    }

    private void calcularImc() {

        if (txtNombre.getText().isEmpty()
                || txtPeso.getText().isEmpty()
                || txtAltura.getText().isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("Todos los campos son obligatorios");
            alerta.showAndWait();
            return;
        }

        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double altura = Double.parseDouble(txtAltura.getText());

            double imc = peso / (altura * altura);

            lblImc.setText(String.format("IMC: %.2f", imc));

            if (imc < 18.5) {
                lblCategoria.setText("Bajo peso");
                lblCategoria.setStyle("-fx-text-fill: blue;");
            } else if (imc < 25) {
                lblCategoria.setText("Normal");
                lblCategoria.setStyle("-fx-text-fill: green;");
            } else if (imc < 30) {
                lblCategoria.setText("Sobrepeso");
                lblCategoria.setStyle("-fx-text-fill: orange;");
            } else {
                lblCategoria.setText("Obesidad");
                lblCategoria.setStyle("-fx-text-fill: red;");
            }

            pbImc.setProgress(Math.min(imc / 40.0, 1.0));

        } catch (NumberFormatException ex) {
            Alert alertaError = new Alert(Alert.AlertType.ERROR);
            alertaError.setTitle("Error de formato");
            alertaError.setHeaderText(null);
            alertaError.setContentText("Por favor, ingresa valores numéricos válidos. Usa puntos para decimales.");
            alertaError.showAndWait();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtPeso.clear();
        txtAltura.clear();

        lblImc.setText("IMC: --");
        lblCategoria.setText("Categoria: ");
        lblCategoria.setStyle(""); 

        pbImc.setProgress(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}