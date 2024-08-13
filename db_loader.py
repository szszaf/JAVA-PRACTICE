from sqlalchemy import create_engine
from sqlalchemy.orm import declarative_base, sessionmaker
from personals_generator import PersonalsGenerator
from random import randint, sample

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

   def generate(self, payers_count=100, invoices_count=1000):
      BASE.metadata.create_all(self.engine)
      Session = sessionmaker(bind=self.engine)
      session = Session()


      payers_pool = self.personal_generator.generate_payers(payers_count)

      for payer in payers_pool:
         individual_or_company = randint(0,1)

         subject = (self.personal_generator.generate_company(payer) if individual_or_company 
                    else self.personal_generator.generate_individual(payer))

         session.add(subject)

      for _ in range(invoices_count):
         payers_indexes = sample(range(0, payers_count), 2)

         invoice = self.personal_generator.generate_invoice(
            payers_pool[payers_indexes[0]],
            payers_pool[payers_indexes[1]])
         
         session.add(invoice)

      session.commit()
      session.close()

if __name__ == '__main__':
   ig = InvoiceGenerator()
   ig.generate(payers_count=100_000, invoices_count=1_000_000)