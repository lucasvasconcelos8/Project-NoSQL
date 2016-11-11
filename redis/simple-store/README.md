Redis Simple Store
=============

Implementation of system "simple-store" using Redis.

This implementation has 3 main parts:
	- [Jedis](https://github.com/xetorthio/jedis) , A client  in Java for Redis
	- Simple-Store dataset
	- Interface (Under Construction!)

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

## Interface

	Under Construction !