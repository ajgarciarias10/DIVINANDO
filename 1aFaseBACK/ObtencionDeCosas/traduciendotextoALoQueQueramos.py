from deep_translator import GoogleTranslator
def traduceloquequieras(idioma,lista):
    traductor = GoogleTranslator(source='auto', target=idioma)
    resultado = traductor.translate(lista)
    return  resultado
#-----------------------------------------------------------------------------

#www.Tecsify.com/blog
  
        