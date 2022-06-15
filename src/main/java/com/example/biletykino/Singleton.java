package com.example.biletykino;

import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;

public final class Singleton {
    private static ModelAndView m;
    private static SimpleDateFormat godzinaFormat;

    public static SimpleDateFormat getDataFormat() {
        if(dataFormat==null)dataFormat=new SimpleDateFormat("dd-MM-yyyy");
        return dataFormat;
    }

    private static SimpleDateFormat dataFormat;
    private Singleton(){
    }
    public static ModelAndView getM() {
        if(m==null)m=new ModelAndView();
        return m;
    }

    public static SimpleDateFormat getGodzinaFormat() {
        if(godzinaFormat==null)godzinaFormat=new SimpleDateFormat("HH:mm");
        return godzinaFormat;
    }
}
