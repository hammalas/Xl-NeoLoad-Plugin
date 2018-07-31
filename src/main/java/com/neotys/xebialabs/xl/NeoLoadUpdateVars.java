package com.neotys.xebialabs.xl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hrexed on 23/03/18.
 */
public class NeoLoadUpdateVars {
    private String variableName;
    private String variableValue;
    private List<String> variables = new ArrayList<>();

    public NeoLoadUpdateVars(String variableName, String variableValue) {
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    public void SetExistingVars(String var) {
        if (var != null) {
            String[] parts = var.split(",");
            Collections.addAll(variables, parts);
        }
    }

    public NeoLoadVarsResponse UpdateVariable() {
        NeoLoadVarsResponse response;
        response = new NeoLoadVarsResponse(variableName + "=" + variableValue);
        for (String variable : variables) {
            response.appendContent(variable);
        }

        return response;
    }
}
