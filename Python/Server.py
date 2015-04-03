import socket 
import time

backlog = 1 
size = 1024 
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
serverSocket.bind(('', 20113)) 
serverSocket.listen(backlog) 
print 'Server online'

while 1:
	clientsock, addr = serverSocket.accept()

	data = clientsock.recv(size)
	if data: 
		print 'Received ping'
		time.sleep(0.1)
		clientsock.send('pong')
		clientsock.close()
	else:
		time.sleep(1);

serverSocket.close()

