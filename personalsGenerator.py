from random import randint, choice
from functools import reduce
from faker import Faker
from invoices_orms import Payer, Individual, Company, Address, Item, Invoice
from datetime import datetime

POLAND_LOCAL = 'pl_PL'
UNITS = [
    "szt.",  
    "kg",    
    "m",     
    "l",     
    "opak.", 
    "karton",
    "m2",    
    "godz.", 
    "kpl.",  
    "t"  
]

PRODUCTS_SERVICES = [
    "Laptop", "Smartfon", "Tablet", "Monitor", "Drukarka",
    "Router", "Kamera", "Klawiatura", "Mysz", "Głośniki",
    "Serwis IT", "Konsultacja Biznesowa", "Hosting", "Szkolenie",
    "Projektowanie Stron WWW", "Zarządzanie Kampanią Marketingową",
    "Obsługa Klienta", "Audyt Bezpieczeństwa", "Księgowość", "Doradztwo Finansowe"
]

VAT_RATES = [0.23, 0.08, 0.05]

NIP_DIGIT_NUMBER = 10
PESEL_DIGIT_NUMBER = 11
RANDOM_DIGIT_NUMBER_IN_PESEL = 5

class PersonalsGenerator:
    def __init__(self, local=POLAND_LOCAL):
        self.fake = Faker(local)

    def generate_payers(self, count=10_000):
      payers = []
      pesel_pool = set()
      nip_pool = set()
      for _ in range(count):
        is_pesel_generated = False
        is_nip_generated = False
        pesel = ''

        while not is_pesel_generated:
            pesel = self.generate_pesel()
            if pesel not in pesel_pool:
               is_pesel_generated = True
               pesel_pool.add(pesel)
         
        while not is_nip_generated:
            nip = self.generate_nip()
            if nip not in nip_pool:
               is_nip_generated = True
               nip_pool.add(nip)
               
        payers.append(Payer(pesel=pesel, 
                            nip=nip, 
                            vat_ue=self.generate_vat_ue('PL', nip),
                            addresses = [self.generate_address()]))  
      return payers
    
    def generate_individual(self, payer):
        return Individual(name=self.fake.name(), 
                          surname=self.fake.last_name(), payer=payer)
    
    def generate_company(self, payer):
        return Company(name=self.fake.company(), payer=payer)
    
    def generate_address(self):
        return Address(
            country = 'Polska',
            city = self.fake.city(),
            zip_code = self.fake.zipcode(),
            street = self.fake.street_name(),
            building_number = randint(1,100),
            local_number = randint(1, 20))

    def generate_pesel(self):
        year = randint(1940, 2004)
        month = randint(1, 12)
        day = randint(1, 28)

        day = '0' + str(day) if day < 10 else str(day)
        year_last_two_digits = '0' + str(year % 100) if (year % 100) < 10 else str(year % 100)
        month = month + (20 if year >= 2000 else 0)
        month = '0' + str(month) if len(str(month)) == 1 else month

        pesel = f"{year_last_two_digits}{month}{day}"

        return str(reduce(lambda acc, digit: acc + str(digit), 
                      [randint(0,9) 
                       for _ in range(RANDOM_DIGIT_NUMBER_IN_PESEL)], pesel))
    
    def generate_nip(self):
        return str(reduce(lambda acc, digit: acc + str(digit), [randint(0,9) 
                                                                for _ in range(NIP_DIGIT_NUMBER)],''))
    
    def generate_vat_ue(self, country_code, nip):
        return country_code + nip
    
    def generate_unit(self):
        return choice(UNITS)
    
    def generate_items_list(self):
        items = []
        total_netto = 0
        total_brutto = 0
        total_vat = 0
        for i in range(randint(1,10)):
            vat = choice(VAT_RATES)
            netto = randint(500, 10000000) / 100
            brutto = netto + netto * vat

            total_netto += netto
            total_brutto += brutto
            total_netto += total_vat

            items.append(Item(position = i,
                              netto_sum = netto,
                              brutto_sum = brutto,
                              vat_sum = vat * netto,
                              vat_rate = vat,
                              amount = randint(1,20),
                              unit = self.generate_unit()))
        return items, total_netto, total_brutto, total_vat
    
    def generate_invoice(self, buyer, seller):
        items, total_netto, total_brutto, total_vat = self.generate_items_list()
        date_of_issue = self.random_date()
        due_date = self.random_date(date_of_issue)
        return Invoice(date_of_issue = date_of_issue,
                       total_netto = total_netto,
                       total_brutto = total_brutto,
                       total_vat = total_vat,
                       due_date = due_date,
                       buyer = buyer,
                       seller = seller,
                       items = items)
    
    def random_date(self, start=datetime(2000, 2, 15), end=datetime(2024, 5, 5)):
        start_timestamp = int(start.timestamp())
        end_timestamp = int(end.timestamp())
        random_timestamp = randint(start_timestamp, end_timestamp)
        return datetime.fromtimestamp(random_timestamp).date()
