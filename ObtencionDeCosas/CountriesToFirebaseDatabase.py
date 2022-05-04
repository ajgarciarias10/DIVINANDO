##IMPORTAMOS LIBRERIAS FIREBASE
import firebase_admin 
from firebase_admin import credentials
from firebase_admin import firestore
##IMPORTAMOS FUNCION DE EXTRAER DATA DE UN TXT EN FIREBASE
import extractingDataFromTXT as extr
##Conectamos con nuestra base de datos en Firebase
cred = credentials.Certificate("./loginandregister-86bb1-firebase-adminsdk-rzsgq-76c0f3e43d.json")

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
db.collection('Paises').add(paises)
