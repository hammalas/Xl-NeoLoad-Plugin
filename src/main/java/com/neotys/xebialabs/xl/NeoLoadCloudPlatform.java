package com.neotys.xebialabs.xl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrexed on 15/02/18.
 */
public class NeoLoadCloudPlatform {
    private String cloudWorkGroup;
    private int duration;
    private List<CloudBooking> cloudLocations;
    private StringBuilder errors;
    private StringBuilder output;
    private String cloudType;
    private static final String MEDIUM = "MEDIUM";
    private static final String LARGE = "LARGE";

    public NeoLoadCloudPlatform(String cloudWorgroup, String duration, String cloudType) {
        cloudLocations = new ArrayList<>();
        errors = new StringBuilder();
        output = new StringBuilder();
        try {
            if (isCorrectType(cloudType)) {
                this.cloudType = cloudType.toUpperCase();
            } else {
                this.cloudType = MEDIUM;
            }

            this.cloudWorkGroup = cloudWorgroup;
            this.duration = Integer.parseInt(duration);

        } catch (NumberFormatException e) {
            addError("ERROR", "Conversion issue", e);
        }
    }

    public void addCloudLocation(String locationId, String number) {
        cloudLocations.add(new CloudBooking(Integer.parseInt(number), locationId));
    }

    private boolean isCorrectType(String type) {
        return type.equalsIgnoreCase(MEDIUM) || type.equalsIgnoreCase(LARGE);
    }

    private void addError(String type, String message, Exception e) {
        if (e != null) {
            errors.append(type)
                    .append(" : ")
                    .append(message)
                    .append(" excception : ")
                    .append(e.getMessage())
                    .append("\n");
        } else {
            errors.append(type)
                    .append("Error :")
                    .append(message)
                    .append("\n");
        }
    }

    private void addOutput(String message) {
        output.append(message).append("\n");
    }

    public CloudResponse generateYmlCloudFile() {
        int code = 0;
        CloudResponse res;
        StringBuilder yml;
        yml = new StringBuilder();
        try {
            addOutput("Generating the YML");
            yml.append("infrastructures:\n");
            yml.append(" - name: My Cloud infrastructure\n");
            yml.append("   type: NEOTYS_CLOUD_LOAD_GENERATOR\n");
            yml.append("   workgroup: " + this.cloudWorkGroup + "\n");
            yml.append("   architecture: " + this.cloudType + "\n");
            yml.append("   duration: " + this.duration + "h\n");
            yml.append("   zones:\n");
            for (int i = 0; i < cloudLocations.size(); i++) {
                if (cloudLocations.get(i).getCloudZoneID() == null) {
                    addError("Location", "Location NUll", null);
                }
                yml.append("   - id: " + cloudLocations.get(i).getCloudZoneID() + "\n");
                yml.append("     count: " + cloudLocations.get(i).getNumberOfLG() + "\n");
            }

            if (yml.length() > 0) {
                addOutput("YML generated : " + yml.toString());
                code = 0;
            }
            if (errors.length() > 0)
                code = 1;
        } catch (Exception e) {
            addError("ERROR", "Technical Error", e);
        }
        res = new CloudResponse(yml.toString(), code);

        res.addToError(errors);
        res.addToOut(output);

        return res;
    }
}


