##Client-Server Socket Program
Execute `python Server.py` to run the server. After the server is up and running, 
the client can be run by invoking `python Client.py`. After this, the client will 
connect to the server and ping 10 messages. The server will reply with a Pong and 
how long the RTT for the message was. The RTT is artificially delayed to allow for
longer times as times on server were reaching 0 ms. The client program will shut 
down after sending and receiving all 10 messages. The server however, will remain 
active to take on other connections. The server can connect with multiple clients 
at the same time.
