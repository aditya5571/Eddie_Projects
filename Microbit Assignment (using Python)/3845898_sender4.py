from microbit import *
import radio

radio.on()
radio.config(group=4)

while True:
    if button_a.is_pressed():
        radio.send("request")

        incoming = radio.receive()

        receiver_IP = incoming


