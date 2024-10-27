# Devices Price Classification System

This project is a two-part system designed to predict and classify the prices of devices based on their characteristics. It combines a machine learning model in Python for prediction with a Spring Boot application for managing data and providing RESTful endpoints.

## Project Structure

The project is divided into two main parts:

1. **Python Project**: 
   - Uses machine learning to predict the prices of devices, helping sellers classify devices according to their features.
   - Implements several notebooks and scripts for data preparation, model training, and prediction.
   - Provides a Flask application (`FlaskR.py`) for interfacing with the ML model.

2. **Spring Boot Project**:
   - Contains a simple entity and endpoints to call the Python prediction service.
   - Manages device data using an H2 in-memory database.
   - Offers RESTful services to perform operations on the stored data.

## Python Project
## **Project Structure**

```
Devices_Price_Classification_System/
│
├── ML_Models/                          # Directory for storing ML models
│   ├── linear_regression_model.pkl     # Linear regression model for price prediction
│   └── clf_svc.pkl                     # Support vector classifier model for price range
├── Source_Data/                        # Directory for source data files
│   ├── train.csv                       # Training data for models
│   ├── screen_properties.csv           # Screen properties data
│   ├── train_cleaned.csv               # Cleaned training data
│   └── test_devices.json               # JSON file containing test device data
├── templates/                          # Templates for the web interface
│   └── index.html                      # HTML template for the Flask web app
├── FlaskR.py                           # Flask application script for running the server
├── ML_model_screen_height.ipynb        # Notebook for creating a linear regression model
├── Preparing_The_Data.ipynb            # Notebook for data cleaning and visualization
├── Price_range_ML_modeling.ipynb       # Notebook for developing the price range ML model
├── Scrapping.ipynb                     # Notebook for scraping screen dimensions data
├── Spring Boot Project                 # Directory for the Spring Boot project
├── Predicted Prices                    # Directory for storing sample predicted prices 
└── README.md                           # Project documentation
```
### Directory Structure

- **ML Models**:
  - `linear_regression_model.pkl`: Linear regression model for price prediction.
  - `clf_svc.pkl`: Support vector classifier for predicting the price range.

- **Source Data**:
  - `train.csv`, `screen_properties.csv`, `train_cleaned.csv`: Data used for training the models.
  - `test_devices.json`: Test data for prediction.

- **templates**:
  - `index.html`: Template for the web interface.

