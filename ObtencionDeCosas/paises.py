import extractingDataFromTXT as extr
class paises :
    def __init__(self, nombredepais, image):
        self.nombredepais = nombredepais
        self.image = image


countries = extr.extractCountries()
imagenes = extr.extractDataFromArrayList(extr.gettingCSV())
paises(countries,imagenes)




