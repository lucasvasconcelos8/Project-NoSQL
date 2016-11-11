Redis Simple Store
=============

Implementation of system "simple-store" using Redis.
The Redis used in this implementation was redis-2.8.19 .

This implementation has 3 main parts:
- [Jedis](https://github.com/xetorthio/jedis) , A client  in Java for Redis
- Simple-Store dataset
- Interface (Under Construction!)

In the case, you don't familiar with Redis(yet!) and want a brief tutorial and explanation of database, read this [tutorial](https://docs.google.com/presentation/d/1QmUeLrLwDY_PS0CPkiqD1qpF8V8ct4Q1ZQQDoF43lzo/edit?usp=sharing) made for me!
	

## Jedis

Jedis is a client in Java for Redis and it1s pretty simple with easy tutorial on github. Jedis also have maven dependency which brings facilities to project with.

The inicialization of jedis is simple (with a single cluster in a localhost which is the case of this implementation):
	
	Jedis jedis = new Jedis("localhost");

After that you go use this "jedis" instance to execute calls in database redis! Just this!

## Simple-Store dataset

This implementation use a very simple dataset, because the purpose is to show a minnimum example using an application Java with Redis.
To help you quick understand the dataset, there is a description:

	Chaves:
		loja:cliente
				cpf
				nome
				telefone1
				telefone2
				telefone3
				pedido1
				pedido2
				pedido3
		loja:pedidos
				codigo
				data:entrega
				data:pedido
				cliente
		loja:item			
				numero
				quantidade
				mercadoria
				pedido
		loja:mercadoria
				codigo
				nome
				preco
		loja:endereco
				logradouro
				cidade
				uf
				cep
				cliente

The keys in database was represent with "loja<type>" for indicate what type you handle
An example of how redis store this dataset is :

	127.0.0.1:6379> keys *
	 1) "loja:endereco4"
	 2) "loja:endereco2"
	 3) "loja:pedido7"
	 4) "loja:cliente6"
	 5) "loja:item11"
	 6) "loja:mercadoria2"
	 7) "loja:mercadoria1"
	 8) "loja:pedido8"
	 9) "loja:cliente5"
	10) "loja:pedido3"
	11) "loja:endereco5"
	12) "loja:pedido9"

the numbers is the way choosen to determine the key (You can use other ways)

## Interface

	Under Construction !
