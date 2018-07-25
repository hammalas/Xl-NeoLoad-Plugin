package com.neotys.xebialabs.xl;

public class CloudBooking
{
    private String CloudLocation;
    private int NumberOfLG;
    //AWS---------------
    private static final String AP_NORTHEAST_1="ap_northeast_1";
    private static final String AP_NORTHEAST_1_location="Asia-Pacific Japan (Tokyo 1)";
    private static final String AP_NORTHEAST_4="ap_northeast_4";
    private static final String AP_NORTHEAST_4_location="Asia-Pacific South Korea (Seoul 1)";
    private static final String AP_SOUTH_2="ap_south_2";
    private static final String AP_SOUTH_2_location="Asia-Pacific India (Mumbai 1)";
    private static final String AP_SOUTHEAST_1="ap_southeast_1";
    private static final String AP_SOUTHEAST_1_location="Asia-Pacific Singapore 1";
    private static final String AP_SOUTHEAST_3="ap_southeast_3";
    private static final String AP_SOUTHEAST_3_location="Asia-Pacific Australia (Sydney 1)";
    private static final String EU_CENTER_2="eu_center_2";
    private static final String EU_CENTER_2_location="Europe Germany (Frankfurt 1)";
    private static final String EU_WEST_1="eu_west_1";
    private static final String EU_WEST_1_location="Europe Ireland (Dublin 1)";
    private static final String EU_WEST_12="eu_west_12";
    private static final String EU_WEST_12_location="Europe United Kingdom (London 3)";
    private static final String NA_NORTHEAST_5="na_northeast_5";
    private static final String NA_NORTHEAST_5_location="North-America Canada (Montreal 3)";
    private static final String SA_EAST_1="sa_east_1";
    private static final String SA_EAST_1_location="South-America Brazil (Sao Paulo 1)";
    private static final String US_EAST_1="us_east_1";
    private static final String US_EAST_1_location="USA East - Virginia (Ashburn 1)";
    private static final String US_EAST_8="us_east_8";
    private static final String US_EAST_8_location="USA East - Ohio (Columbus)";
    private static final String US_WEST_1="us_west_1";
    private static final String US_WEST_1_location="USA West - California (San Jose 1)";
    private static final String US_WEST_2="us_west_2";
    private static final String US_WEST_2_location="USA West - Oregon (Portland)";

    //---Rackspace
    private static final String US_NORTHCENTRAL_1="us_northcentral_1";
    private static final String US_NORTHCENTRAL_location="USA Central - Illinois (Chicago 1)";
    private static final String US_SOUTHCENTRAL_1="us_southcentral_1";
    private static final String US_SOUTHCENTRAL_1_location="USA Central - Texas (Dallas 1)";
    private static final String US_EAST_5="us_east_5";
    private static final String US_EAST_5_location="USA East - Virginia (Reston)";
    private static final String AP_EAST_2="ap_east_2";
    private static final String AP_EAST_2_location="Asia-Pacific China (Hong Kong 2)";
    private static final String AP_SOUTHEAST_4="ap_southeast_4";
    private static final String AP_SOUTHEAST_4_location="Asia-Pacific Australia (Sydney 2)";
    private static final String EU_WEST_2="eu_west_2";
    private static final String EU_WEST_2_location="Europe United Kingdom (London 1)";


    //CloudSigma
    private static final String EU_CENTER_1="eu_center_1";
    private static final String EU_CENTER_1_location="Europe Switzerland (Zurich)";

