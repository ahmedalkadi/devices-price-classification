'''
In the following python script, we will create a Flask API that will allow us to predict the price range of a mobile device based on its features.
We will use a trained SCV model to make the predictions. 
The data is stored in a SQLite database and we will use SQLAlchemy to interact with the database.
The data is loaded from a JSON file (test dataset ) into the database.

The API will have the following endpoints:
1. GET /api/devices/ - Get all devices
2. GET /api/devices/<id> - Get a device by id
3. POST /api/devices - Add a new device
4. GET /api/predict/<deviceId> - Predict the price range of a device by id
5. POST /predict - Predict the price range of a device using a form

Note the port number is 5000 to avoid conflicts with the "spring boot" server.


Your can run the API by running the following:

A) command in your terminal:
python FlaskR.
Open other terminal and run the following command to test the API:

1-for gettting all devices:
Invoke-RestMethod -Uri http://127.0.0.1:5000/api/devices/ -Method POST

2-for getting a device by id:
Invoke-RestMethod -Uri http://127.0.01:5000/api/devices/{id} -Method GET

3-for predicting the price range of a device by id:
Invoke-RestMethod -Uri http://127.0.01:5000/api/predict/{id} -Method GET

4-for adding a new device:
for adding a new device:
$device = @{"battery_power"=1000; "blue"=1; "clock_speed"=2.0; "dual_sim"=1; "fc"=5; "four_g"=1; "int_memory"=16; "m_dep"=0.6; "mobile_wt"=200; "n_cores"=8; "pc"=10; "px_height"=1000; "px_width"=2000; "ram"=4096; "sc_h"=10; "sc_w"=5; "talk_time"=10; "three_g"=1; "touch_screen"=1; "wifi"=1}  
Invoke-RestMethod -Uri http://127.0.01:5000/api/devices -Method POST -Body ($device | ConvertTo-Json) -ContentType "application/json"


B) using the website interface of the server:
http://127.0.0.1:5000/
then click on the endpoint you want to test.

'''



import json
from flask import Flask, request, jsonify, render_template
from flask_sqlalchemy import SQLAlchemy
import pickle
import pandas as pd

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///devices.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

# Load your trained model
model = pickle.load(open('ML Models/clf_SVC.pkl', 'rb'))

