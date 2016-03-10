/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FZ4432
 */
public class OpResult 
{
    Boolean resultStatus;
    String  msgStr;
    String  errStr;
    
    public OpResult()
    {
        resultStatus = false;
        msgStr = "";
        errStr = "";
    }
    public OpResult(Boolean rs, String ms, String es)
    {
        resultStatus = rs;
        msgStr = ms;
        errStr = es;
    }
    
    Boolean GetResultStatus()
    {
        return resultStatus;
    }
    
    String GetMsgStatusStr()
    {
        return msgStr;
    }
    
    String GetErrStatusStr()
    {
        return errStr;
    }
    
    void Clear()
    {
        resultStatus = false;
        msgStr = "";
        errStr = "";
    }
    void SetResultStatus(Boolean rs)
    {
        resultStatus = rs;
    }
    
    void SetMsgStatusStr(String str)
    {
        msgStr = str;
    }
    void SetErrStatusStr(String str)
    {
        errStr = str;
    }
    
    void AppendMsgStatusStr(String str)
    {
        msgStr = msgStr + "\n" + str;
    }
    void AppendErrStatusStr(String str)
    {
        errStr = errStr + "\n" + str;
    }

}