    //Microsoft Azure
    private static final String AP_EAST_1="ap_east_1";
    private static final String AP_EAST_1_location="Asia-Pacific China (Hong Kong 1)";
    private static final String AP_NORTHEAST_2="ap_northeast_2";
    private static final String AP_NORTHEAST_2_location="Asia-Pacific Japan (Saitama)";
    private static final String AP_NORTHEAST_3="ap_northeast_3";
    private static final String AP_NORTHEAST_3_location="Asia-Pacific Japan (Osaka)";
    private static final String AP_NORTHEAST_5="ap_northeast_5";
    private static final String AP_NORTHEAST_5_location="Asia-Pacific South Korea (Seoul 2)";
    private static final String AP_NORTHEAST_6="ap_northeast_6";
    private static final String AP_NORTHEAST_6_location="Asia-Pacific South Korea (Busan)";
    private static final String AP_SOUTH_3="ap_south_3";
    private static final String AP_SOUTH_3_location="Asia-Pacific India (Pune)";
    private static final String AP_SOUTH_4="ap_south_4";
    private static final String AP_SOUTH_4_location="Asia-Pacific India (Chennai 2)";
    private static final String AP_SOUTHEAST_2="ap_southeast_2";
    private static final String AP_SOUTHEAST_2_location="Asia-Pacific Singapore 2";
    private static final String AP_SOUTHEAST_6="ap_southeast_6";
    private static final String AP_SOUTHEAST_6_location="Asia-Pacific Australia (Melbourne 2)";
    private static final String AP_SOUTHEAST_7="ap_southeast_7";
    private static final String AP_SOUTHEAST_7_location="Asia-Pacific Australia (Sydney 3)";
    private static final String EU_WEST_5="eu_west_5";
    private static final String EU_WEST_5_location="Europe Netherlands (Amsterdam 2)";
    private static final String EU_WEST_6="eu_west_6";
    private static final String EU_WEST_6_location="Europe Ireland (Dublin 2)";
    private static final String EU_WEST_13="eu_west_13";
    private static final String EU_WEST_13_location="Europe United Kingdom (Cardiff)";
    private static final String EU_WEST_14="eu_west_14";
    private static final String EU_WEST_14_location="Europe United Kingdom (London 4)";
    private static final String NA_NORTHEAST_6="na_northeast_6";
    private static final String NA_NORTHEAST_6_location="North-America Canada (Toronto 2)";
    private static final String NA_NORTHEAST_7="na_northeast_7";
    private static final String NA_NORTHEAST_7_location="North-America Canada (Québec City)";
    private static final String SA_EAST_2="sa_east_2";
    private static final String SA_EAST_2_location="South-America Brazil (Sao Paulo 2)";
    private static final String US_EAST_4="us_east_4";
    private static final String US_EAST_4_location="USA East - Virginia (Ashburn 2)";
    private static final String US_NORTHCENTRAL_2="us_northcentral_2";
    private static final String US_NORTHCENTRAL_2_location="USA Central - Illinois (Chicago 2)";
    private static final String US_NORTHCENTRAL_3="us_northcentral_3";
    private static final String US_NORTHCENTRAL_3_location="USA Central - Iowa (West Des Moines)";
    private static final String US_SOUTHCENTRAL_2="us_southcentral_2";
    private static final String US_SOUTHCENTRAL_2_location="USA Central - Texas (San Antonio)";
    private static final String US_WEST_7="us_west_7";
    private static final String US_WEST_7_location="USA West - California (San Francisco)";
    private static final String US_WEST_8="us_west_8";
    private static final String US_WEST_8_location="USA West - California (Los Angeles)";
    private static final String US_WEST_9="us_west_9";
    private static final String US_WEST_9_location="USA West - Arizona (Phoenix)";

    //Google Compute Engine
    private static final String AP_EAST_3="ap_east_3";
    private static final String AP_EAST_3_location="Asia-Pacific Taiwan (Changhua County)";
    private static final String AP_NORTHEAST_7="ap_northeast_7";
    private static final String AP_NORTHEAST_7_location="Asia-Pacific Japan (Tokyo 2)";
    private static final String AP_SOUTHEAST_8="ap_southeast_8";
    private static final String AP_SOUTHEAST_8_location="Asia-Pacific Singapore 3";
    private static final String EU_WEST_7="eu_west_7";
    private static final String EU_WEST_7_location="Europe Belgium (St. Ghislain)";
    private static final String US_EAST_3="us_east_3";
    private static final String US_EAST_3_location="USA East - South Carolina (Berkeley County)";
    private static final String US_EAST_9="us_east_9";
    private static final String US_EAST_9_location="USA East - Virginia (Ashburn 3)";
    private static final String US_NORTHCENTRAl_4="us_northcentral_4";
    private static final String US_NORTHCENTRAl_4_location="USA Central - Iowa (Council Bluffs)";
    private static final String US_WEST_6="us_west_6";
    private static final String US_WEST_6_location="USA West - Oregon (The Dalles)";

