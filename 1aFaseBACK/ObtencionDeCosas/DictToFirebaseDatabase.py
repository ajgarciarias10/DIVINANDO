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
##STRINGS DE CADA DICCIONARIO
dictEspañol = 'listado-general.txt'
dictBritish = 'wordsBritishhhh.txt'



##CREAMOS UN RESULTADO ARRAYLIST DE LO QUE TE DEVUELVE LA FUNCIÓN EXTRAE DICCIONARIO
listadepalabritas =  extr.extractDictionary(dictEspañol)
palabrasEspañolasPataNegra = extr.isnotAccent(listadepalabritas)
palabrasTildeanasEspañolasPataNegra = extr.isAccent(listadepalabritas)
palabrasBritanicasPataPirata = extr.extractDictionary(dictBritish)


##CREAMOS UN ARRAY DE DATOS CON LOS ARRAYLIST DENTRO DEL PROPIO ARRAY DE DATOS
datos ={
    'palabrasBritanicas': palabrasBritanicasPataPirata,    
    'palabrasEspañola' : palabrasEspañolasPataNegra,
    'palabrastildeanas' : palabrasTildeanasEspañolasPataNegra
}

#UTILIZAMOS METODO POST PARA SUBIR EL ARRAY DE DATOS A FIREBASE
#Add documents
db.collection('Diccionario').document("palabras").set(datos)