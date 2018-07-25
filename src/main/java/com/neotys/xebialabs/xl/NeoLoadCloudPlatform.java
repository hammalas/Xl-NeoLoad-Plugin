package com.neotys.xebialabs.xl;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.List;
import com.xebialabs.deployit.plugin.api.udm.ConfigurationItem;
import com.xebialabs.deployit.plugin.api.udm.Configuration;
/**
 * Created by hrexed on 15/02/18.
 */
public class NeoLoadCloudPlatform {
    private String CloudWorkGroup;
    private int duration;
    private List<CloudBooking> CloudLocations;
    private StringBuilder errors;
    private StringBuilder output;
    private String CloudType;
    private final static String MEDIUM="MEDIUM";
    private final static String LARGE="LARGE";

    public NeoLoadCloudPlatform(String Worgroup, String duration,String type)
    {
        CloudLocations=new ArrayList<CloudBooking>();
        errors = new StringBuilder();
        output= new StringBuilder();
        try
        {
            if(IsCorrectType(type))
                this.CloudType=type.toUpperCase();
            else
                this.CloudType=MEDIUM;

            this.CloudWorkGroup=Worgroup;
            this.duration=Integer.parseInt(duration);

        }
        catch(NumberFormatException e)
        {
            AddError("ERROR","Conversion issue",e);
        }
    }

    public void AddCloudLocation(String LocationId,String Number)
    {
        CloudLocations.add(new CloudBooking(Integer.parseInt(Number),LocationId));
    }

    private boolean IsCorrectType(String type)
    {
        if(type.equalsIgnoreCase(MEDIUM)||type.equalsIgnoreCase(LARGE))
            return true;
        else
            return false;
    }

    private void AddError(String Type,String message, Exception e)
    {
            if(e!=null)
                errors.append(Type + " : " + message + " excception : " + e.getMessage()).append("\n");
            else
                errors.append(Type+"Error :" + message ).append("\n");

    }
    private void AddOutput(String message)
    {
         output.append( message ).append("\n");

    }

    public CloudResponse GenerateYmlCloudFile()
    {
        int code=0;
        CloudResponse res;
        StringBuilder yml ;
        yml=new StringBuilder();
        try {
            AddOutput("Generating the YML");
            yml.append("infrastructures:\n");
            yml.append(" - name: My Cloud infrastructure\n");
            yml.append("   type: NEOTYS_CLOUD_LOAD_GENERATOR\n");
            yml.append("   workgroup: " + this.CloudWorkGroup+"\n");
            yml.append("   architecture: " + this.CloudType+"\n");
            yml.append("   duration: " + this.duration + "h\n");
            yml.append("   zones:\n");
            for (int i = 0; i < CloudLocations.size(); i++) {

                if(CloudLocations.get(i).GetCloudZoneID()==null)
                    AddError("Location", "Location NUll",null);
                yml.append("   - id: " + CloudLocations.get(i).GetCloudZoneID()+"\n");
                yml.append("     count: " + CloudLocations.get(i).getNumberOfLG()+"\n");
            }

            if (yml.length() > 0) {
                AddOutput("YML generated : "+yml.toString());
                code = 0;
            }
            if (errors.length() > 0)
                code = 1;
        }
        catch (Exception e)
        {
            AddError("ERROR", "Technical Error",e);
        }
        res= new CloudResponse(yml.toString(),code);

        res.addToError(errors);
        res.addToOut(output);

        return res;
    }
}


