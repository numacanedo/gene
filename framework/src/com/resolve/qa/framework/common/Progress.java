package com.resolve.qa.framework.common;

public class Progress
{
    int cur;
    int limit;
    boolean firstElementAsDefault;
    Progress pre;
    Progress next;
    
    public Progress(int cur, int limit, boolean firstElementAsDefault, Progress pre, Progress next)
    {
        super();
        this.cur = cur;
        this.limit = limit;
        this.firstElementAsDefault = firstElementAsDefault;
        this.pre = pre;
        this.next = next;
    }
    public int getCur()
    {
        return cur;
    }
    public void setCur(int cur)
    {
        this.cur = cur;
    }
    public int getLimit()
    {
        return limit;
    }
    public void setLimit(int limit)
    {
        this.limit = limit;
    }
    public boolean isFirstElementAsDefault()
    {
        return firstElementAsDefault;
    }
    public void setFirstElementAsDefault(boolean firstElementAsDefault)
    {
        this.firstElementAsDefault = firstElementAsDefault;
    }
    public Progress getPre()
    {
        return pre;
    }
    public void setPre(Progress pre)
    {
        this.pre = pre;
    }
    public Progress getNext()
    {
        return next;
    }
    public void setNext(Progress next)
    {
        this.next = next;
    }
    


}
