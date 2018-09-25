package com.example.mytest.AdbShellServer;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by maolei on 2017/2/20.
 */

public class AdbShellPrivServer implements ISocketServerCallBack{
    private ServerSocket mSrvSocket;
    private static int srvSockPort = 36088;
    private boolean mStop = false;

    public AdbShellPrivServer(){
        mSrvSocket = null;
    }

    public void testDemo() {
        runSocketServer();
    }

    private void runSocketServer(){
        if (null != mSrvSocket)
            return;
        try {
            System.out.println("Start ConnectionAsServer Runable");
            mSrvSocket = new ServerSocket();
            mSrvSocket.setReuseAddress(true);
            mSrvSocket.bind(new InetSocketAddress("0.0.0.0", srvSockPort));
            while (!mStop) {
                Socket socket = createConnection();
                if (null != socket && !mStop) {
                    System.out.println("Start ConnectionAsServer Runable");
                    // 接收每一个客户端的连接，并返回socket实例
                    SocketSession session = new SocketSession(socket,this);
                    // 为每一个客户端启一个线程，去执行操作
                    session.start();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("ConnectionAsServer Runable Exception" + e.toString());
        } finally {
            if (mSrvSocket != null) {
                try {
                    System.out.println("mSvrSocket closed ");
                    mSrvSocket.close();
                    /**
                     * A firmware bug fixed after Android 4.2.2.
                     * https://github.com/square/okhttp/issues/1684
                     * https://github.com/square/okhttp/pull/1817
                     * https://code.google.com/p/android/issues/detail?id=54072
                     */
                } catch (Exception e) {
                    //DmLog.w(TAG, "mSvrSocket close1 error, exception = %s",e);
                    System.out.println("mSvrSocket closed2 exception" + e.toString());
                }
                mSrvSocket = null;
            }
        }
    }

    private Socket createConnection() {
        if ( mSrvSocket == null )
            return null;
        try {
            Socket socket = mSrvSocket.accept();
            if ( socket == null )
                return null;
            // 由于是采用网络共享的方式建立的socket，
            // 所以，关闭socket时，放弃缓存数据，无延迟关闭。
            // 不然会导致连接无法释放，导致端口长期占用
            socket.setSoLinger(true, 0);
            socket.setKeepAlive(true);
            socket.setTcpNoDelay(true);
            return socket;
        }  catch (Exception e) {
            // TODO: handle exception
            System.out.println("createConnection exception" + e.toString());
        }
        return null;
    }

    public void OnStopServer(){
        System.out.println("停止server，mStop = " + mStop);
        mStop = true;
    }
}
