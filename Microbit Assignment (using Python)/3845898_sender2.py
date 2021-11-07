from microbit import *
import radio

radio.on()
radio.config(group=2)

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
    "Twenty one",
]

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

string_sender = "AD"
string_receiver = "MS"
header = string_sender + string_receiver

index = 0
packets_lost = 0
packets_sent = 0
start_timer = 0
first_time = True
resend = False
ready_to_receive = False

lower = 0
seqN = 0
window_size = 4


def tick():
    display.show(Image("00009:00090:90900:09000:00000"))
    sleep(500)
    display.clear()
    sleep(500)


while True:
    if button_a.is_pressed() and first_time:
        while seqN < window_size:
            packet = seqNum[seqN] + header + stringlist[index]
            radio.send(packet)
            start_timer = running_time()
            packets_sent += 1
            seqN += 1
            index += 1
            tick()

        ready_to_receive = True
        sleep(800)
        first_time = False

    while ready_to_receive:
        incoming = radio.receive()

        if incoming and incoming[0:2] == seqNum[lower + 1]:
            packet = packet[2:]
            next_packets = seqNum[seqN] + packet
            radio.send(next_packets)
            packets_sent += 1
            lower += 1
            seqN += 1
            tick()
        else:
            sleep(2000)
            stop_timer = running_time()
            display.scroll("TIMEOUT")
            resend = True

        if resend:
            tempSeqNo = lower
            while tempSeqNo <= seqN:
                temp_packet = seqNum[seqN] + packet[1:]
                radio.send(temp_packet)
                start_timer = running_time()
                packets_sent += 1
                packets_lost += 1
                tempSeqNo += 1
                tick()
            display.scroll("Lost: " + str(packets_lost))

            packet_loss_rate = packets_lost / packets_sent
            threshold = 0.5
            if packet_loss_rate >= threshold:
                display.scroll("Network Lost!!")
                ready_to_receive = False