- **Notebooks**:
  - `ML_model_screen_height.ipynb`: Creates a linear regression model to preprocess fake data related to device screen heights.
  - `Preparing_The_Data.ipynb`: Cleans and visualizes the device data.
  - `Price_range_ML_modeling.ipynb`: Develops the machine learning model for predicting the price range.
  - `Scrapping.ipynb`: Scrapes screen dimensions and DPR data from the website ["https://ineedlearn.wordpress.com/2020/12/26/width-height-and-dpr-of-cell-phones/"](https://ineedlearn.wordpress.com/2020/12/26/width-height-and-dpr-of-cell-phones/).

### Flask Application (`FlaskR.py`)

The `FlaskR.py` script provides a RESTful API to interact with the machine learning model for predicting device prices.

#### Features:
- Creates a SQLite database to store device data.
- Loads the data from the `test_devices.json` file.
- Provides a web server and terminal-based user interface to perform predictions.



## Requirements

### Python Project
- Python 3.x
- Flask
- scikit-learn
- pandas
- numpy
- sqlite3

### Spring Boot Project
- Java 8 or above
- Maven
   - Install required dependencies using:

2. **Running the Flask server**:
   - Start the server by running:
     ```bash
     python FlaskR.py
     ```
   - The server will be accessible at `http://localhost:5000`.

3. **Available Endpoints**:
   - **GET /predict**: Perform predictions on device data.
   - **POST /add_device**: Add a new device to the database.
   - **GET /devices**: Retrieve all stored devices.

4. **Access the Web Interface**:
   - Open a web browser and navigate to `http://localhost:5000`.
   - Use the interface to perform various tasks such as predicting device prices.

### Spring Boot Project

The Spring Boot application works alongside the Python project, using the same test data for performing necessary operations.

#### Features:
- Utilizes an H2 in-memory database for data management.
- Imports data from `test_devices.json`.
- Provides endpoints for performing CRUD operations.

#### Usage:
1. **Setup the environment**:
   - Make sure Java and Maven are installed.
   - Build the project using:
     ```bash
     mvn clean install
     ```

2. **Running the Spring Boot Application**:
   - Start the application using:
     ```bash
     mvn spring-boot:run
     ```
   - The server will be accessible at `http://localhost:8080`.

3. **Managing the H2 Database**:
   - Access the H2 console at `http://localhost:8080/h2-console`.
   - Use `jdbc:h2:mem:testdb` as the JDBC URL to connect.

4. **RESTful Endpoints**:
   - **GET /api/devices**: List all devices.
   - **POST /api/devices**: Add a new device.
   - **PUT /api/devices/{id}**: Update an existing device.
   - **DELETE /api/devices/{id}**: Delete a device.

#### Testing with Postman

1. Import the test data by making a POST request to `/api/devices` with `test_devices.json` as the payload.
2. Perform GET, POST, PUT, and DELETE requests to interact with the device data.



Here’s the revised README.md file with the additional information and examples for terminal use:

# Devices Price Classification System

This project is a two-part system designed to predict and classify device prices based on their characteristics. It integrates a machine learning model in Python for predictions with a Spring Boot application for managing data and providing RESTful endpoints.


## **Overview**

The project is composed of two main parts:

1. **Python Project**: Predicts the prices of devices, enabling sellers to classify devices according to their characteristics. It includes scripts and notebooks for data preprocessing, model training, and a Flask application (`FlaskR.py`) to interface with the model.
   
2. **Spring Boot Project**: Manages device data using an H2 in-memory database and provides endpoints for CRUD operations. It uses the same test data as the Python project.

## **Python Project Details**

The `FlaskR.py` script creates a Flask API that allows us to predict the price range of a mobile device based on its features using a trained Support Vector Classifier (SVC) model. The data is stored in a SQLite database, and we use SQLAlchemy to interact with it. The data is loaded from a JSON file (test dataset) into the database.

### **API Endpoints**

The API provides the following endpoints:

1. **GET /api/devices/** - Get all devices.
2. **GET /api/devices/<id>** - Get a device by ID.
3. **POST /api/devices** - Add a new device.
4. **GET /api/predict/<deviceId>** - Predict the price range of a device by ID.
5. **POST /predict** - Predict the price range of a device using a form.

**Note:** The Flask server runs on port 5000 to avoid conflicts with the Spring Boot server.

### **Running the Flask Application**

1. **Setup the Environment**:
   - Make sure you have Python installed.
   - Install the required dependencies:
     ```bash
     pip install -r requirements.txt
     ```

2. **Start the Flask Server**:
   - Run the Flask application:
     ```bash
     python FlaskR.py
     ```
   - The server will be accessible at `http://127.0.0.1:5000`.

3. **Access the Web Interface**:
   - Open a browser and navigate to `http://127.0.0.1:5000` to use the web interface.
   - Click on the desired endpoint to test.

### **Examples for Terminal Usage**

Here are some examples of how to test the API using the terminal:

1. **Get all devices:**
   ```bash
   curl -X GET http://127.0.0.1:5000/api/devices/
   ```

2. **Get a device by ID:**
   ```bash
   curl -X GET http://127.0.0.1:5000/api/devices/{id}
   ```

3. **Predict the price range of a device by ID:**
   ```bash
   curl -X GET http://127.0.0.1:5000/api/predict/{id}
   ```

4. **Add a new device:**
   ```bash
   curl -X POST http://127.0.0.1:5000/api/devices -H "Content-Type: application/json" -d '{"battery_power":1000, "blue":1, "clock_speed":2.0, "dual_sim":1, "fc":5, "four_g":1, "int_memory":16, "m_dep":0.6, "mobile_wt":200, "n_cores":8, "pc":10, "px_height":1000, "px_width":2000, "ram":4096, "sc_h":10, "sc_w":5, "talk_time":10, "three_g":1, "touch_screen":1, "wifi":1}'
   ```

### **Using PowerShell**

You can also use PowerShell to test the API:

1. **Get all devices:**
   ```powershell
   Invoke-RestMethod -Uri http://127.0.0.1:5000/api/devices/ -Method GET
   ```

2. **Get a device by ID:**
   ```powershell
   Invoke-RestMethod -Uri http://127.0.0.1:5000/api/devices/{id} -Method GET
   ```

3. **Predict the price range of a device by ID:**
   ```powershell
   Invoke-RestMethod -Uri http://127.0.0.1:5000/api/predict/{id} -Method GET
   ```

4. **Add a new device:**
   ```powershell
   $device = @{
       "battery_power"=1000; "blue"=1; "clock_speed"=2.0; "dual_sim"=1;
       "fc"=5; "four_g"=1; "int_memory"=16; "m_dep"=0.6; "mobile_wt"=200;
       "n_cores"=8; "pc"=10; "px_height"=1000; "px_width"=2000;
       "ram"=4096; "sc_h"=10; "sc_w"=5; "talk_time"=10; "three_g"=1;
       "touch_screen"=1; "wifi"=1
   }
   Invoke-RestMethod -Uri http://127.0.0.1:5000/api/devices -Method POST -Body ($device | ConvertTo-Json) -ContentType "application/json"
   ```

## **Spring Boot Project Details**

The Spring Boot project manages device data through an H2 in-memory database and provides RESTful endpoints for interacting with the data.

### **Running the Spring Boot Application**

1. **Setup the Environment**:
   - Ensure you have Java and Maven installed.

2. **Start the Application**:
   - Run the Spring Boot application:


3. **Managing the H2 Database**:
   - Access the H2 console at `http://localhost:8080/h2-console`.
   - Use `jdbc:h2:mem:testdb` as the JDBC URL.

### **Available Endpoints**

- **GET /api/devices**: Retrieve all devices.
- **POST /api/devices**: Add a new device.
- **PUT /api/devices/{id}**: Update a device by ID.
- **DELETE /api/devices/{id}**: Delete a device by ID.

### **Testing with Postman**

1. Import the data by sending a POST request to `/api/devices` using `test_devices.json`.
2. Perform different requests to interact with the device data.


## **Postman Usage Examples**

You can use Postman to interact with the Spring Boot API endpoints for testing the device management and price prediction functionalities.

### **Get a Specific Device**

To retrieve a device by its ID, send a GET request to:
```
GET http://localhost:8080/api/devices/69
```
This will fetch the details of the device with ID 69 from the database.

### **Get All Devices**

To get all the devices stored in the database, send a GET request to:
```
GET http://localhost:8080/api/devices/
```
This will return a list of all available devices.

### **Add a New Device**

To add a new device to the database:
1. Send a POST request to:
   ```
   POST http://localhost:8080/api/devices/
   ```
2. In the Body tab, select `raw` and choose `JSON` format.
3. Type the JSON data for the new device in the following format:
   ```json
   {
       "battery_power": 1500,
       "blue": 1,
       "clock_speed": 2.5,
       "dual_sim": 1,
       "fc": 10,
       "four_g": 1,
       "int_memory": 32,
       "m_dep": 0.8,
       "mobile_wt": 180,
       "n_cores": 4,
       "pc": 13,
       "px_height": 1280,
       "px_width": 720,
       "ram": 3072,
       "sc_h": 15,
       "sc_w": 8,
       "talk_time": 12,
       "three_g": 1,
       "touch_screen": 1,
       "wifi": 1
   }
   ```
4. Click on "Send" to add the device to the database.

### **Predict the Price Range for a Device**

To predict the price range of a specific device, send a POST request to:
```
POST http://localhost:8080/api/devices/predict/69
```
Replace `69` with the ID of the device for which you want to make the prediction. This will return the predicted price range for the specified device.



## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ahmedalkadi/devices-price-classification.git
## **License**

This project is licensed under the MIT License. See the `LICENSE` file for more information.

## **Acknowledgments**

- Datasets used for training and model development.
- The website ["https://ineedlearn.wordpress.com/2020/12/26/width-height-and-dpr-of-cell-phones/"](https://ineedlearn.wordpress.com/2020/12/26/width-height-and-dpr-of-cell-phones/) for screen data.
```
This expanded `README.md` includes a comprehensive guide on setting up, running, and testing the API using both terminal commands and PowerShell. It also explains the endpoints provided by the Flask API and the Spring Boot application.
```