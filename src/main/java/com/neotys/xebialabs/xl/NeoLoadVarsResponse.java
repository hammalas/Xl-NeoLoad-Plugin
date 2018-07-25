package com.neotys.xebialabs.xl;

/**
 * Created by hrexed on 23/03/18.
 */
public class NeoLoadVarsResponse extends CloudResponse {

    public NeoLoadVarsResponse(String content, int code) {
        super(content, code);
    }
    public void appendContent(String Content)

    {
        if(!this.YmlContent.contains(Content))
             this.YmlContent+=","+Content;
    }
}
