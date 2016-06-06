package tw.edu.itu.traning;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tw.edu.itu.traning.Adapters.BusAdapter;
import tw.edu.itu.traning.Adapters.CursorAdapterBus;
import tw.edu.itu.traning.classes.busItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SqliteDB db=new SqliteDB(this,"myTest",null,1);
        //db.execSQL("delete from bus");

        //取得公車資訊
        findViewById(R.id.btnSaveToSqlLite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APIUtility api=new APIUtility();
                api.execute("GET","BusInfo");
                api.SetOnGetResultListener(new APIUtility.OnGetResultListener() {
                    @Override
                    public void onGetResult(int sCode, Object result) {
                        if(sCode==200){
                            try {
                                JSONArray ary=new JSONArray(result.toString());

                                for(int i=0;i<ary.length();i++){
                                    JSONObject o=ary.getJSONObject(i);

                                    ContentValues values=new ContentValues();

                                    values.put("LineNo",o.getInt("LineNo"));
                                    values.put("Company",o.getString("Company"));
                                    values.put("BusInfo",o.getString("BusInfo"));
                                    values.put("Time",o.getInt("Time"));
                                    values.put("EatTime",o.getString("EatTime"));

                                    db.Insert("bus",values);
                                }

                                Toast.makeText(getApplicationContext(), "下載完成", Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

        //從SqlLite取得
        findViewById(R.id.btnGetFromSqlLite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cur=db.query("select * from bus",null);
                CursorAdapterBus cursorAdapterBus =new CursorAdapterBus(getApplicationContext(),cur,0);

                ((ListView)findViewById(R.id.listView)).setAdapter(cursorAdapterBus);
            }
        });

        findViewById(R.id.btnRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIUtility api=new APIUtility();
                api.execute("GET","BusInfo");

                api.SetOnGetResultListener(new APIUtility.OnGetResultListener() {
                    @Override
                    public void onGetResult(int sCode, Object result) {
                        if (sCode == 200) {
                            List<String> listDataHeader = new ArrayList<>();
                            HashMap<String, List<busItem>> listDataChild = new HashMap<>();
                            listDataHeader.add("到站時刻  (時：分)");
                            listDataHeader.add("未發車");

                            List<busItem> childItem1 = new ArrayList<>();
                            List<busItem> childItem2 = new ArrayList<>();

                            try {
                                JSONArray businfos = new JSONArray(result.toString());

                                for (int i = 0; i < businfos.length(); i++) {
                                    JSONObject bus = businfos.getJSONObject(i);

                                    if (bus.getInt("Time") > -1)
                                        childItem1.add(new busItem(bus.getInt("LineNo"),
                                                bus.getString("Company"),
                                                bus.getString("BusInfo"),
                                                bus.getInt("Time"),
                                                bus.getString("EatTime")
                                        ));
                                    else
                                        childItem2.add(new busItem(bus.getInt("LineNo"),
                                                bus.getString("Company"),
                                                bus.getString("BusInfo"),
                                                bus.getInt("Time"),
                                                bus.getString("EatTime")
                                        ));
                                }

                                listDataChild.put("到站時刻  (時：分)", childItem1);
                                listDataChild.put("未發車", childItem2);
                                BusAdapter busAdapter = new BusAdapter(getApplicationContext(),
                                        listDataHeader, listDataChild);
                                ((ExpandableListView)
                                        findViewById(R.id.expendView)).setAdapter(busAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

    }
}
