import sys
from com.neotys.xebialabs.xl import NeoLoadTest

releaseid = getCurrentRelease().id
releasename = getCurrentRelease().name

if NeoLoadPath is None:
    print "No Controller defined"
    sys.exit(1)

CreateVar = False
NeoLoadCloudcreated = False
variables = releaseApi.getVariables(releaseid)
for var in variables:
    if var.key == 'NeoLoadVariable':
        NeoLoadVariable = var.value
        CreateVar = True
    if var.key == 'NeoLoadCloudYML':
        NeoLoadCloudYML = var.value
        NeoLoadCloudcreated = True
# --------add trhe release id and title
# ->${release.id}
# ->${release.title}
session = NeoLoadTest(NeoLoadPath["NL_Host"], NeoLoadPath["username"], NeoLoadPath["password"],
                      NeoLoadPath["NL_Controller_Path"], NeoLoadPath["OS"], NeoloadScenario, LocalNeoLoadProject,
                      NeoLoadTestDescription, NeoloadWebAPItoken, CollabProjectName, CollaborationProjectPath,
                      NumberOfUser, NumberOfhour)
session.setReleaseInformation(releaseid, releasename)

if NeoLoadWebAPI is not None:
    session.setWebAPI(NeoLoadWebAPI["NL_WEB_URL"])

if NeoLoadTeamServer is not None:
    session.setNTS(NeoLoadTeamServer["TeamServerHost"], NeoLoadTeamServer["username"], NeoLoadTeamServer["password"],
                   NeoLoadTeamServer["licenceID"])

if NeoLoadCollaboration is not None:
    session.setCollab(NeoLoadCollaboration["Url"], NeoLoadCollaboration["username"], NeoLoadCollaboration["password"])

if NeoloadCloudPlatform is not None:
    session.setNLCloud(NeoloadCloudPlatform["username"], NeoloadCloudPlatform["password"])

if NeoLoadCloudcreated:
    session.addCloudYML(NeoLoadCloudYML)

if CreateVar:
    session.setVarialbe(NeoLoadVariable)

response = session.execute()

# set variables
output = response.stdout
error = response.stderr

reportxmlbyte = response.reportXMLBytes
reportdtdbyte = response.reportDTDBytes
junitbyte = response.junitXMLBytes
pdfbytes = response.reportPDFBytes
com = response.comment
taskid = getCurrentTask().id

if reportdtdbyte is not None:
    att = taskApi.addAttachment(taskid, "report.dtd", reportdtdbyte)

if reportxmlbyte is not None:
    att = taskApi.addAttachment(taskid, "report.xml", reportxmlbyte)

if junitbyte is not None:
    att = taskApi.addAttachment(taskid, "junit.xml", junitbyte)

if pdfbytes is not None:
    att = taskApi.addAttachment(taskid, "report.pdf", pdfbytes)

if com is not None:
    comment = Comment(com)
    att = taskApi.commentTask(taskid, comment)

if response.responseCode == 0:
    print output
else:
    print "Exit code "
    print response.responseCode
    print
    print "#### Output:"
    print output

    print "#### Error stream:"
    print error
    print
    print "----"

    sys.exit(response.responseCode)
