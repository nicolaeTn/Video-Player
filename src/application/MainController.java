package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
/**
 * Class that implements the functionality of the video player.
 * @author Nicolae Turcan
 *
 */
public class MainController implements Initializable {
	// Prepares the view that will display the video and the controls
	@FXML private MediaView mediaView;
	private MediaPlayer mediaPlayer;
	private Media media;
	// Volume and time sliders
	@FXML Slider volumeSlider;
	@FXML Slider timeSlider;
	// Path of the video file
	private String filePath;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void openFile(ActionEvent event) {
		// File chooser for choosing a video file
		FileChooser fileChooser = new FileChooser();
		// Choose only .mp4 files
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File", "*.mp4");
		fileChooser.getExtensionFilters().add(filter);
		File file = fileChooser.showOpenDialog(null);
		// Stores the video path
		filePath = file.toURI().toString();
		// If file path not empty
		if(filePath != null) {
			media = new Media(filePath);
			mediaPlayer = new MediaPlayer(media);
			mediaView.setMediaPlayer(mediaPlayer);
			// Plays the video
			mediaPlayer.play();
			// Sets width and height of the video
			DoubleProperty width = mediaView.fitWidthProperty();
			DoubleProperty height = mediaView.fitHeightProperty();
			width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
			height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		}
		// Sets the volume slider
		volumeSlider.setValue(mediaPlayer.getVolume() * 100);
		// Adds a listener to the volume slider
		volumeSlider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable){
				// Sets the volume to where the user selects
				mediaPlayer.setVolume(volumeSlider.getValue() / 100);
			}
		});
		// Updates the time slider as the video progresses
		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {

			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
				timeSlider.setValue(newValue.toSeconds());
			}

		});
		// Listens for mouse clicks on the time slider
		timeSlider.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// Forwards the video to the selected time stamp
				mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
			}	

		});
		// Listens for mouse drags on the time slider
		timeSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// Forwards the video to the selected time stamp
				mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
			}	

		});
		// Height of the time slider
		timeSlider.setPrefHeight(50);
	}
	/**
	 * Plays the video.
	 * @param event play event
	 */
	public void play(ActionEvent event) {
		mediaPlayer.play();
		mediaPlayer.setRate(1);
	}
	/**
	 * pause the video.
	 * @param event pause event
	 */
	public void pause(ActionEvent event) {
		mediaPlayer.pause();
	}
	/**
	 * Fast-forwards the video.
	 * @param event fast-forward event
	 */
	public void fastForward(ActionEvent event) {
		// Sets a higher video play rate
		mediaPlayer.setRate(2);
	}
	/**
	 * Slows down the video.
	 * @param event slow down event
	 */
	public void slowDown(ActionEvent event) {
		// Sets a slower video play rate
		mediaPlayer.setRate(.5);
	}
	/**
	 * Stop the video.
	 * @param event stop event
	 */
	public void stop(ActionEvent event) {
		mediaPlayer.seek(mediaPlayer.getTotalDuration());
		mediaPlayer.stop();
	}
}
