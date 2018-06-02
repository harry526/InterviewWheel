package com.test.wheelstreettest.model;

/**
 * Created by Harish on 5/25/2018.
 */

public class QANDA {


    private String TYPE ;
    private String QUA;
    private String ANS ;
    private String Q_ID;

    public QANDA(String t,String q,String a,String id){
        this.TYPE=t;
        this.QUA=q;
        this.ANS=a;
        this.Q_ID=id;
    }

    public QANDA() {
    }

    public String getANS() {
        return ANS;
    }

    public String getQ_ID() {
        return Q_ID;
    }

    public String getQUA() {
        return QUA;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setANS(String ANS) {
        this.ANS = ANS;
    }

    public void setQ_ID(String q_ID) {
        Q_ID = q_ID;
    }

    public void setQUA(String QUA) {
        this.QUA = QUA;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
