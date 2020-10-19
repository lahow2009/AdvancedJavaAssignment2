import java.io.File;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

public class controller {

	private view view;
	private model model;
	private File selectedFile = null;
	public controller(view view, model model) {
		this.view = view;
		this.model = model;
		this.view.chooseSourceListener(e -> {
			
			view.setDatabaseContentsInvisible();
			FileChooser  file = new FileChooser();
			file.setTitle("Open File");	
			selectedFile = file.showOpenDialog(main.stage);
			
			this.view.setlblSource(selectedFile.getName());
			
			if(!this.view.gettxtR().isEmpty())
				this.view.settxtR("");
		});
		
		this.view.domParserListener(e -> {
			
			if(selectedFile.getName().contains(".xml"))
			{
				String xml = this.model.parsingXmlContents(selectedFile);
				this.view.settxtR(xml);
			}
			else
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Parsing file is not in xml type!!");
				alert.show();
			}
		});
		
		this.view.proceedToDBListener(e -> {
			view.setDatabaseContentsVisible();
			
		});
			
		this.view.searchKeyWordListener(e -> {
			this.view.settxtR3("");
			String movie = this.model.searchMovie(this.view.gettxtR2().trim());
			this.view.settxtR3(movie);
			
		});
		
		this.view.proceedToVisualizationListener(e -> {
			view.setVisualContentsVisible();
			
		});
		
		this.view.barChartVisualListener(e -> {
			this.model.barChartVisualization(this.view.isRadioButtonSelected());
			
		});
		
		this.view.pieChartVisualListener(e -> {
			this.model.pieChartVisualization(this.view.isRadioButtonSelected());
			
		});
	}
}
