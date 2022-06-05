#validations register

def main(mail, password, confirm, name):
	msg = ""
	if mail == "" or password == "" or confirm == "" or name == "":
		msg = "Empty field, please check it, by python"
	elif len(password) < 8 or len(confirm) < 8:
		msg = "Min password 8 characters" 
	elif len(name) < 4:
		msg = "Min name 4 character, check it"
	elif password != confirm:
		msg = "Pass and confirm pass are diferent"
	return msg 