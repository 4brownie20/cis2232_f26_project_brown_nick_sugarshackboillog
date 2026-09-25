# Project:  Sugar Shack Boil Log #

---
## Development Team ##
Business Client:  Kody Verhulp
<br/>
Lead Developer:  Nick Brown
<br/>
Quality Control:  TBD
<br/>
---
## Description ##
This project is a digital boil log for a small-scale sugar shack (maple syrup operation).  It replaces the paper clipboard sheets that are easily damaged
in a sugar shack environment.  Each boil session is recorded, including the date, tap count, sap volume, sap sugar content (Brix), boil start and end times,
fuel used and the amount of syrup produced.<br/><br/>
It takes roughly 40 litres of sap to make 1 litre of syrup, but sap sugar levels change daily with tree health and weather.  Sugar makers use the Rule of 86 to
predict how much sap is needed per litre of syrup.  By comparing the actual syrup produced to the predicted amount, the application helps the producer quickly
spot problems such as leaks, filtration losses, equipment defects or calibration errors.  Over the season, the data (efficiency, fuel cost per litre and yield)
shows trends that help the producer decide when to expand their sugar bush, change fuel sources or upgrade their evaporator.
---
## Colour ##
Main Colour:  #b5651d (amber)<br/>
Dark Colour:  #7a4312 (dark amber)<br/>
Light Colour:  #f3e3d3 (light amber)<br/>
Accent Colour:  #f5b041 (gold)<br/>
---
## Required Fields ##
This will be a list of fields and their datatype (class design format).  There are expected to be a minimum of six fields.
-boilId: int //primary key <br/>
-boilDate: String Note:  yyyy-MM-dd <br/>
-tapCount: int //number of taps <br/>
-sapVolume: double //litres of sap boiled <br/>
-sapBrix: double //sugar content of the sap (Brix) <br/>
-boilStartTime: String Note:  hh:mm (time the fire was lit) <br/>
-boilEndTime: String Note:  hh:mm (time the last batch was taken off) <br/>
-fuelType: String //wood, propane, oil or electric <br/>
-fuelQuantity: double //fuel burned in the unit for the fuelType (cords, litres or kWh) <br/>
-syrupProduced: double //litres of finished syrup produced <br/>
-boilEfficiency: double //calculated - actual yield against predicted yield (%) <br/>
-costPerLitre: double //calculated - fuel cost per litre of syrup <br/>
---
## Calculation ##

Once the user enters all of the values, the program will calculate the boil efficiency and the fuel cost per litre of syrup.
The Rule of 86 gives the litres of sap needed per litre of syrup (finished syrup is 66 Brix).
<br/>
Fuel unit prices:<br/>
Wood 150.00 per cord (128 cubic feet)<br/>
Propane 0.83 per litre<br/>
Oil 2.10 per litre<br/>
Electric 0.15 per kWh<br/>
<br/>
boilEfficiency calculation:<br/>
<br/>
sapRatio = 86 / sapBrix<br/>
predictedSyrup = sapVolume / sapRatio<br/>
boilEfficiency = syrupProduced / predictedSyrup * 100<br/>
<br/>
costPerLitre calculation:<br/>
<br/>
unitPrice = looked up from fuelType<br/>
fuelCost = fuelQuantity * unitPrice<br/>
costPerLitre = fuelCost / syrupProduced<br/>
<br/>
Example:  800 L of sap at 2.0 Brix, 17.5 L of syrup, 0.5 cord of wood<br/>
sapRatio = 43, predictedSyrup = 18.60 L, boilEfficiency = 94.06%<br/>
fuelCost = 75.00, costPerLitre = 4.29<br/>

---
## Report Details ##
To be determined with the BA in Sprint 2.<br/>
