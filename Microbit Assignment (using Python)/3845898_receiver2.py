from microbit import *
import radio

radio.on()
radio.config(group=2)

seqNum = [
    "01",
    "02",
    "03",
    "04",
    "05",
    "06",
    "07",
    "08",
    "09",
    "10",
    "11",
    "12",
    "13",
    "14",
    "15",
    "16",
    "17",
    "18",
    "19",
    "20",
    "21",
]

index = 0
x = 0
y = 0

while True:
    incoming = radio.receive()

    if incoming:
        ack_copy = ""
        if incoming[0:2] == seqNum[index]:
            ack_copy = incoming[0:2]
            index += 1
            radio.send(seqNum[index])

            display.set_pixel(x, y, 9)
            x += 1
            if x == 5:
                x = 0
                y += 1
        else:
            display.show(Image.SAD)
            sleep(200)
            display.clear()
            radio.send(ack_copy)
