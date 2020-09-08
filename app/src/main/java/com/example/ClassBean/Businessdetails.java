package com.example.ClassBean;

public class Businessdetails {
    public String providerName="";
    public int state;

    public Businessdetails(String _providerName){
        providerName=_providerName;
        state=1;
    }
    private static Businessdetails loginBusiness;

    public String getProviderName() {
        if(loginBusiness==null)
            return "ERROR";
        return providerName;
    }

    public int getState() {
        return state;
    }

    public static Businessdetails getLoginBusiness(String a) {
        if(loginBusiness==null)
            initData(a);
        return loginBusiness;
    }


    private static void initData(final String a){
        String b=null;
        try
        {new Thread(new Runnable() {
            @Override
            public void run() {
                String[] spite=a.split(",");
                loginBusiness=new Businessdetails(spite[0]);
            }
        }).start();

        }catch (Exception e){ }
    }

}
