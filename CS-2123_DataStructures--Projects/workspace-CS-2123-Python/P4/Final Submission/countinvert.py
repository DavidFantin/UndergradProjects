# Bryan Lavender
# David Fantin
# 10/31/19
# Project 4: Counting Inversions
# CS 2123, The University of Tulsa

from collections import deque
import itertools

def mergeandcount(lft,rgt):
    """
    Glue procedure to count inversions between lft and rgt.
    Input: two ordered sequences lft and rgt
    Output: tuple (number inversions, sorted combined sequence)
    """   
    lftp = 0        # Left pointer
    rgtp = 0        # Right pointer
    varcnt = 0      # Number of inversions
    out = []        # Sorted seq
    while lft and rgt:
        ai = lft[lftp]     # Value at the left pointer
        bj = rgt[rgtp]     # Value at the right pointer
        if bj < ai:            # If value at the right pointer < value at the left pointer:
            out.append(bj)         # Add the value at the right pointer to the sorted sequence
            rgt.remove(bj)         # Remove the value from the temporary rgt list
            varcnt += len(lft)     # Increment the number of inversions by the remaining length of lft 
            print(bj, " conflicts with ", lft)
        else:
            out.append(ai)
            lft.remove(ai)
    
    if lft:             # Adds the remainder in lft to the final sorted output
        out += lft
    if rgt:             # Adds the remainder in rgt to the final sorted output
        out += rgt

    to_return = (varcnt, out)
    return to_return

def sortandcount(seq):
    """
    Divide-conquer-glue method for counting inversions.
    Function should invoke mergeandcount() to complete glue step.
    Input: ordered sequence seq
    Output: tuple (number inversions, sequence)
    """
    if len(seq) > 1: 
        mid = len(seq)//2                     # Splits seq
        lft , rgt = seq[:mid] , seq[mid:]     # lft = left side of seq; rgt = right side of seq
        ra = sortandcount(lft)                # Recusive call until lft has length 1
        rb = sortandcount(rgt)                # Recusive call until rgt has length 1
        r = mergeandcount(ra[1],rb[1])        
    else:
        return (0, seq)
       
    num = ra[0] + rb[0] + r[0]                # Number of inversions
    to_return = (num, r[1])                   # r[1] = sorted sequence
    return to_return

if __name__ =="__main__":
    seq1 = [7, 10, 18, 3, 14, 17, 23, 2, 11, 16]
    seq2 = [2, 1, 3, 6, 7, 8, 5, 4, 9, 10]
    seq3 = [1, 3, 2, 6, 4, 5, 7, 10, 8, 9]
    songs1 = [(1,"Stevie Ray Vaughan: Couldn't Stand the Weather"),
             (2,"Jimi Hendrix: Voodoo Chile"),
             (3,"The Lumineers: Ho Hey"),
             (4,"Adele: Chasing Pavements"),
             (5,"Cake: I Will Survive"),
             (6,"Aretha Franklin: I Will Survive"),
             (7,"Beyonce: All the Single Ladies"),
             (8,"Coldplay: Clocks"),
             (9,"Nickelback: Gotta be Somebody"),
             (10,"Garth Brooks: Friends in Low Places")]
    songs2 = [(3,"The Lumineers: Ho Hey"),
             (4,"Adele: Chasing Pavements"),
             (2,"Jimi Hendrix: Voodoo Chile"),
             (1,"Stevie Ray Vaughan: Couldn't Stand the Weather"),
             (8,"Coldplay: Clocks"),
             (6,"Aretha Franklin: I Will Survive"),
             (5,"Cake: I Will Survive"),
             (7,"Beyonce: All the Single Ladies"),
             (9,"Nickelback: Gotta be Somebody"),
             (10,"Garth Brooks: Friends in Low Places")]
    songs3 = [(1,"Stevie Ray Vaughan: Couldn't Stand the Weather"),
             (2,"Jimi Hendrix: Voodoo Chile"),
             (3,"The Lumineers: Ho Hey"),
             (4,"Adele: Chasing Pavements"),
             (6,"Aretha Franklin: I Will Survive"),
             (5,"Cake: I Will Survive"),
             (7,"Beyonce: All the Single Ladies"),
             (8,"Coldplay: Clocks"),
             (10,"Garth Brooks: Friends in Low Places"),
             (9,"Nickelback: Gotta be Somebody")]
    print(seq1)
    print("# Inversions: %i\n" %sortandcount(seq1)[0])
    print(seq2)
    print("# Inversions: %i\n" %sortandcount(seq2)[0])
    print(seq3)
    print("# Inversions: %i\n" %sortandcount(seq3)[0])
    print(songs1)
    print("# Inversions: %i\n" %sortandcount(songs1)[0])
    print(songs2)
    print("# Inversions: %i\n" %sortandcount(songs2)[0])
    print(songs3)
    print("# Inversions: %i\n" %sortandcount(songs3)[0])