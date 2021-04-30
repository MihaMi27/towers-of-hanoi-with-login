package userinfo;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;

import game.Menu;

public class ResetPassword extends JFrame {     
    static JPasswordField oldPassword_field = new JPasswordField();
    static JPasswordField newPassword_field = new JPasswordField();
    static JPasswordField rptNewPassword_field = new JPasswordField();     

    public ResetPassword() {
        // Init        
        Container cp = getContentPane();
        JLabel label_naslov = new JLabel("Reset Password");        
        JLabel label_oldPassword = new JLabel("Old Password");
        JLabel label_newPassword = new JLabel("New Password");
        JLabel label_rptNewPassword = new JLabel("Repeat New Password");
        JButton btn_change = new JButton("Change");
        JButton btn_back = new JButton("Back");
        

        // Frame Properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new Dimension(440, 450));
        setLocationRelativeTo(null);
        setTitle("Reset Password");     
        
        // Element Properties
        cp.setLayout(null);
        label_naslov.setHorizontalAlignment(SwingConstants.CENTER);
        label_naslov.setFont(new Font("Helvetica", Font.PLAIN, 30));
        label_oldPassword.setFont(new Font("Helvetica", Font.PLAIN, 13)); 
        label_newPassword.setFont(new Font("Helvetica", Font.PLAIN, 13));
        label_rptNewPassword.setFont(new Font("Helvetica", Font.PLAIN, 13));        
        btn_change.setFont(new Font("Helvetica", Font.BOLD, 13));
        btn_back.setFont(new Font("Helvetica", Font.BOLD, 13));        
        
        // Element Coloring
        cp.setBackground(new Color(37,37,38)); 		
        label_naslov.setForeground(new Color(240,240,240));        
        label_oldPassword.setForeground(new Color(240,240,240));       
        label_newPassword.setForeground(new Color(240,240,240));        
        label_rptNewPassword.setForeground(new Color(240,240,240));
        btn_change.setBackground(new Color(0, 122, 204));
        btn_change.setForeground(new Color(240,240,240));
        btn_back.setBackground(new Color(0, 122, 204));
        btn_back.setForeground(new Color(240,240,240));        

        // Positioning
        label_naslov.setBounds(60,40,300,50);        
        label_oldPassword.setBounds(60,130,300,25);
        label_newPassword.setBounds(60,190,300,25);
        label_rptNewPassword.setBounds(60,250,300,25);        
        oldPassword_field.setBounds(60,155,300,30);
        newPassword_field.setBounds(60,215,300,30);
        rptNewPassword_field.setBounds(60,275,300,30);
        btn_change.setBounds(60,320,300,30);        
        btn_back.setBounds(60,360,300,30);

        // Action Listeners
        btn_change.addActionListener(e -> {
            reset();
        });

        btn_back.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Menu();
                }
            });                
            dispose();            
        });
        
        // Adding
        cp.add(label_naslov);        
        cp.add(label_oldPassword);
        cp.add(label_newPassword);
        cp.add(label_rptNewPassword);        
        cp.add(oldPassword_field);
        cp.add(newPassword_field);
        cp.add(rptNewPassword_field);
        cp.add(btn_change);
        cp.add(btn_back);       

        // Set Visible
        this.setVisible(true);

    }

    public void reset() {
        String clear_username = getCurrentUsername();
        String clear_oldPassword = new String(oldPassword_field.getPassword());
        String clear_newPassword = new String(newPassword_field.getPassword());
        String clear_rptNewPassword = new String(rptNewPassword_field.getPassword());        
        String hashed_oldPassword = Login.hashing(clear_oldPassword);
        String hashed_newPassword = Login.hashing(clear_newPassword);
        String hashed_rptNewPassword = Login.hashing(clear_rptNewPassword);
        String clear_nickname = getCurrentNickname();
        int lineIndex = -1;
        

        if (!clear_username.isEmpty() && !clear_oldPassword.isEmpty() && !clear_newPassword.isEmpty() && !clear_rptNewPassword.isEmpty()) {
            if (hashed_newPassword.equals(hashed_rptNewPassword)) {
                if ((lineIndex = Login.checkInFile(Login.info,clear_username)) != -1) {
                    try {
                        if (hashed_oldPassword.equals(Login.getHashedPassword(Login.info,lineIndex))) {
                            String newInfo = clear_username+" "+hashed_newPassword+" "+clear_nickname;
                            Login.replaceLine(Login.info,newInfo, lineIndex);
                            JOptionPane.showMessageDialog(null, "Password reset successfully", "Success: Reset", JOptionPane.PLAIN_MESSAGE);
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {                                    
                                    new Menu();
                                }                                
                            });
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Old password doesn't match", "Error: Reset", JOptionPane.ERROR_MESSAGE);
                        }                        
                    } catch (IOException ioe) {                        
                        ioe.printStackTrace();
                        JOptionPane.showMessageDialog(null, "File error occured", "Error", JOptionPane.ERROR_MESSAGE);
                    }                    
                } else {
                    JOptionPane.showMessageDialog(null, "User does not exist or the password is wrong", "Error: Reset", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "New passwords don't match", "Error: Reset", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "At least one field is empty", "Error: Reset", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static String getCurrentUsername() {
        File currentUserInfo = new File("./userinfo/currentUser.txt");
        String username = "";
        try {
            Scanner sc = new Scanner(currentUserInfo);
            username = sc.nextLine().split(" ")[0];
            sc.close();
        } catch (FileNotFoundException fnfe) {            
            fnfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "File error occured", "Error", JOptionPane.ERROR_MESSAGE);
        }        
        return username;
    }
    public static String getCurrentNickname() {
        File currentUserInfo = new File("./userinfo/currentUser.txt");
        String nickname = "";
        try {
            Scanner sc = new Scanner(currentUserInfo);
            nickname = sc.nextLine().split(" ")[1];
            sc.close();
        } catch (FileNotFoundException fnfe) {            
            fnfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "File error occured", "Error", JOptionPane.ERROR_MESSAGE);
        }        
        return nickname;
    }
}
