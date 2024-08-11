from faker import Faker
from sqlalchemy import create_engine, Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship, sessionmaker
from sqlalchemy.ext.declarative import declarative_base

BASE = declarative_base()
POLISH_LOCALE = 'pl_PL'

class InvoiceGenerator:
   
    def __init__(self, seed=42):
      self.engine = create_engine(
         'postgresql://usr:pass@localhost:5432/sqlalchemy')
      self.fake = Faker(POLISH_LOCALE)

    def generate_payers(self):
      pass

class Payer(BASE):
   __tablename__ = 'Payers'
   
   PK_PayerID = Column(Integer, primary_key=True)
   pesel = Column(String(11))
   nip = Column(String(10))
   vatUe = Column(String(15))
   email = Column(String(50))
   phoneNumber = Column(String(15))


class Individual(BASE):
   __tablename__ = 'Individuals'

   PK_IndividualID = Column(Integer, 
                            ForeignKey('payers.PK_PayerID'), primary_key=True)
   name = Column(String(20), nullable=False)
   surname = Column(String(50), nullable=False)

class Company(BASE):
   __tablename__ = 'Companies'

   PK_CompanyID = Column(Integer, 
                         ForeignKey('payers.PK_PayerID'), primary_key=True)
   name = Column(String(80), nullable=False)


class Address(BASE):
   __tablename__ = 'Addresses'
   
   PK_AddressID = Column(Integer, primary_key=True)
   country = Column(String(50), nullable=False)
   city = Column(String(50), nullable=False)
   zipCode = Column(String(10))
   street = Column(String(30))
   buildingNumber = Column(Integer)
   localNumber = Column(Integer)
   
class PayerAddress(BASE):
   __tablename___ = 'PayersAddresses'

   PK_PayerAddressID = Column(Integer, primary_key=True)
   FK_PayerID = Column(Integer, ForeignKey('payers.PK_PayerID'))
   FK_AddressID = Column(Integer, ForeignKey('addresses.PK_AddressID'))


if __name__ == '__main__':
   pass