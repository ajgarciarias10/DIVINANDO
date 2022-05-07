##IMPORTAMOS LIBRERIAS FIREBASE
import firebase_admin 
from firebase_admin import credentials
from firebase_admin import firestore
from numpy import extract
##IMPORTAMOS FUNCION DE EXTRAER DATA DE UN TXT EN FIREBASE
import extractingDataFromTXT as extr
import traduciendotextoALoQueQueramos as traduct
##Conectamos con nuestra base de datos en Firebase
cred = credentials.Certificate("./loginandregister-86bb1-firebase-adminsdk-rzsgq-76c0f3e43d.json")

firebase_admin.initialize_app(cred)
db=firestore.client()

paises ={
    "EnglishCountries" : extr.devuelvepaisEnIngles(),
    "Paises": extr.devuelvepaisEnEspañol(),
    "urls" : extr.url()

}



    
    #Add documents
db.collection('Paises').document("ListaPaises").set(paises)
