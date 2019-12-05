
from pdb import set_trace as bp
import re
import json
import numpy
import copy
#LOG_FILE = '../finalPresentacionPrioBajada.log'
#LOG_FILE = '../finalPresentacionPrioSubida.log'
LOG_FILE = '../finalTest.log'
#PETRI_FILE = '/home/ramiro/repos/Concurrente/src/petriNet/red_final_prio_bajada.json'
#PETRI_FILE = '/home/ramiro/repos/Concurrente/src/petriNet/red_final_prio_subida.json'
PETRI_FILE = '/home/ramiro/repos/Concurrente/src/petriNet/red_final.json'
N_TRANSICIONES = ['0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15']
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

                
                transiciones = remover_invariantes(transiciones,[['7','0','2','3','4','15','5','6'],['7','1','2','3','4','15','5','6'],['13','12','14'],['8','10','11','9']])
                
                for i in range(transiciones.count('*')):
                        transiciones.remove('*')

                print(transiciones)

                for i in N_TRANSICIONES:
                        print('La transcicion ', i,' sobro ', transiciones.count(i))
                nuevas = list(map(lambda x : int(x,10),transiciones))
                print(nuevas)
                probar_Tinv(nuevas, 16, marcados[-1])

                        
                #robar_Tinv(transiciones, N_TRANSICIONES, marcados[-1])

def remover_invariantes(ts, invvect):
        
        '''
        c = copy.deepcopy(ts)
        matches = [[] for y in range(len(invvect))]
        next_c_ptr =[0]*len(invvect)
        for x in range(10000):
                for ic,car in enumerate(c):
                        for ni,inv in enumerate(invvect):
                                if car == inv[next_c_ptr[ni]]:
                                        matches[ni].append(ic)
                                        next_c_ptr[ni] += 1
                                if next_c_ptr[ni] == len(inv):
                                        for indcar in matches[ni]:
                                                c[indcar] = '*'
                                        for j in range(len(matches)):
                                                matches[j] = []
                                                next_c_ptr[j] = 0
        return c
        '''
        #Compilamos las expresiones regulares para los tres invariantes
        reg_inv1 = re.compile(r"T7.*?T6")
        reg_inv2 = re.compile(r"T13.*?T14")
        reg_inv3 = re.compile(r"T8.*?T9")

        #filtramos por el primer invariante
        filter_regex1 = reg_inv1.search(ts)


                


def probar_Tinv(ts,ntrns,marca_log):
        sigma = []
        for i in range(ntrns):
                sigma.append([ts.count(i)])

        for ind,val in enumerate(sigma):
                print('Sigma[', ind, '] : ', val)
        print()
        sigma = numpy.matrix(sigma)

        with open(PETRI_FILE) as fd :
                jobs = json.loads(fd.read())
                matI = numpy.matrix(jobs['petriNet']['incidence_matrix'])
                Mi   = numpy.matrix(jobs['petriNet']['init_marking'])
                Mf   = numpy.transpose((matI * sigma)) + Mi
                Mlog = numpy.matrix(marca_log)
                print('\nMarcado final atravez de invariantes : \n\n',Mf,'\n')
                print('\nMarcado final del log :\n\n', Mlog, '\n')
                print('\nComparacion : ', Mlog == Mf)


if __name__ == '__main__':
        main()
