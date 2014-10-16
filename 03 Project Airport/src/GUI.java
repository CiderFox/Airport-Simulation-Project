import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {
	

	private JTextField LandingTimeTextField = new JTextField();
	private JTextField LandArriveProbabilityTextField = new JTextField();
	private JTextField MaxFuelTextField = new JTextField();
	private JTextField DepartTimeTextField = new JTextField();
	private JTextField DepartProbTextField = new JTextField();
	private JTextField SimTimeTextField = new JTextField();
	

	private	JButton submitButton;
	
	private JPanel contentPane;  
	private JPanel inputPane;	

	private JTextArea jtaDisplay;
	

	private JComboBox combo1 = new JComboBox(new Integer[] {1,2});

	
	private Runway runway1; //= new Runway(Integer.parseInt(LandingTimeTextField.getText()), Integer.parseInt(DepartTimeTextField.getText()));
	private Runway runway2; //= new Runway(Integer.parseInt(LandingTimeTextField.getText()), Integer.parseInt(DepartTimeTextField.getText()));

	public GUI() {

		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 200);
		
		//output Pane
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//input Pane
		inputPane = new JPanel();		
		inputPane.setBounds(10, 32, 574, 160);		
		contentPane.add(inputPane);
		inputPane.setLayout(null);
		
		// Default font for the user interface
		Font defaultFont = new Font("Arial", Font.PLAIN, 11);
		
		//inputPane && Landing Aircraft
		JLabel lblLandTime = new JLabel("Landing Time: ");
		lblLandTime.setFont(defaultFont);
		lblLandTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLandTime.setBounds(16, 11, 140, 14);
		inputPane.add(lblLandTime);
		
		JLabel lblArrivalProbability = new JLabel("Arrival Probability: ");
		lblArrivalProbability.setFont(defaultFont);
		lblArrivalProbability.setHorizontalAlignment(SwingConstants.RIGHT);
		lblArrivalProbability.setBounds(16, 36, 140, 14);
		inputPane.add(lblArrivalProbability);
				
		JLabel lblMaxFuelTime = new JLabel("Max Fuel Air Time: ");
		lblMaxFuelTime.setFont(defaultFont);
		lblMaxFuelTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaxFuelTime.setBounds(16, 58, 140, 20);
		inputPane.add(lblMaxFuelTime);
		
		//Take off Aircraft
		JLabel lblDepartTime = new JLabel("Departure Time: ");
		lblDepartTime.setFont(defaultFont);
		lblDepartTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDepartTime.setBounds(16, 140, 140, 14);
		inputPane.add(lblDepartTime);
		
		JLabel lblDepartProb = new JLabel("Departure Probability: ");
		lblDepartProb.setFont(defaultFont);
		lblDepartProb.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDepartProb.setBounds(25, 200, 140, 14);
		contentPane.add(lblDepartProb);
		
		//Other
		JLabel lblTotalSimTime = new JLabel("Total SimulationTime: ");
		lblTotalSimTime.setFont(defaultFont);
		lblTotalSimTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalSimTime.setBounds(25, 260, 140, 14); 
		contentPane.add(lblTotalSimTime);
		
		
		
		//input field for name
		LandingTimeTextField = new JTextField();
		LandingTimeTextField.setFont(defaultFont);
		LandingTimeTextField.setBounds(160, 8, 220, 20);
		inputPane.add(LandingTimeTextField);
		LandingTimeTextField.setColumns(25);
		
		LandArriveProbabilityTextField = new JTextField();
		LandArriveProbabilityTextField.setFont(defaultFont);
		LandArriveProbabilityTextField.setBounds(160, 33, 220, 20);
		inputPane.add(LandArriveProbabilityTextField);
		LandArriveProbabilityTextField.setColumns(25);
				
		// input field for price
		MaxFuelTextField = new JTextField();
		MaxFuelTextField.setFont(defaultFont);
		MaxFuelTextField.setBounds(160, 58, 220, 20);
		inputPane.add(MaxFuelTextField);
		MaxFuelTextField.setColumns(25);
		
		DepartTimeTextField = new JTextField();
		DepartTimeTextField.setFont(defaultFont);
		DepartTimeTextField.setBounds(160, 140, 220, 20); //16, 140, 140, 14
		inputPane.add(DepartTimeTextField);
		DepartTimeTextField.setColumns(25);
		
		DepartProbTextField = new JTextField();
		DepartProbTextField.setFont(defaultFont);
		DepartProbTextField.setBounds(170, 200, 220, 20); //25, 200, 140, 14
		contentPane.add(DepartProbTextField);
		DepartProbTextField.setColumns(25);
		
		SimTimeTextField = new JTextField();
		SimTimeTextField.setFont(defaultFont);
		SimTimeTextField.setBounds(170, 260, 220, 20);
		contentPane.add(SimTimeTextField);
		SimTimeTextField.setColumns(25);
		
		
		submitButton = new JButton("Simulate");
		submitButton.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")				
			public void mouseClicked(MouseEvent e) {				
				if (combo1.getSelectedItem().equals(1)) {
				jtaDisplay.setText(runway1.runwaySimulate(Integer.parseInt(LandingTimeTextField.getText()), Integer.parseInt(DepartTimeTextField.getText()), 
						Integer.parseInt(MaxFuelTextField.getText()), Double.parseDouble(DepartProbTextField.getText()), 
						Double.parseDouble(LandArriveProbabilityTextField.getText()), Integer.parseInt(SimTimeTextField.getText()), 1,
						Integer.parseInt(LandingTimeTextField.getText()), Integer.parseInt(DepartTimeTextField.getText())));
				}else{
					if(combo1.getSelectedItem().equals(2)) {
						jtaDisplay.setText(runway2.runwaySimulate(Integer.parseInt(LandingTimeTextField.getText()), Integer.parseInt(DepartTimeTextField.getText()), 
								Integer.parseInt(MaxFuelTextField.getText()), Double.parseDouble(DepartProbTextField.getText()), 
								Double.parseDouble(LandArriveProbabilityTextField.getText()), Integer.parseInt(SimTimeTextField.getText()), 2,
								Integer.parseInt(LandingTimeTextField.getText()), Integer.parseInt(DepartTimeTextField.getText())));
					}
				}
				
			}
		});
		submitButton.setFont(defaultFont);
		submitButton.setBounds(485, 517, 90, 28);  
		contentPane.add(submitButton);
		
		JLabel lblcombo1 = new JLabel("Number of Runways: ");
		lblcombo1.setFont(defaultFont);
		lblcombo1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblcombo1.setBounds(430, 8, 120, 20); 
		inputPane.add(lblcombo1);
		
		combo1.setFont(defaultFont);
		combo1.setBounds(440, 33, 110, 20);
		inputPane.add(combo1);
		
	
		//Display Area
		jtaDisplay = new JTextArea(100, 200);
		jtaDisplay.setEditable (false); 
		jtaDisplay.setFont(defaultFont);
		contentPane.add(jtaDisplay);

		//ScrollPane
		JScrollPane scrollPane = new JScrollPane(jtaDisplay);
		scrollPane.setBounds(10, 300, 574, 203);
		contentPane.add(scrollPane);


	}

	public static void main(String[] args) {
		GUI frame = new GUI();
		frame.pack();
		frame.setTitle("Airport");
		frame.setSize(610,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}



