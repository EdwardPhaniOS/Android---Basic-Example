package ebookfrenzy.com.remotebound;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.widget.Toast;

public class RemoteService extends Service {

    public RemoteService() {
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            String dataString = data.getString("MyString");
            Toast.makeText(getApplicationContext(), dataString, Toast.LENGTH_LONG).show();
        }
    }

    final Messenger myMessenger = new Messenger(new IncomingHandler());
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myMessenger.getBinder();
    }

}
