from faker import Faker
from sqlalchemy import create_engine
from sqlalchemy.orm import declarative_base, sessionmaker
from personalsGenerator import PersonalsGenerator
from invoices_orms import Item, Unit, Invoice, Payer, Address
from random import randint
from datetime import datetime

BASE = declarative_base()
DB_USERNAME = 'postgres'
DB_PASSWORD = 'password'
DB_ADDRESS = 'localhost:5432'
DB_NAME = 'InvoicesDB'

class InvoiceGenerator:
   def __init__(self):
      self.engine = create_engine(
         'postgresql://' +
         DB_USERNAME +
         ':' + 
         DB_PASSWORD + 
         '@' + 
         DB_ADDRESS + 
         '/' + 
         DB_NAME)
      self.personal_generator = PersonalsGenerator()

   def generate(self):
      payers = self.personal_generator.generate_payers(2)

      BASE.metadata.create_all(self.engine)
      Session = sessionmaker(bind=self.engine)
      session = Session()

      # session.add(
      #    self.personal_generator.generate_invoice(
      #       payers[0], payers[1]
      #    )
      # )


      session.add(
         Invoice(date_of_issue = datetime(2024, 2, 15),
                       total_netto = 123,
                       total_brutto = 123,
                       total_vat = 123,
                       due_date = datetime(2024, 3, 6),
                       buyer = Payer(pesel = "1234567891", nip = "123456781", vat_ue = 'abcd1'),
                       items = [Item(name="Papier", unit = Unit(name = "szt"))]
      ))
      session.commit()

if __name__ == '__main__':
   ig = InvoiceGenerator()
   ig.generate()