package pegatrampo.webinfor.com.pegatrampo;

import android.app.ListActivity;
import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 98287028191 on 16/12/14.
 */

// Link de referencia:
//  --> http://www.perfectapk.com/sqliteopenhelper-example.html
public class ListViewDemoFragment extends android.support.v4.app.ListFragment {
    private List<ListViewItem> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the items list
        mItems = new ArrayList<ListViewItem>();
        Resources resources = getResources();


        //mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_job_img), getString(R.string.str_aim),getString(R.string.str_txt_search), getString(R.string.str_aim_description)));
        //mItems.add(new ListViewItem(resources.getDrawable(R.drawable.jobs_2), getString(R.string.str_bebo), getString(R.string.str_txt_search), getString(R.string.str_bebo_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_job_img), getString(R.string.str_youtube),  getString(R.string.str_youtube_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.jobs_2), getString(R.string.str_bebo),  getString(R.string.str_bebo_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_job_img), getString(R.string.str_youtube),  getString(R.string.str_youtube_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.jobs_2), getString(R.string.str_bebo), getString(R.string.str_bebo_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_job_img), getString(R.string.str_youtube), getString(R.string.str_youtube_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.jobs_2), getString(R.string.str_bebo), getString(R.string.str_bebo_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_job_img), getString(R.string.str_youtube),  getString(R.string.str_youtube_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.jobs_2), getString(R.string.str_bebo),   getString(R.string.str_bebo_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_job_img), getString(R.string.str_youtube),  getString(R.string.str_youtube_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_job_img), getString(R.string.str_aim),   getString(R.string.str_aim_description)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_job_img), getString(R.string.str_aim),  getString(R.string.str_aim_description)));

        // initialize and set the list adapter
        setListAdapter(new ListViewDemoAdapter(getActivity(), mItems));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        ListViewItem item = mItems.get(position);

        // Quando clicar no item levar o cara para outra tela com a descrição e o nome da vaga



        // do something
        Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
    }
}