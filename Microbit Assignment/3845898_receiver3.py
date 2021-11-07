from microbit import *
import radio

radio.on()
radio.config(group=3)

x = 0
y = 0


def numberOfCheckBits(m):
    for p in range(m):
        if 2 ** p >= m + p + 1:
            return p


def detectError(array, p):
    n = len(array)
    result = 0

    for i in range(p):
        value = 0
        for j in range(1, n + 1):
            if j & (2 ** i) == (2 ** i):
                value = value ^ int(array[-1 * j])

        result = result + value * (10 ** i)
    return int(str(result), 2)  # returning a decimal


while True:
    incoming = radio.receive()

    if incoming:
        m = 8
        p = numberOfCheckBits(m)

        correction = detectError(incoming, p)

        if correction:
            display.scroll("Error position: " + str(correction))
            sleep(300)
        else:
            sleep(300)
            display.scroll("No error")
            sleep(100)
            display.show(Image.HAPPY)
            sleep(700)
            display.clear()

        display.set_pixel(x, y, 9)
        x += 1
        if x == 5:
            x = 0
            y += 1
