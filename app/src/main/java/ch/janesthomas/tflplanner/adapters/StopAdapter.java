package ch.janesthomas.tflplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ch.janesthomas.tflplanner.R;
import ch.janesthomas.tflplanner.models.StopModel;

public class StopAdapter extends ArrayAdapter<StopModel> implements View.OnClickListener{

    private ArrayList<StopModel> stopList;
    Context context;

    private static class ViewHolder {
        TextView txtName;
    }

    public StopAdapter(ArrayList<StopModel> stops, Context context) {
        super(context, R.layout.row_item, stops);

    }

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        Object object = getItem(position);
        StopModel stopModel = (StopModel) object;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        StopModel stopModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        StopAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

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

        // Return the completed view to render on screen
        return convertView;
    }

}
