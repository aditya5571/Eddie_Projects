
from _datetime import datetime
import os
import subprocess
import time
import webbrowser

import pyttsx3
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support.wait import WebDriverWait
from webdriver_manager.chrome import ChromeDriverManager

import speech_recognition as sr


class jarvisEngine:
    engine = pyttsx3.init('sapi5')
    voices = engine.getProperty('voices')
    
    engine.setProperty('voice', voices[1].id)
    engine.setProperty('rate', 185)
    
    
    def speak(self, audio):
        self.engine.say(audio)
        self.engine.runAndWait()
    
    
    def getTime(self):
        currentHour = int(datetime.now().hour)
        currentMin = int(datetime.now().minute)
        
        d = datetime.strptime(f"{currentHour}:{currentMin}", "%H:%M")  # create an object of the current time
        time = d.strftime("%I:%M %p")
        
        self.speak(f"{time}, sir. Are you feeling sleepy already?")
    
    
    def wishMe(self):
        time = self.getTime()
        if (time > '0') and (time < '12') and ('am' in time):
            self.speak(f"Good Morning. It's {time} in Melbourne, hope you have a fantastic day.")
        
        elif (time >= '0') and (time < '5') and ('pm' in time):
            self.speak(f"Afternoon. It's {time} in Melbourne, hope you have a fantastic day.")
        
        else:
            self.speak(f"It's {time} in Melbourne, hope you have an amazing night.")
    
    
    def takeCommand(self):
        r = sr.Recognizer()
        r.energy_threshold = 4000
        
        with sr.Microphone() as source:
            print("\n Listening... \n")
            r.pause_threshold = 0.8
            r.adjust_for_ambient_noise(source, duration = 1)
            audioFile = r.listen(source)
        
        try:
            userAudioString = r.recognize_google(audioFile, language='en-US')
            userAudioString = userAudioString.upper()
            print(f"> {userAudioString} \n")
            
        except Exception as e:
            self.speak("I'm sorry, can you come again?")
            return "None"
        
        return userAudioString.lower()
    
    



    def chromeSetup(self):
        browser = webdriver.Chrome(ChromeDriverManager().install())
        return browser


    def getChromeLink(self, searchArg):
            searchArg = searchArg.split()
            searchArg = 'https://' + searchArg[-1]
            
            print("Opening link: " + searchArg)
            
            browser = self.chromeSetup()
            browser.get(searchArg)    


    def searchGoogle(self, searchArg):
        browser = self.chromeSetup()
        chromeSearchBarXPath = '/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input'
        
        searchArg = searchArg.partition("google")
        searchArg = searchArg[-1]
        
        browser.get('http://www.google.com')
        
        # timeout period
        waitTime = WebDriverWait(browser, 600)
        
        # clear screen
        os.system('cls')
        
        # wait until google searchbar is visible
        google_search_bar = waitTime.until(EC.presence_of_element_located((By.XPATH, chromeSearchBarXPath)))
        
        try:
            google_search_bar.click()
            google_search_bar.send_keys(searchArg)
            
            time.sleep(0.3)
            
            google_search_bar.send_keys(Keys.ENTER)
        
        except:
            print("Search couldn't work")




        
#     def startChrome(self):
# #         run_dir = r"C:\Users\adity\OneDrive\Desktop\\Run.lnk"
# #         cmd_dir = r"C:\Users\adity\OneDrive\Desktop\\Command_Prompt.lnk"
# #         run = subprocess.Popen([cmd_dir], shell=True)
# #         subprocess.run()
# #         os.system("start chrome.exe")
#         
# #         webdriver.Chrome(r'C:\Users\adity\OneDrive\Documents\Python\New folder\chromedriver.exe')
#         
#         driver = webdriver.Chrome(ChromeDriverManager().install())
#         chromeAddressBar = driver.find_element_by_xpath('//body').send_keys(Keys.CONTROL, "l")
#         
#         waitTime = WebDriverWait(driver, 600)
#         waitTime.until(
#             EC.presence_of_element_located((By.ID, chromeAddressBar))
#         )
#         
#         self.speak("What would you like me to search sir?")        
        
        
        
        
#         browser = self.chromeSetup()
#         chromeSearchBarXPath = '/html/body/div[2]/div[2]/form/div[2]/div[1]/div[1]/div/div[2]/input'
# 
#         if ('searchGoogle' in searchArg):
#             searchArg = searchArg.partition("searchGoogle")
#             searchArg = searchArg[-1]
#             
#             browser.get('http://www.google.com')
#              
#             # timeout period
#             waitTime = WebDriverWait(browser, 600)
#              
#             # clear screen
#             os.system('cls')
#              
#             # wait until searchGoogle bar is visible
#             google_search_bar = waitTime.until(EC.presence_of_element_located((By.XPATH, chromeSearchBarXPath)))
#              
#             try:
#                 google_search_bar.click()
#                 google_search_bar.send_keys(searchArg)
#                  
#                 time.sleep(0.3)
#                  
#                 google_search_bar.send_keys(Keys.ENTER)
#              
#             except:
#                 print("Exception 1: Search couldn't work")
# #             
#             
#         elif('.com' in searchArg):
#             searchArg = searchArg.split()
#             searchArg = 'https://' + searchArg[-1]
#             
#             print("String is" + searchArg)
#             
#             browser.get(searchArg)
#         
#                 
#         print("Search finished")
#         
#             
# #             # timeout period
# #             waitTime = WebDriverWait(browser, 600)
# #             
# #             # clear screen
# #             os.system('cls')
# #             
# #             print("1")
# #             
# #             # wait until searchGoogle bar is visible
# #             chromeAddressBar = browser.find_element(By.XPATH, chromeSearchBarXPath).sendKeys(Keys.CONTROL, "l")
# #             google_search_bar = waitTime.until(EC.presence_of_element_located((By.XPATH, chromeAddressBar)))
# #             
# #             print("2")
# #             
# #             print(dir(browser))
# #             
# #             try:
# #                 google_search_bar.click()
# #                 google_search_bar.send_keys(searchArg)
# #                 
# #                 time.sleep(0.3)
# #                 
# #                 google_search_bar.send_keys(Keys.ENTER)
# #                 
# #                 print("3")
# #                 
# #             except:
# #                 print("Exception 1: Search couldn't work")
#                 


