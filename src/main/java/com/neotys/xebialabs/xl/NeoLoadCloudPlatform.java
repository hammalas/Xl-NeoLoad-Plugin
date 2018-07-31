package com.neotys.xebialabs.xl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrexed on 15/02/18.
 */
public class NeoLoadCloudPlatform {
    private String cloudWorkGroup;
    private int duration;
    private List<CloudBooking> cloudLocations = new ArrayList<>();;
    private StringBuilder errors = new StringBuilder();
    private StringBuilder output = new StringBuilder();
    private String cloudType;
    private static final String MEDIUM = "MEDIUM";
    private static final String LARGE = "LARGE";

    public NeoLoadCloudPlatform(String cloudWorkGroup, String duration, String cloudType) {

            if (isCorrectType(cloudType)) {
                this.cloudType = cloudType.toUpperCase();
            } else {
                this.cloudType = MEDIUM;
            }
            this.cloudWorkGroup = cloudWorkGroup;
	    try {
            this.duration = Integer.parseInt(duration);

        } catch (NumberFormatException e) {
            addError("ERROR", "Conversion issue", e);
        }
    }

    public void addCloudLocation(String locationId, String number) {
        cloudLocations.add(new CloudBooking(Integer.parseInt(number), locationId));
    }

    private boolean isCorrectType(String type) {
        return MEDIUM.equalsIgnoreCase(type) || LARGE.equalsIgnoreCase(type);
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
        CloudResponse cloudResponse;
        StringBuilder yml = new StringBuilder();
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
                    addError("Location", "Location NULL", null);
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
        cloudResponse = new CloudResponse(yml.toString(), code);

        cloudResponse.addToError(errors);
        cloudResponse.addToOut(output);

        return cloudResponse;
    }
}


