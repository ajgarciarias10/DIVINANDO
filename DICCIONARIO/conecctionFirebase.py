##IMPORTAMOS LIBRERIAS FIREBASE
from firebase import firebase
##IMPORTAMOS FUNCION DE EXTRAER DATA DE UN TXT EN FIREBASE
import extractingDataFromTXT as extr
##Conectamos con nuestra base de datos en Firebase
firebase = firebase.FirebaseApplication("https://loginandregister-86bb1-default-rtdb.europe-west1.firebasedatabase.app/",None)

##STRINGS DE CADA DICCIONARIO
dictEspañol = 'listado-general.txt'
dictBritish = 'wordsBritishhhh.txt'

##CREAMOS UN RESULTADO ARRAYLIST DE LO QUE TE DEVUELVE LA FUNCIÓN EXTRAE DICCIONARIO
palabrasEspañolasPataNegra = extr.extractDictionary(dictEspañol)
palabrasBritanicasPataPirata = extr.extractDictionary(dictBritish)


##CREAMOS UN ARRAY DE DATOS CON LOS ARRAYLIST DENTRO DEL PROPIO ARRAY DE DATOS
datos ={
    'BR': palabrasBritanicasPataPirata,    
    'ES' : palabrasEspañolasPataNegra,
}

#UTILIZAMOS METODO POST PARA SUBIR EL ARRAY DE DATOS A FIREBASE
resultado = firebase.post('/bd/diccionarios',datos)