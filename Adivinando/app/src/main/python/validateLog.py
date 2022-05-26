#validations login

def main(mail, password):
	msg = ""
	if mail == "" or password == "":
		msg = "Empty field, please check it, by python"
	elif len(password) < 8:
		msg = "Min password 8 characters" 
	return msg   