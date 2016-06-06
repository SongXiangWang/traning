package tw.edu.itu.traning.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import tw.edu.itu.traning.R;

/**
 * Created by kaden on 2016/6/6.
 */
public class CursorAdapterBus extends CursorAdapter {

    public CursorAdapterBus(Context context, Cursor c, int flags){
        super(context,c,flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater infalInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return infalInflater.inflate(R.layout.item_bus,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtLine=(TextView)view.findViewById(R.id.txtLineNo);
        txtLine.setText(cursor.getString(cursor.getColumnIndex("LineNo")));
    }
}
