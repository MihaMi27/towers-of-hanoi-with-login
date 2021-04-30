package userinfo;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import game.Menu;

public class Login extends JFrame {
    static File info = new File("./userinfo/userinfo.txt"); // 0 == username, 1 == pass, 2 == nick    
    static File currentUser = new File("./userinfo/currentUser.txt"); // 0 == username, 1 == nick
    static JTextField username_field = new JTextField();
    static JPasswordField password_field = new JPasswordField();
    static JTextField nickname_field = new JTextField();
    private static JFrame login_frame;
    static byte[] salt = {-20, 14, -57, -97, -82, 95, 95, 84, 39, -110, 20, -24, 115, -37, 11, 88};
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login();
            }
        });
    }

    public Login() {
        // Init
        login_frame = this;
        Container cp = getContentPane();
        JLabel label_naslov = new JLabel("Login");
        JLabel label_username = new JLabel("Username");
        JLabel label_password = new JLabel("Password");       
        JLabel label_nickname = new JLabel("Nickname (not required at login)");       
        JButton btn_signup = new JButton("Sign Up");
        JButton btn_login = new JButton("Login");        

        // Frame Properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        cp.setLayout(null);
        setSize(new Dimension(440, 450));
        setLocationRelativeTo(null);
        setTitle("Login/Registration");        
        

        // Element Properties        
        cp.setBackground(new Color(37, 37, 38));
        label_naslov.setHorizontalAlignment(SwingConstants.CENTER);
        label_naslov.setFont(new Font("Helvetica", 0, 30));
        label_naslov.setForeground(new Color(240,240,240));
        label_username.setFont(new Font("Helvetica", 0, 13));
        label_username.setForeground(new Color(240,240,240));
        label_password.setFont(new Font("Helvetica", 0, 13));
        label_password.setForeground(new Color(240,240,240));
        label_nickname.setFont(new Font("Helvetica", 0, 13));
        label_nickname.setForeground(new Color(240,240,240));
        btn_login.setFont(new Font("Helvetica", 1, 13));
        btn_signup.setFont(new Font("Helvetica", 1, 13));        
        btn_login.setBackground(new Color(0, 122, 204));
        btn_signup.setBackground(new Color(0, 122, 204));
        btn_login.setForeground(new Color(240,240,240));
        btn_signup.setForeground(new Color(240,240,240));
        

        // Positioning
        label_naslov.setBounds(60, 60, 300, 50);
        label_username.setBounds(60, 120, 300, 25);
        label_password.setBounds(60, 180, 300, 25);
        label_nickname.setBounds(60, 235, 300, 25);
        username_field.setBounds(60, 145, 300, 30);
        password_field.setBounds(60, 205, 300, 30);
        nickname_field.setBounds(60, 260, 300, 30);
        btn_signup.setBounds(60, 345, 100, 30);
        btn_login.setBounds(260, 345, 100, 30);       

        // Action Listeners
        btn_signup.addActionListener(e -> {
            signup();
        });

        btn_login.addActionListener(e -> {
            login();            
        });


        // Adding
        cp.add(label_naslov);
        cp.add(label_username);
        cp.add(label_password);
        cp.add(label_nickname);
        cp.add(username_field);
        cp.add(password_field);
        cp.add(nickname_field);
        cp.add(btn_signup);
        cp.add(btn_login);        

        // Set Visible
        this.setVisible(true);

    }

    public static void login() {
        String clear_username = username_field.getText();
        String clear_password = new String(password_field.getPassword());        
        String hashed_password = hashing(clear_password);
        int lineIndex = -1;
        
        // Create login "database" if it doesn't exist
        if (!info.exists()) {
            try {                
                info.getParentFile().mkdirs();
                info.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                JOptionPane.showMessageDialog(null, "Login \"database\" could not be created", "Error", JOptionPane.ERROR_MESSAGE);                
            }
        }

        if ((lineIndex = checkInFile(info, clear_username)) != -1) {
            try {                
                if (hashed_password.equals(getHashedPassword(info, lineIndex))) {                    
                    if (currentUser.exists()) {
                        currentUser.delete();
                    }
                    currentUser.getParentFile().mkdirs();
                    currentUser.createNewFile();                                        
                    String clear_nickname = getLine(info, lineIndex).split(" ")[2];                    
                    PrintWriter pw = new PrintWriter(currentUser);
                    pw.println(clear_username+" "+clear_nickname);
                    pw.close();
                    new Menu().setVisible(true);
                    login_frame.dispose();               
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username/password, perhaps the user does not exist","Error: Login", JOptionPane.ERROR_MESSAGE);
                }               
            } catch (HeadlessException he) {  
                he.printStackTrace();              
                System.out.println("This program requires a desktop interface.");
            } catch (IOException ioe) {   
                ioe.printStackTrace();             
                JOptionPane.showMessageDialog(null, "File error occured", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } else {            
            JOptionPane.showMessageDialog(null, "Invalid username/password, perhaps the user does not exist","Error: Login", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static String hashing(String clear_string) {
        byte[] hashBytes = null;

        // Hashing
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            hashBytes = md.digest(clear_string.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
            JOptionPane.showMessageDialog(null, "Did not find SHA-512 algorithm", "Error", JOptionPane.ERROR_MESSAGE);
        }

        StringBuilder hash = new StringBuilder(2 * hashBytes.length);
        for (byte h : hashBytes) {
            String hex = Integer.toHexString(0xff & h);
            if (hex.length() == 1) {
                hash.append('0');
            }
            hash.append(hex);
        }

        return hash.toString();
    }
    
    public static int checkInFile(File f, String clear_username) {
        boolean exists = false;
        int lineIndex = -1;
        try {
            RandomAccessFile raf = new RandomAccessFile(f,"r");            
            String line;
            int i = 0;
            while ((line = raf.readLine()) != null) {              
                String[] line_array = line.split(" ");
                String line_username = "";
                for (char x : line_array[0].toCharArray()) {
                    Pattern p = Pattern.compile("[\\w,\\s]+");
                    Matcher m = p.matcher(x+"");
                    if (m.matches()) {
                        line_username+=x;
                    }
                }
                if (clear_username.equals(line_username)) {                    
                    exists = true;
                    lineIndex = i;
                    break;                                        
                } else {
                    i++;                    
                }
            }
            raf.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(null, "File error occured", "Error", JOptionPane.ERROR_MESSAGE);
        }        
        if (exists) {            
            return lineIndex;
        } else {            
            return -1;
        }
    }

    public static boolean validUsername(String username) {
        boolean valid = false;
        Pattern p = Pattern.compile("\\w+");
        Matcher m = p.matcher(username);
        if (m.matches()) {
            valid = true;
        }
        return valid;
    }

    public static String getLine(File f, int lineIndex) throws IOException {
        String line = "";
        String clean_line = "";
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        int i = 0;
        while ((line = raf.readLine()) != null) {
            if (i == lineIndex) {
                for (char x : line.toCharArray()) {
                    Pattern p = Pattern.compile("[\\w,\\s]+");
                    Matcher m = p.matcher(x+"");
                    if (m.matches()) {
                        clean_line+=x;
                    }
                }
                break;
            } else {
                i++;
                line = "";
            }
        }             
        raf.close();
        return clean_line;
    }

    public static void replaceLine(File f, String text, int lineIndex) throws IOException {        
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        raf.seek(lineIndex);
        for (char x : text.toCharArray()) {
            raf.writeChar(x);
        }
        raf.writeChars(System.lineSeparator());
        raf.close();     
        
    }

    public static void appendLine(File f, String text) throws IOException {        
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        raf.seek(raf.length());
        for (char x : text.toCharArray()) {
            raf.writeChar(x);
        }
        raf.writeChars(System.lineSeparator());
        raf.close();        
    }

    public static void signup() {
        String clear_username = username_field.getText();
        String clear_password = new String(password_field.getPassword());
        String clear_nickname = nickname_field.getText();
        // Create login "database" if it doesn't exist
        if (!info.exists()) {
            try {                
                info.getParentFile().mkdirs();
                info.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                JOptionPane.showMessageDialog(null, "Login \"database\" could not be created", "Error", JOptionPane.ERROR_MESSAGE);                
            }
        }

        if (clear_username.isEmpty() || clear_password.isEmpty() || clear_nickname.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Empty username/password/nickname", "Error: Registration", JOptionPane.ERROR_MESSAGE);
        } else {
            if (validUsername(clear_username)) {                
                String hashed_password = hashing(clear_password);
                if (checkInFile(info, clear_username) == -1) {
                    String line = clear_username + " " + hashed_password + " " + clear_nickname;
                    try {                 
                        appendLine(info,line);
                        JOptionPane.showMessageDialog(null, "You are now registered. Please log in!", "Success: Registration", JOptionPane.PLAIN_MESSAGE);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        JOptionPane.showMessageDialog(null, "File error occured", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Username already in use", "Error: Registration", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public static String getHashedPassword(File f, int lineIndex) throws IOException {        
        return getLine(f, lineIndex).split(" ")[1];
    }

}
