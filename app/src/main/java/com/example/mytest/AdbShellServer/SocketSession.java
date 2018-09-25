package com.example.mytest.AdbShellServer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by maolei on 2017/2/17.
 */

public class SocketSession extends Thread {
    private Socket mSocket;
    private DataOutputStream mOutputStream;
    private DataInputStream mInputStream;
    private ISocketServerCallBack mServerCb;
    /*
 * 构造函数，接收每一个socket实例
 */
    public SocketSession(Socket socket, ISocketServerCallBack cb) {
        this.mSocket = socket;
        mServerCb = cb;
    }

    /*
     * 线程执行方法
     */
    public void run() {
        try {
            System.out.println("start handle socket thread");
            //对象数据流的获取与返回
            mOutputStream = new DataOutputStream(new BufferedOutputStream(mSocket.getOutputStream(), 8096));
            mInputStream = new DataInputStream(new BufferedInputStream(mSocket.getInputStream(), 8096));
            while (!mSocket.isClosed()) {
                if (mInputStream.available() > 0 && mInputStream.available() < 4) {
                    System.out.println("Cleaning Bad Data ====== 1");
                    break;
                }
                int nType = mInputStream.readInt();
                if ( nType == AdbServerCommon.nTypeShellCmd){
                    int nLength = mInputStream.readInt();
                    if ( nLength > 0 ){
                        byte[] valueBuffer = new byte[nLength];
                        mInputStream.readFully(valueBuffer);
                        AdbServerCommon.runShellCmd(new String(valueBuffer));
                    }
                }else if (nType == AdbServerCommon.nTypeStopServer){
                    System.out.println("停止server");
                    if (mServerCb != null)
                        mServerCb.OnStopServer();
                }else if (nType == AdbServerCommon.nTypeClicked || nType == AdbServerCommon.nTypeLongPressed){
                    int nLength = mInputStream.readInt();
                    if ( nLength > 0 ){
                        byte[] valueBuffer = new byte[nLength];
                        mInputStream.readFully(valueBuffer);
                        AdbServerCommon.execInputEvent(new String(valueBuffer),nType == AdbServerCommon.nTypeLongPressed);
                    }
                }else {
                    System.out.println("类型错误");
                }
            }
            mInputStream.close();
            mOutputStream.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            System.out.println("end handle socket thread");
        }
    }
}
