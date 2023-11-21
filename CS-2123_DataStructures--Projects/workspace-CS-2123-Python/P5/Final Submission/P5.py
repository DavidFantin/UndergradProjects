#Tyler Moore
#Starter code for HW5 Q1

#Justin McNiel and David Fantin

def read_playlist(filename):
	"""
	Input: filename of CSV file listing (song,artist,genre) triples
	Output: List of (song,artist,genre)
	"""
	playlist = []
	for line in open(filename):
		bits = [b.strip() for b in line.split(',')]
		playlist.append(bits)
	return playlist

def iter_playlist_transform(s,t,compareType="Song"):
	"""
	Computes the edit distance for two playlists s and t, and prints the minimal edits 
	  required to transform playlist s into playlist t.
	Inputs:
	s: 1st playlist (format: list of (track name, artist, genre) triples)
	t: 2nd playlist (format: list of (track name, artist, genre) triples)
	compareType: String indicating the type of comparison to make.
	   "Song" (default): songs in a playlist are considered equivalent if the 
		 (song name, artist, genre) triples match.
	   "Genre": songs in a playlist are considered equivalent if the same genre is used.
	   "Artist": songs in a playlist are considered equivalent if the same artist is used.
	Output: The minimum edit distance and the minimal edits required to transform playlist
	  s into playlist t.
	"""
	pass
	
	compare_operations = ['song', 'artist', 'genre']
	compare_index = compare_operations.index(compareType.lower())
	
	def equivalent(song1, song2):
		if compare_index == 0:
			return song1 == song2
		else:
			return song1[compare_index] == song2[compare_index]
	
	C = []
	
	tmp = [[]]
	tmp.extend(s)
	s = tmp
	
	tmp = [[]]
	tmp.extend(t)
	t = tmp
	
	edits = {}
	
	C.append([x for x in range(len(t)+1)])
	for i in range(len(s)):
		C.append([i+1])
		edits[i] = {}
		if i != 0:
			edits[i][0] = ("Remove %s\n"%(str(s[i])),2)
	
	for j in range(1,len(t)):
		edits[0][j] = ("Insert %s\n"%(str(t[j])),1)
		
	for i in range(1, len(s)):
		for j in range(1, len(t)):
			c_match, c_ins, c_del = None, None, None
			matchEdit = ""
			if equivalent(s[i],t[j]):
				c_match = C[i-1][j-1]
				matchEdit += "Leave %s\n"%(str(s[i]))
			else:
				c_match = C[i-1][j-1]+1
				matchEdit += "Replace %s with %s\n"%(str(s[i]),str(t[j]))
			
			c_ins = C[i][j-1]+1
			c_del = C[i-1][j]+1
			
			c_min = min(c_match, c_ins, c_del)
			
			if c_min == c_match:
				edits[i][j] = (matchEdit,0)
			elif c_min == c_ins:
				edits[i][j] = ("Insert %s\n"%(str(t[j])),1)
			elif c_min == c_del:
				edits[i][j] = ("Remove %s\n"%(str(s[i])),2)
			else:
				raise Exception('wut')
			
			C[i].append(c_min)
			
	print("%s edits required to turn playlist 1 into playlist 2."%C[i][j])
	
	tmpI, tmpJ = i, j
	
	outputLst = [edits[i][j][0]]
	
	while i != 0 or j != 0:
		code = edits[i][j][1]
		if code == 0:
			i -= 1
			j -= 1
		elif code == 1:
			i -= 1
		elif code == 2:
			j -= 1
		
		if i==0 and j==0:
			break
		
		try:
			outputLst.append(edits[i][j][0])
		except KeyError:
			'''for x in range(10):
				print('')
			print(outputLst, i, j)
			
			for x in edits.keys():
				print(x, edits[x])'''
			break
	
	outputLst.reverse()
	
	print(''.join(outputLst))
	
	i, j = tmpI, tmpJ
	
	return C[i][j]
		


if __name__=="__main__":
	#obtain local copy from http://secon.utulsa.edu/cs2123/blues1.csv
	b1 = read_playlist("blues1.csv")
	#obtain local copy from http://secon.utulsa.edu/cs2123/blues2.csv
	b2 = read_playlist("blues2.csv")
	print("Playlist 1")
	for song in b1:
		print(song)
	print("Playlist 2")
	for song in b2:
		print(song)
	print("Comparing playlist similarity by song")
	iter_playlist_transform(b1,b2)
	print("Comparing playlist similarity by genre")
	iter_playlist_transform(b1,b2,"Genre")
	print("Comparing playlist similarity by artist")
	iter_playlist_transform(b1,b2,"Artist")
	#include your own playlists below
	
	print("\n\n\n\n")
	p1 = read_playlist("Playlist1.csv")
	p2 = read_playlist("Playlist2.csv")
	print("Playlist 1")
	for song in p1:
		print(song)
	print("Playlist 2")
	for song in p2:
		print(song)
	print("Comparing playlist similarity by song")
	iter_playlist_transform(p1,p2)
	print("Comparing playlist similarity by genre")
	iter_playlist_transform(p1,p2,"Genre")
	print("Comparing playlist similarity by artist")
	iter_playlist_transform(p1,p2,"Artist")