

/*Query to create Customer table in Pizza Shop database */
create table Customer ( CustomerId integer not null auto_increment,
FirstName varchar(60) not null, LastName varchar(60) not null, Address varchar(80) not null, 
EmailAddress varchar(100) not null, ContactNumber varchar(10), Pincode integer(6) not null, 
Status integer(1), constraint pk_customer primary key (CustomerId) );

/*Query to create Order table    */
CREATE TABLE `Order`(
    OrderID INTEGER NOT NULL AUTO_INCREMENT,
    CustomerId int,
    ItemId int not null,
    OrderDetails VARCHAR(250) NOT NULL,
    OrderType VARCHAR(50) NOT NULL,
    OrderDate timestamp DEFAULT now() NOT NULL,
    OrderQuantity int NOT NULL,
	OrderTotal numeric(5,2) not null ,
    PRIMARY KEY(OrderID),
    constraint fk_Order FOREIGN KEY(CustomerId) 
	REFERENCES Customer(CustomerId),
	constraint fk_itemid foreign key(ItemId)
	references Items(ItemId)
);


POSTGRES QUERIES 
------------------------------------
create table Customer ( 
	CustomerId SERIAL not null ,
FirstName varchar(60) not null,
	LastName varchar(60) not null,
	Address varchar(80) not null, 
EmailAddress varchar(100) not null,
	ContactNumber varchar(10), 
	Pincode int not null, 	
Status int, 
	constraint pk_customer primary key (CustomerId) );
	
	
	
	
	
	CREATE TABLE OrderPizzza(
    OrderID SERIAL NOT NULL ,
    CustomerId int,
    ItemId int not null,
    OrderDetails VARCHAR(250) NOT NULL,
    OrderType VARCHAR(50) NOT NULL,
    OrderDate timestamp DEFAULT now() NOT NULL,
    OrderQuantity int NOT NULL,
	OrderTotal real not null ,
    PRIMARY KEY(OrderID),
    constraint fk_Order FOREIGN KEY(CustomerId) 
	REFERENCES Customer(CustomerId),
	constraint fk_itemid foreign key(ItemId)
	references Items(ItemId)
);


create table Items
( ItemId serial not null ,
 ItemDescription varchar(250) not null,
 Quantity int not null,
 LastModified timestamp default now(),
 price real not null,
 CONSTRAINT pk_Items PRIMARY KEY(ItemId)
  );