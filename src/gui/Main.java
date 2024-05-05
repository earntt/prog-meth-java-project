package gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.FoundationPile;
import model.GameModel;
import model.TableauPile;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application class for Solitaire. The responsibility of this class is limited
 * to assembling the major UI components and launching the application. All
 * gesture handling logic is handled by its composed elements, which act
 * as observers of the game model.
 */
public class Main extends Application
{
	private static final int WIDTH = 685;
	private static final int HEIGHT = 600;
	private static final int MARGIN_OUTER = 10;
	private static final String TITLE = "Solitaire";
	private static final String VERSION = "8.8.8";
	private boolean isSoundPlaying = true;

	private SoundPlayer gameMusic = new SoundPlayer(
			ClassLoader.getSystemResource("background.mp3").toString()
	);
	private SoundPlayer giveUpSound = new SoundPlayer(
			ClassLoader.getSystemResource("gameover.wav").toString()
	);
	private SoundPlayer buttonSound = new SoundPlayer(
			ClassLoader.getSystemResource("btnSound.mp3").toString()
	);
	private SoundPlayer homePageSound = new SoundPlayer(
			ClassLoader.getSystemResource("homepage.mp3").toString()
	);

	/**
	 * Application head.
	 */
	public Main() {}

	/**
	 * Launches the application.
	 * @param pArgs This program takes no argument.
	 */
	public static void main(String[] pArgs)
	{
		launch(pArgs);
	}

