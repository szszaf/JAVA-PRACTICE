import random
from functools import reduce

class PersonalsGenerator:

    def generate_pesel(self):
        year = random.randint(1940, 2004)
        month = random.randint(1, 12)
        day = random.randint(1, 28)

        year_last_two_digits = year % 100
        month = month + (20 if year >= 2000 else 0)

        pesel = f"{year_last_two_digits}{month}{day}"
        count_of_remaining_digits = 5

        return str(reduce(lambda acc, x: acc + str(x), 
                      [random.randint(0,9) for _ in range(count_of_remaining_digits)], pesel))



if __name__ == '__main__':
    print('hello')
    pg = PersonalsGenerator()
    print(pg.generate_pesel())