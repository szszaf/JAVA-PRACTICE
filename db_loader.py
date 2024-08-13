from sqlalchemy import create_engine
from sqlalchemy.orm import declarative_base, sessionmaker
from personals_generator import PersonalsGenerator
from random import randint, sample
from sys import stdout
from time import sleep

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

      for payer, i in zip(payers_pool, range(payers_count)):
         individual_or_company = randint(0,1)

         subject = (self.personal_generator.generate_company(payer) if individual_or_company 
                    else self.personal_generator.generate_individual(payer))

         session.add(subject)

         if (i//payers_count) % 100 == 0:
            print(f"Generated {int((i/payers_count) * 100)}% of payers.")
            sleep(0.2)
            clear_last_terminal_line()

      print('Payers have been generated.')

      for i in range(invoices_count):
         payers_indexes = sample(range(0, payers_count), 2)

         invoice = self.personal_generator.generate_invoice(
            payers_pool[payers_indexes[0]],
            payers_pool[payers_indexes[1]])
            
         session.add(invoice)

         if (i//invoices_count) % 100 == 0:   
            print(f"Generated {int((i/invoices_count) * 100)}% of invoices.")
            sleep(0.2)
            clear_last_terminal_line()

      print('Invoices have been generated.')

      print('saving to db...')
      session.commit()
      session.close()

def clear_last_terminal_line():
    stdout.write('\x1b[1A')  
    stdout.write('\x1b[2K')  
    stdout.flush()

if __name__ == '__main__':
   ig = InvoiceGenerator()
   ig.generate(payers_count=1000, invoices_count=10_000)