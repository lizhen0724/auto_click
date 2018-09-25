package com.example.mytest;

import android.util.DisplayMetrics;
import android.view.Display;

import com.example.mytest.AdbShellServer.AdbShellPrivServer;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class Main {

    private static final String SOCKET_NAME = "scrcpy";

    public static void main(String... args) {

/*        ControlEvent ce = new ControlEvent();
        ce.injectMouse(ACTION_DOWN,Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        ce.injectMouse(ACTION_UP,Integer.parseInt(args[0]),Integer.parseInt(args[1]));*/

        AdbShellPrivServer server = new AdbShellPrivServer();
        server.testDemo();
    }

    /**
     * 对象转byte
     * @param obj
     * @return
     */
    private static byte[] ObjectToByte(Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }
}