    // OVH
    private static final String EU_WEST_8="eu_west_8";
    private static final String EU_WEST_8_location="Europe France (Strasbourg)";
    private static final String EU_WEST_11="eu_west_11";
    private static final String EU_WEST_11_location="Europe France (Gravelines)";
    private static final String NA_NORTHEAST_1="na_northeast_1";
    private static final String NA_NORTHEAST_1_location="North-America Canada (Montreal 1)";

    // SoftLayer
    private static final String EU_WEST_3="eu_west_3";
    private static final String EU_WEST_3_location="Europe United Kingdom (London 2)";
    private static final String EU_WEST_9="eu_west_9";
    private static final String EU_WEST_9_location="Europe France (Paris 1)";
    private static final String EU_WEST_10="eu_west_10";
    private static final String EU_WEST_10_location="Europe Netherlands (Amsterdam 3)";
    private static final String EU_CENTER_3="eu_center_3";
    private static final String EU_CENTER_3_location="Europe Germany (Frankfurt 2)";
    private static final String EU_NORTH_1="eu_north_1";
    private static final String EU_NORTH_1_location="Europe Norway (Oslo)";
    private static final String EU_SOUTH_1="eu_south_1";
    private static final String EU_SOUTH_1_location="Europe Italy (Milan)";
    private static final String NA_NORTHEAST_2="na_northeast_2";
    private static final String NA_NORTHEAST_2_location="North-America Canada (Toronto 1)";
    private static final String NA_NORTHEAST_3="na_northeast_3";
    private static final String NA_NORTHEAST_3_location="North-America Mexico (Mexico City)";
    private static final String NA_NORTHEAST_4="na_northeast_4";
    private static final String NA_NORTHEAST_4_location="North-America Canada (Montreal 2)";
    private static final String US_WEST_3="us_west_3";
    private static final String US_WEST_3_location="USA West - Washington (Seattle)";
    private static final String US_WEST_5="us_west_5";
    private static final String US_WEST_5_location="USA West - California (San Jose 2)";
    private static final String US_SOUTHCENTRAl_3="us_southcentral_3";
    private static final String US_SOUTHCENTRAl_3_location="USA Central - Texas (Houston)";
    private static final String US_SOUTHCENTRAL_4="us_southcentral_4";
    private static final String US_SOUTHCENTRAL_4_location="USA Central - Texas (Dallas 2)";
    private static final String US_EAST_2="us_east_2";
    private static final String US_EAST_2_location="USA East - Washington, D.C.";
    private static final String AP_SOUTH_EAST_5="ap_southeast_5";
    private static final String AP_SOUTH_EAST_5_location="Asia-Pacific Australia (Melbourne 1)";
    private static final String AP_SOUTH_1="ap_south_1";
    private static final String AP_SOUTH_1_location="Asia-Pacific India (Chennai 1)";

    // Outscale
    private static final String EU_WEST_4="eu_west_4";
    private static final String EU_WEST_4_location="Europe France (Paris 2)";
    private static final String US_EAST_7="us_east_7";
    private static final String US_EAST_7_location="USA East - New Jersey";

    public CloudBooking(String location,int NbLG)
    {
        ConvertCloudZone(location);
        this.NumberOfLG=NbLG;
    }
    public CloudBooking(int NbLG,String locationid)
    {
        ConvertCloudZoneID(locationid);
        this.NumberOfLG=NbLG;
    }

