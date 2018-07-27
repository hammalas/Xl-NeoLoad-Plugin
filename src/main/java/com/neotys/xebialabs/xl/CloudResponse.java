package com.neotys.xebialabs.xl;

/**
 * Created by hrexed on 16/02/18.
 */
public class CloudResponse {
    public int rc;
    public String stdout;
    public String stderr;
    public String ymlContent;

    CloudResponse(String content, int code) {
        this.ymlContent = content;
        this.rc = code;
    }

    void addToOut(StringBuilder s) {
        this.stdout += "\n" + s.toString();
    }

    void addToError(StringBuilder s) {
        this.stderr += "\n" + s.toString();
    }
}