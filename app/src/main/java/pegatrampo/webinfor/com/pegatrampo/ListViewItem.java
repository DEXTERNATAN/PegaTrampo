package pegatrampo.webinfor.com.pegatrampo;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by 98287028191 on 16/12/14.
 */
public class ListViewItem implements Serializable{

    private static final long serialVersionUID = 6601006766832473959L;

    //public final Drawable icon;       // the drawable for the ListView item ImageView
    public final String title;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description

    public ListViewItem(Drawable icon, String title,String description) {
        //this.icon = icon;
        this.title = title;
        this.description = description;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}