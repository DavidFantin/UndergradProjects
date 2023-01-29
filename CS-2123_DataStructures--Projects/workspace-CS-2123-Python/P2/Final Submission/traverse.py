# David Fantin Andrew DeVoss Group 5


# utilized Tyler Moore's
# Starter code to P2.

import urllib.request, urllib.parse, urllib.error
from collections import deque


def byte2str(b):
    """
    Input: byte sequence b of a string
    Output: string form of the byte sequence
    Required for python 3 functionality
    """
    return "".join(chr(a) for a in b)


def getLinks(url, baseurl="http://secon.utulsa.edu/cs2123/webtraverse/"):
    """
    Input: url to visit, Boolean absolute indicates whether URLs should include absolute path (default) or not
    Output: list of pairs of URLs and associated text
    """
    # import the HTML parser package
    try:
        from bs4 import BeautifulSoup
    except:
        print('You must first install the BeautifulSoup package for this code to work')
        raise
    # fetch the URL and load it into the HTML parser
    soup = BeautifulSoup(urllib.request.urlopen(url).read(), features="html.parser")
    # pull out the links from the HTML and return
    return [baseurl + byte2str(a["href"].encode('ascii', 'ignore')) for a in soup.findAll('a')]


def print_dfs(url):
    """
    Print all links reachable from a starting **url**
    in depth-first order
    """
    print(itr_dfs(url))
    #
def itr_dfs(url):
    S, Q = [], []  # Visited-set and queue              this commenting is from the in-class code
    Q.append(url)  # We plan on visiting url
    while Q:  # Planned nodes left?
        u = Q.pop()  # Get one
        if u in S: continue  # Already visited? Skip it
        S.append(u)  # We've visited it now
        Q.extend(getLinks(u))  # Schedule all neighbors
    S.reverse()                                         # except we had to reverse it
    return S




def print_bfs(url):
    """
    Print all links reachable from a starting **url**
    in breadth-first order
    """
    print(itr_bfs(url)[0])     # the first element in the returned list is the bfs processed list
    #

def itr_bfs(url):
    S, Q = [], []   # S is the list to be processed, Q is a queue
    Q.append(url)
    S.append(url)
    Parents = {}    # The set for parents is only used when finding the shortest path
    Parents[url] = None
    pointer_idx = 0     # acts as the front of a dequeue
    while pointer_idx < len(Q):
        u = Q[pointer_idx]      # 'remove' from the front
        pointer_idx += 1
        children = getLinks(u)      # children of the parent node
        for child in children:
            if child not in S:      # only adds the child if it is not already ready to be output
                Parents[child] = u
                S.append(child)
                Q.append(child)
    important = [S, Parents]

    return important



def find_shortest_path(url1, url2):
    """
    Find and return the shortest path
    from **url1** to **url2** if one exists.
    If no such path exists, say so.
    """
    parentDict = itr_bfs(url1)[1]       # the first index of the output is the parent dictionary
    if url2 not in parentDict:          # the only way it cannot be reached is if it isn't in the tree
        print("There is no path from "+url1+" to "+url2)
        return
    path = [url2]
    previous = url2
    while parentDict[previous] != url1:     # go until the target is found
        path.append(parentDict[previous])
        previous = parentDict[previous]
    path.append(url1)
    path.reverse()      # we found the path starting at the back, so we flipped it
    print(path)

def find_max_depth(start_url):
    """
    Find and return the URL that is the greatest distance from start_url, along with the sequence of links that must be followed to reach the page.
    For this problem, distance is defined as the minimum number of links that must be followed from start_url to reach the page.
    """
    important = itr_bfs(start_url)
    farthest = important[0][len(important[0])-1]    # in a bfs, the farthest from the root has to be the last leaf
    parent = important[1]
    path = [farthest]
    previous = farthest
    print("The farthest url is: "+farthest+" and the path to get there is:")
    while parent[previous] != start_url:        # go until the root is reached (similar to shortest path)
        path.append(parent[previous])
        previous = parent[previous]
    path.append(start_url)
    path.reverse()      # once again, we found the path starting at the end, so we flipped it
    print(path)


if __name__ == "__main__":
    print(getLinks("https://www.businessnewsdaily.com/4661-starting-a-business-website.html"))
    # starturl = "http://secon.utulsa.edu/cs2123/webtraverse/index.html"
    # print("*********** (a) Depth-first search   **********")
    # print_dfs(starturl)
    # print("*********** (b) Breadth-first search **********")
    # print_bfs(starturl)
    # print("*********** (c) Find shortest path between two URLs ********")
    # find_shortest_path("http://secon.utulsa.edu/cs2123/webtraverse/index.html",
    #                    "http://secon.utulsa.edu/cs2123/webtraverse/wainwright.html")
    # find_shortest_path("http://secon.utulsa.edu/cs2123/webtraverse/turing.html",
    #                    "http://secon.utulsa.edu/cs2123/webtraverse/dijkstra.html")
    # print("*********** (d) Find the longest shortest path from a starting URL *****")
    # find_max_depth(starturl)

