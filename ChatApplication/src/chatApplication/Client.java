package chatApplication;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class Client extends JFrame 
{
   static JButton sb;
   static JTextField tf;
   static JTextArea ta;
   static Socket soc;
   static DataInputStream dis;
   static DataOutputStream dout;
   static String FILE_TO_RECEIVED="C:/Users/dataaa.txt";
   static int FILE_SIZE = 6022386;  
   static FileOutputStream fos;
   static BufferedOutputStream bos;


	public Client(){}
	public Client(String s)
	{
		super(s);
	}
	
	public void setComponents()
	{
		sb = new JButton("SEND");
		tf = new JTextField();
		ta = new JTextArea();
		setLayout(null);
		ta.setBounds(10,10,300,200);
		tf.setBounds(10,220,300,50);
		sb.setBounds(320,220,80,50);

		add(sb);
		add(tf);
		add(ta);
		
		sb.addActionListener(new Handler());
 	}

	class Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String msg="";
				msg=tf.getText();
				dout.writeUTF(msg);
				tf.setText("");

			}
			catch(Exception f)
			{
				f.printStackTrace();
			}			
		}
	}

 	public static void main(String[] args)
	{
		Client jf=new Client("CLIENT");
		jf.setComponents();
		jf.setVisible(true);
		jf.setSize(450,350);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{
		String msgin="";
		soc=new Socket("localhost",9806);
		 System.out.println("Connecting...");

		//InputStream is = soc.getInputStream();
		dis=new DataInputStream(soc.getInputStream());
		dout=new DataOutputStream(soc.getOutputStream());
		int bytesRead;
    		//int current = 0;
    		FileOutputStream fos = null;
    		BufferedOutputStream bos = null;
			//msgin=dis.readUTF();
		while(!msgin.equals("exit")){
			msgin=dis.readUTF();
			if(!msgin.equals("F!LE"))
			{
				
				ta.setText(ta.getText()+"\n Server : "+msgin);
			}
		    System.out.println("yep!!");
      // receive file
			if(msgin.equals("F!LE")){
				
				byte [] mybytearray  = new byte [FILE_SIZE];
				InputStream is = soc.getInputStream();
				fos = new FileOutputStream(FILE_TO_RECEIVED);
				bos = new BufferedOutputStream(fos);
				
				System.out.println("+t");
				bytesRead = is.read(mybytearray,0,mybytearray.length);
				
				bos.write(mybytearray, 0 , mybytearray.length);
				bos.flush();
				System.out.println("File " + FILE_TO_RECEIVED);
				
			}
			
			
		}
		}//try


		catch(Exception m) {
		 m.printStackTrace();
	 	}

		/*finally
		{
			
		if (fos != null) fos.close();
      		if (bos != null) bos.close();
      		if (soc != null) soc.close();
		
		}*/


	}
}



