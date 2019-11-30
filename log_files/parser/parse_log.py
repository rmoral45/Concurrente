
from pdb import set_trace as bp
import re
import json
LOG_FILE = '../finalv4.log'
N_TRANSICIONES = 16
def main():

        with open(LOG_FILE) as fd:
                raw = fd.read()
                raw = re.findall('(?:INFO).*',raw)
                raw = list(map(lambda x : x.replace('INFO: ', ''), raw))
                jslist = []
                for item in raw :
                        jslist.append(json.loads(item))

                #genero lista de disparos y marcados

                transiciones = []
                marcados = []
                for js in jslist :
                        transiciones.append(js['disparo'])
                        marcados.append(js['marcado'])
                for i in range(N_TRANSICIONES):
                        if transiciones.count(i) < 1:
                                print('La transicion : ', i, ' no se disparo nunca\n')

                for i in range(N_TRANSICIONES):
                        print('La transicion : ', i, ' se disparo ', transiciones.count(i))



if __name__ == '__main__':
        main()
