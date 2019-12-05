


#c = ['0','0','5','11','5','4','5','12','1','5','5','2','3','5','5','5','5','5','4','5','7']

#c = ['0','11','12','11','5','4','0','1','2','5','12','1','5','5','2','3','5','5','5','5','5','4','5','7']
#c = ['0','0','1','11','2','12','17','0','11','5','4','0','1','2','5','12','17','1','5','5','2','3','5','5','5','5','5','4','5','7']
c = ['0','1','11','12','17','0','11','5','4','0','1','2','5','12','17','1','5','5','2','3','5','5','5','5','5','4','5','7']
invvect = [['0','1','2'],['0','11','12','17'],['3','4','7']]
'''
nmatches = [0,0,0]
print(invvect, '\n\n')
print('          ',c)
for num ,i in enumerate(invvect):
    ind = 0
    indv = []
    for k in range(len(c)):
        if c[k] == i[ind]:
            ind += 1
            indv.append(k)
        if ind == len(i):
            for r in indv:
                c[r] = '*'
                ind = 0;
                indv = []
                nmatches[num] += 1
            print('tentativo ' , c)
            print()
print (c)
print('\n\n', nmatches)
'''
print(invvect, '\n')
print (c)
matches = [[],[],[]]
rp_c = ['A','B','C','D','E','F']
next_c_ptr =[0,0,0]
for x in range(len(invvect)):
    for ic,car in enumerate(c):
        for ni,inv in enumerate(invvect):
            if car == inv[next_c_ptr[ni]]:
                matches[ni].append(ic)
                next_c_ptr[ni] += 1
            if next_c_ptr[ni] == len(inv):
                rr = rp_c.pop()
                for indcar in matches[ni]:
                    c[indcar] = '*'
                for j in range(len(matches)):
                    matches[j] = []
                    next_c_ptr[j] = 0
                print(c)

print(c)
