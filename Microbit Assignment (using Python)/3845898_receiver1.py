from microbit import *
import radio

radio.on()
radio.config(group=1)

string_sender = "MS"
string_receiver = "AD"
header = string_sender + string_receiver
acknowledgement = "ACK"
ack = header + acknowledgement

x = 0
y = 0
while True:
    incoming = radio.receive()

    if incoming:
        if incoming[0] == "0":
            radio.send("1" + (ack))
            display.set_pixel(x, y, 9)
            x += 1
            if x == 5:
                x = 0
                y += 1
            sleep(300)
        if incoming[0] == "1":
            radio.send("0" + (ack))
            display.set_pixel(x, y, 9)
            x += 1
            if x == 5:
                x = 0
                y += 1
            sleep(300)
