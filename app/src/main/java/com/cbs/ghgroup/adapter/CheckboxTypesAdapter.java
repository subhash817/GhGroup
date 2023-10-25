package com.cbs.ghgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.model.typescheckbox.CheckboxTypes;

import java.util.ArrayList;
import java.util.List;

public class CheckboxTypesAdapter extends ArrayAdapter<CheckboxTypes> {

    private Context mContext;
    private ArrayList<CheckboxTypes> listState;
    private CheckboxTypesAdapter checkboxTypesAdapter;
    private boolean isFromView = false;

    public CheckboxTypesAdapter(Context context, int resource, List<CheckboxTypes> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<CheckboxTypes>) objects;
        this.checkboxTypesAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_types_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text_types);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox_types);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).getTitle());

        // To check weather checked event fire from getview() or user input
        isFromView = false;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);

       /* holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked())
                {
                    listState.get(position).setSelected(true);
                }
                else
                {

                }
                holder.mTextView.setText(listState.get(position).getTitle());
            }
        });*/



        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                   // holder.mTextView.setText(listState.get(position).getTitle());

                }
            }

        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }

}
