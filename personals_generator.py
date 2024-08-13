from random import randint, choice
from functools import reduce
from faker import Faker
from invoices_orms import Payer, Individual, Company, Address, Item, Invoice, InvoiceItem, Unit
from datetime import datetime

POLAND_LOCAL = 'pl_PL'

PRODUCTS_SERVICES_WITH_UNITS = {
    'Laptop': 'sztuka',               
    'Smartfon': 'sztuka',             
    'Telewizor': 'sztuka',            
    'Monitor': 'sztuka',              
    'Kamera': 'sztuka',               
    'Tablet': 'sztuka',               
    'Drukarka': 'sztuka',             
    'Wynajem samochodu': 'sztuka',    
    'Farba do malowania': 'kg',       
    'Materiał budowlany': 'm2',       
    'Gips': 'kg',                     
    'Papier': 'kg',                   
    'Wynajem sprzętu': 'dzień',       
    'Usługa konserwacji': 'sztuka',   
    'Konsultacja': 'sztuka',          
    'Obraz': 'sztuka',                
    'Rama do obrazu': 'sztuka',       
    'Meble biurowe': 'sztuka',        
    'Oprogramowanie': 'sztuka',       
    'Karta podarunkowa': 'sztuka',    
    'Ubrania robocze': 'sztuka',      
    'Narzędzia ręczne': 'sztuka',     
    'Podzespoły komputerowe': 'sztuka',
    'Akcesoria do telefonów': 'sztuka',
    'Materiał izolacyjny': 'm2',      
    'Chemikalia': 'kg',               
    'Systemy alarmowe': 'sztuka',     
    'Głośniki': 'sztuka',             
    'Hurtownia narzędzi': 'sztuka',   
    'Zestawy komputerowe': 'sztuka',  
    'Części zamienne': 'sztuka'       
}

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
        full_name = self.fake.name()
        first_name = full_name.split(" ")[0]
        last_name = full_name.split(" ")[1]

        return Individual(name=first_name, 
                          surname=last_name, payer=payer)
    
    def generate_company(self, payer):
        return Company(name=self.fake.company(), payer=payer)
    
    def generate_address(self):
        return Address(
            country = 'Polska',
            city = self.fake.city(),
            zip_code = self.fake.zipcode(),
            street = self.fake.street_name(),
            building_number = str(randint(1,100)),
            local_number = str(randint(1, 20)))

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
        return Unit(name=choice(list(PRODUCTS_SERVICES_WITH_UNITS.values())))
    
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

            item = Item(
                name=self.generate_product_service(),
                unit=self.generate_unit()
            )

            invoiceitem = InvoiceItem(
                position=i,
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

        invoice = Invoice(date_of_issue = date_of_issue,
                       total_netto = total_netto,
                       total_brutto = total_brutto,
                       total_vat = total_vat,
                       due_date = due_date,
                       buyer = buyer,
                       seller = seller,
                        invoiceitem=items)
        

        return invoice
    
    def random_date(self, start=datetime(2000, 2, 15), end=datetime(2024, 5, 5)):
        start_timestamp = int(start.timestamp())
        end_timestamp = int(end.timestamp())
        random_timestamp = randint(start_timestamp, end_timestamp)
        return datetime.fromtimestamp(random_timestamp)
    
    def generate_product_service(self):
        return choice(list(PRODUCTS_SERVICES_WITH_UNITS.keys()))

