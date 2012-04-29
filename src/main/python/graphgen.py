import networkx as nx

f = open("../../../socialist.map", "w")
G = nx.newman_watts_strogatz_graph(100, 25, .25)
ec = 0
for edge in G.edges():
	f.write(str(edge[0]) + ":" + str(edge[1]) + "\n")
	ec += 1
print ec
