package com.throne.emm.net.result;

public abstract class BaseResult {
    
    public abstract void dataAnalysis(String content);
    
    public abstract void postFailure();
}
