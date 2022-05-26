#Set text for answer

def main(num):
	msg = ""
	if(num == 0):
		msg = "LET'S GO, touch me to start"
	elif(num == 1):
		msg = "ANSWER A IS..."
	elif(num == 3):
		msg = "ANSWER B IS..."
	elif(num == 5):
		msg = "ANSWER C IS..."
	elif(num == 7):
		msg = "ANSWER D IS..."
	elif(num == -1):
		msg = "your answer is..."
	return msg