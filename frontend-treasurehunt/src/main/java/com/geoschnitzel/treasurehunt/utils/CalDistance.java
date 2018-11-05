package com.geoschnitzel.treasurehunt.utils;

import com.geoschnitzel.treasurehunt.rest.CoordinateItem;
import com.geoschnitzel.treasurehunt.rest.GameTargetItem;
import com.geoschnitzel.treasurehunt.rest.TargetItem;
import android.location.Location;

public class CalDistance {
    public enum ScaleType {
        Meter,
        Kilomenter,
        Miles,
        NauticMiles
    }
    public static double distance(TargetItem t, CoordinateItem c, ScaleType st) {

        return distance(t.getArea().getCoordinate().getLatitude(),
                t.getArea().getCoordinate().getLongitude(),
                c.getLatitude(),
                c.getLongitude(),
                st);
    }
    public static double distance(GameTargetItem t, CoordinateItem c, ScaleType st) {

        return distance(t.getArea().getCoordinate().getLatitude(),
                t.getArea().getCoordinate().getLongitude(),
                c.getLatitude(),
                c.getLongitude(),
                st);
    }
    public static double distance(TargetItem t, Location c, ScaleType st) {

        return distance(t.getArea().getCoordinate().getLatitude(),
                t.getArea().getCoordinate().getLongitude(),
                c.getLatitude(),
                c.getLongitude(),
                st);
    }
    public static double distance(GameTargetItem t, Location c, ScaleType st) {

        return distance(t.getArea().getCoordinate().getLatitude(),
                t.getArea().getCoordinate().getLongitude(),
                c.getLatitude(),
                c.getLongitude(),
                st);
    }
    public static double distance(CoordinateItem c1, CoordinateItem c2, ScaleType st) {
        return distance(
                c1.getLatitude(),
                c1.getLongitude(),
                c2.getLatitude(),
                c2.getLongitude(),
                st);
    }

    public static double distance(TargetItem t1, TargetItem t2, ScaleType st) {
        return distance(t1.getArea().getCoordinate().getLatitude(),
                t1.getArea().getCoordinate().getLongitude(),
                t2.getArea().getCoordinate().getLatitude(),
                t2.getArea().getCoordinate().getLongitude(),
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