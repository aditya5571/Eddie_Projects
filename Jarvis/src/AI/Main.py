'''
Created on 25 Nov. 2020

@author: Aditya V.

Jarvis, prototype 1.0

'''

from AI import Engine, Testing_Conditions


if __name__ == '__main__':
    jE = Engine.jarvisEngine()
    test = Testing_Conditions.conditions()
    
    print(jE.voices[1])
    
    
    #jE.wishMe()
    jE.speak("I'm Jarvis, good to meet you")
    
    while True:
        userAudioString = jE.takeCommand().lower()
        test.action(jE, userAudioString)
