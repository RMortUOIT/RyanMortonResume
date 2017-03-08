#finds file created by user and deletes it
import getUserManifest as userMan
import os

def deleteUserFile(userName, userKey, fileName):
    #find which drive the file is stored in
    public = True
    drive = userMan.findUserItem(userName, fileName)
    print('File is at drive ' + str(drive))
    #delete file here
    path = ''
    if(drive == 0):
        path = '/driveMain/' + userName
    elif(drive > 0):
        path = '/drive' + str(drive) + '/' + userName
    if((public) & (drive >= 0)):
        path = path + '/PUBLIC/' + fileName
    elif(drive >= 0):
        path = path + '/PRIVATE/' + fileName
    
    success = 0
    if(path <> ''):
        os.remove(path)
        userMan.removeUserItem(userName, userKey, fileName)
    else:
        success = -1
    
    return success

