
from pdb import set_trace as bp
import re
import json
import numpy
import copy
LOG_FILE = '../finalPresentacion.log'
PETRI_FILE = '/home/dabratte/repos/Concurrente/src/petriNet/red_final.json'
N_TRANSICIONES = ['T0','T1','T2','T3','T4','T5','T6','T7','T8','T9','T10','T11','T12','T13','T14','T15']
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

                for i in N_TRANSICIONES :
                        if transiciones.count(i) < 1:
                                print('La transicion : ', i, ' no se disparo nunca\n')

                for i in N_TRANSICIONES :
                        print('La transicion : ', i, ' se disparo ', transiciones.count(i))

                for i in range(len(transiciones)):
                        if transiciones[i] == 'T1' :
                                transiciones[i] = 'T0'

                
                t_inv_mat = []
                t_inv_mat.append(remover_invariantes(transiciones,['T7','T0','T2','T3','T4','T15','T5','T6']))
                #t_inv_mat.append(remover_invariantes(transiciones,['T8','T10','T9','T11']))
                t_inv_mat.append(remover_invariantes(transiciones,['T13','T12','T14']))
                clean = []
                for i in t_inv_mat:
                        bp()
                        clean += i.split('T')
                for i in range(len(transiciones)) :
                        if transiciones[i] in ['T7','T0','T2','T3','T4','T15','T5','T6','T13','T12','T14'] :
                                transiciones[i] = ''

                clean += ''.join(transiciones).split('T')
                probar_Tinv(clean, marcados[-1])

def remover_invariantes(ts, invariantes):
        '''
        min_rep = len(ts)

        #obtengo cantidad de invariantes completos
        for inv in invariantes:
                if ts.count(inv) < min_rep:
                        min_rep = ts.count(inv)

        #remuevo las secuencias invariantes
        for inv in invariantes :
                for k in range(min_rep):
                        ts.remove(inv)
        '''
        nv = copy.deepcopy(ts)
        for i in range(len(nv)):
                if nv[i] not in invariantes:
                        nv[i] = ''
        union = ''.join(nv)
        inv = ''.join(invariantes)
        union = union.replace(inv, 'x')
        print('resto del invariante ', inv)
        print('es : ', union)
        return union
        
def probar_Tinv(ts,marca_log):
        sigma = []
        for i in N_TRANSICIONES:
                sigma.append([ts.count(i[1:])])
        bp()

        sigma = numpy.matrix(sigma)

        with open(PETRI_FILE) as fd :
                jobs = json.loads(fd.read())
                matI = numpy.matrix(jobs['petriNet']['incidence_matrix'])
                Mi   = numpy.matrix(jobs['petriNet']['init_marking'])
                Mf   = numpy.transpose((matI * sigma)) + Mi
                Mlog = numpy.matrix(marca_log)
                print('\nMarcado final atravez de invariantes : \n',Mf,'\n')
                print('Marcado final del log :\n', Mlog, '\n')
                print('Comparacion : ', Mlog == Mf)



if __name__ == '__main__':
        main()
