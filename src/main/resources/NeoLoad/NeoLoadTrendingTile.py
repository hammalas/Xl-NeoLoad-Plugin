import sys,json
from com.xebialabs.xlrelease.domain import Task
from com.xebialabs.deployit.plugin.api.reflect import Type
from com.neotys.xebialabs.xl import NeoLoadFileUtil
from java.lang import Exception

def GetNeoLoadData(title,releaseid,trendingtype):
    output="{ \"type\":\""+trendingtype+"\","
    result="\"data_graph\":[ "

    try:

        TasksList=_taskApi.searchTasksByTitle(title,None,releaseid)
        for task in TasksList:
             if task.type.name=="CustomScriptTask" :
                # if task.type=="NeoLoad.LaunchTest" :
                attachmens=task.attachments
                for att in attachmens:
                    if att.fileUri=="jcr:report.xml":
                        file_byte= _releaseApi.getAttachment(att.id)
                        if trendingtype == "Hit/s":
                            hits=NeoLoadFileUtil.GetStat("hits",file_byte)
                            result+="{\"kpi\":"+hits+"},"
                        if trendingtype == "Response Time":
                            error=NeoLoadFileUtil.GetStat("response",file_byte)
                            result+="{\"kpi\":"+error+"},"
                        if trendingtype == "Errors":
                            response=NeoLoadFileUtil.GetStat("error",file_byte)
                            result+="{\"kpi\":"+response+"},"


        if result[-1:] == ",":
            result=result[:-1]


        result+="]"

        output+=result+"}"
        return output
    except Exception as e:
        print  e.getMessage()
        return None


releaseid=release.id

if NeoLoadTaskTitle is not None and TrendingData is not None:
    response=GetNeoLoadData(NeoLoadTaskTitle,releaseid,TrendingData)
    if response is not None:
        data = json.loads(response)
        # url = "/api/extension/neotys/TrendingData?NeoLoadTaskTitle="+NeoLoadTaskTitle+"&releaseid="+releaseid

#request = HttpRequest({"url": "http://localhost:5516"})
#response = request.get(url, contentType='application/json')

#if response.status != 200:
#    raise Exception("Request to neoload trending api failed with status %s, response %s" % (response.status, response.response))



#NeoLoadTaskTitle = request.query["NeoLoadTaskTitle"]

##json=GetNeoLoadData(NeoLoadTaskTitle)
