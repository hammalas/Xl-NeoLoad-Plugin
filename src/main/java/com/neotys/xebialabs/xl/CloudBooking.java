package com.neotys.xebialabs.xl;

class CloudBooking {
    private String cloudLocation;
    private int numberOfLG;

    //AWS---------------
    private static final String AP_NORTH_EAST_1 = "ap_northeast_1";
    private static final String AP_NORTH_EAST_1_LOCATION = "Asia-Pacific Japan (Tokyo 1)";
    private static final String AP_NORTH_EAST_4 = "ap_northeast_4";
    private static final String AP_NORTH_EAST_4_LOCATION = "Asia-Pacific South Korea (Seoul 1)";
    private static final String AP_SOUTH_2 = "ap_south_2";
    private static final String AP_SOUTH_2_LOCATION = "Asia-Pacific India (Mumbai 1)";
    private static final String AP_SOUTH_EAST_1 = "ap_southeast_1";
    private static final String AP_SOUTH_EAST_1_LOCATION = "Asia-Pacific Singapore 1";
    private static final String AP_SOUTH_EAST_3 = "ap_southeast_3";
    private static final String AP_SOUTH_EAST_3_LOCATION = "Asia-Pacific Australia (Sydney 1)";
    private static final String EU_CENTER_2 = "eu_center_2";
    private static final String EU_CENTER_2_LOCATION = "Europe Germany (Frankfurt 1)";
    private static final String EU_WEST_1 = "eu_west_1";
    private static final String EU_WEST_1_LOCATION = "Europe Ireland (Dublin 1)";
    private static final String EU_WEST_12 = "eu_west_12";
    private static final String EU_WEST_12_LOCATION = "Europe United Kingdom (London 3)";
    private static final String NA_NORTH_EAST_5 = "na_northeast_5";
    private static final String NA_NORTH_EAST_5_LOCATION = "North-America Canada (Montreal 3)";
    private static final String SA_EAST_1 = "sa_east_1";
    private static final String SA_EAST_1_LOCATION = "South-America Brazil (Sao Paulo 1)";
    private static final String US_EAST_1 = "us_east_1";
    private static final String US_EAST_1_LOCATION = "USA East - Virginia (Ashburn 1)";
    private static final String US_EAST_8 = "us_east_8";
    private static final String US_EAST_8_LOCATION = "USA East - Ohio (Columbus)";
    private static final String US_WEST_1 = "us_west_1";
    private static final String US_WEST_1_LOCATION = "USA West - California (San Jose 1)";
    private static final String US_WEST_2 = "us_west_2";
    private static final String US_WEST_2_LOCATION = "USA West - Oregon (Portland)";

    //---Rackspace
    private static final String US_NORTH_CENTRAL_1 = "us_northcentral_1";
    private static final String US_NORTH_CENTRAL_LOCATION = "USA Central - Illinois (Chicago 1)";
    private static final String US_SOUTH_CENTRAL_1 = "us_southcentral_1";
    private static final String US_SOUTH_CENTRAL_1_LOCATION = "USA Central - Texas (Dallas 1)";
    private static final String US_EAST_5 = "us_east_5";
    private static final String US_EAST_5_LOCATION = "USA East - Virginia (Reston)";
    private static final String AP_EAST_2 = "ap_east_2";
    private static final String AP_EAST_2_LOCATION = "Asia-Pacific China (Hong Kong 2)";
    private static final String AP_SOUTH_EAST_4 = "ap_southeast_4";
    private static final String AP_SOUTH_EAST_4_LOCATION = "Asia-Pacific Australia (Sydney 2)";
    private static final String EU_WEST_2 = "eu_west_2";
    private static final String EU_WEST_2_LOCATION = "Europe United Kingdom (London 1)";

    //CloudSigma
    private static final String EU_CENTER_1 = "eu_center_1";
    private static final String EU_CENTER_1_LOCATION = "Europe Switzerland (Zurich)";

