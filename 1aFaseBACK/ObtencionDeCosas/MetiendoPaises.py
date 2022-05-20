##IMPORTAMOS LIBRERIAS FIREBASE
import firebase_admin 
from firebase_admin import credentials
from firebase_admin import firestore
from numpy import extract
##IMPORTAMOS FUNCION DE EXTRAER DATA DE UN TXT EN FIREBASE
import extractingDataFromTXT as extr
import traduciendotextoALoQueQueramos as traduct
##Conectamos con nuestra base de datos en Firebase
cred = credentials.Certificate("./divinando-5bf80-firebase-adminsdk-j0xou-12697c0f12.json")

firebase_admin.initialize_app(cred)
db=firestore.client()

paises ={
    "EnglishCountries" : extr.devuelvepaisEnIngles(),
    "Paises": extr.devuelvepaisEnEspañol(),
    "urls" : extr.url()

}



    
    #Add documents
db.collection('Paises').document("ListaPaises").set(paises)
