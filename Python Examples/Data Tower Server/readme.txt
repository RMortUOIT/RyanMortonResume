This project is a basic file sharing server that allows users to manage files saved to the data
tower via their client application (see Java examples). The server itself uses a simple HTTP
handler in order to handle requests made by the user on the server. The central commands, 
uploading and downloading files, are handled via a chunk loading system where the files are
uploaded/downloaded to the client in chunks up to 15mb each. The user also has the ability to
delete their own files and also download files that other users have made public.

The data tower itself is a desktop PC made from older parts. It uses 5 seperate SATA drives that
give it a combined capacity of 2.5TB. The data tower runs Ubuntu as its OS and the server is
entirely written in Python.

Included in this project are the primary python source files, the getStatus.sh file which is 
simply how the server gets the current state of all 5 drives, and a rough informal UML image
that shows the basic topology of the system.