package chatApplication;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame 
{
   static JButton sb;
   static JButton sbb;
   static JButton sbu;
   static JTextField tf;
   static JTextField tf1;
   static JTextArea ta;
   static JLabel lc;
   static JLabel lf;
   static ServerSocket ss;
   static Socket soc;
   static DataInputStream dis;
   static DataOutputStream dout;
   static JFileChooser j;
   static String FILE_TO_SEND;
   static FileInputStream fis;
   static BufferedInputStream bis;
   static OutputStream os;
   static int x=10;
   
	public Server(){}
	public Server(String s)
	{
		super(s);
	}
	
	public void setComponents()
	{
		sb = new JButton("SEND");
		sbu = new JButton("UPLOAD FILE");
		sbb = new JButton("BROWSE");
		lc = new JLabel("CHAT SYSTEM");
		lf = new JLabel("FTS");
		tf = new JTextField();
		ta = new JTextArea();
		tf1 = new JTextField();
		

		setLayout(null);

		ta.setBounds(10,70,340,200);
		tf.setBounds(10,280,250,50);
		tf1.setBounds(10,400,250,50);
		sb.setBounds(270,280,80,45);
		sbb.setBounds(270,400,100,45);
		sbu.setBounds(60,460,150,45);
		lc.setBounds(100,10,100,40);
		lf.setBounds(120,350,80,50);

		add(sb);
		add(tf);
		add(ta);
		add(tf1);
		add(sbb);
		add(sbu);
		add(lc);
		add(lf);
		
		sb.addActionListener(new SHandler());
		sbu.addActionListener(new UHandler());
		sbb.addActionListener(new BHandler());
 	}

	class SHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String msg="";
				msg=tf.getText();
				dout.writeUTF(msg);
				tf.setText("");
				x=20;
			}
			catch(Exception f)
			{
				f.printStackTrace();
			}			

		}
	}

	class UHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				dout.writeUTF("F!LE");
				FILE_TO_SEND=tf1.getText();
				dout.writeUTF(FILE_TO_SEND);
				tf1.setText("");
				System.out.println("Accepted connection : " + soc);
          // send file
				File myFile = new File (FILE_TO_SEND);
				byte [] mybytearray  = new byte [(int)myFile.length()];
				fis = new FileInputStream(myFile);
				bis = new BufferedInputStream(fis);
				bis.read(mybytearray,0,mybytearray.length);
				os = soc.getOutputStream();
				System.out.println("Sending " +FILE_TO_SEND+ "(" + mybytearray.length + " bytes)");
				os.write(mybytearray,0,mybytearray.length);
				os.flush();
				System.out.println("Done.");
		
			
			}//try
			catch(Exception f)
			{
				f.printStackTrace();
			}			

		}
	}



	private static class BHandler implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e)
		{
			
    		    String com = e.getActionCommand(); 
  
         		   JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
            		   int r = j.showOpenDialog(null); 

            		   if (r == JFileChooser.APPROVE_OPTION) 
				{
                  		   tf1.setText(j.getSelectedFile().getAbsolutePath()); 
				           FILE_TO_SEND=j.getSelectedFile().getAbsolutePath();
				}


         	 	  else
             		  	tf1.setText("No File Choosen"); 
       		  
	       }
	}




 	public static void main(String[] args)
	{
		Server jf=new Server("SERVER");
		jf.setComponents();
		jf.setVisible(true);
		jf.setSize(390,550);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try{
		String msgin="";
		ss=new ServerSocket(9806);
		soc=ss.accept();
		dis=new DataInputStream(soc.getInputStream());
		dout=new DataOutputStream(soc.getOutputStream());
		FileInputStream fis = null;
    		BufferedInputStream bis = null;
    		OutputStream os = null;
		

		//for chat
		while(!msgin.equals("exit"))
		{
			msgin=dis.readUTF();
			ta.setText(ta.getText()+"\n Client : "+msgin);
		}
		
		
		//for ftp	
		
		
		

		}//end of try


		catch(Exception e) 
		{
		 e.printStackTrace();
	 	}

		/*finally
		{
			if (bis != null) bis.close();
          		if (os != null) os.close();
          		if (soc!=null) soc.close();
		}*/

		
	}
}