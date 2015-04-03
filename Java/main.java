// main class
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class main 
{
	private static boolean server = true;
	public static void main(String[] args) throws InterruptedException, IOException
	{
		Register r = new Register("zaland@cs.dal.ca", "zaland@cs.dal.ca", "a83fb", "0", "200", "129.173.67.10:20120");
		String regisPort = null, regisHost = null, peerPort = null, myIdentity = null, targetIdentity = null, filename = null, characters = null;
		
		/* test cases for the arguments provided 
		 * ------------------------------------------------------------------------------------------------------------ */
	
		// check for the first 3 options; regisPort, regisHost, and peerPort
		for(int i = 0; i < args.length; i++)
		{
			if(args[i].equals("-p") && i % 2 == 0)
			{
				regisPort = args[i+1];
				
				// checks for the last three options; targetIdentity, filename, and characters
				if(args.length == 10 || args.length == 8 || args.length == 6)
				{
					characters = args[args.length - 1];
					filename = args[args.length - 2];
					targetIdentity = args[args.length - 3];
				}
			}
			
			else if(args[i].equals("-h") && i % 2 == 0)
			{
				regisHost = args[i+1];
				
				// checks for the last three options; targetIdentity, filename, and characters
				if(args.length == 8 || args.length == 6)
				{
					characters = args[args.length - 1];
					filename = args[args.length - 2];
					targetIdentity = args[args.length - 3];
				}
			}
			
			else if(args[i].equals("-m") && i % 2 == 0)
			{
				peerPort = args[i+1];
				
				// checks for the last three options; targetIdentity, filename, and characters
				if(args.length == 6)
				{
					characters = args[args.length - 1];
					filename = args[args.length - 2];
					targetIdentity = args[args.length - 3];
				}
			}
		}
		
		// if no values are present in the beginning, still check for the three other values at end
		if(regisPort == null && regisHost == null && peerPort == null && args.length == 4)
		{
			characters = args[args.length - 1];
			filename = args[args.length - 2];
			targetIdentity = args[args.length - 3];
		}
		
		// sets the target_identity
		if(regisPort == null && regisHost == null && peerPort == null && (args.length == 1 || args.length == 4))
			myIdentity = args[0];
		
		else if((regisPort != null && regisHost == null && peerPort == null)
				|| (regisPort == null && regisHost != null && peerPort == null)
				|| (regisPort == null && regisHost == null && peerPort != null))
			myIdentity = args[2];
		
		else if((regisPort != null && regisHost != null && peerPort == null)
				|| (regisPort == null && regisHost != null && peerPort != null)
				|| (regisPort != null && regisHost == null && peerPort != null))
			myIdentity = args[4];
		
		else if(regisPort != null && regisHost != null && peerPort != null)
			myIdentity = args[6];
		
		// default values for the variables
		if(regisPort == null)
			regisPort = "11111";
		if(regisHost == null)
			regisHost = "Registration";
		if(peerPort == null)
			peerPort = "20100";
		
		System.out.println(regisPort + " " + regisHost + " " + peerPort + " " + myIdentity + "\n" + targetIdentity + " " + filename + " " + characters);
		
		/* ---------------------------------------------------------------------------------------------------------- */
		register(r);
		
		char[] a = readFile();
		
		runServer();		
	}	
	
	public static void runServer()
	{
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(11131);
		} catch (IOException e) {
			System.out.println("Server already running on this port. Switching to client.");
			runClient();
		}
		
		System.out.println("Server socket created");
		
		while (server == true)
		{
			// try to create a server
	    	Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("Error, client could not connect");
			}
		    System.out.println("Socket accepted");
			BufferedReader input = null;
			
			// try to get input from the client
			try {
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println("Input: " + input.readLine());
			} catch (IOException e) {
				System.out.println("Error, could not read input");
			}
		    
			// attempt to close the socket with the client
		    try {
				socket.close();
			} catch (IOException e) {
				System.out.println("Error, could not close socket");
			}		    
		}
		
		//unreachable code, to close server control-z or control-c
	    /*try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, could not close server socket");
		}*/
	}
	
	public static void runClient()
	{
		Scanner scan = new Scanner(System.in);
		// runs the client code
		try 
		{
		    Socket socket = new Socket("127.0.0.1", 1111);
		    PrintWriter output =  new PrintWriter(socket.getOutputStream(), true);
		    System.out.print("Output created\nInput: ");
		    output.write(scan.nextLine());

		    output.flush();
		    socket.close();
		} 
		
		// if the client cannot connect, then an error appears
		catch (IOException p) 
		{
			System.out.println("Error, could not connect to socket");
		}
	}
	
	// register with the registration node
	public static void register(Register r) throws IOException
	{
		Socket register = null;
        PrintWriter out = null;
        BufferedReader input = null;

        try {

            System.setProperty("java.net.preferIPv4Stack", "true");

            register = new Socket("bluenose.cs.dal.ca", 10000);
            out = new PrintWriter(register.getOutputStream(), true);
            out.flush();
            //out.write("REGISTER barney@cs.dal.ca SIPL/1.0\nTo: barney@cs.dal.ca\nFrom: barney@cs.dal.ca\nCall-ID: a83fb\nCSeq: 0\nExpires 200\nContact: 129.173.67.10:20120\n");
            out.write(r.createInfo());
        } catch (IOException e) {
	        System.err.println("Couldn't open socket for the connection.");
	        System.exit(1);
        }
        //input.close();
        out.close();
        register.close();
	}
	
	// grabs the characters from the file and stores it in an array
	public static char[] readFile()
	{
		String s = "";
		File file = new File("foo.txt");
		Scanner scanner;
		try 
		{
			scanner = new Scanner(file);
			s = scanner.nextLine();
			while (scanner.hasNextLine())
			       s = s + "\n" + scanner.nextLine();
			
			char[] charArray = s.toCharArray();
			return charArray;
		} 
		
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}