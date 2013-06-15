package com.runnirr.doodleviewer.fetcher;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class DoodleData implements Serializable {

	private static final long serialVersionUID = 1L;

    public final String standalone_html;
    public final Long finish;
    public final String name;
    public final Long run_date;
    public final String title;
    public final String url;
    private final String[] countries;
    public final Integer height;
    private final String[] blacklist;
    private final Long start;
    public final String blog_text;
    public final String id;
    public final String alternate_url;
    public final Boolean is_global;
    public final Integer width;
    private final String[] tags;
    public final Boolean is_dynamic;

    private transient Bitmap image = null;

    public DoodleData(){
        // Dummy data when no results
        standalone_html = null;
        finish = null;
        name = null;
        run_date = null;
        title = "No Result";
        url = null;
        countries = null;
        height = null;
        blacklist = null;
        start = null;
        blog_text = null;
        id = null;
        alternate_url = null;
        is_global = null;
        width = null;
        tags = null;
        is_dynamic = null;
    }

	public DoodleData(String standalone_html,
            Long finish,
            String name,
            Long run_date,
            String title,
            String url,
            String[] countries,
            Integer height,
            String[] blacklist,
            Long start,
            String blog_text,
            String id,
            String alternate_url,
            Boolean is_global,
            Integer width,
            String[] tags,
            Boolean is_dynamic){

        // This is never called when using Gson apparently
        this.standalone_html = standalone_html;
        this.finish = finish;
        this.name = name;
        this.run_date = run_date;
        this.title = title;
        this.url = url;
        this.countries = countries;
        this.height = height;
        this.blacklist = blacklist;
        this.start = start;
        this.blog_text = blog_text;
        this.id = id;
        this.alternate_url = alternate_url;
        this.is_global = is_global;
        this.width = width;
        this.tags = tags;
        this.is_dynamic = is_dynamic;
	}

    public Bitmap getImage(){
        return image;
    }

    /**
     * Set the image if it isn't already set
     * @param bm Image to set
     * @return True if the image was previously not set
     */
    public boolean setImage(Bitmap bm){
        if (image == null){
            image = bm;
            return true;
        }
        return false;
    }

    public Long getStartMilli(){
        return start * 1000;
    }

    public String getDateString(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(getStartMilli());
    }
}
