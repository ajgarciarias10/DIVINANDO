##IMPORTAMOS LIBRERIAS FIREBASE
import firebase_admin 
from firebase_admin import credentials
from firebase_admin import firestore
##IMPORTAMOS FUNCION DE EXTRAER DATA DE UN TXT EN FIREBASE
import extractingDataFromTXT as extr
##Conectamos con nuestra base de datos en Firebase
cred = credentials.Certificate("./divinando-5bf80-firebase-adminsdk-j0xou-12697c0f12.json")

firebase_admin.initialize_app(cred)
db=firestore.client()

##Extraemos el país
countries = extr.extractCountriesFromArrayList(extr.gettingCSV())
##Extraemos las imagenes
imagenes = extr.extractURLFromArrayList(extr.gettingCSV())
##Extramos los paises pero en castellano
pais = extr.extractPaises("paisesEnEspañol.txt")
##DATO DE TIPO PAIS
paises = {
    'EnglishCountries' : countries,
    'Paises': pais,
    'Url' : imagenes
}
#Add documents
db.collection('Paises').document("ListaPaises").set(paises)


###############################FUNCIONES QUE YA NO SE UTILIZAN#################################################################################################

##FUNCION QUE PALABRAS CON 5 LETRAS DEL DICCIONARIO 
def extractPaises(archivo):
    ##CREAMOS UN ARRAYLIST DE PALABRA
    palabra=list()
    ##Abrimos el archivo lo lee
    with open(archivo,'r', encoding='utf-8') as f:
        ##Para cada linea en la lectura
        for line in f:
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
def  extractURLFromArrayList(arrayconlasCosas) :
    ##Almacenamos en un arraylist de Imagenes cada imagen
    imagesURL = list()
    tuples = [tuple(x) for x in arrayconlasCosas.values]
    for each in tuples:
        imagesURL.append(each[2])
    return imagesURL
#Limpiamos los datos de la lista y obtenmos en la posicion 0 de la lista los nombres de los paises
def  extractCountriesFromArrayList(arrayconlasCosas) :
    ##Almacenamos en un arraylist de Imagenes cada imagen
    Countries = list()
    tuples = [tuple(x) for x in arrayconlasCosas.values]
    for each in tuples:
        Countries.append(each[0])
    ##Devolvemos el pais
    return Countries
