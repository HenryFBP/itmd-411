import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;

public class testframe extends JFrame
{


	private static final long serialVersionUID = 1L;
	private static final int PADDING = 10;
	private static final int MAX_WIDTH_MULT = 3;
	private static final int JTABLE_COLUMN_PADDING = 10;
	
	private static final String TITLE = "Henry Post's ITM411 SQL Crunchifier v1.67acdefg2";
	
	private static final String HELP_MESSAGE = "'F1' will bring me back up.\n"
											+ "\n"
											+ "Hi, Papademas or a TA. How are you? Hopefully well.\n"
											+ " Sorry for the fake lambdas and typecasts everywhere. I didn't want to have to get java 8 or make you do the same.\n"
											+ "Anyways, here are usage tips:\n"
											+ "\n"
											+ "Double-click any command from command history to run it.\n"
											+ "Double-right-click any command from command history to delete it.\n"
											+ "\n"
											+ "That's kinda it. Have fun.\n"
											+ "Oh, also, I made a really useful function called stringDisplaySize() that \n"
											+ "renders fonts to EXACTLY determine how large the strings should be given a fontface, fontsize, etc.\n"
											+ "My serialization is in 'Serializer.java'. Have fun!\n"
											+ "-Henry.";
	
	
	
	private static final String DEFAULT_COMMAND = "SELECT * FROM " + Dao.TABLE_NAME;
	
	private static String[] SUGGESTED_COMMANDS = {
			DEFAULT_COMMAND,
			"SELECT * "+						"FROM " + Dao.TABLE_NAME + " WHERE pid < 10",
			"SELECT * "+						"FROM " + Dao.TABLE_NAME + " LIMIT 13",
			"SELECT pid, id, age, gender "+ 	"FROM " + Dao.TABLE_NAME + " WHERE AGE = 30",
			"SELECT age "+						"FROM " + Dao.TABLE_NAME + " ORDER BY income ASC LIMIT 10",
			"DESCRIBE " + Dao.TABLE_NAME,
			
	};
	
	private static final Object[] DEFAULT_FONT_PROPERTIES = {"Dialog",Font.BOLD,12};
	
	
	private static ArrayList<Object> LAMBDA_PARAMS = new ArrayList<>();

	private static final int L_MODE1_TEXT_DISPLAY_WIDTH = 		0x0001;
	private static final int L_MODE1_STRING_NUMBER_OF_CHARS = 	0x0002;

	private static final int L_MODE2_GREATER_THAN =			 	0xFF01;
	private static final int L_MODE2_LESS_THAN = 				0xFF02;	
	
	
	private JPanel contentPane;
	private JTable tableResults;
	private JTextField textFieldSQLQuery;
	private JLabel lblNewLabel;
	private JPanel panelInfo;
	private JPanel panelScrollPaneResults;
	private JButton btnRun;
	private JPanel panelCommandHistory;
	private JMenuBar menuBar;

	
	
	
	/***
	 * Ensure that a 2d object arraylist is n by m everywhere with no "bumpiness". <br>
	 * Fills with {filler}.<br>
	 * <br>
	 * Example if {filler} were -1:<br>
	 * 
	 * <code>
	 * {<br>
	 *&nbsp;{ 1,    "hi",   2,   'a' },<br>
	 *&nbsp;{ 2,    "ho",   4,   'b',   'c',   'e' },<br>
	 *&nbsp;{ [1, 2] }<br>
	 * }<br>
	 * </code><br>
	 * <br>
	 * turns into<br>
	 * <br>
	 * <code>
	 * {<br>
	 *&nbsp;{ 1,    "hi",   2,   'a',   -1,    -1  },<br>
	 *&nbsp;{ 2,    "ho",   4,   'b',   'c',   'e' },<br>
	 *&nbsp;{ [1, 2], -1,   -1,   -1,    -1,    -1,}<br>
	 * }<br>
	 * </code>
	 */
	
