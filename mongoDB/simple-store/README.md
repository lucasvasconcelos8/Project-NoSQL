MongoDB Simple Store
=============

Implementation of system "simple-store" using MongoDB.
The Mongo used in this implementation was MongoDB 3.2 .

This implementation has 3 main parts:
- [MongoClient](https://docs.mongodb.com/getting-started/java/client/) , The client  in Java for MongoDB
- Simple-Store dataset
- Interface (Under Construction!)

In the case, you don't familiar with MongoDB(yet!) and want a brief tutorial and explanation of database, read this [tutorial](https://docs.google.com/presentation/d/1QaBObc0nQNZzymat9hpAtiAj8qN46Peaj4bgKgf6g4o/edit?usp=sharing) made for me!
	

## MongoCLient

The mongo-driver is a client in Java for MongoDB and it's pretty simple with easy tutorial on github. Also MongoDB driver have maven dependency which brings facilities to project with him.


## Simple-Store dataset

This implementation use a very simple dataset, because the purpose is to show a minnimum example using an application Java with MongoDB.
To help you understand the dataset, there is a description:


The documents in MongoDB fit well with the structure of dataset, and make easy storage many process any time you want.

An example of how mongoDB (a document of collection) store this dataset is :

	{
        "_id" : ObjectId("573000a1b404f339f8478e2c"),
        "cliente_id" : "7",
        "cpf" : "06525447578",
        "nome" : "Bruna Mesquita",
        "telefones" : [
                "988567438"
                ],
        "pedidos" : [
                "11"
                ]
	}
	{
        "_id" : ObjectId("573000a1b404f339f8478e2d"),
        "pedido_id" : "1",
        "data_pedido" : "2014-05-10T00:00:00Z",
        "data_entrega" : "2014-05-25T00:00:00Z",
        "cliente_cpf" : "06325348346"
	}

the id of each type of class of the dataset is store with your respective document to be the difference between them.

## Interface

	Under Construction !
