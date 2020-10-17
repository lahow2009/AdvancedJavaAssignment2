
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JTextArea;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class XMLParsingSolution extends Application {
	
	File selectedFile = null;
	
	// to store result
	Hashtable<String, Integer> keywordFrequency = new Hashtable<String, Integer>();
	Hashtable<String, ArrayList<String>> movieKeywords = new Hashtable<String, ArrayList<String>>();
	ArrayList<String> movieTitles = new ArrayList<String>();
	ArrayList<String> allKeyWords = new ArrayList<String>();
	HashSet<String> uniqueKeyWords = new HashSet<String>();
	Map<String, Integer> mapSortedByValues = new LinkedHashMap<String, Integer>();
	@Override
	public void start(Stage stage) throws Exception {
	
		Button btnSource = new Button("Choose Source");
		Label lblSource = new Label("");


		HBox hboxSource = new HBox(btnSource,lblSource);

		hboxSource.setSpacing(10);
		TextArea txtR = new TextArea();
		Button btnDatabase = new Button("Search Movies");
		TextArea txtR2 = new TextArea();
		txtR2.setMaxSize(270, 5);
		txtR2.setPromptText("Separate keywords with spaces");
		HBox hboxDatabase = new HBox(txtR2,btnDatabase);
		hboxDatabase.setVisible(false);
		hboxDatabase.setSpacing(10);
		
		// creating Radio Button Group 
		final ToggleGroup buttonGroup = new ToggleGroup();
		
		//Radio Buttons for keyword frequency 
		RadioButton t3 = new RadioButton("Top 3");
		t3.setToggleGroup(buttonGroup);
		t3.setSelected(true);
		RadioButton t5 = new RadioButton("Top 5");
		t5.setToggleGroup(buttonGroup);
		RadioButton t8 = new RadioButton("Top 8");
		t8.setToggleGroup(buttonGroup);
		RadioButton t10 = new RadioButton("Top 10");
		t10.setToggleGroup(buttonGroup);
		
		// Button for pie chart
		Button btnPie = new Button("Show Pie");
		//Button for bar graph
		Button btnGraph = new Button("Show graph");
		
		HBox hBoxKey = new HBox(t3,t5,t8,t10);
		hBoxKey.setSpacing(10);
		
		HBox visBox = new HBox(btnPie,btnGraph);
		visBox.setSpacing(10);
				
		btnSource.setOnAction(e -> { 
		
		FileChooser  file = new FileChooser();
		file.setTitle("Open File");		
		selectedFile = file.showOpenDialog(stage); 
		lblSource.setText(selectedFile.getName()); 
		hboxDatabase.setVisible(true);
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
		               System.out.println("Title : " 
		                  + eElement
		                  .getElementsByTagName("title")
		                  .item(0)
		                  .getTextContent());
		               movieTitles.add(eElement.getElementsByTagName("title").item(0).getTextContent());
		               movieKeywords.put(eElement.getElementsByTagName("title").item(0).getTextContent(), new ArrayList<String>());
		               txtR.setText(txtR.getText() + "Title : " + eElement.getElementsByTagName("title").item(0).getTextContent() + "\n");
		               
		              
		               txtR.setText(txtR.getText() + "Year : " + eElement.getElementsByTagName("year").item(0).getTextContent() + "\n");
		               
		               
		               txtR.setText(txtR.getText() + "Rating : " + eElement.getElementsByTagName("rating").item(0).getTextContent() + "\n");
		               
		              
		            	NodeList directorsList = eElement.getElementsByTagName("director");
		            	for (int count = 0; count < directorsList.getLength(); count++) {
		            		   
		            	    Node nodeDirector = directorsList.item(count); 
		            	    if (nodeDirector.getNodeType() == nodeDirector.ELEMENT_NODE) {
		                         Element director = (Element) nodeDirector;
   	                             
		                         txtR.setText(txtR.getText() + "Director Name : " + director.getElementsByTagName("name").item(0).getTextContent() + "\n");
		            	    }   
		            	}
				           
		               
				       
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
		            	   

							// checking the occurence of keywords 	   
				            NodeList keyword = eElement.getElementsByTagName("kw");
				            for (int count = 0; count<keyword.getLength(); count++) {
				            	Node nodeItem = keyword.item(count);
				            	if (nodeItem.getNodeType() == nodeItem.ELEMENT_NODE) {
			                           Element item = (Element) nodeItem;
			                           System.out.print( "Keywords : ");
			                           System.out.println(item.getTextContent());
			                           allKeyWords.add(item.getTextContent().trim());
			                           movieKeywords.get(eElement.getElementsByTagName("title").item(0).getTextContent()).add(item.getTextContent().trim());
			            		   }   
				            }	   
		           
				         for(int i = 0;i<allKeyWords.size();i++)
				        	 uniqueKeyWords.add(allKeyWords.get(i));
				         
				         for (String stock : uniqueKeyWords) 
				         {
				        	 int count = 0;
				        	 System.out.println(stock);
				        	 for(int i = 0;i<allKeyWords.size();i++)
				        	 {
				        		 if(allKeyWords.get(i).equals(stock))
				        			 count++;
				        	 }
				        	 keywordFrequency.put(stock, count); 
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
		
		Button btnSearch = new Button("Load Text");
		btnSearch.setOnAction(e -> {
			BufferedReader reader = null;
			try
			{
				txtR.setText("");
				txtR.setText("Movie list : "+movieKeywords);
			}catch(Exception er)
			{
				er.printStackTrace();				
			}
			finally {
				
			}
		});
		
		
		
		btnDatabase.setOnAction(e -> {
			ArrayList<String> searchTerms = new ArrayList<String>();
			
			String line = txtR2.getText().trim();
			System.out.println(line);
			
			BufferedReader reader = null;
			try
			{
				txtR.setText("");
				for(int i = 0;i < movieTitles.size();i++)
				{
					if(movieTitles.get(i).toLowerCase().contains(line.toLowerCase()))
						txtR.setText(txtR.getText() + movieTitles.get(i) + "\n");
					
					
				}
				String moviesList = txtR.getText().trim();
				if(moviesList.contains("\n"))
				{
					Hashtable<String,Integer> resultMovieKeywords = new Hashtable<String,Integer>();
					String[] movies = moviesList.split("\n");
					for(int i = 0;i < movies.length; i++)
					{
						for(int j= 0;j < movieKeywords.get(movies[i].trim()).size();j++)
						{
							int frequency = keywordFrequency.get(movieKeywords.get(movies[i].trim()).get(j));
							resultMovieKeywords.put(movieKeywords.get(movies[i].trim()).get(j), frequency);
						}
					}
					//get all the entries from the hashtable and put it in a List
					List<Map.Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(resultMovieKeywords.entrySet());
					 
					//sort the entries based on the value by custom Comparator
					Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
					 
					  public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
					      return entry2.getValue().compareTo( entry1.getValue() );
					  }
					  
					});
					 
					mapSortedByValues = new LinkedHashMap<String, Integer>();
					 
					//put all sorted entries in LinkedHashMap
					for( Map.Entry<String, Integer> entry : list  ){
					    mapSortedByValues.put(entry.getKey(), entry.getValue());
					}
					 
					System.out.println("Map sorted by values: " + mapSortedByValues);
					txtR.setText(txtR.getText()+ "\n Associated keywords \n"+mapSortedByValues);
				}
				else
				{
					Hashtable<String,Integer> resultMovieKeywords = new Hashtable<String,Integer>();
					for(int i = 0; i < movieKeywords.get(txtR.getText().trim()).size();i++)
					{
						int frequency = keywordFrequency.get(movieKeywords.get(txtR.getText().trim()).get(i));
						resultMovieKeywords.put(movieKeywords.get(txtR.getText().trim()).get(i), frequency); 
						//get all the entries from the hashtable and put it in a List
						List<Map.Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(resultMovieKeywords.entrySet());
						 
						//sort the entries based on the value by custom Comparator
						Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
						 
						  public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
						      return entry2.getValue().compareTo( entry1.getValue() );
						  }
						  
						});
						 
						mapSortedByValues = new LinkedHashMap<String, Integer>();
						 
						//put all sorted entries in LinkedHashMap
						for( Map.Entry<String, Integer> entry : list  ){
						    mapSortedByValues.put(entry.getKey(), entry.getValue());
						}
						 
						System.out.println("Map sorted by values: " + mapSortedByValues);
 					}
					txtR.setText(txtR.getText()+ "\n Associated keywords \n"+mapSortedByValues);
				}
			}catch(Exception er)
			{
				er.printStackTrace();				
			}
			
		});
		
		btnGraph.setOnAction( e-> {			
			try {
					int keywordCount = 0;
					CategoryAxis xAxis = new CategoryAxis();
					NumberAxis yAxis = new NumberAxis();
					xAxis.setLabel("Keywords");
					yAxis.setLabel("Frequency");
			
					BarChart<String,Integer> bar = new BarChart(xAxis,yAxis);
					bar.setTitle("Keywords Frequency Bar Chart");
					
					if(t3.isSelected())
						keywordCount = 3;
					else if(t5.isSelected())
						keywordCount = 5;
					else if(t8.isSelected())
						keywordCount = 8;
					else if(t10.isSelected())
						keywordCount = 10;
					
					XYChart.Series<String, Integer> series = new XYChart.Series<>();
					int count = 0;
					for (String i : mapSortedByValues.keySet()) 
					{
						count++;
						series.getData().add(new XYChart.Data(i,mapSortedByValues.get(i)));
						if(count == keywordCount)
							break;
					}
					
					
					series.setName("Keywords Frequency");
			
					bar.getData().add(series);
					Group root = new Group(bar);
					//root.getChildren().add(bar);
					Scene sc = new Scene(root,500,400);
					Stage stage1 = new Stage();
					stage1.setTitle("Bar Chart");
					stage1.setScene(sc);
					stage1.show();
			
				}catch(Exception e1) {
					e1.printStackTrace();
			}
			
		});
		
		btnPie.setOnAction(e -> {
			
			try {
					int keywordCount = 0;
					ObservableList<Data> list = FXCollections.observableArrayList();
					
					if(t3.isSelected())
						keywordCount = 3;
					else if(t5.isSelected())
						keywordCount = 5;
					else if(t8.isSelected())
						keywordCount = 8;
					else if(t10.isSelected())
						keywordCount = 10;
					
					
					int count = 0;
					for (String i : mapSortedByValues.keySet()) 
					{
						count++;
						list.add(new PieChart.Data(i,mapSortedByValues.get(i)));
						if(count == keywordCount)
							break;
					}
					
					PieChart pieChart = new PieChart();
					pieChart.setData(list);
					pieChart.setLegendSide(Side.LEFT);
					pieChart.setTitle("Keywords Frequency Pie Chart");
					pieChart.setClockwise(false);
			
					Group root = new Group();
					root.getChildren().add(pieChart);
					Scene sc = new Scene(root,500,400);
					Stage stage1 = new Stage();
					stage1.setScene(sc);
					stage1.setTitle("Pie Chart");
					stage1.show();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			
		});
		HBox hboxParsing = new HBox(btnDOMParser,btnSearch );
		hboxParsing.setSpacing(10);
		
		
		
		GridPane grdPane = new GridPane();
		
		grdPane.setPadding(new Insets(10,10,10,10));
		grdPane.setHgap(10);
		grdPane.setVgap(10);
		grdPane.setAlignment(Pos.CENTER);
		
		grdPane.addRow(0,hboxSource);
		grdPane.addRow(1,hboxParsing);
		grdPane.addRow(2,hboxDatabase);
		grdPane.addRow(3,txtR);
		grdPane.addRow(4,hBoxKey);
		grdPane.addRow(5,visBox);
		
		Scene scene = new Scene(grdPane,600,600);
		stage.setScene(scene);
		stage.setTitle("IMDB XML File Parsing System");
		stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);

	}

}
