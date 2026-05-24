import datetime


firstRun = True

log = []

def logEvent(event:str):
    now = datetime.datetime.now()
    nowSring = str(now)
    log.append(nowSring+": "+event)
    writeFile(nowSring+": "+event+"\n")

def getLog():
    audit = '\n'.join(log)
    return audit


def writeFile(log):
    global firstRun
    if firstRun:
        # append the log to a file named audit_log.txt
        with open('audit_log.txt', 'w') as f:
            f.write(log)
        firstRun = False
    else:
        with open('audit_log.txt', 'a') as f:
            f.write(log)

def newAudit():
    global log
    log = []
    global firstRun
    firstRun = True