import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {

	public static Stage stage;
	public static void main(String[] args) {		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		view view = new view();
		model model = new model();
		controller controller = new controller(view, model);
		
		Scene scene = new Scene(view.asParent(), 600, 600);
		
		stage.setScene(scene);
		stage.setTitle("IMDB XML File Parsing System");
		stage.show();
		
	}

}
