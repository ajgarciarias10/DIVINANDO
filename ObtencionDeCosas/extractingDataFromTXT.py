import extractingDataFromTXT as extr
##LIBRERIA EXTRACCION CSV
import pandas as pd
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
##FUNCION EXTRAE TODOS LOS PAISES DEL MUNDO EN INGLES
def extractCountries():
    ##CREAMOS UN ARRAYLIST DE PALABRA
    palabra=list()
     ##Abrimos el archivo lo lee
    with open('listado-paises.txt','r', encoding='utf-8') as f:
        for line in f:
            ##METODO DE AÑADIR PALABRA
            palabra.append(line.replace('\n',''))
    ##DEVOLVEMOS LA PALABRA       
    return palabra
#Funcion para coger los datos del csv que devuelve el objeto df
def  gettingCSV():
    #Leemos el csv para utilizar la librería pandas que lo convierte en tabla
    df = pd.read_csv("flags.csv")
    ##Devolvemos el array bidimensional
    return df
#Limpiar los datos de la lista y sacamos esos datos en tuplas
def  extractDataFromArrayList(arrayconlasCosas) :
    ##Almacenamos en un arraylist de Imagenes cada imagen
    imagesURL = list()
    tuples = [tuple(x) for x in arrayconlasCosas.values]
    for each in tuples:
        imagesURL.append(each[2])
    return imagesURL



