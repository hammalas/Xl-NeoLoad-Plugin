package com.neotys.xebialabs.xl;

/**
 * Created by hrexed on 23/03/18.
 */
class NeoLoadVarsResponse extends CloudResponse {

    NeoLoadVarsResponse(String content, int code) {
        super(content, code);
    }

    void appendContent(String content) {
        if (!this.ymlContent.contains(content))
            this.ymlContent += "," + content;
    }
}
