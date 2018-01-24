/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework1;

import java.util.Stack;
import java.util.*;
import java.util.Scanner;
import javax.swing.JTree;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
 
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

 
import java.net.URL;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;
/**
 *
 * @author User
 */
public class Homework1 {
    
    public static Node tree;
    public static Stack<Character> sta = new Stack<Character>();
    /**
     * @param args the command line arguments
     */ 
    public static void main(String[] args) 
    {
         // Begin of arguments input sample
//                System.out.print ("input : ");
//                Scanner input = new Scanner(System.in);
//                Homework1 y = input.nextLine();
//                String postfix=input.nextLine();
//                char [] c = postfix.replace("", "").toCharArray();
//                root =y.tree(c);
  		/*if (args.length > 0) {
  			String input = args[0];
  			if (input.equalsIgnoreCase("251-*32*+")) {
  				System.out.println("(2*(5-1))+(3*2)=14");
  			}
  		}*/
                    System.out.print("input : ");
                    Scanner ip = new Scanner(System.in);
                    String y = ip.nextLine();
                    String ex = y;
                 //String ex = "251-*32*+"; 
                 int i =0;
                 do{
                    sta.push(ex.charAt(i));
                    i++;                    
                 }while(i < ex.length());
                 tree = new Node(sta.pop());
                 System.out.print("output : ");
                 infix(tree); 
                 inorder(tree);
                 System.out.print( "= " + calculate(tree) );                 
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI.createAndShowGUI();
            }    
                
                
            
        });
    }
 
    public static void inorder (Node n)
    {
        if(n.data == '+' || n.data == '-' || n.data == '*' || n.data == '/')
        {
            if(n!=tree) System.out.print("("); 
            inorder(n.left); 
            System.out.print(n.data);           
            inorder(n.right);
            if(n!=tree) System.out.print(")");    
           
         }else{
                if(n!=tree) System.out.print(n.data);
        }      
    }
    
    public static void infix (Node n)
    {
        if(n.data == '+' || n.data == '-' || n.data == '*' || n.data == '/')
             {
                 n.right = new Node(sta.pop()); infix(n.right);
                 n.left =  new Node(sta.pop()); infix(n.left);
             }
    }
   
    public static int calculate (Node n)           
    {        
        if( n.data == '+' ) return calculate(n.left) + calculate(n.right);     
        else if( n.data == '-' ) return calculate(n.left) - calculate(n.right);
        else if( n.data == '*' ) return calculate(n.left) * calculate(n.right);
        else if( n.data == '/' ) return calculate(n.left) / calculate(n.right);
        else  return Integer.parseInt(n.data.toString());       
    }   
}
  class Node
    {
        Character data;
        Node left; 
        Node right;		
        Node(char sth)
        {
           data=sth;
        }      
        public String toString(){
            return data.toString();
        }     
    }
   class GUI extends JPanel implements TreeSelectionListener {
    private JEditorPane htmlPane;
    private JTree tree;
    private URL helpURL;
    private static boolean DEBUG = false;
 
    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";
     
    //Optionally set the look and feel.
    private static boolean useSystemLookAndFeel = false;
 
    public GUI() {
        super(new GridLayout(1,0));
 
        //Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode(Homework1.tree);
        createNodes(top,Homework1.tree);
 
        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
 
               //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
 
        if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            tree.putClientProperty("JTree.lineStyle", lineStyle);
        }
 
        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);
 
        //Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        initHelp();
        JScrollPane htmlView = new JScrollPane(htmlPane);
 
        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);
 
        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100); 
        splitPane.setPreferredSize(new Dimension(500, 300));
        
        ImageIcon NI =  createImageIcon("middle.gif");
 		DefaultTreeCellRenderer rend = new DefaultTreeCellRenderer();
 		rend.setOpenIcon(NI);
 		rend.setClosedIcon(NI);
 		tree.setCellRenderer(rend);
        //Add the split pane to this panel.
        add(splitPane);
    }
 
    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode TreeN = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();
 
        if (TreeN == null) return;
 
       Object nodeIn = TreeN.getUserObject();
       Node y = (Node)nodeIn;
       Homework1.inorder(y);
       htmlPane.setText(y.data.toString());
                  
    }

     protected static ImageIcon createImageIcon(String path) {
            java.net.URL imgURL = GUI.class.getResource("middle.gif");
             if (imgURL != null) {
               return new ImageIcon(imgURL);
             } else {
                 System.err.println("Couldn't find file: " + path);
                 return null;
             }
         }
 
    private class BookInfo {
        public String bookName;
        public URL bookURL;
 
        public BookInfo(String book, String filename) {
            bookName = book;
            bookURL = getClass().getResource(filename);
            if (bookURL == null) {
                System.err.println("Couldn't find file: "
                                   + filename);
            }
        }
 
        public String toString() {
            return bookName;
        }
    }
 
    private void initHelp() {
        String s = "TreeDemoHelp.html";
        helpURL = getClass().getResource(s);
        if (helpURL == null) {
            System.err.println("Couldn't open help file: " + s);
        } else if (DEBUG) {
            System.out.println("Help URL is " + helpURL);
        }
 
        displayURL(helpURL);
    }
 
    private void displayURL(URL url) {
        try {
            if (url != null) {
                htmlPane.setPage(url);
            } else { //null url
        htmlPane.setText("File Not Found");
                if (DEBUG) {
                    System.out.println("Attempted to display a null URL.");
                }
            }
        } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + url);
        }
    }
 
    private void createNodes(DefaultMutableTreeNode top ,Node tree) {
        
        if(tree.left != null){
                 
                DefaultMutableTreeNode lt = new DefaultMutableTreeNode(tree.left);
                top.add(lt);
                createNodes(lt,tree.left);
             }
             if(tree.right != null){
                 
                DefaultMutableTreeNode rt = new DefaultMutableTreeNode(tree.right);
                top.add(rt);
                createNodes(rt,tree.right);
            }
    }
         
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI() {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }
 
        //Create and set up the window.
        JFrame frame = new JFrame("TreeDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new GUI());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    
}

