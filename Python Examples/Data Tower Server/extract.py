# data is taken from the temp file the relevant data is extracted
# and then printed back into the temp
import subprocess

def getDriveData():
    #run shell to find data on each disk
    subprocess.call(['./getStatus.sh'], stdout=open('temp.txt', 'w'))
    
    #open temp file script printed to to extract results
    with open('temp.txt') as f:
        content = f.readlines();
    
    #by address 2=used, 3=available
    driveMainRaw =  content[1].split()
    drive1Raw = content[3].split()
    drive2Raw = content[5].split()
    drive3Raw = content[7].split()
    drive4Raw = content[9].split()
    
    #extract relevant data
    driveMain = str(float(driveMainRaw[2])/(float(driveMainRaw[3])+float(driveMainRaw[2])))
    drive1 = str(float(drive1Raw[2])/(float(drive1Raw[3])+float(drive1Raw[2])))
    drive2 = str(float(drive2Raw[2])/(float(drive2Raw[3])+float(drive2Raw[2])))
    drive3 = str(float(drive3Raw[2])/(float(drive3Raw[3])+float(drive3Raw[2])))
    drive4 = str(float(drive4Raw[2])/(float(drive4Raw[3])+float(drive4Raw[2])))
    
    return driveMain + '\n' + drive1 + '\n' + drive2 + '\n' + drive3 + '\n' + drive4
