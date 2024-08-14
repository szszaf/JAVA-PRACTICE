CREATE TABLE Payers(
	id SERIAL PRIMARY KEY,
	pesel VARCHAR(11) UNIQUE,
	nip VARCHAR(10) UNIQUE,
	vat_ue VARCHAR(15) UNIQUE
);

CREATE TABLE Individuals(
	id INT PRIMARY KEY,
	"name" VARCHAR(50) NOT NULL,
	surname VARCHAR(80) NOT NULL
);

CREATE TABLE Companies(
	id INT PRIMARY KEY,
	"name" VARCHAR(80) NOT NULL
);

CREATE TABLE Addresses(
	id SERIAL PRIMARY KEY,
	country VARCHAR(50) NOT NULL,
	city VARCHAR(50) NOT NULL,
	zip_code VARCHAR(10),
	street VARCHAR(30),
	building_number VARCHAR(8),
	local_number VARCHAR(8)
);


CREATE TABLE PayersAddresses(
	id SERIAL PRIMARY KEY,
	payer_id INT,
	address_id INT,
    owns_from DATE NOT NULL,
    owns_to DATE
);

CREATE TABLE Invoices(
	id SERIAL PRIMARY KEY,
	date_of_issue DATE NOT NULL,
	total_netto NUMERIC(10,2),
	total_brutto NUMERIC(10,2),
    total_vat NUMERIC(10,2),
	due_date DATE,
	buyer_id INT,
	seller_id INT
);

CREATE TABLE Items(
	id SERIAL PRIMARY KEY,
    "name" VARCHAR(80) UNIQUE,
	unit_id INT
);

CREATE TABLE InvoicesItems(
	id SERIAL PRIMARY KEY,
    position SMALLINT NOT NULL,
    netto_sum NUMERIC(10,2) NOT NULL,
    brutto_sum NUMERIC(10,2) NOT NULL,
    vat_sum NUMERIC(10,2) NOT NULL,
    vat_rate NUMERIC(5,2) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
	invoice_id INT,
	item_id INT
);

CREATE TABLE Units(
	id SERIAL PRIMARY KEY,
	"name" VARCHAR(20) UNIQUE
);


ALTER TABLE Individuals
ADD CONSTRAINT FK_Payer FOREIGN KEY(id) REFERENCES Payers(id);

ALTER TABLE Companies
ADD CONSTRAINT FK_Payer FOREIGN KEY(id) REFERENCES Payers(id);

ALTER TABLE PayersAddresses
ADD CONSTRAINT FK_Payer FOREIGN KEY(payer_id) REFERENCES Payers(id);

ALTER TABLE PayersAddresses
ADD CONSTRAINT FK_AddressID FOREIGN KEY(address_id) REFERENCES Addresses(id);

ALTER TABLE Invoices
ADD CONSTRAINT FK_Buyer FOREIGN KEY(buyer_id) REFERENCES Payers(id);

ALTER TABLE Invoices
ADD CONSTRAINT FK_Seller FOREIGN KEY(seller_id) REFERENCES Payers(id);

ALTER TABLE InvoicesItems
ADD CONSTRAINT FK_Item FOREIGN KEY(item_id) REFERENCES Items(id);

ALTER TABLE InvoicesItems
ADD CONSTRAINT FK_Invoice FOREIGN KEY(invoice_id) REFERENCES Invoices(id);

ALTER TABLE Items
ADD CONSTRAINT FK_Unit FOREIGN KEY(unit_id) REFERENCES Units(id);

