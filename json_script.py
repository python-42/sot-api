def getName(all):
    all = str(all)
    i = all.index(":")
    i2 = all.index(",")
    return all[i:i2].replace(": ", "").replace("'", "").replace(" ", "_").replace('"', "").lower()


path = "./api/src/main/resources/static/islands/"

with open('all.json', 'r') as f:
    data = f.read()

data.replace("[", " ")
data.replace("]", " ")

arr = data.split("},")

f = None

for i in arr:
    name = getName(i)
    i = i.replace("    ", "")
    i = i.replace("  ", "")
    i = i[:len(i) -1]
    i = i + ',\n"imagePath" : "api/src/main/resources/static/images/'+name+'.png"\n' + '}'
    
    f = open(path + name +".json", "w")
    f.write(i)
