# Project team: P3 partner 10
# Team Names:
# (1) Fantin, David
# (2) Alboqmi, Rami
# As a reference we use the P3 Starter Code provided by Dr. Moore (CS 2123 The University of Tulsa)
# Implementation of interval partitioning algorithm
import datetime, operator, time
from datetime import timedelta
from heapq import heappush , heappop

def scheduleRooms(rooms,cls):
    """
    Input: rooms - list of available rooms
           cls   - dictionary mapping class names to pair of (start,end) times
    Output: Return a dictionary mapping the room name to a list of
    non-conflicting scheduled classes.
    If there are not enough rooms to hold the classes, return 'Not enough rooms'.
    """

    ## Create dic of rooms with a calendar for each room. Starts from 8:00 to 22:00 and increment the time each 15 minutes
    hours = []
    for x in range(8,22):
        starttime = str(x) + ":00"
        FirstFifteenMinute = datetime.datetime.strptime(starttime, '%H:%M')
        SecondFifteenMinute = FirstFifteenMinute + datetime.timedelta(hours=0.25)
        ThirdFifteenMinute =  SecondFifteenMinute + datetime.timedelta(hours=0.25)
        FourthFifteenMinute = ThirdFifteenMinute + datetime.timedelta(hours=0.25)

        # append the time to the list hours
        hours.append(FirstFifteenMinute.strftime('%H:%M'))
        hours.append(SecondFifteenMinute.strftime('%H:%M'))
        hours.append(ThirdFifteenMinute.strftime('%H:%M'))
        hours.append(FourthFifteenMinute.strftime('%H:%M'))


    # Assign the Hours list that we created to each room
    roomCalnedar = { i : hours  for i in rooms}
    # Create the assign Dic that will use to store the final result
    rmassign = {i: [] for i in rooms}

    # We created this to help us conunt how many rooms we manged to find a room for.
    # At the end, we will check if the number of room (from parameters rooms is less than this len(processedCourses),
    #                                  then we should return "Not enough rooms"
    #                           Otherwise, we return the result of rmassign {} at the end
    processedCourses = []



    ## Sort all courses by start time to choose by start time
    sortedcls = sorted(cls.items(), key=operator.itemgetter(0))

    ## Go through all rooms in the dictionary of the rooms
    for i in rmassign:
        ## Go through all courses in the sorted cls
        for j in sortedcls:
            ## Check if this course is not previously processed
            if (j[0] not in processedCourses):

                ## Info: "course:",j[0], " has start time:",j[1][0], " and end time:",j[1][1])
                ## if there there is a space for this course in room i
                if  j[1][0].strftime('%H:%M') in roomCalnedar[i] and j[1][1].strftime('%H:%M') in roomCalnedar[i]:

                    ## get the start time of this class as this foramt H:M for exmaple: 9:30
                    starttime = roomCalnedar[i].index(j[1][0].strftime('%H:%M'))
                    ## get the end time of this course as this format H:M for exmaple: 11:30
                    endtime = roomCalnedar[i].index(j[1][1].strftime('%H:%M'))
                    ## Assign this class to the room
                    rmassign[i].append(j[0])
                    ## Add this class to the processed courses
                    processedCourses.append(j[0])

                    ## remove this time-window from the roomCalnedar. So, no other classs will be allow can added because it is busy.
                    roomCalnedar[i] = roomCalnedar[i][endtime:]

                    ## Space Not found in  this room i
    ## finally, check if we processed all course. That means two numbers should match. Then, we should return the schedule
    if len(processedCourses) == len(cls):
        return rmassign
    ## otherwise, we do not have space for all courses, we should return "Not enough rooms"
    else:
        return "Not enough rooms"



if __name__=="__main__":
    cl1 = {"a": (datetime.time(9),datetime.time(10,30)),
           "b": (datetime.time(9),datetime.time(12,30)),
           "c": (datetime.time(9),datetime.time(10,30)),
           "d": (datetime.time(11),datetime.time(12,30)),
           "e": (datetime.time(11),datetime.time(14)),
           "f": (datetime.time(13),datetime.time(14,30)),
           "g": (datetime.time(13),datetime.time(14,30)),
           "h": (datetime.time(14),datetime.time(16,30)),
           "i": (datetime.time(15),datetime.time(16,30)),
           "j": (datetime.time(15),datetime.time(16,30))}
    rm1 = [1,2,3]
    print(cl1)
    print(scheduleRooms(rm1,cl1))
    print(scheduleRooms([1,2],cl1))
    ensrooms = ['KEH U1','KEH M1','KEH M2','KEH M3','KEH U2','KEH U3','KEH U4','KEH M4','KEH U8','KEH U9']
    csclasses = {'CS 1043': (datetime.time(9,30),datetime.time(11)),
              'CS 2003': (datetime.time(10,30),datetime.time(12)),
              'CS 2123': (datetime.time(11,15),datetime.time(12,45)),
              'CS 3003': (datetime.time(8,15),datetime.time(11,30)),
              'CS 3353': (datetime.time(11),datetime.time(12)),
              'CS 4013': (datetime.time(13),datetime.time(14,45)),
              'CS 4063': (datetime.time(12,30),datetime.time(14,30)),
              'CS 4123': (datetime.time(14),datetime.time(15)),
              'CS 4163': (datetime.time(14),datetime.time(16,30)),
              'CS 4253': (datetime.time(12),datetime.time(16)),
    }
    print(csclasses)
    print(scheduleRooms(ensrooms,csclasses))
    print(scheduleRooms(ensrooms[:4],csclasses))
