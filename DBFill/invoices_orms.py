from sqlalchemy.orm import declarative_base, relationship
from sqlalchemy import Column, Integer, String, Date, Numeric, SmallInteger, ForeignKey, Boolean

BASE = declarative_base()

class Payer(BASE):
   __tablename__ = 'payers'
   
   id = Column(Integer, primary_key=True, autoincrement=True)
   nip = Column(String(10), unique=True)
   vat_ue = Column(String(15), unique=True)

   individual = relationship("Individual", uselist=False, back_populates="payer")
   company = relationship("Company", uselist=False, back_populates="payer")
   payeraddress = relationship('PayerAddress', back_populates='payer')
   invoices_as_buyer = relationship("Invoice", foreign_keys='Invoice.buyer_id', back_populates='buyer')
   invoices_as_seller = relationship("Invoice", foreign_keys='Invoice.seller_id', back_populates='seller')

class Individual(BASE):
   __tablename__ = 'individuals'

   id = Column(Integer, ForeignKey('payers.id'), primary_key=True)
   pesel = Column(String(11))
   name = Column(String(20), nullable=False)
   surname = Column(String(50), nullable=False)

   payer = relationship("Payer", uselist=False, back_populates="individual")

class Company(BASE):
   __tablename__ = 'companies'

   id = Column(Integer, ForeignKey('payers.id'), primary_key=True)
   name = Column(String(80), nullable=False)

   payer = relationship("Payer", uselist=False, back_populates="company")


class Address(BASE):
   __tablename__ = 'addresses'
   
   id = Column(Integer, primary_key=True)
   country = Column(String(50), nullable=False)
   city = Column(String(50), nullable=False)
   zip_code = Column(String(10))
   street = Column(String(30))
   building_number = Column(Integer)
   local_number = Column(Integer)

   payeraddress = relationship('PayerAddress', back_populates='address')

class PayerAddress(BASE):
   __tablename__ = 'payersaddresses'

   id = Column(Integer, primary_key=True)
   owns_from = Column(Date)
   owns_to = Column(Date)
   payer_id = Column(Integer, ForeignKey('payers.id'))
   address_id = Column(Integer, ForeignKey('addresses.id'))

   payer = relationship('Payer', back_populates='payeraddress')
   address = relationship('Address', back_populates='payeraddress')

class Invoice(BASE):
   __tablename__ = 'invoices'

   id = Column(Integer, primary_key=True)
   tag = Column(String, nullable = False)
   date_of_issue = Column(Date, nullable=False)
   total_netto = Column(Numeric(precision=10, scale=2), nullable=False)
   total_brutto = Column(Numeric(precision=10, scale=2), nullable=False)
   total_vat = Column(Numeric(precision=10, scale=2), nullable=False)
   due_date = Column(Date)
   is_paid = Column(Boolean)
   buyer_id = Column(Integer, ForeignKey('payers.id'))
   seller_id = Column(Integer, ForeignKey('payers.id'))

   buyer = relationship('Payer', uselist=False, 
                        foreign_keys=[buyer_id], back_populates='invoices_as_buyer')
   seller = relationship('Payer', uselist=False, foreign_keys=[seller_id], 
                         back_populates='invoices_as_seller')
   invoiceitem = relationship('InvoiceItem', back_populates='invoices')

class Item(BASE):
   __tablename__ = 'items'

   id = Column(Integer, primary_key=True)
   name = Column(String(80))
   unit_id = Column(Integer, ForeignKey('units.id'))

   unit = relationship("Unit", uselist=False, back_populates='items')
   invoiceitem = relationship("InvoiceItem", back_populates='items')

   def __repr__(self):
      return self.name
   
class Unit(BASE):
   __tablename__ = 'units'

   id = Column(Integer, primary_key=True)
   name = Column(String(20))

   items = relationship("Item", back_populates='unit')

class InvoiceItem(BASE):
   __tablename__ = 'invoicesitems'

   id = Column(Integer, primary_key=True)
   position = Column(SmallInteger)
   netto_sum = Column(Numeric(precision=10, scale=2), nullable=False)
   brutto_sum = Column(Numeric(precision=10, scale=2), nullable=False)
   vat_sum = Column(Numeric(precision=10, scale=2), nullable=False)
   vat_rate = Column(Numeric(precision=10, scale=2), nullable=False)
   amount = Column(Numeric(precision=10, scale=2), nullable=False)
   invoice_id = Column(Integer, ForeignKey('invoices.id'))
   item_id = Column(Integer, ForeignKey('items.id'))

   items = relationship('Item', back_populates='invoiceitem')
   invoices = relationship('Invoice', back_populates='invoiceitem')
