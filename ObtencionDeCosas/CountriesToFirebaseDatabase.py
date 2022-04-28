##IMPORTAMOS LIBRERIAS FIREBASE
from firebase import firebase
##IMPORTAMOS FUNCION DE EXTRAER DATA DE UN TXT EN FIREBASE
import extractingDataFromTXT as extr
##Conectamos con nuestra base de datos en Firebase
firebase = firebase.FirebaseApplication("https://loginandregister-86bb1-default-rtdb.europe-west1.firebasedatabase.app/",None)
##Extraemos el país
countries = extr.extractCountriesFromArrayList(extr.gettingCSV())
##Extraemos las imagenes
imagenes = extr.extractURLFromArrayList(extr.gettingCSV())
##DATO DE TIPO PAIS
paises = {
    'pais' : countries,
    'url' : imagenes
}
#UTILIZAMOS METODO POST PARA SUBIR EL ARRAY DE DATOS A FIREBASE
resultado = firebase.post('/bd/quizCountries', paises)