    //Microsoft Azure
    private static final String AP_EAST_1 = "ap_east_1";
    private static final String AP_EAST_1_LOCATION = "Asia-Pacific China (Hong Kong 1)";
    private static final String AP_NORTH_EAST_2 = "ap_northeast_2";
    private static final String AP_NORTH_EAST_2_LOCATION = "Asia-Pacific Japan (Saitama)";
    private static final String AP_NORTH_EAST_3 = "ap_northeast_3";
    private static final String AP_NORTH_EAST_3_LOCATION = "Asia-Pacific Japan (Osaka)";
    private static final String AP_NORTH_EAST_5 = "ap_northeast_5";
    private static final String AP_NORTH_EAST_5_LOCATION = "Asia-Pacific South Korea (Seoul 2)";
    private static final String AP_NORTH_EAST_6 = "ap_northeast_6";
    private static final String AP_NORTH_EAST_6_LOCATION = "Asia-Pacific South Korea (Busan)";
    private static final String AP_SOUTH_3 = "ap_south_3";
    private static final String AP_SOUTH_3_LOCATION = "Asia-Pacific India (Pune)";
    private static final String AP_SOUTH_4 = "ap_south_4";
    private static final String AP_SOUTH_4_LOCATION = "Asia-Pacific India (Chennai 2)";
    private static final String AP_SOUTH_EAST_2 = "ap_southeast_2";
    private static final String AP_SOUTH_EAST_2_LOCATION = "Asia-Pacific Singapore 2";
    private static final String AP_SOUTH_EAST_6 = "ap_southeast_6";
    private static final String AP_SOUTH_EAST_6_LOCATION = "Asia-Pacific Australia (Melbourne 2)";
    private static final String AP_SOUTH_EAST_7 = "ap_southeast_7";
    private static final String AP_SOUTH_EAST_7_LOCATION = "Asia-Pacific Australia (Sydney 3)";
    private static final String EU_WEST_5 = "eu_west_5";
    private static final String EU_WEST_5_LOCATION = "Europe Netherlands (Amsterdam 2)";
    private static final String EU_WEST_6 = "eu_west_6";
    private static final String EU_WEST_6_LOCATION = "Europe Ireland (Dublin 2)";
    private static final String EU_WEST_13 = "eu_west_13";
    private static final String EU_WEST_13_LOCATION = "Europe United Kingdom (Cardiff)";
    private static final String EU_WEST_14 = "eu_west_14";
    private static final String EU_WEST_14_LOCATION = "Europe United Kingdom (London 4)";
    private static final String NA_NORTH_EAST_6 = "na_northeast_6";
    private static final String NA_NORTH_EAST_6_LOCATION = "North-America Canada (Toronto 2)";
    private static final String NA_NORTH_EAST_7 = "na_northeast_7";
    private static final String NA_NORTH_EAST_7_LOCATION = "North-America Canada (Québec City)";
    private static final String SA_EAST_2 = "sa_east_2";
    private static final String SA_EAST_2_LOCATION = "South-America Brazil (Sao Paulo 2)";
    private static final String US_EAST_4 = "us_east_4";
    private static final String US_EAST_4_LOCATION = "USA East - Virginia (Ashburn 2)";
    private static final String US_NORTH_CENTRAL_2 = "us_northcentral_2";
    private static final String US_NORTH_CENTRAL_2_LOCATION = "USA Central - Illinois (Chicago 2)";
    private static final String US_NORTH_CENTRAL_3 = "us_northcentral_3";
    private static final String US_NORTH_CENTRAL_3_LOCATION = "USA Central - Iowa (West Des Moines)";
    private static final String US_SOUTH_CENTRAL_2 = "us_southcentral_2";
    private static final String US_SOUTH_CENTRAL_2_LOCATION = "USA Central - Texas (San Antonio)";
    private static final String US_WEST_7 = "us_west_7";
    private static final String US_WEST_7_LOCATION = "USA West - California (San Francisco)";
    private static final String US_WEST_8 = "us_west_8";
    private static final String US_WEST_8_LOCATION = "USA West - California (Los Angeles)";
    private static final String US_WEST_9 = "us_west_9";
    private static final String US_WEST_9_LOCATION = "USA West - Arizona (Phoenix)";

