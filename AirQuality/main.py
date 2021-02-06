from pip._vendor import requests
import json





# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    response = requests.get("https://www.airnowapi.org/aq/forecast/zipCode/?format=application/json&zipCode=84115&date=2021-02-05&distance=25&API_KEY=1FF55248-313C-4A68-984E-35E7FE1EB44A")

    def jprint(obj):
        # create a formatted string of the Python JSON object
        text = json.dumps(obj, sort_keys=True, indent=4)
        print(text)
    jprint(response.json())


# https://www.airnowapi.org/aq/observation/zipCode/current/?format=application/json&zipCode=84115&distance=5&API_KEY=1FF55248-313C-4A68-984E-35E7FE1EB44A