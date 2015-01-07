package pegatrampo.webinfor.com.pegatrampo;

import android.graphics.drawable.Drawable;

/**
 * Created by 98287028191 on 16/12/14.
 */
public class ListViewItem {
    public final Drawable icon;       // the drawable for the ListView item ImageView
    public final String title;        // the text for the ListView item title
    //public final String tvSearch;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description

    public ListViewItem(Drawable icon, String title,
                        //String tvSearch,
                        String description) {
        this.icon = icon;
        this.title = title;
        //this.tvSearch = tvSearch;
        this.description = description;
    }

}