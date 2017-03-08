#handles both the POST and GET requests and returns response to the HTTPserver
import getUserManifest as userMan
import getFile as getter
import writeFile as fileWriter
import deleteFile as deleter
import chunkWriter as CW

def handlePost(path, body):
    path = path.replace('/', '') #remove the / at the start
    breakDown = path.split('=?=')
    if(breakDown[0] == 'userWrite'):
        print('File Write Requested')
        userName = breakDown[1]
        userKey = breakDown[2]
        fileName = breakDown[3]
        public = (breakDown[4] == 'True')
        chunk = int(breakDown[5])
        if(chunk == 0):
            result = CW.createFile(userName, userKey, fileName, public)
            if(result == 0):
                result = CW.writeChunk(userName, userKey, fileName, ''.join(body))
            else:
                result = 1
        else:
            result = CW.writeChunk(userName, userKey, fileName, ''.join(body))
        if(result == 0):
            return 'save successfull'
        elif(result == 1):
            return 'ERROR:Could not verify user credentials'
        elif(result == 2):
            return 'ERROR:Data tower is full'
    return 'request failed'


def handleGet(path):
    #must get the nature of the request by breaking down the path
    path = path.replace('/', '') #remove the / at the start
    breakDown = path.split('=?=') #weird hopefully unique breaker
    if(breakDown[0] == 'userMan'):
        print('user manifest requested: ' + path)
        userName = breakDown[1]
        userKey = breakDown[2]
        content = userMan.getUserMan(userName, userKey)
        if(len(content) > 0):
            return content
    elif(breakDown[0] == 'userFile'):
        print('User own file requested: ' + path)
        userName = breakDown[1]
        fileName = breakDown[2]
        chunk = int(breakDown[3])
        content = CW.readChunk(userName, fileName, chunk)
        if(len(content) > 0):
            return content
    elif(breakDown[0] == 'delete'):
        print('User request delete: ' + path)
        userName = breakDown[1]
        userKey = breakDown[2]
        fileName = breakDown[3]
        success = deleter.deleteUserFile(userName, userKey, fileName)
        if(success == 0):
            return 'FILE DELETED'
    elif(breakDown[0] == 'getPub'):
        print('Public manifest requested')
        userName = breakDown[1]
        response = userMan.getPublicMan(userName)
        return response
    elif(breakDown[0] == 'getUsers'):
        print('User list requested')
        response = userMan.getUsers()
        return response
    elif(breakDown[0] == 'AreYouThere'):
        print('Are You There requested')
        return 'yes'
    return 'Request Failed'


