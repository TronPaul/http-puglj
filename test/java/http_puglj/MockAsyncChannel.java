package http_puglj;

import org.httpkit.server.AsyncChannel;

import java.util.ArrayList;
import java.util.List;

public class MockAsyncChannel extends AsyncChannel {
    private List<Object> sent;

    public MockAsyncChannel() {
        super(null, null);
        this.sent = new ArrayList<Object>();
    }

    public boolean isWebSocket() {
        return true;
    }

    @Override
    public boolean send(java.lang.Object data, boolean close) throws java.io.IOException {
        if (isClosed()) {
            return false;
        }
        sent.add(data);
        if (close) {
            serverClose(1000);
        }

        return true;
    }

    public List<Object> getSent() {
        return sent;
    }

    @Override
    public String toString() {
        return "MockAsyncChannel<->List<Object>";
    }
}
