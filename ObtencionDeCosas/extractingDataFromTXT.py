import extractingDataFromTXT as extr
import json
##LIBRERIA EXTRACCION CSV
import pandas as pd
import traduciendotextoALoQueQueramos as traduct
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
##fUNCION QUE SE METE EN UN JSON Y EXTRAE SUS DATOS
def extractDataFromJson(archivo):
    f = open(archivo, "r")
    content = f.read()
    jsondecoded = json.loads(content)
    return jsondecoded
def devuelvepaisEnIngles():
        ##Extraemos el país en ingles
    paiEnIngle = list()
    for entity in extr.extractDataFromJson('countries.json')["Countries"]:
        ##DATO DE TIPO PAIS
            paiEnIngle.append(entity["name"])
    return paiEnIngle
def devuelvepaisEnEspañol():
        ##Extraemos el país en ingles
    paiEnIngle = list()
    for entity in extr.extractDataFromJson('countries.json')["Countries"]:
        ##DATO DE TIPO PAIS
            paiEnIngle.append(traduct.traduceloquequieras('es',entity["name"]))
    return paiEnIngle
def url():
        ##Extraemos el país en ingles
    paiEnIngle = list()
    for entity in extr.extractDataFromJson('countries.json')["Countries"]:
        ##DATO DE TIPO PAIS
            paiEnIngle.append((entity["flag"]))
    return paiEnIngle
        