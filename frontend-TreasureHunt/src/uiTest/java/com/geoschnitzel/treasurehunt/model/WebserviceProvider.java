package com.geoschnitzel.treasurehunt.model;

public class WebserviceProvider {

    public static WebService getWebservice() {
        return WebServiceTest.instance();
    }

}
