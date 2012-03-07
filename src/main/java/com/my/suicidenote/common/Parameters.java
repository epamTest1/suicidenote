package com.my.suicidenote.common;

/**
 *
 * @author Andrii_Manuiev
 * 
 * parameters from request
 */
public class Parameters {

    //sender first and last name 
    public static final String FROM = "from";
    // The name of the group whom want sender to send 
    public static final String TO = "to";
    // the main letter text
    public static final String SAY = "i-want-to-say";
    // the list of recipient
    //can be sent-to, sent-to-0, sent-to-1
    public static final String SEND_TO = "send-to";
    //time when letter should be send
    public static final String WHEN = "when";
    // user timezone
    public static final String TIME_ZONE = "time-zone";

    private Parameters() {
    }
}
