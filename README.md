# ChâTop

<div>
<img alt="Static Badge" src="https://img.shields.io/badge/Java-%23ff7b0c">
<img alt="Static Badge" src="https://img.shields.io/badge/Spring_Boot-5bd84c">
<img alt="Static Badge" src="https://img.shields.io/badge/Swagger-8df14f">
</div>

For this project, I worked as a back-end developer for ChâTop, a company that rents out properties in tourist areas.
The project took the form of a portal to connect prospective tenants and owners for seasonal rentals, initially on the Basque coast and later throughout France.
There was already a fairly basic Angular application running on data mocked up using Mockoon.
My job was to implement the backend. I had the Mockoon environment, a Postman collection to test the API and a database schema.

Users (tenants or owners) needed to be able to authenticate themselves to access the rental ads. And be able to post ads or messages to other users to book their rentals.

The API also needed to be documented using Swagger.

### Api Documentation

Given the api is running, can be found here :
 http://localhost:3001/swagger-ui/index.html#/

### Setup

To initialize the database :
- `CREATE DATABASE chatop;`
- `USE chatop;`
- `create table images (
  id bigint auto_increment primary key,
  bytes mediumblob null,
  name varchar(255) null,
  type varchar(255) null
  );`
- `create table users (
  id int auto_increment primary key,
  created_at date null,
  email varchar(255) not null,
  name varchar(255) not null,
  password varchar(255) not null,
  updated_at date null
  );`
- `create table rentals (
  id int auto_increment primary key,
  created_at date null,
  description varchar(2000) not null,
  name varchar(255) not null,
  picture varchar(255) not null,
  price int not null,
  surface int not null,
  updated_at date null,
  owner_id int not null,
  constraint UKgev8r1mct7oy6h9x0oi7nvic3 unique (name),
  constraint FKf462yhxa9vd3m2qdmcoixg1fv foreign key (owner_id) references users (id)
  );`
- `create table messages (
  id int auto_increment primary key,
  created_at date null,
  message varchar(2000) null,
  updated_at date null,
  rental_id int null,
  user_id int null,
  constraint FK3ce1i9w1rtics9wjwj8y5y3md foreign key (rental_id) references rentals (id),
  constraint FKpsmh6clh3csorw43eaodlqvkn foreign key (user_id) references users (id)
  );`

Don't forget to check the 'application.properties' file to define the correct user.

To launch the project locally (Front – https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring) : 
- `npm install`
- `npm run start`

To launch the project locally (Back) : 
- `mvn install`
- `mvn exec:java -D exec.mainClass="com.openclassrooms.chatop.OcChaTopApplication"`

