
#include <ESP32Firebase.h>
#include <DHT.h>

#define DHT11_PIN 4
#define DHT22_PIN 5

#define _SSID "iPhone de sofien"          // Your WiFi SSID
#define _PASSWORD "dimatetyatem"      // Your WiFi Password
#define REFERENCE_URL "https://meteo-89ecb-default-rtdb.firebaseio.com/"  // Your Firebase project reference url

DHT dht11(DHT11_PIN, DHT11);
DHT dht22(DHT22_PIN, DHT22);
Firebase firebase(REFERENCE_URL);

void setup() {
  Serial.begin(115200);
  
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(1000);

  // Connect to WiFi
  Serial.println();
  Serial.println();
  Serial.print("Connecting to: ");
  Serial.println(_SSID);
  WiFi.begin(_SSID, _PASSWORD);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print("-");
  }

  Serial.println("");
  Serial.println("WiFi Connected");

  // Print the IP address
  Serial.print("IP Address: ");
  Serial.print("http://");
  Serial.print(WiFi.localIP());
  Serial.println("/");
  
 dht11.begin();
  dht22.begin();


  
}

void loop() {
  delay(2000);

  float humidityDHT11 = dht11.readHumidity();
  float temperatureDHT11 = dht11.readTemperature();
  float humidityDHT22 = dht22.readHumidity();
  float temperatureDHT22 = dht22.readTemperature();

  if (isnan(humidityDHT11) || isnan(temperatureDHT11) || isnan(humidityDHT22) || isnan(temperatureDHT22)) {
    Serial.println("Failed to read DHT sensors.");
    return;
  }

  Serial.println("Reading DHT sensors:");
  Serial.print("DHT11 - Humidity: ");
  Serial.print(humidityDHT11);
  Serial.print("%, Temperature: ");
  Serial.print(temperatureDHT11);
  Serial.println("°C");

  Serial.print("DHT22 - Humidity: ");
  Serial.print(humidityDHT22);
  Serial.print("%, Temperature: ");
  Serial.print(temperatureDHT22);
  Serial.println("°C");

  // Convert the float values to strings
  String tempStrDHT11 = String(temperatureDHT11);
  String humStrDHT11 = String(humidityDHT11);
  String tempStrDHT22 = String(temperatureDHT22);
  String humStrDHT22 = String(humidityDHT22);

 firebase.setString("dht11/temp",tempStrDHT11 );
 firebase.setString("dht11/hum",humStrDHT11 );

 firebase.setString("dht22/temp",tempStrDHT22 );
 firebase.setString("dht22/hum",humStrDHT22 );
}
