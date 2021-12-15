from microbit import *
import radio

radio.on()
radio.config(group=4)

IP_address = "205.165.47.142"

while True:
    incoming = radio.receive()

    if incoming:
        if incoming == "request":
            radio.send(IP_address)

