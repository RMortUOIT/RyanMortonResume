#gets users public and private files
import sys
import copy

def getUserMan(name, key):
    
    with open('UserMans/'+name+'.txt') as manifestFile:
        manifestContent = manifestFile.readlines()
    
    response = ""
    
    if(manifestContent[0].replace('\n', '') == key):
        for currentLine in manifestContent:
            response = response + currentLine
    else:
        response = 'NOT FOUND'
    return response


def getPublicMan(name):
    #returns only public items in user manifest
    with open('UserMans/'+name+'.txt') as manifestFile:
        manifestContent = manifestFile.readlines()
    
    response = ''
    for index in range(1, len(manifestContent)):
        if(manifestContent[index].split('==>')[0] == 'PRIVATEFILES'):
            break;
        else:
            response = response + manifestContent[index]
    return response

def addUserItem(name, key, item, location, public):
    #places item in the manifest
    with open('UserMans/'+name+'.txt') as manifestFile:
        manContent = manifestFile.readlines()
    
    if(manContent[0].replace('\n', '') == key):
        if(public):
            manContent.insert(1, item + '-=3=-' + str(location) + '\n')
        else:
            manContent.append(item + '-=3=-' +str(location) + '\n')
        manFileUpdate = open('UserMans/'+name+'.txt', 'w')
        for currentLine in manContent:
            manFileUpdate.write(currentLine)
        manFileUpdate.close()



def removeUserItem(name, key, item):
    success = 0
    #delte in manifest
    with open('UserMans/'+name+'.txt') as manifestFile:
        manContent = manifestFile.readlines()
    
    manFileUpdate = open('UserMans/'+name+'.txt', 'w')
    for currentLine in manContent:
        if(currentLine.split('-=3=-')[0] <> item):
            manFileUpdate.write(currentLine)
    manFileUpdate.close()
    
    return success



def findUserItem(name, item):
    
    response = -1
    with open('UserMans/'+name+'.txt') as manifestFile:
        manifestContent = manifestFile.readlines()
    
    for currentLine in manifestContent:
        breakDown = currentLine.split("-=3=-")
        if((len(breakDown) == 2) & (breakDown[0] == item)):
            response = breakDown[1].replace('\n', '')
            break
    return response

def getUsers():
    #take from MasterManifest the user names
    with open('MasterManifest.txt') as manifestFile:
        manifestContent = manifestFile.readlines()
    
    response = ''
    for currentLine in manifestContent:
        response = response + currentLine
    return response
