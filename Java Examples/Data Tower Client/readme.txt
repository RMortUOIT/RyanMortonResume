This project is a java swing app that uses the API provided by the data tower server (see python 
samples) to allow users to manage the files they have stored on the data tower and also download
files that other users have made public.

The data tower itself is a desktop PC made from older parts. It uses 5 seperate SATA drives that
give it a combined capacity of 2.5TB. The data tower runs Ubuntu as its OS and the server is
entirely written in Python.

NOTE that this is a beta version of the client and some features still require implementing, such
as the ability for the user to automatically overwrite files stored on the tower instead of having
to manually delete them and re-upload them.

Included in this section are some images that show what the UI looks like and the 3 primary source
files. 