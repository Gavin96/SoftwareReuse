package com.HaroldLIU;

public class LicenseManager {

    //For Capacity
    private  int CapacityCounting;
    private  int CapacityMax;
    private  int CapacityInit;

    //For Thoughput
    private int ThroughputMax;  //预设上限
    private long time; //时间间隔
    private int ThroughputCounting;// Counting
    private int initCounting;
    private long lastRequestTime;


    public int getCountingCapacity () {return CapacityCounting;}

    public void ThroughputInit(int _max,long _time,int _initCounting)
    {
        ThroughputMax = _max;
        time = _time;
        ThroughputCounting = _initCounting;
        initCounting = _initCounting;
    }

    public void CapacityInit(int _max,int _initCounting)
    {
        CapacityMax = _max;
        CapacityCounting = _initCounting;
        CapacityInit = _initCounting;
    }

    public boolean ThroughputCheck()
    {
        long currentTime = System.currentTimeMillis();
        if (lastRequestTime == 0 )
        {
            ThroughputCounting = initCounting;
            lastRequestTime = currentTime;
        }
        else
        {
            if (currentTime - lastRequestTime > time)
            {
                ThroughputCounting = initCounting;
                lastRequestTime = currentTime;
            }
            else
            {
                ThroughputCounting++;
                if (ThroughputCounting > ThroughputMax)

                    return false;
            }
        }
        return  true;
    }

    public boolean CapacityCheck()
    {
        CapacityCounting ++ ;
        if (CapacityCounting > CapacityMax)
        {
            CapacityCounting = CapacityInit;
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        LicenseManager licenseManager = new LicenseManager();
        licenseManager.CapacityInit(10, 1);
        licenseManager.ThroughputInit(10,1000,5);
        for (int i = 0; i<11;i++)
        {
           if(licenseManager.CapacityCheck())
               System.out.println("OK");
            else
               System.out.println("Out of Range");
        }

        for (int i = 0;i<11;i++)
        {
            try {
                Thread.sleep(200);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(licenseManager.ThroughputCheck())
                System.out.println("OK");
            else
                System.out.println("Out of Range");
        }

    }
}
