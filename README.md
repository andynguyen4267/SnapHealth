# Snaphealth


---------------------------------------------------------------------------------------------------------------------------

## Collect Data

##### Diets.csv (metadata of meals) -- Kaggle.com 
##### User personal info (json): username, password, age, gender, weight, height
##### foodRecommendation system: activity level, body goal, diet type
##### Sleep tracker system (csv): sleep duration


---------------------------------------------------------------------------------------------------------------------------

## Manipulate Data

##### Diets.csv -- Remove extraction_day, extraction_time (don't need) && Calculate total calories
##### Username, password (json) --- check log in
##### age, gender, weight, height (json) --- calculate BMI, BMR, DTEE, Harris-Benedict --> Calories intake
##### age, gender (json) --- water intake recommendation
##### age (json) --- sleep recommendation


---------------------------------------------------------------------------------------------------------------------------

## FOOD RECOMMENDATION

#####   Food Recommendation is the feature that can recommend our users 3 meals per day with the acceptable calories and suitable diet type to help users reach their body goal

#####   Algorithm support : Harris-Benedict equation, BMI, BMR, TDEE, (+200 calo for gain weight and -200 calo for lose weight)
#####                       Calculate calories by macros with ratio (1:4:4 - protein:carb:fat)
#####                       Match data with metadata with calories bound of +- 100 calories

---------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------

## THE HYDRATION TRACKER

#####   Hydration Tracker is the feature that can recommend our users the amount of water intake per day and keep track the number of water drank

#####   4 options for user: Small Gulp : 43ml, Large Gulp : 77ml, Five Mouthfuls : 157ml, A mug : 240ml
#####   System will be reseted after 24 hours

---------------------------------------------------------------------------------------------------------------------------

## SLEEP TRACKER 

##### Sleep Tracker is the feature that can keep track user sleep duration and give feedback based on suggestion sleep time based on age

##### Algorithm support : Sleep duration, compare w system suggestion sleep time (bound +- 1 hours)
#####                     inbound = good quality, +outbound = oversleep, bad quality, -outbound = undersleep, bad quality


---------------------------------------------------------------------------------------------------------------------------

## WORK CITES

###### research : https://www.kaggle.com/datasets/thedevastator/healthy-diet-recipes-a-comprehensive-dataset
###### research : https://www.ncbi.nlm.nih.gov/pmc/articles/PMC7784146/#:~:text=In%20men%2C%20the%20Harris%2DBenedict,4.6756%20x%20age%20in%20years.
###### research : https://www.livestrong.com/article/526442-the-activity-factor-for-calculating-calories-burned/
###### research : https://www.betterhealth.vic.gov.au/health/healthyliving/water-a-vital-nutrient
###### research : https://www.cdc.gov/sleep/about_sleep/how_much_sleep.html
###### logos/icons : https://www.flaticon.com/


---------------------------------------------------------------------------------------------------------------------------

## MILESTONE 1: COLLECT MEALS DATA FOR FOOD RECOMMENDATION (WEEK 1)
#### In week 1, we research the collect data of meals from kaggle, we created github, make sure all member invited and also decided to work on android studio (Kotlin) for a whole quarter.
###### Nguyen, Jay, Andy, Adam : completed

## MILESTONE 2: DECIDE WHAT TO DO, ASSIGN TASK AND SUBMIT PROJECT PROPOSAL (WEEK 2)
#### In week 2, we let our member choose what to do depend on their interest and ability. Andy : UI, Nguyen, Jay : Backend, Adam :?
###### Nguyen, Jay, Andy : completed, Adam : not respond

## MILESTONE 3: IN PROGRESS WITH HYDRATION, GOOGLEFIT API, HOMEPAGE (WEEK 3 - WEEK 5)
#### From week 3 to week 5, we need our system in progress of hydration tracker, also dealing with googleFIT API to retreive user data from Fit, also homepage need to be almost done
###### Nguyen, Jay, Andy : completed, Adam : not respond

## MILESTONE 3: FINISH ACCEPTABLE HYDRATION SYSTEM, FINISH GOOGLEFIT API, FINISH HOMEPAGE, START FOOD RECOMMENDATION SYSTEM, SIGN UP PAGE AND FIND THE WAY TO RECORD USER DATA (WEEK 6 - WEEK 7)
#### From week 6 to week 7, we need out hydration system work well, also can retrieve data from FIT, finish homepage UI and start sign up page where first time user registers and start food recommendation system
###### Nguyen, Andy : partial done, Jay : cannot firgure out API, Adam: new assign to find the way to store data from sign up page

## MILESTONE 4: FINISH RECOMMENDATION SYSTEM, SIGN UP PAGE, CAN STORE USER DATA, START MANIPULATE DIETS, WON'T WORK WITH API ANYMORE FILE AND RECOMMEND USER MEALS (WEEK 8)
#### In Week 8, we need our recommendation system work, sign up page done and store data from sign up page, recommend meals for user and beyond.
###### Nguyen, Andy : done, Jay: switch to backend, Adam: not respond

## MILESTONE 5 : FOOD RECOMMENDATION, HYDRATION TRACKER, SIGN UP PAGE, HOME PAGE, CAN STORE USER DATA, CAN RECORD USER DATA, START SLEEP (WEEK 9)
#### In week 9, we need our system done except sleep tracker and UI
###### Nguyen, Andy, Jay: done, Adam: not completed 

## MILESTONE 6 : FINISH THE WHOLE SYSTEM AND START TESTING (WEEK 10)
#### In week 10, we need our main features need to work well. Developing and testing will be performed in the whole week and submit.
###### Nguyen, Andy, Jay: completed, Adam: not completed
