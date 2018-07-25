import sys
from com.neotys.xebialabs.xl import NeoLoadUpdateVars

if ConstantVariableName is None:
    print "No Variable Name defined"
    sys.exit(1)

if ConstantVariableValue is None:
    print "No Variable Name defined"
    sys.exit(1)

Vars= NeoLoadUpdateVars(ConstantVariableName,ConstantVariableValue)
releaseid=getCurrentRelease().id
variables=releaseApi.getVariables(releaseid)
CreateVar=True

for var in variables :
    if var.key == 'NeoLoadVariable':
        Vars.SetExistingVars(var.value)
        Neoloadupdated=var
        CreateVar=False

response=Vars.UpdateVariable()


if CreateVar == True :
    NeoLoadVar=Variable('NeoLoadVariable', response.YmlContent)
    releaseApi.createVariable(releaseid, NeoLoadVar)
else:
    Neoloadupdated.value=response.YmlContent
    releaseApi.updateVariable( Neoloadupdated)


# set variables
output = response.stdout
error = response.stderr


if response.rc == 0:
    print output
else:
    print "Exit code "
    print response.rc
    print
    print "#### Output:"
    print output

    print "#### Error stream:"
    print error
    print
    print "----"

    sys.exit(response.rc)