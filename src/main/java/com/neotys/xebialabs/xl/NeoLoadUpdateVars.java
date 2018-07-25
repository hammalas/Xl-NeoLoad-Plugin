package com.neotys.xebialabs.xl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrexed on 23/03/18.
 */
public class NeoLoadUpdateVars {
    public String VariableName;
    public String VarirableValue;
    public String Output;
    public List<String> Variable;

    public NeoLoadUpdateVars(String variableName, String varirableValue) {
        VariableName = variableName;
        VarirableValue = varirableValue;
        Variable=new ArrayList<String>();
        //Variable.add();
    }

    public void SetExistingVars(String var)
    {
        if(var!=null) {
            String[] parts = var.split(",");
            for (int i = 0; i < parts.length; i++) {
                Variable.add(parts[i]);
            }
        }
    }

    public NeoLoadVarsResponse UpdateVariable()
    {
        NeoLoadVarsResponse response;
        int exitcode=0;

        response=new NeoLoadVarsResponse(VariableName+"="+VarirableValue,exitcode);
        for(int i=0;i<Variable.size();i++)
        {
            response.appendContent(Variable.get(i));
        }

        return response;
    }
}
