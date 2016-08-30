package com.client.chatwindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.stanbol.client.enhancer.impl.EnhancerParameters;
import org.apache.stanbol.client.enhancer.model.EnhancementStructure;
import org.apache.stanbol.client.enhancer.model.EntityAnnotation;
import org.apache.stanbol.client.enhancer.model.TextAnnotation;

import com.client.login.MainLauncher;
import com.messages.BubbleSpec;
import com.messages.BubbledLabel;
import com.messages.Message;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ChatController implements Initializable {
	@FXML
	protected TextArea messageBox;
	@FXML
	protected VBox chatPane;
	@FXML
	protected BorderPane borderPane;
	@FXML
	protected TextArea debugTextBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void sendButtonAction() throws IOException {
		String msg = messageBox.getText();
		if (!messageBox.getText().isEmpty()) {
			// Listener.send(msg);
			Message createMessage = new Message();
	        createMessage.setType("USER");
	        createMessage.setMsg(msg);
	        addToChat(createMessage);

			messageBox.setText("");
		}
	}

	public synchronized void addToChat(Message msg) {
		try {
			String str = msg.getMsg();
			EnhancerParameters parameters = EnhancerParameters.builder().buildDefault(str);
			EnhancementStructure eRes = MainLauncher.client.enhance(parameters);
			String text = "";
			text += "********************************************";
			for (TextAnnotation ta : eRes.getTextAnnotations()) {
				text += "\nSelection Context: " + ta.getSelectionContext();
				text += "\nSelected Text: " + ta.getSelectedText();
				text += "\nEngine: " + ta.getCreator();
				text += "\nCandidates: ";
				for (EntityAnnotation ea : eRes.getEntityAnnotations(ta))
					text += "\n\t" + ea.getEntityLabel() + " - " + ea.getEntityReference();
			}

			/*text += "********************************************";
			for (TextAnnotation ta : eRes.getBestAnnotations().keySet()) {
				text += "\nSelection Context: " + ta.getSelectionContext();
				text += "\nSelected Text: " + ta.getSelectedText();
				text += "\nEngine: " + ta.getCreator();
				text += "\nCandidates: ";
				for (EntityAnnotation ea : eRes.getEntityAnnotations(ta))
					text += "\n\t" + ea.getEntityLabel() + " - " + ea.getEntityReference();
			}*/

			debugTextBox.setText(text);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Task<HBox> othersMessages = new Task<HBox>() {
			@Override
			public HBox call() throws Exception {
				Image image = new Image(getClass().getClassLoader().getResource("images/" + msg.getPicture() + ".png").toString());
				ImageView profileImage = new ImageView(image);
				profileImage.setFitHeight(32);
				profileImage.setFitWidth(32);

				BubbledLabel bl6 = new BubbledLabel();

				bl6.setText(msg.getName() + ": " + msg.getMsg());
				bl6.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
				HBox x = new HBox();
				bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);
				x.getChildren().addAll(profileImage, bl6);
				System.out.println("ONLINE USERS: " + Integer.toString(msg.getUserlist().size()));
				return x;
			}
		};

		othersMessages.setOnSucceeded(event -> {
			chatPane.getChildren().add(othersMessages.getValue());
		});

		Task<HBox> yourMessages = new Task<HBox>() {
			@Override
			public HBox call() throws Exception {
				//Image image = userImageView.getImage();
				Image image = new Image(getClass().getClassLoader().getResource("images/default.png").toString());
				ImageView profileImage = new ImageView(image);
				profileImage.setFitHeight(32);
				profileImage.setFitWidth(32);

				BubbledLabel bl6 = new BubbledLabel();

				bl6.setText(msg.getMsg());
				bl6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
				HBox x = new HBox();
				x.setAlignment(Pos.TOP_RIGHT);
				bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
				x.getChildren().addAll(bl6, profileImage);
				return x;
			}
		};

		yourMessages.setOnSucceeded(event -> {
			chatPane.getChildren().add(yourMessages.getValue());
		});

		//if (msg.getName().equals(usernameLabel.getText())) {
			Thread t2 = new Thread(yourMessages);
			t2.setDaemon(true);
			t2.start();
			System.out.println("you");
		/*} else {
			Thread t = new Thread(othersMessages);
			t.setDaemon(true);
			t.start();
			System.out.println("them");
		}*/
		System.out.println(msg.getName() + ": " + msg.getMsg());
	}

	public void sendMethod(KeyEvent event) throws IOException {
		if (event.getCode() == KeyCode.ENTER) {
			sendButtonAction();
			messageBox.setText("");
		}
	}
}
