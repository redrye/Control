import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import com.sun.org.apache.xerces.internal.parsers.SAXParser;


public class Control extends JFrame{
 	JFrame frame;
    JButton nextButton = new JButton ("Next Screen");
    ApplicationPanel a = new ApplicationPanel();
    PlacesPanel p = new PlacesPanel();
    ShellPanel sh = new ShellPanel();
    SystemPanel sys = new SystemPanel();
    public void setup() {
        frame = new JFrame();
		frame.setLayout(new GridLayout(1, 5));
        frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(a);
        frame.add(p);
        frame.add(sh);
        frame.add(sys);
        frame.pack();
    }
    public class ApplicationPanel extends JPanel { {
   	    XMLComponent xmlComponent = new XMLComponent();
   	    this.setBorder(BorderFactory.createTitledBorder("Applications"));
   	    this.add(xmlComponent.build("applications.xml"));
    } }
    public class PlacesPanel extends JPanel {{
   	    XMLComponent xmlComponent = new XMLComponent();
    	this.setBorder(BorderFactory.createTitledBorder("Places"));
    	this.add(xmlComponent.build("places.xml"));
    }}
    public class ShellPanel extends JPanel {{
   	    XMLComponent xmlComponent = new XMLComponent();
    	this.setBorder(BorderFactory.createTitledBorder("Shells"));
    	this.add(xmlComponent.build("shells.xml"));
    }}
    public class SystemPanel extends JPanel {{
   	    XMLComponent xmlComponent = new XMLComponent();    	
    	this.setBorder(BorderFactory.createTitledBorder("System"));
    	this.add(xmlComponent.build("system.xml"));
    }}
    public static void main (String[] args) {
        Control Control = new Control();
        Control.setup();
    }
}


class XMLComponent extends DefaultHandler {
  private boolean containerActive = false;
  private JPanel primaryContainer = new JPanel();
  private SAXParser parser = new SAXParser();
  public XMLComponent() {
    super();
  }
  public JComponent build(String xmlDocument) {
    parser.setContentHandler(this);
    try {
      parser.parse(new InputSource(new FileInputStream(xmlDocument)));
    	} catch (Exception ex) {
      System.out.println(ex);
    }
    BoxLayout layout = new BoxLayout(primaryContainer, BoxLayout.Y_AXIS);
    primaryContainer.setLayout(layout);
    return primaryContainer;
  }
  public void startElement(String namespaceURI, String name, String qName, Attributes atts) {
	  if (name.equals("button")) {
		  Dimension d = primaryContainer.getMaximumSize();
		  JButton ctlbutton = new JButton(atts.getValue("name"));
		  ctlbutton.setActionCommand(atts.getValue("command"));
		  ctlbutton.addActionListener(new buttonListener());
		  ctlbutton.setMaximumSize(d);
		  primaryContainer.add(ctlbutton);

    }
  }
  	
  public class buttonListener implements ActionListener {
	    public void actionPerformed(ActionEvent ae) {
	    	String action = ae.getActionCommand();
	    	String[] command = new String[] {action};
	    	Runtime runtime = Runtime.getRuntime();
	    	try {
	    		Process process = runtime.exec(command);
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    		}
	    	}
	    	}
  }
