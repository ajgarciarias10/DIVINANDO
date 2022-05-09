import re #IMPORTACION LIBRERIA EXPRESIONES REGULARES

####################################################################################
####################################################################################

##FUNCIONES VALIDORAS DE CONTRASEÑA Y EMAIL


def isEMAILValid(correo):
    #Se utiliza expresiones regulares validoras de email
    expresion_regular = r"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])"
    return re.match(expresion_regular, correo) is not None

def isPaswordValid(contraseña):
    #Variable  de validacion
    validate=False 
    #Calcula la longuitud de la contraseña
    length=len(contraseña)
    #Variable de validacion de espacio
    space=False
    #Variable de validacion de mayus
    mayus=False
    #Variable de validacion de minus 
    minus=False
    #Variable de validacion de numbers 
    numbers=False
    #Variable de validacion de si es alfanumerica 
    isAlphaNumeric=contraseña.isalnum()
    #Variable de validacion de datos
    correctData=True 
    #Bucle que recorre los caracteres de la contraseña   
    for carac in contraseña: 
        #Si hay espacio
        if carac.isspace()==True: 
            #Variable de espacio se pone en Verdadero
            space=True 
        #Si es  mayuscula
        if carac.isupper()== True: 
            #Variable de mayuscula se pone en Verdadero
            mayus=True 
        #Si es  minuscula   
        if carac.islower()== True:
            #Variable de minuscula se pone en Verdadero
            minus=True 
        #Si solo hay numero  
        if carac.isdigit()== True: 
            #Variable de numeros se pone en Verdadero
            numbers=True 
    #Si hay espacio                    
    if space==True:
            print("La contraseña no puede contener espacios")
    else:
        #Variable de si esta validado todo se pone en Verdadero
        validate=True 
    #Comprobamos la longuitud de la contraseña si es menor que 8               
    if length <8 and validate==True:
        print("Mínimo 8 caracteres")
        #cambia a False la validacion
        validate=False 
    #Si  son correctas las  mayus, minus, numbers isAlphaNumeric no alfanuméricos
    if mayus == True and minus ==True and numbers == True and isAlphaNumeric== False and validate ==True:
        validate = True 
    #Si uno o mas requisitos de mayus, minus, numbers isAlphaNumeric no alfanuméricos no se cumple
    else:
        correctData=False 
    #En caso de que la variable correct data sea falsa     
    if validate == True and correctData==False:
        print("La contraseña elegida no es segura: debe contener letras minúsculas, mayusculas, números alfanumericos y  al menos 1 carácter no alfanumérico @/&etc...")

    if validate == True and correctData ==True:
        return True

####################################################################################
####################################################################################