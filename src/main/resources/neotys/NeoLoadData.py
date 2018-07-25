import sys
from com.neotys.xebialabs.xl import NeoLoadFileUtil

def GetNeoLoadData(title,releaseid):
    result="{\"data_graph\":[ "
    try:

        TasksList=taskApi.searchTasksByTitle(title,None,releaseid)
        for task in TasksList:
            if task.type=="NeoLoad.LaunchTest" :
                attachmens=task.attachments
                for att in attachmens:
                    if att.name=="report.xml":
                        file_byte= releaseApi.getAttachment(att.id)
                        hits=NeoLoadFileUtil("hits",file_byte)
                        error=NeoLoadFileUtil("error",file_byte)
                        response=NeoLoadFileUtil("response",file_byte)
                        result+="{\"hits\":"+hits+",\"error\":"+error+",\"response\": "+response+"},"


        result=result[:-1]
        result+="]}"
        return result
    except:
        return None


title = request.query["NeoLoadTaskTitle"]
releaseid = request.query["releaseid"]
if title is not None and releaseid is not None:
    resJythonResponse()
    response.entity=GetNeoLoadData(NeoLoadTaskTitle)
else :
    response.entity=None
    response.exception="No Get Parameters found"

#NeoLoadTaskTitle = request.query["NeoLoadTaskTitle"]

##json=GetNeoLoadData(NeoLoadTaskTitle)
