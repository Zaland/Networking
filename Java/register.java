
public class Register 
{
	private String to;
	private String from;
	private String callid;
	private String CSeq;
	private String expires;
	private String contact;
	private int registered;
	
	// submits the values of the registration ticket
	public Register(String to, String from, String callid, String CSeq, String expires, String contact)
	{
		this.to = to;
		this.from = from;
		this.callid = callid;
		this.CSeq = CSeq;
		this.expires = expires;
		this.contact = contact;
		registered = 0;
	}
	
	// checks if all the values are filled in the node
	public boolean checkValues()
	{
		if(to == null) return false;
		else if(from == null) return false;
		else if(callid == null) return false;
		else if(CSeq == null) return false;
		else if(expires == null) return false;
		else if(contact == null) return false;
		else return true;
	}
	
	// returns error code or OK status based on above method
	// 200 = OK		404 = Error, Bad Headers
	/*public void checkRegister()
	{
		// registers the node
		if(checkValues() == true && registered == 0) 
			registered = 1;
		
		// de-registers the node
		else if(checkValues() == true && registered == 1)
			registered = 0;
		
		// error message for wrong values 
		else if(checkValues() == false) 
			System.out.println("Error 404 Bad Headers");
	}*/
	
	// prints the registration node information
	public String createInfo()
	{
		return "REGISTER " + to + " SIPL/1.0\nTo: " + to + "\nFrom: " + from + "\nCall-ID: " 
				+ callid + "\nCSeq: " + CSeq + "\nExpires: " + expires + "\nContact: " + contact + "\n";
	}
}
