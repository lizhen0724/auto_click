package com.example.mytest;


import android.graphics.Point;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;

/**
 * Union of all supported event types, identified by their {@code type}.
 */
public final class ControlEvent{
    public static final int TYPE_MOUSE = 2;
    private final ServiceManager serviceManager = new ServiceManager();
    private final MotionEvent.PointerProperties[] pointerProperties = {new MotionEvent.PointerProperties()};
    private final MotionEvent.PointerCoords[] pointerCoords = {new MotionEvent.PointerCoords()};
    private long lastMouseDown;

    public ControlEvent() {
        initPointer();
    }

    public static EventBean createMotionControlEvent(int action, int buttons, Position position) {
        EventBean event = new EventBean();
        event.type = TYPE_MOUSE;
        event.action = action;
        event.buttons = buttons;
        event.position = position;
        return event;
    }

    private void initPointer() {
        MotionEvent.PointerProperties props = pointerProperties[0];
        props.id = 0;
        props.toolType = MotionEvent.TOOL_TYPE_FINGER;

        MotionEvent.PointerCoords coords = pointerCoords[0];
        coords.orientation = 0;
        coords.pressure = 1;
        coords.size = 1;
    }

    private void setPointerCoords(Point point) {
        MotionEvent.PointerCoords coords = pointerCoords[0];
        coords.x = point.x;
        coords.y = point.y;
    }

    public boolean injectMouse(int action, int X,int Y) {
        long now = SystemClock.uptimeMillis();
        if (action == MotionEvent.ACTION_DOWN) {
            lastMouseDown = now;
        }
        Point point = new Point(X,Y);
        if (point == null) {
            // ignore event
            return false;
        }
        setPointerCoords(point);
        MotionEvent event = MotionEvent.obtain(now, now, action, 1, pointerProperties, pointerCoords, 0, 1, 1f, 1f, 0, 0,
                InputDevice.SOURCE_TOUCHSCREEN, 0);
        return serviceManager.getInputManager().injectInputEvent(event,InputManager.INJECT_INPUT_EVENT_MODE_ASYNC);
    }
}
