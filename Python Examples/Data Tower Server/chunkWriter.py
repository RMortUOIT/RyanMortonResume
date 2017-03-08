#writes file chunk (line) given index
import getUserManifest as UserMan
import extract as driveExtract

def writeChunk(name, key, fileName, chunk):
    success = 0 #assume success and if fail = 1
    #verify user name and find file location
    location = UserMan.findUserItem(name, fileName)
    #append chunk to end of file
    if(location == 0):
        file = open('/driveMain/' + name + '/PUBLIC/' + fileName, 'a')
        file.write(chunk + '\n')
        file.close()
    elif(location > 0):
        file = open('/drive' + str(location) +'/' + name + '/PUBLIC/' + fileName, 'a')
        file.write(chunk + '\n')
        file.close()
    else:
        success = 1
    
    return success

#right now it assumes chunk exists
def readChunk(name, fileName, index):
    #find file
    location = UserMan.findUserItem(name, fileName)
    if(location == 0):
        file = open('/driveMain/' + name + '/PUBLIC/' + fileName, 'r')
        for i, line in enumerate(file):
            if(i == index):
                response = line
        file.close()
    elif(location > 0):
        file = open('/drive' + str(location) +'/' + name + '/PUBLIC/' + fileName, 'r')
        for i, line in enumerate(file):
            if(i == index):
                response = line.replace('\n', '')
        file.close()
    else:
        response = 'NOT FOUND'
    return response


def createFile(name, key, fileName, public):
    #use drive data to pick best drive
    driveData = driveExtract.getDriveData().split('\n')
    min = float(driveData[0])
    bestDrive = 0
    if(float(driveData[1]) < min):
        min = float(driveData[1])
        bestDrive = 1
    if(float(driveData[2]) < min):
        min = float(driveData[2])
        bestDrive = 2
    if(float(driveData[3]) < min):
        min = float(driveData[3])
        bestDrive = 3
    if(float(driveData[4]) < min):
        min = float(driveData[4])
        bestDrive = 4
    #create file in correct area
    if(bestDrive == 0):
        file = open('/driveMain/' + name + '/PUBLIC/' + fileName, 'w+')
    else:
        file = open('/drive' + str(bestDrive) + '/' + name + '/PUBLIC/' + fileName, 'w+')
    file.write('')
    file.close()
    #add file to manifest add option for private
    if(public):
        UserMan.addUserItem(name, key, fileName, bestDrive, True)
    else:
        UserMan.addUserItem(name, key, fileName, bestDrive, False)
    return 0
