package tw.edu.itu.traning.classes;

/**
 * Created by kaden on 2016/5/17.
 */
public class busItem {
    int LineNo;
    String Company;
    String BusInfo;
    String EatTime;
    int Time;

    public busItem(int lineNo, String company, String busInfo, int time, String eatTime){
        LineNo=lineNo;
        Company=company;
        BusInfo=busInfo;
        Time=time;
        EatTime=eatTime;
    }

    public int getLineNo(){
        return LineNo;
    }

    public String getCompany(){
        return Company;
    }

    public String getBusInfo(){
        return BusInfo;
    }

    public int getTime(){
        return Time;
    }

    public String getEatTime(){return EatTime;}

}
