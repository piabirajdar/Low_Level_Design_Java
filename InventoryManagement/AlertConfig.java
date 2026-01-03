// AlertConfig is a compact value object that pairs a threshold with a listener. 
// The listener is an object that registers interest in events and gets notified when those events happen. 
// The warehouse does not need to know what the listener does when notified.
// It just calls a method on the listener when stock crosses a threshold and lets the listener handle the notification.
public class AlertConfig {
    private final int threshold;
    private final AlertListener listener;
    private boolean hasFired;

    public AlertConfig(int threshold, AlertListener listener) {
        this.threshold = threshold;
        this.listener = listener;
        this.hasFired = false;
    }

    public int getThreshold() {
        return threshold;
    }

    public AlertListener getListener() {
        return listener;
    }

    public boolean hasFired() {
        return hasFired;
    }

    public void setHasFired(boolean hasFired) {
        this.hasFired = hasFired;
    }
}

