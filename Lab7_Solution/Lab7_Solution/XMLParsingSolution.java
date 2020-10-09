
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class XMLParsingSolution extends Application {

	File selectedFile = null;
	
	@Override
	public void start(Stage stage) throws Exception {
	
		Button btnSource = new Button("Choose Source");
		Label lblSource = new Label("");
		HBox hboxSource = new HBox(btnSource,lblSource );
		hboxSource.setSpacing(10);
		TextArea txtR = new TextArea();
				
		btnSource.setOnAction(e -> { 
		
		FileChooser  file = new FileChooser();
		file.setTitle("Open File");		
		selectedFile = file.showOpenDialog(stage); 
		lblSource.setText(selectedFile.getName()); 
		
		});
		
		Button btnDOMParser = new Button("DOM Parsing");
		
		btnDOMParser.setOnAction(e -> { 
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder docBuilder = null;
			try {
				
				docBuilder = factory.newDocumentBuilder();
				
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				Document doc = docBuilder.parse(selectedFile.getPath());
				
				doc.getDocumentElement().normalize();
				
				//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				txtR.setText("");
				txtR.setText(txtR.getText() + "Root element :" + doc.getDocumentElement().getNodeName() + "\n" );
		        NodeList nList = doc.getElementsByTagName("movie");
		        //System.out.println("----------------------------");
		        txtR.setText(txtR.getText() + "----------------------------------" + "\n");
		        
		        for (int temp = 0; temp < nList.getLength(); temp++) {
		            Node nNode = nList.item(temp);
		            //System.out.println("\nCurrent Element :" + nNode.getNodeName());
		            txtR.setText(txtR.getText() + "\nCurrent Element :" + nNode.getNodeName() + "\n");
		            
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		                              
		               
		               /*System.out.println("Title : " 
		                  + eElement
		                  .getElementsByTagName("title")
		                  .item(0)
		                  .getTextContent());*/
		               
		               txtR.setText(txtR.getText() + "Title : " + eElement.getElementsByTagName("title").item(0).getTextContent() + "\n");
		               
		               /*System.out.println("Year : " 
		                  + eElement
		                  .getElementsByTagName("year")
		                  .item(0)
		                  .getTextContent());*/
		               
		               txtR.setText(txtR.getText() + "Year : " + eElement.getElementsByTagName("year").item(0).getTextContent() + "\n");
		               
		               /*System.out.println("Rating : " 
		                  + eElement
		                  .getElementsByTagName("rating")
		                  .item(0)
		                  .getTextContent());*/
		               
		               txtR.setText(txtR.getText() + "Rating : " + eElement.getElementsByTagName("rating").item(0).getTextContent() + "\n");
		               
		              
		            	NodeList directorsList = eElement.getElementsByTagName("director");
		            	for (int count = 0; count < directorsList.getLength(); count++) {
		            		   
		            	    Node nodeDirector = directorsList.item(count); 
		            	    if (nodeDirector.getNodeType() == nodeDirector.ELEMENT_NODE) {
		                         Element director = (Element) nodeDirector;
   	                             /*System.out.print( "director name : ");
		                         System.out.println(director.getElementsByTagName("name").item(0).getTextContent());*/
		                         txtR.setText(txtR.getText() + "Director Name : " + director.getElementsByTagName("name").item(0).getTextContent() + "\n");
		            	    }   
		            	}
				           //System.out.println("Director Name :" + eElement1.getElementsByTagName("name").item(0).getTextContent());
		               
				       
		               NodeList genres = eElement.getElementsByTagName("item");
		               
		            
		            	   for (int count = 0; count < genres.getLength(); count++) {
		            		   
		            		   Node nodeItem = genres.item(count); 
		            		   if (nodeItem.getNodeType() == nodeItem.ELEMENT_NODE) {
		                           Element item = (Element) nodeItem;
		                           /*System.out.print( "genre : ");
		                           System.out.println(item.getTextContent());*/
		                           txtR.setText(txtR.getText() + "Genre : " + item.getTextContent() + "\n");
		                           
		            		   }   
		            	   }
				         
		            }
		         }
				
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			});
		
		Button btnSAXParser = new Button("SAX Parsing");
		
		btnSAXParser.setOnAction(e -> {
			//obtain and configure a SAX based Parser 
			SAXParserFactory factory = SAXParserFactory.newInstance();
			
			//obtain object for SAX Parser
			SAXParser saxparser = null;
			try {
				saxparser = factory.newSAXParser();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
					saxparser.parse(selectedFile.getPath(), new UserHandler());
					txtR.setText("");
					txtR.setText(UserHandler.output);  
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		});
		Button btnSearch = new Button("Load Text");
		btnSearch.setOnAction(e -> {
			BufferedReader reader = null;
			try
			{
				txtR.setText("");
				reader = new BufferedReader(new FileReader(selectedFile.getPath()));
				String line = reader.readLine();
				while(line != null) {
					txtR.setText(txtR.getText() + line + "\n");
					line = reader.readLine();
				}
				reader.close();
			}catch(Exception er)
			{
				er.printStackTrace();				
			}
			finally {
				
			}
		});
		HBox hboxParsing = new HBox(btnDOMParser,btnSAXParser,btnSearch );
		hboxParsing.setSpacing(10);
		
		
		
		GridPane grdPane = new GridPane();
		
		grdPane.setPadding(new Insets(10,10,10,10));
		grdPane.setHgap(10);
		grdPane.setVgap(10);
		grdPane.setAlignment(Pos.CENTER);
		
		grdPane.addRow(0,hboxSource);
		grdPane.addRow(1,hboxParsing);
		grdPane.addRow(2,txtR);
		
		Scene scene = new Scene(grdPane,400,400);
		stage.setScene(scene);
		stage.setTitle("IMDB XML File Parsing System");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
