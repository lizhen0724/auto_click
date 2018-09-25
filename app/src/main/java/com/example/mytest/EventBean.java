package com.example.mytest;

import java.io.Serializable;

public class EventBean implements Serializable {
    public int type;
    public int metaState; // KeyEvent.META_*
    public int action; // KeyEvent.ACTION_* or MotionEvent.ACTION_* or COMMAND_*
    public int buttons; // MotionEvent.BUTTON_*
    public Position position;
}
