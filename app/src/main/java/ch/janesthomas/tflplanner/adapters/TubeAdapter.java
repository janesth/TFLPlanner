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


public class TubeAdapter extends ArrayAdapter<TubeModel> {

    private static class ViewHolder {
        TextView txtName;
    }

    public TubeAdapter(ArrayList<TubeModel> tube, Context context) {
        super(context, R.layout.row_item, tube);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TubeModel tubeModel = getItem(position);
        ViewHolder viewHolder;

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
        return convertView;
    }
}
