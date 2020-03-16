package de.mgeo.json.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private Date now = new Date();

    public String getDateCurrent(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format( now  );
    }

    public Date getDateFromString(String format, String stringDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date=null;
        try {
            date = dateFormat.parse ( stringDate );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getNewId() {
        return this.getDateCurrent("YYYYMMddHHss");
    }
}
