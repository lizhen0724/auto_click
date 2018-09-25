package com.example.mytest.AdbShellServer;

import android.text.TextUtils;

import com.example.mytest.ControlEvent;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

/**
 * Created by maolei on 2017/2/20.
 */

public class AdbServerCommon {
    static public final int nAdbServerPort = 36088;
    static public final int nTypeShellCmd = 1;
    static public final int nTypeStopServer = 2;
    static public final int nTypeClicked = 3;
    static public final int nTypeLongPressed = 4;

    public static boolean isTypeValid(int type){
        return type == nTypeShellCmd || type == nTypeStopServer || type == nTypeClicked || type == nTypeLongPressed;
    }

    public static String runShellCmd(String shellCmd){
        System.out.println("runShellCmd：" + shellCmd);
        return null;
    }

    public static boolean execInputEvent(String inputEvent,boolean longPressed){
        System.out.println("execInputEvent：" + inputEvent);
        if (!TextUtils.isEmpty(inputEvent)){
            String[] args = inputEvent.split(",");
            if (args.length >= 2){
                System.out.println("execInputEvent： x pos = "  + args[0] + "y pos = " + args[1]);
                ControlEvent ce = new ControlEvent();
                ce.injectMouse(ACTION_DOWN,Integer.parseInt(args[0]),Integer.parseInt(args[1]));
                if (longPressed){
                    try{
                        Thread.sleep(500);
                        System.out.println("longPress sleep 500ms");
                    }catch (Exception e){
                        System.out.println("longPress sleep error:" + e.toString() );
                    }
                }
                ce.injectMouse(ACTION_UP,Integer.parseInt(args[0]),Integer.parseInt(args[1]));
                return true;
            }
        }
        return false;
    }
}
