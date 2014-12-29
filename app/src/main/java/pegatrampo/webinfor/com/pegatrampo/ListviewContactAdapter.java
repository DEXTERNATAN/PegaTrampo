
package pegatrampo.webinfor.com.pegatrampo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListviewContactAdapter extends BaseAdapter implements Filterable {

    // Arra
    //private List<ListaContatos> ListaContatos = null;

    // ArrayList que permite buscar todos os contatos
    private static ArrayList<ListviewContactItem> listContact;
    private ArrayList<ListviewContactItem>originalData = null;
    private ArrayList<ListviewContactItem>filteredData = null;
    //private ItemsFilter mFilter;
    private ItemFilter mFilter = new ItemFilter();
    private LayoutInflater mInflater;

    public ListviewContactAdapter(Context context, ArrayList<ListviewContactItem> data){

        //Atual
        this.filteredData = data ;
        this.originalData = data ;
        mInflater = LayoutInflater.from(context);

        // Antigo
        //listContact = results;
        //mInflater = LayoutInflater.from(photosFragment);
    }

    @Override
    public int getCount() {
        //return listContact.size();
        return filteredData.size();
    }

    @Override
    public Object getItem(int position) {
        //return listContact.get(arg0);
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){

            convertView = mInflater.inflate(R.layout.trampos_item, null);
            holder = new ViewHolder();
            holder.txtname = (TextView) convertView.findViewById(R.id.txtcargotrampo);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.txtname.setText(listContact.get(position).getName());

        holder.txtname.setText(filteredData.get(position).getName());

        return convertView;
    }

    static class ViewHolder{
        TextView txtname;
    }


    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<ListviewContactItem> list = originalData;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<String>(count);

            String filterableString ;

           for (int i = 0; i < count; i++) {
                Log.i("PEGATRAMPOS", String.valueOf(list.get(i)));
                filterableString = String.valueOf(list.get(i));
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<ListviewContactItem>) results.values;
            notifyDataSetChanged();
        }

    }
/*
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        }
        else
        {
            for (WorldPopulation wp : arraylist)
            {
                if (wp.getCountry().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }*/
}