	public ArrayList<ArrayList<Object>> ensurePadded(ArrayList<ArrayList<Object>> list)
	{
		ArrayList<ArrayList<Object>> ret = new ArrayList<>();
		ret = list;
		
		int largest = list.get(0).size();
		
		for (int i = 0; i < list.size(); i++)
		{
			if(list.get(i).size() > largest)
			{
				largest = list.get(i).size();
			}
		}
		
		for(int i = 0; i < list.size(); i++)
		{
			System.out.printf("unfinished. ha. %d",i);
		}
		
		
		
		
		return ret;
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					testframe frame = new testframe();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public static void lamb_duh_args(Object[] a)
	{
		LAMBDA_PARAMS.clear();
		LAMBDA_PARAMS.add(-1);
		
		if(a != null)
		{
			for (int i = 0; i < a.length; i++)
			{
				LAMBDA_PARAMS.add(a[i]);
			}
		}
		return;
	}
	
	/***
	 * Too lazy to use wrapper classes. Oh well. Here's a switch-case! Totally a higher-order function, right???
	 * Sorry in advance to the TAs.
	 * @param a	Object a.
	 * @param b Object b.
	 * @param mode2	How to compare these two objects?
	 * @return	The difference between the two objects.
	 */
	public static Object lamb_duh(Object a, Object b, int mode2)
	{
		switch (mode2)
		{
		case L_MODE2_GREATER_THAN:
			return ((Double)a > (Double)b);
		case L_MODE2_LESS_THAN:
			return ((Double)a < (Double)b);


		default:
			return null;
		}
	}
	
	/***
	 * This is a unary version of the {@link #lamb_duh(Object, int)} function defined here: <br>{@link #lamb_duh(Object, Object, int)}
	 * @param a	Object a.
	 * @param mode1	How to 'size up' or 'measure' this object?
	 * @return The 'value' of this object.
	 */
	
	public static Object lamb_duh(Object a, int mode1)
	{
//		System.out.println("Passed object:"+a.toString());
		
		Object ret = null;
		
		
		boolean optArgs = false;
		if((LAMBDA_PARAMS.size() >= 1) && (LAMBDA_PARAMS.get(0) != null))
		{
			optArgs = true;
		}
		
		switch(mode1)
		{
			case L_MODE1_TEXT_DISPLAY_WIDTH: //get render width
				if(optArgs) //if we want to use optional args
				{
					ret = stringDisplaySize((String)a, (String)LAMBDA_PARAMS.get(1), (Integer)LAMBDA_PARAMS.get(2), (Integer)LAMBDA_PARAMS.get(3))[0];
				}
				else
				{
					ret = stringDisplaySize((String)a)[0];
				}
				break;
				
			case L_MODE1_STRING_NUMBER_OF_CHARS: //get number of chars
				return (Integer)((String)a).length();
				

		}
		
//		System.out.println("Returning object: "+ret.toString());
		return ret;
	}
	
	
	/***
	 * Calculates, in pixels, the width & height of a string of text.
	 * @param 	s			The string in question.
	 * @param 	fontName	The name of the font. i.e: "Tahoma", "Times New Roman", etc.
	 * @param 	fontStyle	The style of the font. i.e: "Font.PLAIN", "Font.BOLD", etc.
	 * @param 	size		The size of the font
	 * @return 	An int[] containing <code>{width, height}</code> of the font in pixels if it were to be rendered
	 * 			in the given context.
	 */
	public static Double[] stringDisplaySize(String s, String fontName, int fontStyle, int size)
	{
		
		AffineTransform aft = new AffineTransform();
		
		FontRenderContext frc = new FontRenderContext(aft, true, true);
		
		Font font = new Font(fontName, fontStyle, size);
		
		Double[] ret = {
				font.getStringBounds(s, frc).getWidth(),
				font.getStringBounds(s, frc).getHeight()
		};
		
//		System.out.println(String.format("String %s sized %d with fontName %s and fontStyle '%d' would be:\n%fpx by %fpx",
//												s,	size,			fontName,	  fontStyle,			ret[0],		ret[1]));
		
		return ret;
}

	/***
	 * Calculates, in pixels, the width & height of a string of text.
	 * This method is a default version of {@link #stringDisplaySize(String, String, int, int)}.
	 * It uses ["Tahoma", Font.PLAIN, 12] as [fontName, fontStyle, size].
	 * @param s
	 * @return
	 */
	public static Double[] stringDisplaySize(String s)
	{
		return stringDisplaySize(s, (String)DEFAULT_FONT_PROPERTIES[0], (int)DEFAULT_FONT_PROPERTIES[1], (int)DEFAULT_FONT_PROPERTIES[2]);
	}
	
	/**
	 * *
	 * @param table			A 2-d array of objects. 
	 * @param lambdaMode 	Compare 2 objects by string length, by render width, by how tasty they are, etc.
	 * 
	 * @return	A 1d array of the longest things in each array's column.
	 * 		   	The "longness" is determined by the chars in the object's
	 * 			.toString() method, or by pretending to render them and
	 * 			looking at their width.
	 * <br>
	 * Example: <br>
	 * <code>
	 * table = <br>
	 * {{1, 	"hi",	3},<br>
	 * {"one",	2,		4}}<br></code>
	 * 
	 * And the return value would be:<br>
	 * 	<code>{3, 2, 1}</code>
	 */
	public static ArrayList<Integer> getLongestElements(ArrayList<ArrayList<Object>> table, int lambdaMode)
	{
		System.out.println("Asked to get longest elements from this table: "+table.toString());
		ArrayList<Integer> ret = new ArrayList<>();
		
		for (int i = 0; i < table.get(0).size(); i++)
		{
			ret.add(-1); //init n empty values for n columns
		}
		
		for (int i = 0; i < table.size(); i++) //loop through all rows
		{
			ArrayList<Object> row = table.get(i);
//			System.out.printf("Row is %s\n",row.toString());
			
			for (int j = 0; j < row.size(); j++) //loop through each individual column
			{
				Object currItem = row.get(j);
				
				if(currItem == null) //take that, nullpointerexception! null.toString() shall not crash me!
				{
					currItem = "null";
				}
				
//				System.out.printf("row.get(j) = %s\n",currItem.toString());
				Integer sizeA = ((Double)lamb_duh(currItem.toString(), lambdaMode)).intValue();
				Integer sizeB = ((Double)lamb_duh(ret.get(j).toString(), lambdaMode)).intValue();
				if(sizeA > sizeB) //if row n's length is greater than bucket[n], add it
				{
					ret.set(j, sizeA);
				}
			}
		}
		
				
		return ret;
	}

	/***
	 * 
	 * @param tabledata
	 * @return
	 */
	public static DefaultTableModel defaultTableModelFromData(ArrayList<ArrayList<Object>> tabledata)
	{		
		Object topCol[] = tabledata.get(0).toArray(new Object[tabledata.get(0).size()]); //arraylist<object> to object[]

		DefaultTableModel dtm = new DefaultTableModel(topCol, 0);		//add X rows
		
		for (int i = 1; i < tabledata.size(); i++)
		{
			dtm.addRow(tabledata.get(i).toArray(new Object[tabledata.get(i).size()]));
		}
		return dtm;
	}
	
	/**
	 * @param table The table to be resized.
	 * @param largestCols An ArrayList of ints that each column's max width will be set to.
	 * Takes an array of integers
	 * 
	 */
	public static void resizeTableCols(JTable table, ArrayList<Integer> largestCols)
	{		
		System.out.println("Using this list of sizes for table column widths: " + largestCols.toString());
		for(int i = 0; i < largestCols.size(); i++)
		{
			int width = largestCols.get(i);
//			int width = largestCols.get(i) * WIDTH_MULT;
//			System.out.println(String.format("Setting table's column %d's maxWidth to %d",i,width));

			table.getColumnModel().getColumn(i).setMinWidth(width + JTABLE_COLUMN_PADDING); //set max width to the array's val at arr[i]
//			table.getColumnModel().getColumn(i).setMaxWidth(width * MAX_WIDTH_MULT); //set max width to the array's val at arr[i]
		}
		return;
	}
	
	
	public static int updateJTable(Dao dao, JTable table, String query)
	{
		
		ResultSet rs = dao.executeQuery(query);
		
		ArrayList<ArrayList<Object>> recordData = dao.resultSetToList(rs);
		
		ArrayList<ArrayList<Object>> recordMetadata = dao.resultSetMetadataToList(rs);
		
		recordData.add(0,recordMetadata.get(0)); //add headers

		Object[] args = DEFAULT_FONT_PROPERTIES;
		
		lamb_duh_args(args); //pass args
		ArrayList<Integer> longestCols = getLongestElements(recordData, L_MODE1_TEXT_DISPLAY_WIDTH);
		lamb_duh_args(null);  //clear 'em

		table.setModel(defaultTableModelFromData(recordData));
		
		resizeTableCols(table, longestCols);
		
		return 1;
				
	}
	
	
	/**
	 * Create the frame.
	 */
	public testframe()
	{
		
		
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1015, 617);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(contentPane, HELP_MESSAGE);
			}
		});
		
		mntmHelp.setActionCommand("(H)elp");
		
		KeyStroke F1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1,0);
		mntmHelp.setAccelerator(F1);
		
		menuBar.add(mntmHelp);
		contentPane = new JPanel();
		contentPane.setAlignmentX(0.0f);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		

		Dao dao = new Dao();
		
		DefaultListModel<String> listCommandHistoryModel = new DefaultListModel<>();
		
		dao.command_history.add(SUGGESTED_COMMANDS[0]);
		
		
		for (int i = 0; i < SUGGESTED_COMMANDS.length; i++)
		{
			listCommandHistoryModel.addElement(SUGGESTED_COMMANDS[i]);
		}

		

						SpringLayout sl_contentPane = new SpringLayout();
						contentPane.setLayout(sl_contentPane);
						
						panelInfo = new JPanel();
						sl_contentPane.putConstraint(SpringLayout.WEST, panelInfo, 10, SpringLayout.WEST, contentPane);
						panelInfo.setMaximumSize(new Dimension(300, 32767));
						panelInfo.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
						sl_contentPane.putConstraint(SpringLayout.NORTH, panelInfo, PADDING, SpringLayout.NORTH, contentPane);
						sl_contentPane.putConstraint(SpringLayout.SOUTH, panelInfo, -PADDING, SpringLayout.SOUTH, contentPane);
						panelInfo.setAlignmentY(Component.TOP_ALIGNMENT);
						panelInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
						contentPane.add(panelInfo);
						SpringLayout sl_panelInfo = new SpringLayout();
						panelInfo.setLayout(sl_panelInfo);
						
						JPanel panelSQLQuery = new JPanel();
						sl_panelInfo.putConstraint(SpringLayout.NORTH, panelSQLQuery, PADDING, SpringLayout.NORTH, panelInfo);
						sl_panelInfo.putConstraint(SpringLayout.WEST, panelSQLQuery, PADDING, SpringLayout.WEST, panelInfo);
						panelInfo.add(panelSQLQuery);
						panelSQLQuery.setLayout(new BorderLayout(0, 0));
						
						textFieldSQLQuery = new JTextField();
						panelSQLQuery.add(textFieldSQLQuery, BorderLayout.CENTER);
						textFieldSQLQuery.setColumns(10);
						
						lblNewLabel = new JLabel("SQL Query");
						lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
						panelSQLQuery.add(lblNewLabel, BorderLayout.NORTH);
						
						btnRun = new JButton("Run!");
						sl_panelInfo.putConstraint(SpringLayout.EAST, panelSQLQuery, -6, SpringLayout.WEST, btnRun);
						sl_panelInfo.putConstraint(SpringLayout.WEST, btnRun, 261, SpringLayout.WEST, panelInfo);
						sl_panelInfo.putConstraint(SpringLayout.EAST, btnRun, -PADDING, SpringLayout.EAST, panelInfo);
						
						
						JLabel labelRecentCommand = new JLabel(DEFAULT_COMMAND);
						labelRecentCommand.setDisplayedMnemonic(KeyEvent.VK_ENTER);
						labelRecentCommand.setBackground(Color.LIGHT_GRAY);
						labelRecentCommand.setHorizontalAlignment(SwingConstants.CENTER);
						
						sl_panelInfo.putConstraint(SpringLayout.NORTH, btnRun, PADDING, SpringLayout.NORTH, panelInfo);
						panelInfo.add(btnRun);
						
						panelCommandHistory = new JPanel();
						sl_panelInfo.putConstraint(SpringLayout.NORTH, panelCommandHistory, PADDING, SpringLayout.SOUTH, panelSQLQuery);
						sl_panelInfo.putConstraint(SpringLayout.SOUTH, panelCommandHistory, -PADDING, SpringLayout.SOUTH, panelInfo);
						sl_panelInfo.putConstraint(SpringLayout.SOUTH, btnRun, -PADDING, SpringLayout.NORTH, panelCommandHistory);
						sl_panelInfo.putConstraint(SpringLayout.WEST, panelCommandHistory, PADDING, SpringLayout.WEST, panelInfo);
						sl_panelInfo.putConstraint(SpringLayout.EAST, panelCommandHistory, -PADDING, SpringLayout.EAST, panelInfo);
						panelInfo.add(panelCommandHistory);
						
						JList listCommandHistory = new JList(listCommandHistoryModel);

						SpringLayout sl_panelCommandHistory = new SpringLayout();
						sl_panelCommandHistory.putConstraint(SpringLayout.NORTH, listCommandHistory, 16, SpringLayout.NORTH, panelCommandHistory);
						sl_panelCommandHistory.putConstraint(SpringLayout.WEST, listCommandHistory, PADDING, SpringLayout.WEST, panelCommandHistory);
						sl_panelCommandHistory.putConstraint(SpringLayout.SOUTH, listCommandHistory, -10, SpringLayout.SOUTH, panelCommandHistory);
						sl_panelCommandHistory.putConstraint(SpringLayout.EAST, listCommandHistory, -PADDING, SpringLayout.EAST, panelCommandHistory);
						panelCommandHistory.setLayout(sl_panelCommandHistory);
						
						
						panelCommandHistory.add(listCommandHistory);
						sl_panelInfo.putConstraint(SpringLayout.NORTH, listCommandHistory, 228, SpringLayout.SOUTH, panelSQLQuery);
						sl_panelInfo.putConstraint(SpringLayout.WEST, listCommandHistory, 0, SpringLayout.WEST, panelSQLQuery);
						sl_panelInfo.putConstraint(SpringLayout.SOUTH, listCommandHistory, -PADDING, SpringLayout.SOUTH, panelInfo);
						sl_panelInfo.putConstraint(SpringLayout.EAST, listCommandHistory, 0, SpringLayout.EAST, btnRun);
						
						JLabel lblCommandHistory = new JLabel("Command History");
						sl_panelCommandHistory.putConstraint(SpringLayout.NORTH, lblCommandHistory, 0, SpringLayout.NORTH, panelCommandHistory);
						sl_panelCommandHistory.putConstraint(SpringLayout.WEST, lblCommandHistory, 0, SpringLayout.WEST, panelCommandHistory);
						sl_panelCommandHistory.putConstraint(SpringLayout.EAST, lblCommandHistory, 310, SpringLayout.WEST, panelCommandHistory);
						lblCommandHistory.setHorizontalAlignment(SwingConstants.CENTER);
						panelCommandHistory.add(lblCommandHistory);
														
														JPanel panelResults = new JPanel();
														sl_contentPane.putConstraint(SpringLayout.NORTH, panelResults, PADDING, SpringLayout.NORTH, contentPane);
														sl_contentPane.putConstraint(SpringLayout.WEST, panelResults, 6, SpringLayout.EAST, panelInfo);
														sl_contentPane.putConstraint(SpringLayout.SOUTH, panelResults, -10, SpringLayout.SOUTH, contentPane);
														sl_contentPane.putConstraint(SpringLayout.EAST, panelResults, -PADDING, SpringLayout.EAST, contentPane);
														contentPane.add(panelResults);
														SpringLayout sl_panelResults = new SpringLayout();
														sl_panelResults.putConstraint(SpringLayout.WEST, labelRecentCommand, 10, SpringLayout.WEST, panelResults);
														sl_panelResults.putConstraint(SpringLayout.EAST, labelRecentCommand, -10, SpringLayout.EAST, panelResults);
														panelResults.setLayout(sl_panelResults);
														
														tableResults = new JTable();
														
		updateJTable(dao, tableResults, DEFAULT_COMMAND);
		
		panelScrollPaneResults = new JPanel();
		sl_panelResults.putConstraint(SpringLayout.SOUTH, panelScrollPaneResults, -10, SpringLayout.SOUTH, panelResults);
		sl_panelResults.putConstraint(SpringLayout.SOUTH, labelRecentCommand, -10, SpringLayout.NORTH, panelScrollPaneResults);
		sl_panelResults.putConstraint(SpringLayout.NORTH, panelScrollPaneResults, 31, SpringLayout.NORTH, panelResults);
		sl_panelResults.putConstraint(SpringLayout.WEST, panelScrollPaneResults, 10, SpringLayout.WEST, labelRecentCommand);
		sl_panelResults.putConstraint(SpringLayout.EAST, panelScrollPaneResults, -PADDING, SpringLayout.EAST, panelResults);
		panelResults.add(panelScrollPaneResults);
		sl_contentPane.putConstraint(SpringLayout.EAST, panelInfo, -PADDING, SpringLayout.WEST, panelScrollPaneResults);
		sl_contentPane.putConstraint(SpringLayout.NORTH, panelScrollPaneResults, 55, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panelScrollPaneResults, 350, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panelScrollPaneResults, -PADDING, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panelScrollPaneResults, -PADDING, SpringLayout.EAST, contentPane);
		SpringLayout sl_panelScrollPaneResults = new SpringLayout();
		panelScrollPaneResults.setLayout(sl_panelScrollPaneResults);
		
		JScrollPane scrollPaneResults = new JScrollPane();
		sl_panelScrollPaneResults.putConstraint(SpringLayout.NORTH, scrollPaneResults, PADDING, SpringLayout.NORTH, panelScrollPaneResults);
		sl_panelScrollPaneResults.putConstraint(SpringLayout.WEST, scrollPaneResults, PADDING, SpringLayout.WEST, panelScrollPaneResults);
		sl_panelScrollPaneResults.putConstraint(SpringLayout.SOUTH, scrollPaneResults, -PADDING, SpringLayout.SOUTH, panelScrollPaneResults);
		sl_panelScrollPaneResults.putConstraint(SpringLayout.EAST, scrollPaneResults, -PADDING, SpringLayout.EAST, panelScrollPaneResults);
		panelScrollPaneResults.add(scrollPaneResults);
		
				tableResults.setColumnSelectionAllowed(true);
				tableResults.setCellSelectionEnabled(true);
				tableResults.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				tableResults.setFillsViewportHeight(true);
				
				
				scrollPaneResults.setViewportView(tableResults);
				panelResults.add(labelRecentCommand);
				
				/***
				 * If the 'run' button gets clicked.
				 */
				btnRun.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						
						System.out.println("WHO KLICK RUN??");
						String query = textFieldSQLQuery.getText();
						labelRecentCommand.setForeground(Color.BLACK);
						try
						{
							updateJTable(dao, tableResults, query);	
							labelRecentCommand.setText(query);
							if(!listCommandHistoryModel.contains(query)) //if command isn't in recent commands
							{
								listCommandHistoryModel.add(0, query);	//add it
							}
						}
						catch(Exception e1)
						{
							e1.printStackTrace();
							labelRecentCommand.setText("ERROR: "+query);
							labelRecentCommand.setForeground(Color.RED);
						}
						

						
						textFieldSQLQuery.setText(query);
						textFieldSQLQuery.setToolTipText(query);

						
					}
				});
				
				
				/***
				 * For single/double clicks.
				 */
				listCommandHistory.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent event)
					{
						JList list = (JList)event.getSource();
						int index = list.locationToIndex(event.getPoint());
						int clicks = event.getClickCount();
						String query = listCommandHistoryModel.get(index);
						

						
						switch (clicks)
						{
						case 2:
							if(SwingUtilities.isLeftMouseButton(event))
							{
								System.out.println(String.format("Clicked list element %d which is %s",index,query));		
								labelRecentCommand.setForeground(Color.BLACK);

								try
								{
									updateJTable(dao, tableResults, query);	
									labelRecentCommand.setText(query);
								}
								catch(Exception e1)
								{
									e1.printStackTrace();
									labelRecentCommand.setText("ERROR: "+query);
									labelRecentCommand.setForeground(Color.RED);
								}

								textFieldSQLQuery.setToolTipText(query);
								labelRecentCommand.setText(query);
								
							}
							if(SwingUtilities.isRightMouseButton(event))
							{
								listCommandHistoryModel.remove(index);
							}
							
							break;
						
						case 1:
							if(SwingUtilities.isLeftMouseButton(event))
							{
								textFieldSQLQuery.setText(query);
								textFieldSQLQuery.setToolTipText(query);
							}
						default:
//							System.out.println("clicky??");
							break;
						}
					}
				});
				
				/***
				 * This is when you press ENTER inside of the SQL query text box.
				 */
				textFieldSQLQuery.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent ae)
					{
						System.out.println("U PRESSIN ENTER?");
					}
				});

					
				mntmHelp.setVisible(true);
				
				JOptionPane.showMessageDialog(contentPane, HELP_MESSAGE);
			
	}
}
