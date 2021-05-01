package userinfo;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import game.Menu;

public class Scoreboard extends JFrame {
    public static String[] columns;
    public static Object[][] data;

    static final Color COLOR_BG = new Color(37,37,38);
    static final Color TEXT_COLOR = new Color(240,240,240);
    static final Color TABLE_BG = new Color(86,86,87);    
    static final Color COLOR_BLUE = new Color(0,122,204);


    public Scoreboard() {
        createTable();

		// Init
		Container cp = getContentPane();
        JLabel label_naslov = new JLabel("Scoreboard");
        JTable table_score = new JTable(new CustomTableModel());
        JScrollPane scroll_table = new JScrollPane(table_score);
		JButton btn_back = new JButton("Back");
        

		// Frame Properties
		setSize(new Dimension(440, 450));
		setTitle("Towers of Hanoi - Scoreboard");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		
		// Element Properties        
		cp.setLayout(null);		
        label_naslov.setHorizontalAlignment(SwingConstants.CENTER);
        label_naslov.setFont(new Font("Helvetica", Font.PLAIN, 30));
		btn_back.setFont(new Font("Helvetica", Font.BOLD, 15));
        table_score.doLayout();     
        table_score.setFillsViewportHeight(true);
        table_score.getColumnModel().getColumn(3).setMaxWidth(60);
		
        
		// Element Coloring	
        cp.setBackground(COLOR_BG);		
        label_naslov.setForeground(TEXT_COLOR);	
		btn_back.setBackground(COLOR_BLUE);
		btn_back.setForeground(TEXT_COLOR);
        table_score.getTableHeader().setBackground(COLOR_BLUE);
        table_score.getTableHeader().setForeground(TEXT_COLOR);
        table_score.setBackground(TABLE_BG);
        table_score.setForeground(TEXT_COLOR);     
						

		// Element Positioning		
		label_naslov.setBounds(60, 60, 300, 30);
        scroll_table.setBounds(20,120,400,180);
        btn_back.setBounds(110, 330, 200, 30);
			

		// Action Listeners		
		btn_back.addActionListener(e -> {            
			SwingUtilities.invokeLater(new Runnable() {
                @Override               
                public void run() {
                    new Menu();                    
                }                
            });
			dispose();
		});
        

		// Adding
        cp.add(label_naslov);
		cp.add(btn_back);
        cp.add(scroll_table);		

		// Set Visible
		this.setVisible(true);
        
        
	}

    public void createTable() {
        File file_score = new File("./userinfo/score.csv");
        ArrayList<Object[]> local_data = new ArrayList<Object[]>();
        if (file_score.exists()) {
            try {
                Scanner sc = new Scanner(file_score);
                String columnString = sc.nextLine();
                columns = columnString.split(",");                
                while(sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] line_array = line.split(",");                    
                    if (line_array.length == columns.length) {
                        local_data.add(line_array);
                    }
                    
                }
                sc.close();
                data = new Object[local_data.size()][];
                for (int i = 0; i < local_data.size(); i++) {
                    data[i] = local_data.get(i);
                }
            } catch (IOException ioe) {            
                JOptionPane.showMessageDialog(null, "There aren't any scores yet", "Error: score file does not exist", JOptionPane.ERROR_MESSAGE);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Menu();
                    }
                });
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "There aren't any scores yet", "Error: score file does not exist", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Menu();
                }
            });
            dispose();
        }
        
    }

    class CustomTableModel extends AbstractTableModel {
        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public int getRowCount() {            
            return data.length;
        }

        @Override
        public Object getValueAt(int row, int col) {            
            return data[row][col];
        }

        public boolean isCellEditable(int row, int col) {            
            return false;
        }

        public String getColumnName(int col) {
            return columns[col];
        }
        
    }

}
