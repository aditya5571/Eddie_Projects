from microbit import *
import radio

radio.on()
radio.config(group=3)

data = "01001111"  # Binary for the English character "O"
sent_counter = 0


def numberOfCheckBits(m):
    for p in range(m):
        if 2 ** p >= m + p + 1:
            return p


def reverseString(s):
    str = ""
    for i in s:
        str = i + str
    return str


def positionOfRedundantBits(incoming, p):
    m = len(data)
    power = 0
    slot = ""
    k = 1

    # If position is power of 2 then insert "0"
    # Else append the actual data to the slots
    for i in range(1, m + p + 1):
        if i == 2 ** power:
            slot += "0"
            power += 1
        else:
            slot += incoming[len(incoming) - k]
            k += 1
    reversed = reverseString(slot)
    return reversed


def calculateParityBits(array, p):
    n = len(array)

    for i in range(p):
        value = 0
        for j in range(1, n + 1):
            if j & (2 ** i) == (2 ** i):
                value = value ^ int(array[-1 * j])

        array = array[: n - (2 ** i)] + str(value) + array[n - (2 ** i) + 1 :]
    return array

# detailed explanation in the pdf document
m = len(data)
p = numberOfCheckBits(m)

array = positionOfRedundantBits(data, p)

array = calculateParityBits(array, p)

while True:
    while sent_counter <= 20:
        if button_a.is_pressed():
            radio.send(str(array))
            sent_counter += 1

            display.show(Image("00009:00090:90900:09000:00000"))
            sleep(500)
            display.clear()
            sleep(300)
        if button_b.is_pressed():
            radio.send(array[1:] + "1")
            sent_counter += 1

            display.show(Image("00009:00090:90900:09000:00000"))
            sleep(500)
            display.clear()
            sleep(300)