    //Google Compute Engine
    private static final String AP_EAST_3 = "ap_east_3";
    private static final String AP_EAST_3_LOCATION = "Asia-Pacific Taiwan (Changhua County)";
    private static final String AP_NORTH_EAST_7 = "ap_northeast_7";
    private static final String AP_NORTH_EAST_7_LOCATION = "Asia-Pacific Japan (Tokyo 2)";
    private static final String AP_SOUTH_EAST_8 = "ap_southeast_8";
    private static final String AP_SOUTH_EAST_8_LOCATION = "Asia-Pacific Singapore 3";
    private static final String EU_WEST_7 = "eu_west_7";
    private static final String EU_WEST_7_LOCATION = "Europe Belgium (St. Ghislain)";
    private static final String US_EAST_3 = "us_east_3";
    private static final String US_EAST_3_LOCATION = "USA East - South Carolina (Berkeley County)";
    private static final String US_EAST_9 = "us_east_9";
    private static final String US_EAST_9_LOCATION = "USA East - Virginia (Ashburn 3)";
    private static final String US_NORTH_CENTRAl_4 = "us_northcentral_4";
    private static final String US_NORTH_CENTRAl_4_LOCATION = "USA Central - Iowa (Council Bluffs)";
    private static final String US_WEST_6 = "us_west_6";
    private static final String US_WEST_6_LOCATION = "USA West - Oregon (The Dalles)";

    // OVH
    private static final String EU_WEST_8 = "eu_west_8";
    private static final String EU_WEST_8_LOCATION = "Europe France (Strasbourg)";
    private static final String EU_WEST_11 = "eu_west_11";
    private static final String EU_WEST_11_LOCATION = "Europe France (Gravelines)";
    private static final String NA_NORTH_EAST_1 = "na_northeast_1";
    private static final String NA_NORTH_EAST_1_LOCATION = "North-America Canada (Montreal 1)";

    // SoftLayer
    private static final String EU_WEST_3 = "eu_west_3";
    private static final String EU_WEST_3_LOCATION = "Europe United Kingdom (London 2)";
    private static final String EU_WEST_9 = "eu_west_9";
    private static final String EU_WEST_9_LOCATION = "Europe France (Paris 1)";
    private static final String EU_WEST_10 = "eu_west_10";
    private static final String EU_WEST_10_LOCATION = "Europe Netherlands (Amsterdam 3)";
    private static final String EU_CENTER_3 = "eu_center_3";
    private static final String EU_CENTER_3_LOCATION = "Europe Germany (Frankfurt 2)";
    private static final String EU_NORTH_1 = "eu_north_1";
    private static final String EU_NORTH_1_LOCATION = "Europe Norway (Oslo)";
    private static final String EU_SOUTH_1 = "eu_south_1";
    private static final String EU_SOUTH_1_LOCATION = "Europe Italy (Milan)";
    private static final String NA_NORTH_EAST_2 = "na_northeast_2";
    private static final String NA_NORTH_EAST_2_LOCATION = "North-America Canada (Toronto 1)";
    private static final String NA_NORTH_EAST_3 = "na_northeast_3";
    private static final String NA_NORTH_EAST_3_LOCATION = "North-America Mexico (Mexico City)";
    private static final String NA_NORTH_EAST_4 = "na_northeast_4";
    private static final String NA_NORTH_EAST_4_LOCATION = "North-America Canada (Montreal 2)";
    private static final String US_WEST_3 = "us_west_3";
    private static final String US_WEST_3_LOCATION = "USA West - Washington (Seattle)";
    private static final String US_WEST_5 = "us_west_5";
    private static final String US_WEST_5_LOCATION = "USA West - California (San Jose 2)";
    private static final String US_SOUTH_CENTRAl_3 = "us_southcentral_3";
    private static final String US_SOUTH_CENTRAl_3_LOCATION = "USA Central - Texas (Houston)";
    private static final String US_SOUTH_CENTRAL_4 = "us_southcentral_4";
    private static final String US_SOUTH_CENTRAL_4_LOCATION = "USA Central - Texas (Dallas 2)";
    private static final String US_EAST_2 = "us_east_2";
    private static final String US_EAST_2_LOCATION = "USA East - Washington, D.C.";
    private static final String AP_SOUTH_EAST_5 = "ap_southeast_5";
    private static final String AP_SOUTH_EAST_5_LOCATION = "Asia-Pacific Australia (Melbourne 1)";
    private static final String AP_SOUTH_1 = "ap_south_1";
    private static final String AP_SOUTH_1_LOCATION = "Asia-Pacific India (Chennai 1)";

