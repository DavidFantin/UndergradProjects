#Tyler Moore
#Starter code for HW5 Q1

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

def playlist_transform(s,t,compareType="Song"):
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
