import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class view {

	
	private GridPane defaultView, defaultView1, defaultView2;
	private Button btnSource, btnDOMParser, proceedToDataBasePage;
	private Label lblSource;
	private TextArea txtR;
	private HBox hboxSource, hboxParsing;
	
	
	Stage stage1 = new Stage();
	private Button btnDatabase, proceedToVisualization;
	private TextArea txtR2, txtR3;
	private HBox hboxDatabase;
	
	Stage stage2 = new Stage();
	private ToggleGroup buttonGroup;	
	private RadioButton t3, t5, t8, t10;	//Radio Buttons for keyword frequency 
	private Button btnPie;
	private Button btnGraph;
	private HBox hBoxKey, visBox;
	
	public Parent asParent() {
		return defaultView;
	}
	
	public view()
	{		
		mainPane();
		sourcePage();
		dataBasePage();
		visualization();
	}

	private void visualization() {
		
		defaultView2 = new GridPane();
		defaultView2.setPadding(new Insets(10,10,10,10));
		defaultView2.setHgap(10);
		defaultView2.setVgap(10);
		
		buttonGroup = new ToggleGroup();
		
		t3 = new RadioButton("Top 3");
		t3.setToggleGroup(buttonGroup);
		t3.setSelected(true);
		
		t5 = new RadioButton("Top 5");
		t5.setToggleGroup(buttonGroup);
		
		t8 = new RadioButton("Top 8");
		t8.setToggleGroup(buttonGroup);
		
		t10 = new RadioButton("Top 10");
		t10.setToggleGroup(buttonGroup);
		
		btnPie = new Button("Show Pie");
		btnGraph = new Button("Show graph");
		
		hBoxKey = new HBox(t3,t5,t8,t10);
		hBoxKey.setSpacing(10);
		
		visBox = new HBox(btnPie,btnGraph);
		visBox.setSpacing(10);
		
		defaultView2.addRow(4,hBoxKey);
		defaultView2.addRow(5,visBox);
		
		Scene sc1 = new Scene(defaultView2,600,600);
		
		stage2.setScene(sc1);
		stage2.setTitle("Visualization Page");
		stage2.show();
	}

	private void dataBasePage() {
		
		defaultView1 = new GridPane();
		defaultView1.setPadding(new Insets(10,10,10,10));
		defaultView1.setHgap(10);
		defaultView1.setVgap(10);
		btnDatabase = new Button("Search Movies");
		
		txtR2 = new TextArea();
		txtR2.setMaxSize(270, 5);
		txtR2.setPromptText("Separate keywords with spaces");
		txtR3 = new TextArea();
		proceedToVisualization = new Button("Proceed");
		proceedToVisualization.setAlignment(Pos.CENTER);
		
		hboxDatabase = new HBox(txtR2,btnDatabase);
		hboxDatabase.setSpacing(10);
		
		defaultView1.addRow(0, hboxDatabase);
		defaultView1.addRow(1, txtR3);
		defaultView1.addRow(2, proceedToVisualization);
		
		Scene sc = new Scene(defaultView1,600,600);
		
		stage1.setScene(sc);
		stage1.setTitle("Database Page");
		stage1.show();
	}

	private void mainPane() {
		
		defaultView = new GridPane();
		defaultView.setPadding(new Insets(10,10,10,10));
		defaultView.setHgap(10);
		defaultView.setVgap(10);	
	}

	private void sourcePage()
	{
		lblSource = new Label("");
		txtR = new TextArea();
		btnSource = new Button("Choose source");
		btnDOMParser = new Button("DOM Parser");
		proceedToDataBasePage = new Button("Proceed");
		proceedToDataBasePage.setAlignment(Pos.CENTER);;
		hboxSource = new HBox(btnSource,lblSource);

		hboxSource.setSpacing(10);
		
		hboxParsing = new HBox(btnDOMParser);

		hboxParsing.setSpacing(10);
		
		defaultView.addRow(0,hboxSource);
		defaultView.addRow(1,hboxParsing);
		defaultView.addRow(3, txtR);
		defaultView.addRow(4, proceedToDataBasePage);
	}
	
	public void setlblSource(String labelName) {
		lblSource.setText(labelName);
	}
	
	public void settxtR(String labelName) {
		txtR.setText(labelName);
	}
	
	public String gettxtR() {
		return txtR.getText();
	}
	
	public void chooseSourceListener(EventHandler<ActionEvent> listener) {
		btnSource.setOnAction(listener);
	}
	
	public void domParserListener(EventHandler<ActionEvent> listener) {
		btnDOMParser.setOnAction(listener);
	}
	public void proceedToDBListener(EventHandler<ActionEvent> listener) {
		proceedToDataBasePage.setOnAction(listener);
	}
	
	
	public void setDatabaseContentsInvisible(){	
		stage1.hide();
		stage2.hide();
	}
	
	//DataBase Page
	public void setDatabaseContentsVisible(){			
		stage1.show();
	}
	public String gettxtR2() {
		return txtR2.getText();
	}
	
	public void settxtR3(String labelName) {
		txtR3.setText(labelName);
	}
	
	public void searchKeyWordListener(EventHandler<ActionEvent> listener) {
		btnDatabase.setOnAction(listener);
	}
	
	public void proceedToVisualizationListener(EventHandler<ActionEvent> listener) {
		proceedToVisualization.setOnAction(listener);
	}
	
	// Visualization Page
	public void setVisualContentsVisible(){			
		stage2.show();
	}
	
	public int isRadioButtonSelected()
	{
		int keywordCount = 0;
		
		if(t3.isSelected())
			keywordCount = 3;
		else if(t5.isSelected())
			keywordCount = 5;
		else if(t8.isSelected())
			keywordCount = 8;
		else if(t10.isSelected())
			keywordCount = 10;
		
		return keywordCount;
		
	}
	
	public void barChartVisualListener(EventHandler<ActionEvent> listener) {
		btnGraph.setOnAction(listener);
	}
	
	public void pieChartVisualListener(EventHandler<ActionEvent> listener) {
		btnPie.setOnAction(listener);
	}
}