    public int getNumberOfLG(){
        return this.NumberOfLG;
    }
    public String GetCloudZoneID()
    {
        System.out.println("Location :"+this.CloudLocation);
        return this.CloudLocation.replace("_","-");
    }
    private void ConvertCloudZoneID(String location)
    {
        switch (location)
        {
            case AP_EAST_2:
                this.CloudLocation=AP_EAST_2;
                break;
            case AP_EAST_3:
                this.CloudLocation=AP_EAST_3;
                break;
            case AP_EAST_1:
                this.CloudLocation=AP_EAST_1;
                break;
            case AP_NORTHEAST_1:
                this.CloudLocation=AP_NORTHEAST_1;
                break;
            case AP_NORTHEAST_2:
                this.CloudLocation=AP_NORTHEAST_2;
                break;
            case AP_NORTHEAST_3:
                this.CloudLocation=AP_NORTHEAST_3;
                break;
            case AP_NORTHEAST_4:
                this.CloudLocation=AP_NORTHEAST_4;
                break;
            case AP_NORTHEAST_5:
                this.CloudLocation=AP_NORTHEAST_5;
                break;
            case AP_NORTHEAST_6:
                this.CloudLocation=AP_NORTHEAST_6;
                break;
            case AP_NORTHEAST_7:
                this.CloudLocation=AP_NORTHEAST_7;
                break;
            case AP_SOUTH_1:
                this.CloudLocation=AP_SOUTH_1;
                break;
            case AP_SOUTH_2:
                this.CloudLocation=AP_SOUTH_2;
                break;
            case AP_SOUTH_3:
                this.CloudLocation=AP_SOUTH_3;
                break;
            case AP_SOUTH_4:
                this.CloudLocation=AP_SOUTH_4;
                break;
            case AP_SOUTH_EAST_5:
                this.CloudLocation=AP_SOUTH_EAST_5;
                break;
            case AP_SOUTHEAST_1:
                this.CloudLocation=AP_SOUTHEAST_1;
                break;
            case AP_SOUTHEAST_2:
                this.CloudLocation=AP_SOUTHEAST_2;
                break;
            case AP_SOUTHEAST_3:
                this.CloudLocation=AP_SOUTHEAST_3;
                break;
            case AP_SOUTHEAST_4:
                this.CloudLocation=AP_SOUTHEAST_4;
                break;
            case AP_SOUTHEAST_6:
                this.CloudLocation=AP_SOUTHEAST_6;
                break;
            case AP_SOUTHEAST_7:
                this.CloudLocation=AP_SOUTHEAST_7;
                break;
            case AP_SOUTHEAST_8:
                this.CloudLocation=AP_SOUTHEAST_8;
                break;
            case EU_CENTER_1:
                this.CloudLocation=EU_CENTER_1;
                break;
            case EU_CENTER_2:
                this.CloudLocation=EU_CENTER_2;

                break;
            case EU_CENTER_3:
                this.CloudLocation=EU_CENTER_3;

                break;
            case EU_WEST_1:
                this.CloudLocation=EU_WEST_1;

                break;
            case EU_WEST_2:
                this.CloudLocation=EU_WEST_2;
                break;
            case EU_WEST_3:
                this.CloudLocation=EU_WEST_3;
                break;
            case EU_WEST_4:
                this.CloudLocation=EU_WEST_4;
                break;
            case EU_WEST_5:
                this.CloudLocation=EU_WEST_5;
                break;
            case EU_WEST_6:
                this.CloudLocation=EU_WEST_6;
                break;
            case EU_WEST_7:
                this.CloudLocation=EU_WEST_7;
                break;
            case EU_WEST_8:
                this.CloudLocation=EU_WEST_8;
                break;
            case EU_WEST_9:
                this.CloudLocation=EU_WEST_9;
                break;
            case EU_WEST_10:
                this.CloudLocation=EU_WEST_10;
                break;
            case EU_WEST_11:
                this.CloudLocation=EU_WEST_11;
                break;
            case EU_WEST_12:
                this.CloudLocation=EU_WEST_12;
                break;
            case EU_WEST_13:
                this.CloudLocation=EU_WEST_13;
                break;
            case  EU_WEST_14:
                this.CloudLocation= EU_WEST_14;
                break;
            case EU_NORTH_1:
                this.CloudLocation=EU_NORTH_1;
                break;
            case EU_SOUTH_1:
                this.CloudLocation=EU_SOUTH_1;
                break;
            case US_EAST_1:
                this.CloudLocation=US_EAST_1;
                break;
            case US_EAST_2:
                this.CloudLocation=US_EAST_2;
                break;
            case US_EAST_3:
                this.CloudLocation=US_EAST_3;
                break;
            case US_EAST_4:
                this.CloudLocation=US_EAST_4;
                break;
            case US_EAST_5:
                this.CloudLocation=US_EAST_5;
                break;
            case US_EAST_7:
                this.CloudLocation=US_EAST_7;
                break;
            case US_EAST_8:
                this.CloudLocation=US_EAST_8;
                break;
            case US_EAST_9:
                this.CloudLocation=US_EAST_9;
                break;
            case US_NORTHCENTRAL_2:
                this.CloudLocation=US_NORTHCENTRAL_2;
                break;
            case US_NORTHCENTRAL_1:
                this.CloudLocation=US_NORTHCENTRAL_1;
                break;
            case US_NORTHCENTRAL_3:
                this.CloudLocation=US_NORTHCENTRAL_3;
                break;
            case US_NORTHCENTRAl_4:
                this.CloudLocation=US_NORTHCENTRAl_4;
                break;
            case US_SOUTHCENTRAL_1:
                this.CloudLocation=US_SOUTHCENTRAL_1;
                break;
            case US_SOUTHCENTRAL_2:
                this.CloudLocation=US_SOUTHCENTRAL_2;
                break;
            case US_SOUTHCENTRAl_3:
                this.CloudLocation=US_SOUTHCENTRAl_3;
                break;
            case US_SOUTHCENTRAL_4:
                this.CloudLocation=US_SOUTHCENTRAL_4;
                break;
            case US_WEST_1:
                this.CloudLocation=US_WEST_1;
                break;
            case US_WEST_2:
                this.CloudLocation=US_WEST_2;
                break;
            case US_WEST_3:
                this.CloudLocation=US_WEST_3;
                break;

            case US_WEST_5:
                this.CloudLocation=US_WEST_5;
                break;
            case US_WEST_6:
                this.CloudLocation=US_WEST_6;
                break;
            case US_WEST_7:
                this.CloudLocation=US_WEST_7;
                break;
            case US_WEST_8:
                this.CloudLocation=US_WEST_8;
                break;
            case US_WEST_9:
                this.CloudLocation=US_WEST_9;
                break;
            case NA_NORTHEAST_1:
                this.CloudLocation=NA_NORTHEAST_1;
                break;
            case NA_NORTHEAST_2:
                this.CloudLocation=NA_NORTHEAST_2;
                break;
            case NA_NORTHEAST_3:
                this.CloudLocation=NA_NORTHEAST_3;
                break;
            case NA_NORTHEAST_4:
                this.CloudLocation=NA_NORTHEAST_4;
                break;
            case NA_NORTHEAST_5:
                this.CloudLocation=NA_NORTHEAST_5;
                break;
            case NA_NORTHEAST_6:
                this.CloudLocation=NA_NORTHEAST_6;
                break;
            case NA_NORTHEAST_7:
                this.CloudLocation=NA_NORTHEAST_7;
                break;
            case SA_EAST_1:
                this.CloudLocation=SA_EAST_1;
                break;
            case SA_EAST_2:
                this.CloudLocation=SA_EAST_2;
                break;
        }
    }
    private void ConvertCloudZone(String location)
    {
        switch (location)
        {
            case AP_EAST_2_location:
                this.CloudLocation=AP_EAST_2;
                break;
            case AP_EAST_3_location:
                this.CloudLocation=AP_EAST_3;
                break;
            case AP_EAST_1_location:
                this.CloudLocation=AP_EAST_1;
                break;
            case AP_NORTHEAST_1_location:
                this.CloudLocation=AP_NORTHEAST_1;
                break;
            case AP_NORTHEAST_2_location:
                this.CloudLocation=AP_NORTHEAST_2;
                break;
            case AP_NORTHEAST_3_location:
                this.CloudLocation=AP_NORTHEAST_3;
                break;
            case AP_NORTHEAST_4_location:
                this.CloudLocation=AP_NORTHEAST_4;
                break;
            case AP_NORTHEAST_5_location:
                this.CloudLocation=AP_NORTHEAST_5;
                break;
            case AP_NORTHEAST_6_location:
                this.CloudLocation=AP_NORTHEAST_6;
                break;
            case AP_NORTHEAST_7_location:
                this.CloudLocation=AP_NORTHEAST_7;
                break;
            case AP_SOUTH_1_location:
                this.CloudLocation=AP_SOUTH_1;
                break;
            case AP_SOUTH_2_location:
                this.CloudLocation=AP_SOUTH_2;
                break;
            case AP_SOUTH_3_location:
                this.CloudLocation=AP_SOUTH_3;
                break;
            case AP_SOUTH_4_location:
                this.CloudLocation=AP_SOUTH_4;
                break;
            case AP_SOUTH_EAST_5_location:
                this.CloudLocation=AP_SOUTH_EAST_5;
                break;
            case AP_SOUTHEAST_1_location:
                this.CloudLocation=AP_SOUTHEAST_1;
                break;
            case AP_SOUTHEAST_2_location:
                this.CloudLocation=AP_SOUTHEAST_2;
                break;
            case AP_SOUTHEAST_3_location:
                this.CloudLocation=AP_SOUTHEAST_3;
                break;
            case AP_SOUTHEAST_4_location:
                this.CloudLocation=AP_SOUTHEAST_4;
                break;
            case AP_SOUTHEAST_6_location:
                this.CloudLocation=AP_SOUTHEAST_6;
                break;
            case AP_SOUTHEAST_7_location:
                this.CloudLocation=AP_SOUTHEAST_7;
                break;
            case AP_SOUTHEAST_8_location:
                this.CloudLocation=AP_SOUTHEAST_8;
                break;
            case EU_CENTER_1_location:
                this.CloudLocation=EU_CENTER_1;
                break;
            case EU_CENTER_2_location:
                this.CloudLocation=EU_CENTER_2;

                break;
            case EU_CENTER_3_location:
                this.CloudLocation=EU_CENTER_3;

                break;
            case EU_WEST_1_location:
                this.CloudLocation=EU_WEST_1;

                break;
            case EU_WEST_2_location:
                this.CloudLocation=EU_WEST_2;
                break;
            case EU_WEST_3_location:
                this.CloudLocation=EU_WEST_3;
                break;
            case EU_WEST_4_location:
                this.CloudLocation=EU_WEST_4;
                break;
            case EU_WEST_5_location:
                this.CloudLocation=EU_WEST_5;
                break;
            case EU_WEST_6_location:
                this.CloudLocation=EU_WEST_6;
                break;
            case EU_WEST_7_location:
                this.CloudLocation=EU_WEST_7;
                break;
            case EU_WEST_8_location:
                this.CloudLocation=EU_WEST_8;
                break;
            case EU_WEST_9_location:
                this.CloudLocation=EU_WEST_9;
                break;
            case EU_WEST_10_location:
                this.CloudLocation=EU_WEST_10;
                break;
            case EU_WEST_11_location:
                this.CloudLocation=EU_WEST_11;
                break;
            case EU_WEST_12_location:
                this.CloudLocation=EU_WEST_12;
                break;
            case EU_WEST_13_location:
                this.CloudLocation=EU_WEST_13;
                break;
            case  EU_WEST_14_location:
                this.CloudLocation= EU_WEST_14;
                break;
            case EU_NORTH_1_location:
                this.CloudLocation=EU_NORTH_1;
                break;
            case EU_SOUTH_1_location:
                this.CloudLocation=EU_SOUTH_1;
                break;
            case US_EAST_1_location:
                this.CloudLocation=US_EAST_1;
                break;
            case US_EAST_2_location:
                this.CloudLocation=US_EAST_2;
                break;
            case US_EAST_3_location:
                this.CloudLocation=US_EAST_3;
                break;
            case US_EAST_4_location:
                this.CloudLocation=US_EAST_4;
                break;
            case US_EAST_5_location:
                this.CloudLocation=US_EAST_5;
                break;
            case US_EAST_7_location:
                this.CloudLocation=US_EAST_7;
                break;
            case US_EAST_8_location:
                this.CloudLocation=US_EAST_8;
                break;
            case US_EAST_9_location:
                this.CloudLocation=US_EAST_9;
                break;
            case US_NORTHCENTRAL_2_location:
                this.CloudLocation=US_NORTHCENTRAL_2;
                break;
            case US_NORTHCENTRAL_location:
                this.CloudLocation=US_NORTHCENTRAL_1;
                break;
            case US_NORTHCENTRAL_3_location:
                this.CloudLocation=US_NORTHCENTRAL_3;
                break;
            case US_NORTHCENTRAl_4_location:
                this.CloudLocation=US_NORTHCENTRAl_4;
                break;
            case US_SOUTHCENTRAL_1_location:
                this.CloudLocation=US_SOUTHCENTRAL_1;
                break;
            case US_SOUTHCENTRAL_2_location:
                this.CloudLocation=US_SOUTHCENTRAL_2;
                break;
            case US_SOUTHCENTRAl_3_location:
                this.CloudLocation=US_SOUTHCENTRAl_3;
                break;
            case US_SOUTHCENTRAL_4_location:
                this.CloudLocation=US_SOUTHCENTRAL_4;
                break;
            case US_WEST_1_location:
                this.CloudLocation=US_WEST_1;
                break;
            case US_WEST_2_location:
                this.CloudLocation=US_WEST_2;
                break;
            case US_WEST_3_location:
                this.CloudLocation=US_WEST_3;
                break;

            case US_WEST_5_location:
                this.CloudLocation=US_WEST_5;
                break;
            case US_WEST_6_location:
                this.CloudLocation=US_WEST_6;
                break;
            case US_WEST_7_location:
                this.CloudLocation=US_WEST_7;
                break;
            case US_WEST_8_location:
                this.CloudLocation=US_WEST_8;
                break;
            case US_WEST_9_location:
                this.CloudLocation=US_WEST_9;
                break;
            case NA_NORTHEAST_1_location:
                this.CloudLocation=NA_NORTHEAST_1;
                break;
            case NA_NORTHEAST_2_location:
                this.CloudLocation=NA_NORTHEAST_2;
                break;
            case NA_NORTHEAST_3_location:
                this.CloudLocation=NA_NORTHEAST_3;
                break;
            case NA_NORTHEAST_4_location:
                this.CloudLocation=NA_NORTHEAST_4;
                break;
            case NA_NORTHEAST_5_location:
                this.CloudLocation=NA_NORTHEAST_5;
                break;
            case NA_NORTHEAST_6_location:
                this.CloudLocation=NA_NORTHEAST_6;
                break;
            case NA_NORTHEAST_7_location:
                this.CloudLocation=NA_NORTHEAST_7;
                break;
            case SA_EAST_1_location:
                this.CloudLocation=SA_EAST_1;
                break;
            case SA_EAST_2_location:
                this.CloudLocation=SA_EAST_2;
                break;
        }
    }
}
