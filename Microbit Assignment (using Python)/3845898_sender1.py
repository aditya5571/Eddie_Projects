from microbit import *
import radio

radio.on()
radio.config(group=1)

stringlist = [
    "One",
    "Two",
    "Three",
    "Four",
    "Five",
    "Six",
    "Seven",
    "Eight",
    "Nine",
    "Ten",
    "Eleven",
    "Twelve",
    "Thirteen",
    "Fourteen",
    "Fifteen",
    "Sixteen",
    "Seventeen",
    "Eighteen",
    "Nineteen",
    "Twenty",
]


string_sender = "AD"
string_receiver = "MS"
header = string_sender + string_receiver

index = 0
packets_lost = 0
packets_sent = 0
seqN = 0
can_Send = True
resend = False


def tick():
    display.show(Image("00009:00090:90900:09000:00000"))
    sleep(500)
    display.clear()
    sleep(500)


while True:
    if button_a.is_pressed() and can_Send:
        packet = str(seqN) + header + stringlist[index]
        radio.send(packet)
        start_timer = running_time()
        packets_sent += 1
        tick()
        if seqN == 1:
            seqN = 0

        can_Send = False

        sleep(1000)
        incoming = radio.receive()

        if incoming:
            if incoming[1:] == "MSADACK":
                if incoming[0] == "1":
                    index += 1
                    seqN = 1
                    can_Send = True
                if incoming[0] == "0":
                    index += 1
                    can_Send = True
            else:
                sleep(2000)
                stop_timer = running_time()
                resend = True

    while resend:
        packet = str(seqN) + header + stringlist[index]
        packets_lost += 1
        display.scroll("Lost: " + str(packets_lost))
        display.scroll("Retrying ")
        radio.send(packet)
        packets_sent += 1
        tick()

        sleep(2000)
        incoming = radio.receive()

        if incoming:
            if incoming[1:] == "MSADACK":
                if incoming[0] == "1":
                    index += 1
                    seqN = 1
                    can_Send = True
                    resend = False
                if incoming[0] == "0":
                    index += 1
                    can_Send = True
                    resend = False

        packet_loss_rate = packets_lost / packets_sent
        threshold = 0.8
        if packet_loss_rate >= threshold:
            display.scroll("Network Lost!!")
            resend = False
