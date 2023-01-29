
foo = {'a':[5,6,3],'b':[3,9,7],'c':[1,4,5]}

#goal: sort the keys by the third element in the value's list
foosrt = [((foo[x][2],x)) for x in foo]
print(foosrt)
foosrt.sort()
print(foosrt)
for val,key in foosrt:
    print(val, key)

#examples with heaps
from heapq import heappush , heappop, heapify


bar = [[3,"hey"],[1,"sup"],[5,"yo"]]
heapify(bar)
while bar:
    key,val =  heappop(bar)
    print(key, val)
    
print(bar)    

bar = [[3,"hey"],[1,"sup"],[5,"yo"]]
heapify(bar)
for k, v in bar:
    print(k,v)

print(bar)

bar2 = [[98,"hi"],[8,"hola"],[41,"hallo"]]
bar3 = []
for k, v in bar2:
    heappush(bar3,(k,v))


print("bar3: ", bar3)
print("bar2: ",bar2)

print("popping off the priority queue for bar3")
while(bar3):
    print(heappop(bar3))

