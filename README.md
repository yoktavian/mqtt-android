# mqtt-android

Sampel pengambilan data mqtt dari http://test.mosquitto.org/

Tambahkan maven pada repositories build.gradle seperti di bawah ini
```java
maven {

    url "https://repo.eclipse.org/content/repositories/paho-snapshots/"

}
```

Kemudian tambahkan library paho pada dependencies build.gradle
```java
  compile 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.0.2'
```

Untuk menghubungkan client dengan broker gunakan beberapa baris code berikut
```java
try {
    MqttClient client = new MqttClient(URL_BROKER, CLIENT_ID, new MemoryPersistence());
    client.setCallback(this);
    client.connect();
    String topic = "temp/random";
    client.subscribe(topic);
} catch (MqttException e) {
    e.printStackTrace();
}
```
CLINET_ID dapat dikosongkan.
Gunakan implement MqttCallback pada activity anda, agar lebih mudah dalam menangani connectionLost, messageArrived, dan deliveryComplete.
Jika anda bingung, silahkan buka source code dan buka MainActivity supaya anda menjadi lebih paham.
Hasilnya di activity anda akan muncul beberapa baris code berikut jika anda menerapkan implement MqttCallback
```java
@Override
public void connectionLost(Throwable cause) {

}

@Override
public void messageArrived(String topic, final MqttMessage message){

}

@Override
public void deliveryComplete(IMqttDeliveryToken token) {

}
```
Jika sudah, maka anda sudah siap untuk menggunakan data yang di terima dari mqtt broker. Pastikan topik yang di subscribe benar,
dan tambahkan beberapa baris kode berikut pada method messageArrived.
```java
@Override
public void messageArrived(String topic, final MqttMessage message){
    runOnUiThread(new Runnable() {
        public void run() {
            textview_anda.setText(String.valueOf(message));
        }
    });
}
```
Baris kode tambahan di atas digunakan supaya kita dapat mengupdate UI ketika ada message baru yang masuk pada topik yang telah di subscribe.
Karena method messageArrived bawaan dari library mqtt paho ini tidak berjalan pada Main Thread, jadi kita tidak bisa melakukan pembaharuan UI secara langsung,
kita harus memaksa method tersebut di jalankan pada Main Thread supaya kita bisa melakukan perubahan UI ketika ada data baru yang muncul.
Dan catatan yang terakhir adalah jangan lupa menambahkan permission INTERNET pada manifest seperti berikut.
```java
 <uses-permission android:name="android.permission.INTERNET"/>
```
