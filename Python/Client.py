import socket
import time

size = 1024

for x in range(0,10):
	# Create the socket connection
	clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	clientSocket.connect(('bluenose.cs.dal.ca', 20113))

	# Grab user input for website
	#web = raw_input('Website: ')

	# Send a ping to the server
	start = int((time.time() * 1000))
	clientSocket.send('ping')

	# Get the server response (pong)
	data = clientSocket.recv(size)
	if data:
		print data
	# If there is no message, proceed to wait 1 second and then check again
	else:
		time.sleep(1)
		data = clientSocket.recv(size)
		# if message is found, then proceed to print the message
		if data:
			print data
		# if message is still not found, then notify the user and proceed
		else:
			print 'pong did not arrive'
			
	end = int((time.time() * 1000))
	print 'time: ', end-start, ' ms\n'
	time.sleep(0.5)

	# Close the socket after we are done with it
	clientSocket.close()
