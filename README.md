# HyFlex-Compatible-Problem
## INTRODUCTION 

The sightseeing tour problem is an optimisation problem where we need to find a route around a 
city which visits a predefined set of tourist landmarks, starting and ending at the tour guide office, 
while minimising the total time taken to complete the tour. This allows each tour guide to maximise 
the number of tours they can give in any given day. 
A popular sightseeing tour company wishes to continue offering sightseeing tours but must adhere 
to new social distancing rules. In order to operate, the company must follow the following 
constraints: 
1. Each tour must operate on foot at all times. 
2. All attendees and the tour guide must maintain a minimum distance between each other 
throughout the tour. 
3. Landmark destinations must not become overpopulated such that social distancing rules 
cannot be adhered to. 
4. Tours may not use footpaths designated as being heavily trafficked by citizens of the city. 

To enable citizens of the city to maintain an appropriate distance from each other, some pavements 
have been made to be one-way only, and the tour company has been banned from using certain 
footpaths that are already heavily trafficked by the citizens. This means that the route from one 
landmark to another may be different depending on whether they walk from landmark A to 
landmark B, or vice versa. 
Fortunately, the owner of the company recognises the complexity of the problem when considering 
the third rule and has such limited the size of groups to the minimum occupancy of all the 
landmarks, allowing us to ignore this constraint. 
Put simply, the Socially Distanced based Sight Seeing Tour Problem (SDSSTP) is a routing-type problem 
which states that given N number of landmarks, and the tour office, the objective is to find a route 
which ensures that all landmarks are visited exactly once, starting and ending at the tour office, such 
that the total tour duration is minimised. 