    // Outscale
    private static final String EU_WEST_4 = "eu_west_4";
    private static final String EU_WEST_4_LOCATION = "Europe France (Paris 2)";
    private static final String US_EAST_7 = "us_east_7";
    private static final String US_EAST_7_LOCATION = "USA East - New Jersey";

    CloudBooking(int numberLG, String locationId) {
        convertCloudZoneID(locationId);
        this.numberOfLG = numberLG;
    }

    int getNumberOfLG() {
        return this.numberOfLG;
    }

    String getCloudZoneID() {
        return this.cloudLocation.replace("_", "-");
    }

    private void convertCloudZoneID(String location) {
        switch (location) {
            case AP_EAST_2:
                this.cloudLocation = AP_EAST_2;
                break;
            case AP_EAST_3:
                this.cloudLocation = AP_EAST_3;
                break;
            case AP_EAST_1:
                this.cloudLocation = AP_EAST_1;
                break;
            case AP_NORTH_EAST_1:
                this.cloudLocation = AP_NORTH_EAST_1;
                break;
            case AP_NORTH_EAST_2:
                this.cloudLocation = AP_NORTH_EAST_2;
                break;
            case AP_NORTH_EAST_3:
                this.cloudLocation = AP_NORTH_EAST_3;
                break;
            case AP_NORTH_EAST_4:
                this.cloudLocation = AP_NORTH_EAST_4;
                break;
            case AP_NORTH_EAST_5:
                this.cloudLocation = AP_NORTH_EAST_5;
                break;
            case AP_NORTH_EAST_6:
                this.cloudLocation = AP_NORTH_EAST_6;
                break;
            case AP_NORTH_EAST_7:
                this.cloudLocation = AP_NORTH_EAST_7;
                break;
            case AP_SOUTH_1:
                this.cloudLocation = AP_SOUTH_1;
                break;
            case AP_SOUTH_2:
                this.cloudLocation = AP_SOUTH_2;
                break;
            case AP_SOUTH_3:
                this.cloudLocation = AP_SOUTH_3;
                break;
            case AP_SOUTH_4:
                this.cloudLocation = AP_SOUTH_4;
                break;
            case AP_SOUTH_EAST_5:
                this.cloudLocation = AP_SOUTH_EAST_5;
                break;
            case AP_SOUTH_EAST_1:
                this.cloudLocation = AP_SOUTH_EAST_1;
                break;
            case AP_SOUTH_EAST_2:
                this.cloudLocation = AP_SOUTH_EAST_2;
                break;
            case AP_SOUTH_EAST_3:
                this.cloudLocation = AP_SOUTH_EAST_3;
                break;
            case AP_SOUTH_EAST_4:
                this.cloudLocation = AP_SOUTH_EAST_4;
                break;
            case AP_SOUTH_EAST_6:
                this.cloudLocation = AP_SOUTH_EAST_6;
                break;
            case AP_SOUTH_EAST_7:
                this.cloudLocation = AP_SOUTH_EAST_7;
                break;
            case AP_SOUTH_EAST_8:
                this.cloudLocation = AP_SOUTH_EAST_8;
                break;
            case EU_CENTER_1:
                this.cloudLocation = EU_CENTER_1;
                break;
            case EU_CENTER_2:
                this.cloudLocation = EU_CENTER_2;
                break;
            case EU_CENTER_3:
                this.cloudLocation = EU_CENTER_3;
                break;
            case EU_WEST_1:
                this.cloudLocation = EU_WEST_1;
                break;
            case EU_WEST_2:
                this.cloudLocation = EU_WEST_2;
                break;
            case EU_WEST_3:
                this.cloudLocation = EU_WEST_3;
                break;
            case EU_WEST_4:
                this.cloudLocation = EU_WEST_4;
                break;
            case EU_WEST_5:
                this.cloudLocation = EU_WEST_5;
                break;
            case EU_WEST_6:
                this.cloudLocation = EU_WEST_6;
                break;
            case EU_WEST_7:
                this.cloudLocation = EU_WEST_7;
                break;
            case EU_WEST_8:
                this.cloudLocation = EU_WEST_8;
                break;
            case EU_WEST_9:
                this.cloudLocation = EU_WEST_9;
                break;
            case EU_WEST_10:
                this.cloudLocation = EU_WEST_10;
                break;
            case EU_WEST_11:
                this.cloudLocation = EU_WEST_11;
                break;
            case EU_WEST_12:
                this.cloudLocation = EU_WEST_12;
                break;
            case EU_WEST_13:
                this.cloudLocation = EU_WEST_13;
                break;
            case EU_WEST_14:
                this.cloudLocation = EU_WEST_14;
                break;
            case EU_NORTH_1:
                this.cloudLocation = EU_NORTH_1;
                break;
            case EU_SOUTH_1:
                this.cloudLocation = EU_SOUTH_1;
                break;
            case US_EAST_1:
                this.cloudLocation = US_EAST_1;
                break;
            case US_EAST_2:
                this.cloudLocation = US_EAST_2;
                break;
            case US_EAST_3:
                this.cloudLocation = US_EAST_3;
                break;
            case US_EAST_4:
                this.cloudLocation = US_EAST_4;
                break;
            case US_EAST_5:
                this.cloudLocation = US_EAST_5;
                break;
            case US_EAST_7:
                this.cloudLocation = US_EAST_7;
                break;
            case US_EAST_8:
                this.cloudLocation = US_EAST_8;
                break;
            case US_EAST_9:
                this.cloudLocation = US_EAST_9;
                break;
            case US_NORTH_CENTRAL_2:
                this.cloudLocation = US_NORTH_CENTRAL_2;
                break;
            case US_NORTH_CENTRAL_1:
                this.cloudLocation = US_NORTH_CENTRAL_1;
                break;
            case US_NORTH_CENTRAL_3:
                this.cloudLocation = US_NORTH_CENTRAL_3;
                break;
            case US_NORTH_CENTRAl_4:
                this.cloudLocation = US_NORTH_CENTRAl_4;
                break;
            case US_SOUTH_CENTRAL_1:
                this.cloudLocation = US_SOUTH_CENTRAL_1;
                break;
            case US_SOUTH_CENTRAL_2:
                this.cloudLocation = US_SOUTH_CENTRAL_2;
                break;
            case US_SOUTH_CENTRAl_3:
                this.cloudLocation = US_SOUTH_CENTRAl_3;
                break;
            case US_SOUTH_CENTRAL_4:
                this.cloudLocation = US_SOUTH_CENTRAL_4;
                break;
            case US_WEST_1:
                this.cloudLocation = US_WEST_1;
                break;
            case US_WEST_2:
                this.cloudLocation = US_WEST_2;
                break;
            case US_WEST_3:
                this.cloudLocation = US_WEST_3;
                break;
            case US_WEST_5:
                this.cloudLocation = US_WEST_5;
                break;
            case US_WEST_6:
                this.cloudLocation = US_WEST_6;
                break;
            case US_WEST_7:
                this.cloudLocation = US_WEST_7;
                break;
            case US_WEST_8:
                this.cloudLocation = US_WEST_8;
                break;
            case US_WEST_9:
                this.cloudLocation = US_WEST_9;
                break;
            case NA_NORTH_EAST_1:
                this.cloudLocation = NA_NORTH_EAST_1;
                break;
            case NA_NORTH_EAST_2:
                this.cloudLocation = NA_NORTH_EAST_2;
                break;
            case NA_NORTH_EAST_3:
                this.cloudLocation = NA_NORTH_EAST_3;
                break;
            case NA_NORTH_EAST_4:
                this.cloudLocation = NA_NORTH_EAST_4;
                break;
            case NA_NORTH_EAST_5:
                this.cloudLocation = NA_NORTH_EAST_5;
                break;
            case NA_NORTH_EAST_6:
                this.cloudLocation = NA_NORTH_EAST_6;
                break;
            case NA_NORTH_EAST_7:
                this.cloudLocation = NA_NORTH_EAST_7;
                break;
            case SA_EAST_1:
                this.cloudLocation = SA_EAST_1;
                break;
            case SA_EAST_2:
                this.cloudLocation = SA_EAST_2;
                break;
        }
    }

