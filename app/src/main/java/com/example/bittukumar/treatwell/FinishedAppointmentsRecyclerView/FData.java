package com.example.bittukumar.treatwell.FinishedAppointmentsRecyclerView;


public class FData {
    public String doc_name;
    public String doc_spec;
    public String hosp_name;
    public String hosp_addr;
    public String fee;
    public String date;
    public String starttime;
    public String endtime;


    public FData(String doc_name,String doc_spec,String hosp_name,String hosp_addr,String fee,String date,String starttime,String endtime)
    {
        this.doc_name = doc_name;
        this.doc_spec = doc_spec;
        this.hosp_name = hosp_name;
        this.hosp_addr = hosp_addr;
        this.fee = fee;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
    }
}

