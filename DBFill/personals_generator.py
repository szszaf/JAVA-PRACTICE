from random import randint, choice
from functools import reduce
from faker import Faker
from invoices_orms import (
    Payer, Individual, Company, PayerAddress, Address, Item, Invoice, InvoiceItem, Unit)
from datetime import datetime

POLAND_LOCAL = 'pl_PL'

UNITS = {
    'sztuka': Unit(name='sztuka'),
    'm2': Unit(name='m2'),
    'kg': Unit(name='kg'),
    'komplet': Unit(name='komplet'),
    'l': Unit(name='l'),
    'zestaw': Unit(name='zestaw'),
    'm3': Unit(name='m3'),
    'opakowanie': Unit(name='opakowanie')
}

PRODUCTS_SERVICES_WITH_UNITS = [
    Item(name="głośnik", unit=UNITS['sztuka']),
    Item(name="laptop", unit=UNITS['sztuka']),
    Item(name='podzespoły komputerowe', unit=UNITS['sztuka']),
    Item(name='materiał izolacyjny', unit=UNITS['m2']),
    Item(name='gips', unit=UNITS['kg']),
    Item(name='pędzle', unit=UNITS['komplet']),
    Item(name='drukarka', unit=UNITS['sztuka']),
    Item(name='monitor', unit=UNITS['sztuka']),
    Item(name='klawiatura', unit=UNITS['sztuka']),
    Item(name='mysz', unit=UNITS['sztuka']),
    Item(name='kabel HDMI', unit=UNITS['sztuka']),
    Item(name='kabel USB', unit=UNITS['sztuka']),
    Item(name='stół roboczy', unit=UNITS['sztuka']),
    Item(name='krzesło biurowe', unit=UNITS['sztuka']),
    Item(name='uchwyt do monitora', unit=UNITS['sztuka']),
    Item(name='system operacyjny', unit=UNITS['sztuka']),
    Item(name='oprogramowanie biurowe', unit=UNITS['sztuka']),
    Item(name='papier A4', unit=UNITS['opakowanie']),
    Item(name='toner', unit=UNITS['sztuka']),
    Item(name='tablica suchościeralna', unit=UNITS['sztuka']),
    Item(name='marker do tablicy', unit=UNITS['sztuka']),
    Item(name='notatnik', unit=UNITS['sztuka']),
    Item(name='długopis', unit=UNITS['sztuka']),
    Item(name='papier toaletowy', unit=UNITS['opakowanie']),
    Item(name='mydło w płynie', unit=UNITS['l']),
    Item(name='ręczniki papierowe', unit=UNITS['opakowanie']),
    Item(name='kosz na śmieci', unit=UNITS['sztuka']),
    Item(name='płyn do czyszczenia', unit=UNITS['l']),
    Item(name='ścierki', unit=UNITS['komplet']),
    Item(name='wiertarka', unit=UNITS['sztuka']),
    Item(name='wkrętarka', unit=UNITS['sztuka']),
    Item(name='młotek', unit=UNITS['sztuka']),
    Item(name='śrubokręt', unit=UNITS['sztuka']),
    Item(name='klucz francuski', unit=UNITS['sztuka']),
    Item(name='miara', unit=UNITS['sztuka']),
    Item(name='zestaw narzędzi', unit=UNITS['zestaw']),
    Item(name='przedłużacz', unit=UNITS['sztuka']),
    Item(name='lampa biurowa', unit=UNITS['sztuka']),
    Item(name='wentylator', unit=UNITS['sztuka']),
    Item(name='kawa', unit=UNITS['opakowanie']),
    Item(name='herbata', unit=UNITS['opakowanie']),
    Item(name='cukier', unit=UNITS['kg']),
    Item(name='mleko', unit=UNITS['l']),
    Item(name='kubek', unit=UNITS['sztuka']),
    Item(name='talerz', unit=UNITS['sztuka']),
    Item(name='sztućce', unit=UNITS['komplet']),
    Item(name='opakowanie na lunch', unit=UNITS['sztuka']),
    Item(name='organizator biurowy', unit=UNITS['sztuka']),
    Item(name='etykiety', unit=UNITS['opakowanie']),
    Item(name='koszulki na dokumenty', unit=UNITS['opakowanie']),
    Item(name='bindownica', unit=UNITS['sztuka']),
    Item(name='woda mineralna', unit=UNITS['l']),
    Item(name='serwetki', unit=UNITS['opakowanie']),
    Item(name='zestaw kluczy', unit=UNITS['zestaw']),
    Item(name='papier do drukarki', unit=UNITS['opakowanie']),
    Item(name='uchwyt do telefonu', unit=UNITS['sztuka']),
    Item(name='płyta CD', unit=UNITS['sztuka']),
    Item(name='podkładka pod mysz', unit=UNITS['sztuka']),
    Item(name='klamki', unit=UNITS['komplet']),
    Item(name='słuchawki', unit=UNITS['sztuka']),
    Item(name='materiał budowlany', unit=UNITS['m2']),
    Item(name='drabina', unit=UNITS['sztuka']),
    Item(name='farba', unit=UNITS['l']),
    Item(name='pianka izolacyjna', unit=UNITS['m3'])
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
      #pesel_pool = set()
      nip_pool = set()
      for _ in range(count):
        #is_pesel_generated = False
        is_nip_generated = False
        #pesel = ''

        # while not is_pesel_generated:
        #     pesel = self.generate_pesel()
        #     if pesel not in pesel_pool:
        #        is_pesel_generated = True
        #        pesel_pool.add(pesel)
         
        while not is_nip_generated:
            nip = self.generate_nip()
            if nip not in nip_pool:
               is_nip_generated = True
               nip_pool.add(nip)
               
        payers.append(Payer(
                            nip=nip, 
                            vat_ue=self.generate_vat_ue('PL', nip),
                            payeraddress = [self.generate_address() for _ in range(1, randint(2, 5))]))  
      return payers
    
    def generate_individual(self, payer):
        first_name = None
        last_name = None

        if randint(0,1):
            first_name = self.fake.first_name_female()
            last_name = self.fake.last_name_female()
        else:
            first_name = self.fake.first_name_male()
            last_name = self.fake.last_name_male()

        return Individual(pesel = self.generate_pesel(),
                        name=first_name, 
                        surname=last_name, payer=payer)
    
    def generate_company(self, payer):
        return Company(name=self.fake.company(), payer=payer)
    
    def generate_address(self, still_owns_probability=0.7):

        owns_from = self.random_date()
        owns_to = (None if self.generate_probability() <= still_owns_probability 
                   else self.random_date(owns_from))

        return PayerAddress(
            address= Address(
                        country = 'Polska',
                        city = self.fake.city(),
                        zip_code = self.fake.zipcode(),
                        street = self.fake.street_name(),
                        building_number = str(randint(1,100)),
                        local_number = str(randint(1, 20))
                        ),
                        owns_from = owns_from,
                        owns_to = owns_to)

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
      
    def generate_items_list(self):
        items = []
        total_netto = 0
        total_brutto = 0
        total_vat = 0
        for i in range(randint(1,10)):
            vat = choice(VAT_RATES)
            netto = randint(500, 10000000) / 100
            vat_sum = netto * vat
            brutto = netto + vat_sum

            total_netto += netto
            total_brutto += brutto
            total_vat += vat_sum

            item = self.generate_item()

            invoiceitem = InvoiceItem(
                position=i + 1,
                netto_sum=netto,
                brutto_sum=brutto,
                vat_sum=vat_sum,
                vat_rate=vat,
                amount=randint(1,20),
                items=item 
            )

            items.append(invoiceitem)

        return items, total_netto, total_brutto, total_vat
    
    def generate_invoice(self, buyer, seller):
        items, total_netto, total_brutto, total_vat = self.generate_items_list()

        date_of_issue = self.random_date()
        due_date = self.random_date(date_of_issue)

        is_paid = randint(0,1)

        invoice = Invoice(tag = self.generate_invoice_tag(date_of_issue),
                        date_of_issue = date_of_issue,
                        total_netto = total_netto,
                        total_brutto = total_brutto,
                        total_vat = total_vat,
                        due_date = due_date,
                        is_paid = is_paid,
                        buyer = buyer,
                        seller = seller,
                        invoiceitem=items)

        return invoice
    
    def generate_invoice_tag(self, date_of_issue):
        return (str(date_of_issue.year) + '/' + 
                str(date_of_issue.month) + '/' + 
                str(date_of_issue.day) + '/' + 
                str(randint(1000,9999)))
    
    def random_date(self, start=datetime(2000, 2, 15), end=datetime(2024, 5, 5)):
        start_timestamp = int(start.timestamp())
        end_timestamp = int(end.timestamp())
        random_timestamp = randint(start_timestamp, end_timestamp)
        return datetime.fromtimestamp(random_timestamp)

    def generate_item(self):
        return choice(PRODUCTS_SERVICES_WITH_UNITS)
    
    def generate_probability(self):
        return randint(0,10) / 10