    private void ConvertCloudZone(String location) {
        switch (location) {
            case AP_EAST_2_LOCATION:
                this.cloudLocation = AP_EAST_2;
                break;
            case AP_EAST_3_LOCATION:
                this.cloudLocation = AP_EAST_3;
                break;
            case AP_EAST_1_LOCATION:
                this.cloudLocation = AP_EAST_1;
                break;
            case AP_NORTH_EAST_1_LOCATION:
                this.cloudLocation = AP_NORTH_EAST_1;
                break;
            case AP_NORTH_EAST_2_LOCATION:
                this.cloudLocation = AP_NORTH_EAST_2;
                break;
            case AP_NORTH_EAST_3_LOCATION:
                this.cloudLocation = AP_NORTH_EAST_3;
                break;
            case AP_NORTH_EAST_4_LOCATION:
                this.cloudLocation = AP_NORTH_EAST_4;
                break;
            case AP_NORTH_EAST_5_LOCATION:
                this.cloudLocation = AP_NORTH_EAST_5;
                break;
            case AP_NORTH_EAST_6_LOCATION:
                this.cloudLocation = AP_NORTH_EAST_6;
                break;
            case AP_NORTH_EAST_7_LOCATION:
                this.cloudLocation = AP_NORTH_EAST_7;
                break;
            case AP_SOUTH_1_LOCATION:
                this.cloudLocation = AP_SOUTH_1;
                break;
            case AP_SOUTH_2_LOCATION:
                this.cloudLocation = AP_SOUTH_2;
                break;
            case AP_SOUTH_3_LOCATION:
                this.cloudLocation = AP_SOUTH_3;
                break;
            case AP_SOUTH_4_LOCATION:
                this.cloudLocation = AP_SOUTH_4;
                break;
            case AP_SOUTH_EAST_5_LOCATION:
                this.cloudLocation = AP_SOUTH_EAST_5;
                break;
            case AP_SOUTH_EAST_1_LOCATION:
                this.cloudLocation = AP_SOUTH_EAST_1;
                break;
            case AP_SOUTH_EAST_2_LOCATION:
                this.cloudLocation = AP_SOUTH_EAST_2;
                break;
            case AP_SOUTH_EAST_3_LOCATION:
                this.cloudLocation = AP_SOUTH_EAST_3;
                break;
            case AP_SOUTH_EAST_4_LOCATION:
                this.cloudLocation = AP_SOUTH_EAST_4;
                break;
            case AP_SOUTH_EAST_6_LOCATION:
                this.cloudLocation = AP_SOUTH_EAST_6;
                break;
            case AP_SOUTH_EAST_7_LOCATION:
                this.cloudLocation = AP_SOUTH_EAST_7;
                break;
            case AP_SOUTH_EAST_8_LOCATION:
                this.cloudLocation = AP_SOUTH_EAST_8;
                break;
            case EU_CENTER_1_LOCATION:
                this.cloudLocation = EU_CENTER_1;
                break;
            case EU_CENTER_2_LOCATION:
                this.cloudLocation = EU_CENTER_2;
                break;
            case EU_CENTER_3_LOCATION:
                this.cloudLocation = EU_CENTER_3;
                break;
            case EU_WEST_1_LOCATION:
                this.cloudLocation = EU_WEST_1;
                break;
            case EU_WEST_2_LOCATION:
                this.cloudLocation = EU_WEST_2;
                break;
            case EU_WEST_3_LOCATION:
                this.cloudLocation = EU_WEST_3;
                break;
            case EU_WEST_4_LOCATION:
                this.cloudLocation = EU_WEST_4;
                break;
            case EU_WEST_5_LOCATION:
                this.cloudLocation = EU_WEST_5;
                break;
            case EU_WEST_6_LOCATION:
                this.cloudLocation = EU_WEST_6;
                break;
            case EU_WEST_7_LOCATION:
                this.cloudLocation = EU_WEST_7;
                break;
            case EU_WEST_8_LOCATION:
                this.cloudLocation = EU_WEST_8;
                break;
            case EU_WEST_9_LOCATION:
                this.cloudLocation = EU_WEST_9;
                break;
            case EU_WEST_10_LOCATION:
                this.cloudLocation = EU_WEST_10;
                break;
            case EU_WEST_11_LOCATION:
                this.cloudLocation = EU_WEST_11;
                break;
            case EU_WEST_12_LOCATION:
                this.cloudLocation = EU_WEST_12;
                break;
            case EU_WEST_13_LOCATION:
                this.cloudLocation = EU_WEST_13;
                break;
            case EU_WEST_14_LOCATION:
                this.cloudLocation = EU_WEST_14;
                break;
            case EU_NORTH_1_LOCATION:
                this.cloudLocation = EU_NORTH_1;
                break;
            case EU_SOUTH_1_LOCATION:
                this.cloudLocation = EU_SOUTH_1;
                break;
            case US_EAST_1_LOCATION:
                this.cloudLocation = US_EAST_1;
                break;
            case US_EAST_2_LOCATION:
                this.cloudLocation = US_EAST_2;
                break;
            case US_EAST_3_LOCATION:
                this.cloudLocation = US_EAST_3;
                break;
            case US_EAST_4_LOCATION:
                this.cloudLocation = US_EAST_4;
                break;
            case US_EAST_5_LOCATION:
                this.cloudLocation = US_EAST_5;
                break;
            case US_EAST_7_LOCATION:
                this.cloudLocation = US_EAST_7;
                break;
            case US_EAST_8_LOCATION:
                this.cloudLocation = US_EAST_8;
                break;
            case US_EAST_9_LOCATION:
                this.cloudLocation = US_EAST_9;
                break;
            case US_NORTH_CENTRAL_2_LOCATION:
                this.cloudLocation = US_NORTH_CENTRAL_2;
                break;
            case US_NORTH_CENTRAL_LOCATION:
                this.cloudLocation = US_NORTH_CENTRAL_1;
                break;
            case US_NORTH_CENTRAL_3_LOCATION:
                this.cloudLocation = US_NORTH_CENTRAL_3;
                break;
            case US_NORTH_CENTRAl_4_LOCATION:
                this.cloudLocation = US_NORTH_CENTRAl_4;
                break;
            case US_SOUTH_CENTRAL_1_LOCATION:
                this.cloudLocation = US_SOUTH_CENTRAL_1;
                break;
            case US_SOUTH_CENTRAL_2_LOCATION:
                this.cloudLocation = US_SOUTH_CENTRAL_2;
                break;
            case US_SOUTH_CENTRAl_3_LOCATION:
                this.cloudLocation = US_SOUTH_CENTRAl_3;
                break;
            case US_SOUTH_CENTRAL_4_LOCATION:
                this.cloudLocation = US_SOUTH_CENTRAL_4;
                break;
            case US_WEST_1_LOCATION:
                this.cloudLocation = US_WEST_1;
                break;
            case US_WEST_2_LOCATION:
                this.cloudLocation = US_WEST_2;
                break;
            case US_WEST_3_LOCATION:
                this.cloudLocation = US_WEST_3;
                break;
            case US_WEST_5_LOCATION:
                this.cloudLocation = US_WEST_5;
                break;
            case US_WEST_6_LOCATION:
                this.cloudLocation = US_WEST_6;
                break;
            case US_WEST_7_LOCATION:
                this.cloudLocation = US_WEST_7;
                break;
            case US_WEST_8_LOCATION:
                this.cloudLocation = US_WEST_8;
                break;
            case US_WEST_9_LOCATION:
                this.cloudLocation = US_WEST_9;
                break;
            case NA_NORTH_EAST_1_LOCATION:
                this.cloudLocation = NA_NORTH_EAST_1;
                break;
            case NA_NORTH_EAST_2_LOCATION:
                this.cloudLocation = NA_NORTH_EAST_2;
                break;
            case NA_NORTH_EAST_3_LOCATION:
                this.cloudLocation = NA_NORTH_EAST_3;
                break;
            case NA_NORTH_EAST_4_LOCATION:
                this.cloudLocation = NA_NORTH_EAST_4;
                break;
            case NA_NORTH_EAST_5_LOCATION:
                this.cloudLocation = NA_NORTH_EAST_5;
                break;
            case NA_NORTH_EAST_6_LOCATION:
                this.cloudLocation = NA_NORTH_EAST_6;
                break;
            case NA_NORTH_EAST_7_LOCATION:
                this.cloudLocation = NA_NORTH_EAST_7;
                break;
            case SA_EAST_1_LOCATION:
                this.cloudLocation = SA_EAST_1;
                break;
            case SA_EAST_2_LOCATION:
                this.cloudLocation = SA_EAST_2;
                break;
        }
    }
}
