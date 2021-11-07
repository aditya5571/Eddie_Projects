
class conditions:
#     jE = Engine.jarvisEngine()
#     userCommand = ""
#     
#     def __init__(self, jE, userCommand):
#         self.userCommand = userCommand
#         self.jE = jE
    
    
    def action(self, jE, userCommand):
        if ('time' in userCommand):
            time = jE.getTime()
            
        
#         elif('what' or 'when' or 'why' in userCommand):
        
#         elif ('open chrome' in userCommand):
#             jE.startChrome()
        
        elif ('open' in userCommand):
            # wants to search some link.com in the browser
            if ('.com' in userCommand):
                jE.getChromeLink(userCommand)
            
            # else wants to open some local application

        elif ('search' and 'google' in userCommand):
            # wants to make a google search
            jE.searchGoogle(userCommand)
        
        else:
            jE.speak("Can you come again?")
            



                
#                 jE.searchGoogle(userCommand)
            
            
#             if ('open' in userCommand):
#                 if ('chrome' in userCommand):
#                     jE.startChrome()
            
#             if ('searchGoogle' in userCommand):
                
        
            # if('what' or "what's" or 'when' or 'why' in userCommand):
                
