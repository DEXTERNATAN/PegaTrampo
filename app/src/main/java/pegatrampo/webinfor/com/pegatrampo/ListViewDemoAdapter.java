package pegatrampo.webinfor.com.pegatrampo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 98287028191 on 16/12/14.
 */
public class ListViewDemoAdapter extends ArrayAdapter<ListViewItem> {

    private static final String TAG = "PEGATRAMPO";
    private List<ListViewItem> items;
    private Context context;


    public ListViewDemoAdapter(Context context, List<ListViewItem> items) {
        super(context, R.layout.listview_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            //viewHolder.tvSearch = (TextView) convertView.findViewById(R.id.tvSearch);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        ListViewItem item = getItem(position);
        viewHolder.ivIcon.setImageDrawable(item.icon);
        viewHolder.tvTitle.setText(item.title);
        //viewHolder.tvSearch.setText(item.tvSearch);
        viewHolder.tvDescription.setText(item.description);

        return convertView;
    }

    public List<ListViewItem> getItemList() {
        return items;
    }

    public void setItemList(List<ListViewItem> itemList) {
        this.items = itemList;
        Log.i(TAG,"Massa: " + itemList);
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     */
    //** @see http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder

    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        //TextView tvSearch;
        TextView tvDescription;
    }
}