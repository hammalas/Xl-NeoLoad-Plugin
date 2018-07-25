package com.neotys.xebialabs.xl;

/**
 * Created by hrexed on 16/02/18.
 */
public class CloudResponse {
    public int rc;
    public String stdout;
    public String stderr;
    public String YmlContent;

    public CloudResponse(String content,int code)
    {
        this.YmlContent=content;
        this.rc=code;
    }


    public void addToOut(StringBuilder s)
    {

        this.stdout+="\n" + s.toString();
    }

    public void addToError(StringBuilder s)
    {

        this.stderr+="\n" + s.toString();
    }





}