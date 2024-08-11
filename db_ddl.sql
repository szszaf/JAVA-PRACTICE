CREATE TABLE Payers(
	PK_PayerID SERIAL PRIMARY KEY,
	pesel VARCHAR(11) UNIQUE,
	nip VARCHAR(10) UNIQUE,
	vatUe VARCHAR(15) UNIQUE,
	email VARCHAR(50),
	phoneNumber VARCHAR(15)
);

CREATE TABLE Individuals(
	PK_IndividualID INT PRIMARY KEY,
	"name" VARCHAR(20) NOT NULL,
	surname VARCHAR(50) NOT NULL
);

CREATE TABLE Companies(
	PK_CompanyID INT PRIMARY KEY,
	"name" VARCHAR(80) NOT NULL
);

CREATE TABLE Addresses(
	PK_AddressID SERIAL PRIMARY KEY,
	country VARCHAR(50) NOT NULL,
	city VARCHAR(50) NOT NULL,
	zipCode VARCHAR(10),
	street VARCHAR(30),
	buildingNumber SMALLINT,
	localNumber SMALLINT
);


CREATE TABLE PayersAddresses(
	PK_PayerAddressID SERIAL PRIMARY KEY,
	FK_PayerID INT NOT NULL,
	FK_AddressID INT NOT NULL
);

CREATE TABLE Invoices(
	PK_InvoicesID SERIAL PRIMARY KEY,
	dateOfIssue DATE NOT NULL,
	totalNetto NUMERIC(10,2),
	totalBrutto NUMERIC(10,2),
	dueDate DATE,
	FK_PayerBuyer INT NOT NULL,
	FK_PayerSeller INT NOT NULL
);

CREATE TABLE Items(
	PK_ItemID SERIAL PRIMARY KEY,
	listID SMALLINT NOT NULL,
	nettoSum NUMERIC(10,2) NOT NULL,
	bruttoSum NUMERIC(10,2) NOT NULL,
	vatSum NUMERIC(10,2) NOT NULL,
	vatRate NUMERIC(5,2) NOT NULL,
	amount NUMERIC(10,2) NOT NULL,
	FK_UnitID INT NOT NULL
);

CREATE TABLE InvoicesItems(
	PK_InvoicesItemsID SERIAL PRIMARY KEY,
	FK_InvoiceID INT NOT NULL,
	FK_ItemID INT NOT NULL
);

CREATE TABLE Units(
	PK_ItemID SERIAL PRIMARY KEY,
	"name" VARCHAR(20)
);