import datetime

log = []

def logEvent(event:str):
    now = datetime.datetime.now()
    nowSring = str(now)
    log.append(nowSring+": "+event)

def getLog():
    audit = '\n'.join(log)
    return audit