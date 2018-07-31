import json
from com.neotys.xebialabs.xl import NeoLoadFileUtil
from java.lang import Exception


def GetNeoLoadData(title, releaseid, trendingtype):
    output = "{ \"type\":\"" + trendingtype + "\","
    result = "\"data_graph\":[ "

    try:
        tasks = []
        try:
            # For new version API
            try:
                release = _releaseApi.getRelease(releaseid)
            except Exception as e:
                print e.getMessage()
                release = _releaseApi.getArchivedRelease(releaseid)

            for phase in release.phases:
                for t in phase.tasks:
                    if t.title == title:
                        tasks.append(t)
        except Exception as e:
            # For old version API
            print e.getMessage()
            tasks = _taskApi.searchTasksByTitle(title, None, releaseid)

        for task in tasks:
            if task.type.name == "CustomScriptTask":
                # if task.type=="NeoLoad.LaunchTest" :
                attachments = task.attachments
                for attachment in attachments:
                    if "report.xml" in attachment.exportFilename or "report.xml" in attachment.fileUri:
                        file_byte = _releaseApi.getAttachment(attachment.id)
                        if trendingtype == "Hit/s":
                            hits = NeoLoadFileUtil.getStat("hits", file_byte)
                            result += "{\"kpi\":" + hits + "},"
                        if trendingtype == "Response Time":
                            error = NeoLoadFileUtil.getStat("response", file_byte)
                            result += "{\"kpi\":" + error + "},"
                        if trendingtype == "Errors":
                            response = NeoLoadFileUtil.getStat("error", file_byte)
                            result += "{\"kpi\":" + response + "},"

        if result[-1:] == ",":
            result = result[:-1]

        result += "]"

        output += result + "}"
        return output
    except Exception as e:
        print  e.getMessage()
        return None


releaseid = release.id

if NeoLoadTaskTitle is not None and TrendingData is not None:
    response = GetNeoLoadData(NeoLoadTaskTitle, releaseid, TrendingData)
    if response is not None:
        data = json.loads(response)