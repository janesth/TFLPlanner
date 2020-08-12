package ch.janesthomas.tflplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ch.janesthomas.tflplanner.R;
import ch.janesthomas.tflplanner.models.TubeModel;


public class TubeAdapter extends ArrayAdapter<TubeModel> implements View.OnClickListener {

    /*
    private ArrayList<TubeModel> tubeModel;
    Context context;
     */

    private static class ViewHolder {
        TextView txtName;
    }

    public TubeAdapter(ArrayList<TubeModel> tube, Context context) {
        super(context, R.layout.row_item, tube);
    }


    @Override
    public void onClick(View view) {

        int position = (Integer) view.getTag();
        Object object = getItem(position);
        TubeModel tubeModel = (TubeModel) object;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TubeModel tubeModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.element_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(tubeModel.getName());

        // Return the completed view to render on screen
        return convertView;
    }

}
