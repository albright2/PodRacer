package myGameEngine;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class DisplaySettingsDialog extends JDialog implements ActionListener
{
 	private boolean useFullScreen = true;				//the current user FSEM selection
	private DisplayMode selectedDisplayMode = null;		//the current user DisplayMode selection
	private GraphicsDevice device ;						//the current default graphics device

  	private JRadioButton windowedModeRadioButton;
  	private JRadioButton fullScreenModeRadioButton;
  	private JRadioButton truck;
  	private JRadioButton car;
  	private JComboBox displayModeComboBox ;
  	private JLabel currentResolutionLabel;
  	boolean mode = false;
  	boolean model = true;

	public DisplaySettingsDialog (GraphicsDevice theDevice)
	{
		setTitle("Options menu");
		
		//initial size of menu
		setSize( 1200, 620 );
		//location of menu starts upper left corner
		setLocation (0,0);
		setResizable( true );

		device = theDevice ;

		doMyLayout();

    	// make the dialog modal, so that 'show' will block until dialog is dismissed
    	setModal( true );
	}

	private void doMyLayout()
	{
		setLayout (new BorderLayout());

		
		//add a bottom (South) panel containing control buttons (Play Single Player, MultiPlayerCancel,cancel)
		JPanel buttonPanel = new JPanel();
			JButton newButton = new JButton("Play Single Player");
			newButton.setActionCommand( "Play Single Player" );
			newButton.addActionListener(this);
			buttonPanel.add(newButton);
			

			newButton = new JButton("Play MultiPlayer");
			newButton.setActionCommand( "Play MultiPlayer" );
			newButton.addActionListener(this);
			buttonPanel.add(newButton);
			
		

			newButton = new JButton("Cancel");
			newButton.setActionCommand( "Cancel" );
			newButton.addActionListener(this);
			buttonPanel.add(newButton);
			
		this.add(buttonPanel, "South");
/////////////////////////////////////////////////////////////////////////////////////////////
		//add a left panel with a radio button group for selecting full or windowed screen mode
		JPanel screenPanel = new JPanel ();
			screenPanel.setBorder (new TitledBorder("Screen Mode:  ") );
			
			
			Box screenButtonBox = new Box(BoxLayout.Y_AXIS);

				windowedModeRadioButton = new JRadioButton("Windowed",true);
				fullScreenModeRadioButton = new JRadioButton ("Full Screen",false);

				ButtonGroup screenModeButtonGroup = new ButtonGroup();
				screenModeButtonGroup.add (windowedModeRadioButton);
				screenModeButtonGroup.add (fullScreenModeRadioButton);

				screenButtonBox.add (windowedModeRadioButton);
				screenButtonBox.add (fullScreenModeRadioButton);
				
				
				
				
				
				/////////////////////////////////////////////////////////////////////////////////
				//top panel
				
				Box screenButtonBox2 = new Box(BoxLayout.Y_AXIS);


				truck = new JRadioButton("Truck",true);
				car = new JRadioButton ("Car",false);

				ButtonGroup screenModeButtonGroup2 = new ButtonGroup();
				screenModeButtonGroup2.add (car);
				screenModeButtonGroup2.add (truck);

				screenButtonBox2.add (car);
				screenButtonBox2.add (truck);

				
				
				
				
				
				
				
				
				
				
				
				
				//add a top panel showing the currently active screen resolution
				JPanel topPanel = new JPanel();
					currentResolutionLabel = new JLabel("Current Resolution: unknown");
					topPanel.add(currentResolutionLabel);
					
					
				
					
					
					
				this.add(topPanel, "North");
		/////////////////////////////////////////////////////////////////////////////////////////////
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				

			screenPanel.add(screenButtonBox);
			topPanel.add(screenButtonBox2);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		this.add(screenPanel, "West");

		//add a rightside panel containing a drop-down list of available display modes
		JPanel displayModesPanel = new JPanel (new BorderLayout());
			displayModesPanel.setBorder (new TitledBorder("New Resolution:  ") );

			//get the display modes supported by the device
			DisplayMode [] modes = device.getDisplayModes();

			//build a set of strings each describing one mode
			Vector displayModeList = getDisplayModeList(modes);

			//add a combo box containing the available modes
			displayModeComboBox = new JComboBox(displayModeList);
			displayModesPanel.add (displayModeComboBox);

    	this.add(displayModesPanel, "East");

	}

	private Vector<String> getDisplayModeList(DisplayMode [] modes)
	{
		Vector<String> displayList = new Vector<String>();

		for (DisplayMode m : modes)
		{
			int width = m.getWidth();
			int height = m.getHeight();
			int depth = m.getBitDepth();
			int rate = m.getRefreshRate();

		
			String s = "" + width + "x" + height + ", " + depth + "-bit color, "
							+ rate + "-Hz refresh rate" ;

			displayList.addElement (s);
		}

		return displayList ;
	}

	
	public void showIt()
	{
		//update the radiobuttons to match the current state
		if (useFullScreen)
		{
			fullScreenModeRadioButton.doClick();
		}
		else
		{
			windowedModeRadioButton.doClick();
		}

		
		
		
		
		//update the label showing the current device resolution
		DisplayMode curMode = device.getDisplayMode();
		int width = curMode.getWidth();
		int height = curMode.getHeight();
		int depth = curMode.getBitDepth();
		int refreshRate = curMode.getRefreshRate();

		currentResolutionLabel.setText("Current Resolution:  " + width + "x" + height
										+ ", " + depth + "-bits, " + refreshRate + "-Hz " );

		//set the combo box so the current actual resolution is selected by default
		displayModeComboBox.setSelectedIndex(getComboBoxIndexOf(curMode));

		this.setVisible(true);
	}

	public DisplayMode getSelectedDisplayMode()
	{
		DisplayMode copy = null;

		if (selectedDisplayMode != null)
		{
			copy = new DisplayMode (selectedDisplayMode.getWidth(),
									selectedDisplayMode.getHeight(),
									selectedDisplayMode.getBitDepth(),
									selectedDisplayMode.getRefreshRate());
		}
		return copy;
	}

	public boolean isFullScreenModeSelected()
	{
		return useFullScreen ;
	}

	private int getComboBoxIndexOf(DisplayMode curMode)
	{
		int numItems = displayModeComboBox.getItemCount();

		boolean found = false;
		int nextItemNum = 0;
		String itemString ;

		while (!found && nextItemNum < numItems)
		{
			itemString = (String) displayModeComboBox.getItemAt(nextItemNum);

			if (match(itemString, curMode))
			{
				found = true;
			}
			else
			{
				nextItemNum ++;
			}
		}

		if (found)
		{
			return nextItemNum;
		}
		else
		{
			return 0 ;
		}
	}

	private boolean match (String modeString, DisplayMode dispMode)
	{
		if (toDisplayMode(modeString).equals(dispMode))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	private DisplayMode toDisplayMode(String modeString)
	{
		//split the given string into its comma-separated composite parts
		String [] parts = modeString.split(",");
		String widthAndHeightString = parts[0].trim();
		String depthString = parts[1].trim();
		String refreshRateString = parts[2].trim();

		//split the width-and-height string into its 'x'-separated parts
		String [] dimensions = widthAndHeightString.split("x");
		String widthString = dimensions[0].trim();
		String heightString = dimensions[1].trim();

		//split the depth and refresh-rate strings at the '-' which trails
		// the numeric value
		String [] depthParts = depthString.split("-");
		String [] rateParts = refreshRateString.split("-");

		//extract the numeric components of the depth and refresh rate strings
		depthString = depthParts[0];
		refreshRateString = rateParts[0];

		//create and return a DisplayMode object describing the selected mode
		DisplayMode mode = new DisplayMode (Integer.valueOf(widthString),
												Integer.valueOf(heightString),
												Integer.valueOf(depthString),
												Integer.valueOf(refreshRateString) );
		return mode ;
	}


	public void actionPerformed(ActionEvent e)
  	{
			if (e.getActionCommand() == "Play Single Player")
			{
				
				
				
				mode=true;
				
				
				
				if(truck.isSelected()) {model=false;}
				
				
						//fetch the string defining the mode selected by the user
				String selectedModeString = (String) displayModeComboBox.getSelectedItem();

				//create and save a DisplayMode object describing the selected mode
				selectedDisplayMode = toDisplayMode (selectedModeString);

				//update the local flags indicating user-selected states
				useFullScreen = fullScreenModeRadioButton.isSelected();

				//System.out.println ("Play Single Player pressed");
			}

			
			
			
			
			else if (e.getActionCommand() == "Play MultiPlayer")
			{
				mode=false;
				if(truck.isSelected()) {model=false;}

				//fetch the string defining the mode selected by the user
				String selectedModeString = (String) displayModeComboBox.getSelectedItem();

				//create and save a DisplayMode object describing the selected mode
				selectedDisplayMode = toDisplayMode (selectedModeString);

				//update the local flags indicating user-selected states
				useFullScreen = fullScreenModeRadioButton.isSelected();
				
		
			}

			
			
			
			
		
      		
      		

      		setVisible(false);
  	}
	public boolean getPlayerMode() {
		
		//truck is false, car is true
		return mode;
	}
	//return to MyGame whether or not the player is playing alone or needs to connect to server

	public boolean getModel() {
		return model;
	}
}

