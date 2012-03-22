import networkx as nx
import math, random

random.seed()
 
# Tokens from 1000 and up
_PRONOUNCE = [
    'vigintillion',
    'novemdecillion',
    'octodecillion',
    'septendecillion',
    'sexdecillion',
    'quindecillion',
    'quattuordecillion',
    'tredecillion',
    'duodecillion',
    'undecillion',
    'decillion',
    'nonillion',
    'octillion',
    'septillion',
    'sextillion',
    'quintillion',
    'quadrillion',
    'trillion',
    'billion',
    'million ',
    'thousand ',
    ''
]
 
# Tokens up to 90
_SMALL = {
    '0' : '',
    '1' : 'one',
    '2' : 'two',
    '3' : 'three',
    '4' : 'four',
    '5' : 'five',
    '6' : 'six',
    '7' : 'seven',
    '8' : 'eight',
    '9' : 'nine',
    '10' : 'ten',
    '11' : 'eleven',
    '12' : 'twelve',
    '13' : 'thirteen',
    '14' : 'fourteen',
    '15' : 'fifteen',
    '16' : 'sixteen',
    '17' : 'seventeen',
    '18' : 'eighteen',
    '19' : 'nineteen',
    '20' : 'twenty',
    '30' : 'thirty',
    '40' : 'forty',
    '50' : 'fifty',
    '60' : 'sixty',
    '70' : 'seventy',
    '80' : 'eighty',
    '90' : 'ninety'
}
 
def get_num(num):
    '''Get token <= 90, return '' if not matched'''
    return _SMALL.get(num, '')
 
def triplets(l):
    '''Split list to triplets. Pad last one with '' if needed'''
    res = []
    for i in range(int(math.ceil(len(l) / 3.0))):
        sect = l[i * 3 : (i + 1) * 3]
        if len(sect) < 3: # Pad last section
            sect += [''] * (3 - len(sect))
        res.append(sect)
    return res
 
def norm_num(num):
    """Normelize number (remove 0's prefix). Return number and string"""
    n = int(num)
    return n, str(n)
 
def small2eng(num):
    '''English representation of a number <= 999'''
    n, num = norm_num(num)
    hundred = ''
    ten = ''
    if len(num) == 3: # Got hundreds
        hundred = get_num(num[0]) + ' hundred'
        num = num[1:]
        n, num = norm_num(num)
    if (n > 20) and (n != (n / 10 * 10)): # Got ones
        tens = get_num(num[0] + '0')
        ones = get_num(num[1])
        ten = tens + ' ' + ones
    else:
        ten = get_num(num)
    if hundred and ten:
        return hundred + ' ' + ten
        #return hundred + ' and ' + ten
    else: # One of the below is empty
        return hundred + ten
 
#FIXME: Currently num2eng(1012) -> 'one thousand, twelve'
# do we want to add last 'and'?
def num2eng(num):
    '''English representation of a number'''
    num = str(long(num)) # Convert to string, throw if bad number
    if (len(num) / 3 >= len(_PRONOUNCE)): # Sanity check
        raise ValueError('Number too big')
 
    if num == '0': # Zero is a special case
        return 'zero'
 
    # Create reversed list
    x = list(num)
    x.reverse()
    pron = [] # Result accumolator
    ct = len(_PRONOUNCE) - 1 # Current index
    for a, b, c in triplets(x): # Work on triplets
        p = small2eng(c + b + a)
        if p:
            pron.append((p + ' ' + _PRONOUNCE[ct])[:-1])
        ct -= 1
    # Create result
    pron.reverse()
    return ', '.join(pron)

def genGraph(nodes, neighbors, p):
	G = None
	try:
		G = nx.newman_watts_strogatz_graph(nodes, neighbors, p)
		return G
	except Exception as e:
		print e
	return None
	# for i in range(nodes):
		# G.node[i]["name"] = num2eng(i).replace(" ", "").replace(",", "")
		# G.node[i]["username"] = num2eng(i).replace(" ", "").replace(",", "")
		# G.node[i]["email"] = G.node[i]["name"] + "@gmail.cad"
		# G.node[i]["age"] = i % 50 + int(random.random() * 20) + 18
		# G.node[i]["network"] = num2eng(i % 10)
		# G.node[i]["password"] = num2eng(i).replace(" ", "").replace(",", "")
		# G.node[i]["birthday"] = str(i % 12 + 1) + "/" + str(i % 31 + 1) + "/" + str(i % 1000 + 1950)

def printGraph(G, f):
	for i in range(G.number_of_nodes()):
		f.write(str(i) + ":\n")
		n = G.neighbors(i)
		f.write("  neighbors:\n")
		for neighbor in n:
			f.write("    - " + str(neighbor) + "\n")
		#for t in ["name", "username", "email", "age", "network", "password", "birthday"]:
		#	f.write("  " + t +": " + str(G.node[i][t]) + "\n")
	f.close()
	
try:
	printGraph(genGraph(100000, 300, 0.5), open("test.stor.yml", "w"))
except Exception as e:
	print e
	raw_input()