	@Override
	public void start(Stage pPrimaryStage)
	{
		homePageSound.setVolume(1.00);
		homePageSound.setLoop();
		homePageSound.startPlay();

		pPrimaryStage.setTitle(TITLE + " " + VERSION);

		VBox homepageLayout = new VBox();
		BackgroundSize backgroundSize = new BackgroundSize(
				WIDTH, HEIGHT, false, false, false, true
		);
		homepageLayout.setBackground(new Background(
				new BackgroundImage(
						new Image("homepage-background.jpg"),
						BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER,
						backgroundSize
				)));;
		homepageLayout.setSpacing(10);
		homepageLayout.setPadding(new Insets(20));
		homepageLayout.setAlignment(Pos.CENTER);

		ImageView logo = new ImageView(new Image("logo.png"));
		Button startButton = new Button("Start Game");
		startButton.setStyle(
				"-fx-background-color: #FB688E; -fx-text-fill: white; " +
				"-fx-font-size: 16px; -fx-background-radius: 5em; " +
				"-fx-padding: 5px 20px; -fx-border-color: #FB688E; " +
				"-fx-border-radius: 5em; -fx-min-width: 120px; " +
				"-fx-min-height: 40px;"
		);

		homepageLayout.getChildren().addAll(logo, startButton);

		GridPane root = new GridPane();
		root.setBackground(new Background(
				new BackgroundImage(
						new Image("background.jpg"),
						BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER,
						backgroundSize
				)));

		root.setHgap(MARGIN_OUTER);
		root.setVgap(MARGIN_OUTER);
		root.setPadding(new Insets(MARGIN_OUTER));

		final GameModel model = new GameModel();
		DeckView deckView = new DeckView(model);
		DiscardPileView discardPileView = new DiscardPileView(model);
		Button newGameButton = new Button("New Game");
		Button soundButton = new Button();
		Button undoButton = new Button();
		newGameButton.setMaxSize(90,30);
		newGameButton.setMinSize(90,30);
		newGameButton.setStyle("-fx-background-color: white; " +
				"-fx-text-fill: #36469B; -fx-background-radius: 5em;"
		);
		ImageView soundImg = new ImageView(
				ClassLoader.getSystemResource("icons8-sound-50.png").toString()
		);
		ImageView muteImg = new ImageView(
				ClassLoader.getSystemResource("icons8-mute-50.png").toString()
		);
		ImageView soundImgHover = new ImageView(
				ClassLoader.getSystemResource("icons8-sound-50-hover.png").toString()
		);
		ImageView muteImgHover = new ImageView(
				ClassLoader.getSystemResource("icons8-mute-50-hover.png").toString()
		);
		ImageView undoImg = new ImageView(
				ClassLoader.getSystemResource("icons8-undo-96.png").toString()
		);
		ImageView undoImgHover = new ImageView(
				ClassLoader.getSystemResource("icons8-undo-96-hover.png").toString()
		);

		soundImg.setFitWidth(30);
		soundImg.setFitHeight(30);
		muteImg.setFitWidth(30);
		muteImg.setFitHeight(30);
		soundImgHover.setFitWidth(30);
		soundImgHover.setFitHeight(30);
		muteImgHover.setFitWidth(30);
		muteImgHover.setFitHeight(30);
		undoImg.setFitWidth(30);
		undoImg.setFitHeight(30);

		undoImgHover.setFitWidth(30);
		undoImgHover.setFitHeight(30);
		soundButton.setGraphic(soundImg);
		soundButton.setAlignment(Pos.CENTER);
		undoButton.setGraphic(undoImg);
		undoButton.setAlignment(Pos.CENTER);
		GridPane.setHalignment(undoButton, HPos.RIGHT);

		soundButton.setStyle("-fx-background-color: transparent, transparent, transparent, transparent, transparent;" +
				"    -fx-pref-height: 30;" +
				"    -fx-pref-width: 30;" +
				"    -fx-min-height: 30;" +
				"    -fx-min-width: 30;" +
				"    -fx-max-height: 30;" +
				"    -fx-max-width: 30;"
		);
		undoButton.setStyle("-fx-background-color: transparent, transparent, transparent, transparent, transparent;" +
				"    -fx-pref-height: 30;" +
				"    -fx-pref-width: 30;" +
				"    -fx-min-height: 30;" +
				"    -fx-min-width: 30;" +
				"    -fx-max-height: 30;" +
				"    -fx-max-width: 30;"
		);


		root.add(soundButton, 0, 0);
		root.add(undoButton, 5, 0);
		GridPane.setMargin(soundButton, new Insets(0, 0, 0, 6));
		root.add(newGameButton, 6, 0);
		root.add(deckView, 0, 1);
		root.add(discardPileView, 1, 1);

		for( FoundationPile index : FoundationPile.values() )
		{
			root.add(new SuitStack(model, index), 3 + index.ordinal(), 1);
		}

		for( TableauPile index : TableauPile.values() )
		{
			root.add(new CardPileView(model, index), index.ordinal(), 2);
		}


		newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent pEvent) {
				try {
					Thread.sleep(2500);
					giveUpSound.stopPlay();
					if(isSoundPlaying)gameMusic.startPlay();
				}
				catch (InterruptedException ie){
					Thread.currentThread().interrupt();
				}


			}
		});

		newGameButton.setOnMouseReleased(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent pEvent){
				giveUpSound.startPlay();
				gameMusic.stopPlay();
				model.reset();
			}
		});

		newGameButton.onMouseMovedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				newGameButton.setStyle("-fx-background-color: #FB688E; " +
						"-fx-background-radius: 5em; -fx-text-fill: white;"
				);
				if(model.isCompleted())newGameButton.setText("again?");
				else newGameButton.setText("don't give up");
			}
		});

		newGameButton.onMouseExitedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				newGameButton.setStyle("-fx-background-color: white; " +
						"-fx-background-radius: 5em; -fx-text-fill: #36469B;"
				);
				newGameButton.setText("New Game");
			}
		});

		undoButton.onMouseMovedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				undoButton.setGraphic(undoImgHover);
			}
		});

		undoButton.onMouseExitedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				undoButton.setGraphic(undoImg);
			}
		});

		soundButton.onMouseMovedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(isSoundPlaying)soundButton.setGraphic(soundImgHover);
				else soundButton.setGraphic(muteImgHover);
			}
		});

		soundButton.onMouseExitedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(isSoundPlaying)soundButton.setGraphic(soundImg);
				else soundButton.setGraphic(muteImg);
			}
		});

		startButton.setOnMouseEntered(e -> {
			startButton.setStyle("-fx-background-color: white; " +
					"-fx-background-radius: 5em; -fx-text-fill: #FB688E; " +
					"-fx-font-size: 16px; -fx-border-radius: 5em; " +
					"-fx-padding: 5px 20px; -fx-border-color: #FB688E;" +
					" -fx-min-width: 120px; -fx-min-height: 40px;"
			);
		});

		startButton.setOnMouseExited(e -> {
			startButton.setStyle("-fx-background-color: #FB688E; " +
					"-fx-text-fill: white; -fx-font-size: 16px; " +
					"-fx-background-radius: 5em; -fx-padding: 5px 20px; " +
					"-fx-border-color: #FB688E; -fx-border-radius: 5em; " +
					"-fx-min-width: 120px; -fx-min-height: 40px;"
			);
		});

		soundButton.setOnMousePressed(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent pEvent)
			{
				if(isSoundPlaying){
					if(gameMusic.isPlaying()) {
						gameMusic.stopPlay();
					}
					soundButton.setGraphic(muteImg);
				}
				else {
					if(!gameMusic.isPlaying()) {
						gameMusic.startPlay();
					}
					soundButton.setGraphic(soundImg);
				}
				isSoundPlaying = !isSoundPlaying;

			}
		});

		undoButton.setOnAction(e ->{
			model.undoLast();
		});

		startButton.setOnAction(e -> {
			buttonSound.stopPlay();
			buttonSound.startPlay();
			pPrimaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
			gameMusic.setVolume(0.45);
			gameMusic.setLoop();
			homePageSound.stopPlay();
			gameMusic.startPlay();
		});


		pPrimaryStage.setResizable(false);
		pPrimaryStage.setScene(new Scene(homepageLayout, WIDTH, HEIGHT));
		pPrimaryStage.show();

	}

}