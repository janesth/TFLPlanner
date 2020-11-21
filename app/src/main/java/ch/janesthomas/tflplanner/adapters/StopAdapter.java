package ch.janesthomas.tflplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import ch.janesthomas.tflplanner.R;
import ch.janesthomas.tflplanner.models.StopModel;

public class StopAdapter extends ArrayAdapter<LinkedTreeMap>{

    private static class ViewHolder {
        TextView txtName;
    }

    public StopAdapter(ArrayList<LinkedTreeMap> stops, Context context) {
        super(context, R.layout.row_item, stops);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String ID = (String) getItem(position).get("id");
        final String COMMONNAME = (String) getItem(position).get("commonName");
        final double LAT = (double) getItem(position).get("lat");
        final double LON = (double) getItem(position).get("lon");


        StopModel stopModel = new StopModel( ID, COMMONNAME , LAT , LON);
        StopAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new StopAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.element_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (StopAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.txtName.setText(stopModel.getCommonName());
        return convertView;
    }
}
