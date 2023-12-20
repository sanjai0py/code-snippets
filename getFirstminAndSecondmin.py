vals = [0]*101
for i in prices:
    vals[i]+=1

firstMin = 0
secondMin = 0

for i in range(1, 100):
    if vals[i] >= 2:
        firstMin = i
        secondMin = i
        break
    elif vals[i] == 1:
        firstMin = i
        break

if secondMin == 0:
    for i in range(firstMin + 1, 100):
        if vals[i] > 0:
            secondMin = i
            break # break on first min

total = firstMin + secondMin
return money - total if total <= money else money 
