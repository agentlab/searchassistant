package com.client.login;

import com.client.chatwindow.ChatController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLauncher extends Application {
	private static Stage primaryStageObj;
	public static ChatController con;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStageObj = primaryStage;
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("styles/ChatView.fxml"));

		Scene mainScene = new Scene(root);
		mainScene.setRoot(root);

		primaryStage.setTitle("SearchAgent");
		primaryStage.setResizable(false);
		primaryStage.setScene(mainScene);
		primaryStage.show();
		primaryStage.setResizable(true);
		primaryStage.setOnCloseRequest(e -> Platform.exit());
	}

	public static Stage getPrimaryStage() {
		return primaryStageObj;
	}
}
