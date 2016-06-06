package tw.edu.itu.traning.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import tw.edu.itu.traning.R;
import tw.edu.itu.traning.classes.busItem;


/**
 * Created by kaden on 2016/5/17.
 */
public class BusAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<busItem>> _listDataChild;

    public BusAdapter(Context context, List<String> listDataHeader,
                      HashMap<String, List<busItem>> listChildData){
        _context=context;
        _listDataChild=listChildData;
        _listDataHeader=listDataHeader;
    }

    @Override
    public int getGroupCount() {
        return _listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _listDataChild.get(_listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_bus, null);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.txtBusGroup);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);

        //讓Group不要收起來
        eLV.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final busItem item=(busItem)getChild(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_bus, null);
        }

        TextView txtLine=(TextView)convertView.findViewById(R.id.txtLineNo);
        txtLine.setText(item.getLineNo()+ " " + item.getCompany());

        ((TextView)convertView.findViewById(R.id.txtBusInfo)).setText(item.getBusInfo());
        ((TextView)convertView.findViewById(R.id.txtTime)).setText(item.getEatTime());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
