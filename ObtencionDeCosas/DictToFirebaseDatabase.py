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
##STRINGS DE CADA DICCIONARIO
dictEspañol = 'listado-general.txt'
dictBritish = 'wordsBritishhhh.txt'



##CREAMOS UN RESULTADO ARRAYLIST DE LO QUE TE DEVUELVE LA FUNCIÓN EXTRAE DICCIONARIO
palabrasEspañolasPataNegra = extr.extractDictionary(dictEspañol)
palabrasBritanicasPataPirata = extr.extractDictionary(dictBritish)


##CREAMOS UN ARRAY DE DATOS CON LOS ARRAYLIST DENTRO DEL PROPIO ARRAY DE DATOS
datos ={
    'palabrasBritanicas': palabrasBritanicasPataPirata,    
    'palabrasEspañola' : palabrasEspañolasPataNegra,
}

#UTILIZAMOS METODO POST PARA SUBIR EL ARRAY DE DATOS A FIREBASE
#Add documents
db.collection('Diccionario').add(datos)