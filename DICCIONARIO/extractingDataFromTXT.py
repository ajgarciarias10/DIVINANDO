##FUNCION QUE PALABRAS CON 5 LETRAS DEL DICCIONARIO 
def extractDictionary(archivo):
    ##CREAMOS UN ARRAYLIST DE PALABRA
    palabra=list()
    ##Abrimos el archivo lo lee
    with open(archivo,'r', encoding='utf-8') as f:
        ##Para cada linea en la lectura
        for line in f:
            ##SI LA LONGITUD ES 6 ES POR QUE HAY ESPACIOS ENTRE CADA PALABRA POR LO QUE EN VEZ DE 5 SON 6
            if(len(line)==6):
                ##METODO DE AÑADIR PALABRA
                palabra.append(line.replace('\n',''))
    ##DEVOLVEMOS LA PALABRA       
    return palabra