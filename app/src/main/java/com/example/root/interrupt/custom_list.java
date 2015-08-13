package com.example.root.interrupt;

/**
 * Created by DELL5521 on 29/12/2014.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class custom_list extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] bname;
    private final String[] time;
    private final String[] start;
    private final String[] route;
    public custom_list(Activity context,
                       String[] bname, String[] author, String[] pub, String[] pri) {
        super(context, R.layout.list_single, bname);
        this.context = context;
        this.bname = bname;
        this.time = author;
        this.start=pub;
    this.route=pri;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.bno);
        TextView authors = (TextView) rowView.findViewById(R.id.bname);
        TextView publish = (TextView) rowView.findViewById(R.id.time);
        TextView cost = (TextView) rowView.findViewById(R.id.route);

        txtTitle.setText(bname[position]);
        authors.setText(start[position]);
        publish.setText(time[position]);
        cost.setText(route[position]);
        return rowView;
    }
}