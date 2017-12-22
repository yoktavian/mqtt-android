package yoktavian.dev.mqtt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MainActivity extends AppCompatActivity implements MqttCallback {

    private TextView txt_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_res = (TextView) findViewById(R.id.txt_res);

        // connecting to broker
        try {
            MqttClient client = new MqttClient("tcp://test.mosquitto.org", "", new MemoryPersistence());
            client.setCallback(this);
            client.connect();
            String topic = "temp/random";
            client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, final MqttMessage message){

        // Update UI on new message arrived in topic temp/random
        runOnUiThread(new Runnable() {
            public void run() {
                txt_res.setText(String.valueOf(message+" C"));
            }
        });
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
