import FunctionsforBack

correcto=False
while correcto==False:
        nombre=input("Ingrese email ")
        if FunctionsforBack.isEMAILValid(nombre) == True:
            print("Usuario creado exitosamente")
            correcto=True

while correcto==True:
    contrasenia=input("Ingrese su Password: ")
    if FunctionsforBack.isPaswordValid(contrasenia)==True:
        print("Contraseña creada exitosamente")
        correcto=False