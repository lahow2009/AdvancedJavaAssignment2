import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserHandler extends DefaultHandler {
				
				boolean btitle = false;
				boolean byear = false;
				boolean brating = false;
				boolean bdirector = false;
				boolean bname = false;
				boolean bitem = false;
				public static String output = "";
				
				//this method is called every time the parser gets an open tag 
				public void startElement(String uri, String localName, String qName, Attributes attribute ) throws SAXException {
					
					if (qName.equalsIgnoreCase("title")) {					
						btitle = true;
					}
					if (qName.equalsIgnoreCase("year")) {
						byear = true;
					}
					if (qName.equalsIgnoreCase("rating")) {
						brating = true;
					}
					if (qName.equalsIgnoreCase("director")) {
						bdirector = true;
					}
					if (qName.equalsIgnoreCase("name")) {
						bname = true;
					}
					if (qName.equalsIgnoreCase("item")) {
						bitem = true;
					}
				}
				
				//this method is called every time the parser gets a closing tag
				public void endElement(String uri, String localName, String qName) throws SAXException {
					
				}
				
				//print data stored between '<' and '>'
				public void characters(char ch[], int start, int length) throws SAXException {
					if(btitle) {
						//System.out.println("title : " + new String(ch, start, length));
						output = output + "\ntitle : " + new String(ch, start, length) + "\n";
						btitle = false;
					}
					if(byear) {
						//System.out.println("year : " + new String(ch, start, length));
						output = output + "year : " + new String(ch, start, length) + "\n";
						byear = false;
					}
					if(brating) {
						//System.out.println("rating : " + new String(ch, start, length));
						output = output + "rating : " + new String(ch, start, length) + "\n";
						brating = false;
					}
					if(bname) {
						if (bdirector) { 
						//System.out.println("director name : " + new String(ch, start, length));
							output = output + "director name : " + new String(ch, start, length) + "\n";
						bdirector = false;
						}
						bname = false;
					}				
					if(bitem) {
						//System.out.println("genre item : " + new String(ch, start, length));
						output = output + "genre : " + new String(ch, start, length) + "\n";
						bitem = false;
					}
				}
			};
