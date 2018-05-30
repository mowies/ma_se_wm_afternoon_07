package com.geoschnitzel.treasurehunt.backend.util;

import com.geoschnitzel.treasurehunt.backend.schema.GameTarget;
import com.geoschnitzel.treasurehunt.backend.schema.Target;
import com.geoschnitzel.treasurehunt.backend.schema.User;
import com.geoschnitzel.treasurehunt.backend.schema.UserPosition;

public class CalDistance {
    public enum ScaleType {
        Meter,
        Kilomenter,
        Miles,
        NauticMiles
    }
    public static double distance(GameTarget t, UserPosition up, ScaleType st) {

        return distance(t.getTarget().getArea().getCoordinate().getLatitude(),
                t.getTarget().getArea().getCoordinate().getLongitude(),
                up.getCoordinate().getLatitude(),
                up.getCoordinate().getLongitude(),
                st);
    }
    public static double distance(UserPosition up1, UserPosition up2, ScaleType st) {
        return distance(
                up1.getCoordinate().getLatitude(),
                up1.getCoordinate().getLongitude(),
                up2.getCoordinate().getLatitude(),
                up2.getCoordinate().getLongitude(),
                st);
    }

    public static double distance(Target t1, Target t2, ScaleType st) {
        return distance(t1.getArea().getCoordinate().getLatitude(),
                t1.getArea().getCoordinate().getLongitude(),
                t2.getArea().getCoordinate().getLatitude(),
                t2.getArea().getCoordinate().getLongitude(),
                st);
    }

    public static double distance(Target t, UserPosition up, ScaleType st) {

        return distance(t.getArea().getCoordinate().getLatitude(),
                t.getArea().getCoordinate().getLongitude(),
                up.getCoordinate().getLatitude(),
                up.getCoordinate().getLongitude(),
                st);
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, ScaleType st) {


        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        switch (st) {
            case Meter:
                dist = dist * 1.609344 * 1000.0;
                break;
            case Kilomenter:
                dist = dist * 1.609344;
                break;
            case Miles:
                //do nothing
                break;
            case NauticMiles:
                dist = dist * 0.8684;
                break;
        }
        return (dist);
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}