class Device(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    battery_power = db.Column(db.Integer, nullable=False)
    blue = db.Column(db.Integer, nullable=False)
    clock_speed = db.Column(db.Float, nullable=False)
    dual_sim = db.Column(db.Integer, nullable=False)
    fc = db.Column(db.Integer, nullable=False)
    four_g = db.Column(db.Integer, nullable=False)
    int_memory = db.Column(db.Integer, nullable=False)
    m_dep = db.Column(db.Float, nullable=False)
    mobile_wt = db.Column(db.Integer, nullable=False)
    n_cores = db.Column(db.Integer, nullable=False)
    pc = db.Column(db.Integer, nullable=False)
    px_height = db.Column(db.Integer, nullable=False)
    px_width = db.Column(db.Integer, nullable=False)
    ram = db.Column(db.Integer, nullable=False)
    sc_h = db.Column(db.Integer, nullable=False)
    sc_w = db.Column(db.Integer, nullable=False)
    talk_time = db.Column(db.Integer, nullable=False)
    three_g = db.Column(db.Integer, nullable=False)
    touch_screen = db.Column(db.Integer, nullable=False)
    wifi = db.Column(db.Integer, nullable=False)

def load_data(json_file):
    with open(json_file, 'r') as file:
        data = json.load(file)
        for item in data:
            device = Device(
                battery_power=item['battery_power'],
                blue=item['blue'],
                clock_speed=item['clock_speed'],
                dual_sim=item['dual_sim'],
                fc=item['fc'],
                four_g=item['four_g'],
                int_memory=item['int_memory'],
                m_dep=item['m_dep'],
                mobile_wt=item['mobile_wt'],
                n_cores=item['n_cores'],
                pc=item['pc'],
                px_height=item['px_height'],
                px_width=item['px_width'],
                ram=item['ram'],
                sc_h=item['sc_h'],
                sc_w=item['sc_w'],
                talk_time=item['talk_time'],
                three_g=item['three_g'],
                touch_screen=item['touch_screen'],
                wifi=item['wifi']
            )
            db.session.add(device)
        db.session.commit()

@app.route('/')
def home():
    return render_template('index.html', prediction=None, features={})

@app.route('/predict', methods=['POST'])
def predict():
    features = {
        'battery_power': float(request.form['battery_power']),
        'blue': int(request.form['blue']),
        'clock_speed': float(request.form['clock_speed']),
        'dual_sim': int(request.form['dual_sim']),
        'fc': float(request.form['fc']),
        'four_g': int(request.form['four_g']),
        'int_memory': int(request.form['int_memory']),
        'm_dep': float(request.form['m_dep']),
        'mobile_wt': float(request.form['mobile_wt']),
        'n_cores': int(request.form['n_cores']),
        'pc': float(request.form['pc']),
        'px_height': float(request.form['px_height']),
        'px_width': float(request.form['px_width']),
        'ram': int(request.form['ram']),
        'sc_h': float(request.form['sc_h']),
        'sc_w': float(request.form['sc_w']),
        'talk_time': float(request.form['talk_time']),
        'three_g': int(request.form['three_g']),
        'touch_screen': int(request.form['touch_screen']),
        'wifi': int(request.form['wifi'])
    }

    input_data = pd.DataFrame([list(features.values())], columns=features.keys())
    input_data = input_data.drop(['sc_w','four_g','m_dep','clock_speed','touch_screen'], axis=1)
    predicted_price_range = model.predict(input_data)
    return render_template('index.html', prediction=int(predicted_price_range[0]), features=features)

@app.route('/api/devices/', methods=['POST'])
def get_all_devices():
    devices = Device.query.all()
    return jsonify([{
        "id": device.id,
        "battery_power": device.battery_power,
        "blue": device.blue,
        "clock_speed": device.clock_speed,
        "dual_sim": device.dual_sim,
        "fc": device.fc,
        "four_g": device.four_g,
        "int_memory": device.int_memory,
        "m_dep": device.m_dep,
        "mobile_wt": device.mobile_wt,
        "n_cores": device.n_cores,
        "pc": device.pc,
        "px_height": device.px_height,
        "px_width": device.px_width,
        "ram": device.ram,
        "sc_h": device.sc_h,
        "sc_w": device.sc_w,
        "talk_time": device.talk_time,
        "three_g": device.three_g,
        "touch_screen": device.touch_screen,
        "wifi": device.wifi
    } for device in devices]), 200

@app.route('/api/devices/<int:id>', methods=['GET'])
def get_device_by_id(id):
    device = Device.query.get_or_404(id)
    return jsonify({
        "id": device.id,
        "battery_power": device.battery_power,
        "blue": device.blue,
        "clock_speed": device.clock_speed,
        "dual_sim": device.dual_sim,
        "fc": device.fc,
        "four_g": device.four_g,
        "int_memory": device.int_memory,
        "m_dep": device.m_dep,
        "mobile_wt": device.mobile_wt,
        "n_cores": device.n_cores,
        "pc": device.pc,
        "px_height": device.px_height,
        "px_width": device.px_width,
        "ram": device.ram,
        "sc_h": device.sc_h,
        "sc_w": device.sc_w,
        "talk_time": device.talk_time,
        "three_g": device.three_g,
        "touch_screen": device.touch_screen,
        "wifi": device.wifi
    }), 200

@app.route('/api/devices', methods=['POST'])
def add_device():
    data = request.get_json()
    new_device = Device(
        battery_power=data['battery_power'],
        blue=data['blue'],
        clock_speed=data['clock_speed'],
        dual_sim=data['dual_sim'],
        fc=data['fc'],
        four_g=data['four_g'],
        int_memory=data['int_memory'],
        m_dep=data['m_dep'],
        mobile_wt=data['mobile_wt'],
        n_cores=data['n_cores'],
        pc=data['pc'],
        px_height=data['px_height'],
        px_width=data['px_width'],
        ram=data['ram'],
        sc_h=data['sc_h'],
        sc_w=data['sc_w'],
        talk_time=data['talk_time'],
        three_g=data['three_g'],
        touch_screen=data['touch_screen'],
        wifi=data['wifi']
    )
    db.session.add(new_device)
    db.session.commit()
    return jsonify({"message": "Device added successfully"}), 201
  
    
@app.route('/api/predict/<int:deviceId>', methods=['POST'])
def api_predict(deviceId):
   
# If no JSON data that means retrieve the data from the SQLite database by the ID then predict the price range
    if request.is_json == False:
        device = Device.query.get_or_404(deviceId)
        features = {
            'battery_power': device.battery_power,
            'blue': device.blue,
            'clock_speed': device.clock_speed,
            'dual_sim': device.dual_sim,
            'fc': device.fc,
            'four_g': device.four_g,
            'int_memory': device.int_memory,
            'm_dep': device.m_dep,
            'mobile_wt': device.mobile_wt,
            'n_cores': device.n_cores,
            'pc': device.pc,
            'px_height': device.px_height,
            'px_width': device.px_width,
            'ram': device.ram,
            'sc_h': device.sc_h,
            'sc_w': device.sc_w,
            'talk_time': device.talk_time,
            'three_g': device.three_g,
            'touch_screen': device.touch_screen,
            'wifi': device.wifi
        }
    # if you got JSON data from external source then predict the price range, put first get the data from the JSON file
    else:
        device = request.get_json()
        features = {
            'battery_power': device.get('battery_power'),
            'blue': device.get('blue'),
            'clock_speed': device.get('clock_speed'),
            'dual_sim': device.get('dual_sim'),
            'fc': device.get('fc'),
            'four_g': device.get('four_g'),
            'int_memory': device.get('int_memory'),
            'm_dep': device.get('m_dep'),
            'mobile_wt': device.get('mobile_wt'),
            'n_cores': device.get('n_cores'),
            'pc': device.get('pc'),
            'px_height': device.get('px_height'),
            'px_width': device.get('px_width'),
            'ram': device.get('ram'),
            'sc_h': device.get('sc_h'),
            'sc_w': device.get('sc_w'),
            'talk_time': device.get('talk_time'),
            'three_g': device.get('three_g'),
            'touch_screen': device.get('touch_screen'),
            'wifi': device.get('wifi')
        }

    # Prepare input data for the model prediction
    input_data = pd.DataFrame([list(features.values())], columns=features.keys())
    input_data = input_data.drop(['sc_w', 'four_g', 'm_dep', 'clock_speed', 'touch_screen'], axis=1)

    # Predict the price range using the model
    predicted_price_range = model.predict(input_data)
    return jsonify({"predicted_price_range": int(predicted_price_range[0])}), 200

if __name__ == '__main__':
    with app.app_context():
        db.create_all()  # Create database and tables
        load_data('Source Data/test_devices.json')  # Load data from JSON file into the database
    app.run(debug=True)
