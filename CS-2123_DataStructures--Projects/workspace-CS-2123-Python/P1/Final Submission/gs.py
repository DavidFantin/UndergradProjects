# Anna Jordan; David Fantin
# Python implementation of stable matching problem
# Project 1
# 9/9/19

def gs(men, women, pref):
    """
    Gale-shapley algorithm, modified to exclude unacceptable matches
    Inputs: men (list of men's names)
            women (list of women's names)
            pref (dictionary of preferences mapping names to list of preferred names in sorted order)
            blocked (list of (man,woman) tuples that are unacceptable matches)
    Output: dictionary of stable matches
    """
    # preprocessing
    ## build the rank dictionary
    rank={}
    for w in women:
        rank[w] = {}
        i = 1
        for m in pref[w]:
            rank[w][m]=i
            i+=1 
    ## create a "pointer" to the next woman to propose
    prefptr = {}
    for m in men:
        prefptr[m] = 0

    freemen = set(men)    #initially all men and women are free
    S = {}           #build dictionary to store engagements 

    #run the algorithm
    while freemen:
        m = freemen.pop()
        #get the highest ranked woman that has not yet been proposed to
        w = pref[m][prefptr[m]]
        prefptr[m]+=1
        if w not in S: S[w] = m
        else:
            mprime = S[w]
            if rank[w][m] < rank[w][mprime]:
                S[w] = m
                freemen.add(mprime)
            else:
                freemen.add(m)
    return S

def gs_block(men, women, pref, blocked):
    """
    Gale-shapley algorithm, modified to exclude unacceptable matches
    Inputs: men (list of men's names)
            women (list of women's names)
            pref (dictionary of preferences mapping names to list of preferred names in sorted order)
            blocked (list of (man,woman) tuples that are unacceptable matches)
    Output: dictionary of stable matches
    """
     # preprocessing
    ## build the rank dictionary
    rank={}
    for w in women:
        rank[w] = {}
        i = 1
        for m in pref[w]:
            rank[w][m]=i
            i+=1 
    ## create a "pointer" to the next woman to propose
    prefptr = {}
    for m in men:
        prefptr[m] = 0

    freemen = set(men)    #initially all men and women are free
    numpartners = len(men) 
    S = {}           #build dictionary to store engagements 
    #run the algorithm
    while freemen and prefptr[m] < numpartners:
        m = freemen.pop()
        #get the highest ranked woman that has not yet been proposed to
        w = pref[m][prefptr[m]]
        prefptr[m]+=1
        if (m,w) in blocked:
            freemen.add(m)
        else:
            if w not in S: 
                S[w] = m
            else:
                mprime = S[w]
                if rank[w][m] < rank[w][mprime]:
                    S[w] = m
                    freemen.add(mprime)
                else:
                      freemen.add(m)
    return S

def gs_tie(men, women, preftie):
    """
    Gale-shapley algorithm, modified to exclude unacceptable matches
    Inputs: men (list of men's names)
            women (list of women's names)
            pref (dictionary of preferences mapping names to list of sets of preferred names in sorted order)
    Output: dictionary of stable matches
    """
    # preprocessing
    ## build the rank dictionary
    rank={}
    for w in women:
        rank[w] = {}
        i = 1
        for s in preftie[w]:
            while len(s)>0:
                m = s.pop()
                rank[w][m]=i
                i+=1
    ## create a "pointer" to the next woman to propose
    prefptr = {}
    for m in men:
        prefptr[m] = 0
    # Ranking the men's preferences
    mRank = {}
    for m in men:
        mRank[m] = {}
        i = 0
        for s in preftie[m]:
            while len(s)>0:
                w = s.pop()
                mRank[m][i]=w
                i+=1
    freemen = set(men)    #initially all men and women are free
    S = {}           #build dictionary to store engagements 

    #run the algorithm
    
    while freemen:
        m = freemen.pop()
        #get the highest ranked woman that has not yet been proposed to
        w = mRank[m][prefptr[m]]
        prefptr[m]+=1
        if w not in S: 
            S[w] = m
        else:
            mprime = S[w]
            if rank[w][m] < rank[w][mprime]:
                S[w] = m
                freemen.add(mprime)
            else:
                freemen.add(m)
    return S

if __name__=="__main__":
    #input data
    themen = ['xavier','yancey','zeus']
    thewomen = ['amy','bertha','clare']

    thepref = {'xavier': ['amy','bertha','clare'],
           'yancey': ['bertha','amy','clare'],
           'zeus': ['amy','bertha','clare'],
           'amy': ['yancey','xavier','zeus'],
           'bertha': ['xavier','yancey','zeus'],
           'clare': ['xavier','yancey','zeus']
           }
    thepreftie = {'xavier': [{'bertha'},{'amy'},{'clare'}],
           'yancey': [{'amy','bertha'},{'clare'}],
           'zeus': [{'amy'},{'bertha','clare'}],
           'amy': [{'zeus','xavier','yancey'}],
           'bertha': [{'zeus'},{'xavier'},{'yancey'},],
           'clare': [{'xavier','yancey'},{'zeus'}]
           }
    
    blocked = {('xavier','clare'),('zeus','clare'),('zeus','amy')}

    #eng
    match = gs(themen,thewomen,thepref)
    print(match)
    
    match_block = gs_block(themen,thewomen,thepref,blocked)
    print(match_block)

    match_tie = gs_tie(themen,thewomen,thepreftie)
    print(match_tie)