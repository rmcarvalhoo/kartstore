# Getting Started

### Challenge 

Criar um serviço que receba pedidos no formato xml e json com 6 campos:
número controle - número aleatório informado pelo cliente.
data cadastro (opcional)
nome - nome do produto
valor - valor monetário unitário produto
quantidade (opcional) - quantidade de produtos.
codigo cliente - identificação numérica do cliente.

Critérios aceitação e manipulação do arquivo:

O arquivo pode conter 1 ou mais pedidos, limitado a 10.
Não poderá aceitar um número de controle já cadastrado.
Caso a data de cadastro não seja enviada o sistema deve assumir a data atual.
Caso a quantidade não seja enviada considerar 1.
Caso a quantidade seja maior que 5 aplicar 5% de desconto no valor total, para quantidades a partir de 10 aplicar 10% de desconto no valor total.
O sistema deve calcular e gravar o valor total do pedido.
Assumir que já existe 10 clientes cadastrados, com códigos de 1 a 10.


Criar um serviço onde possa consultar os pedidos enviados pelos clientes.
Critérios aceitação:
O retorno deve trazer todos os dados do pedido.

filtros da consulta:
número pedido, data cadastro, todos

### Solution

- java 17
- unit test
- JPA
- Mysql
- Solid
- Strategy
- Flyway
- Docker
- Maven
- swagger
- jacoco

### How do use

#### Set MAVEN HOME and JAVA HOME 
```
export JAVA_HOME=/usr/lib/jvm/java-17-oracle
export M2_HOME=/opt/maven
export MAVEN_HOME=/opt/maven
export PATH=${M2_HOME}/bin:${PATH}
```

#### Compile
```
mvn clean package
```

#### Start docker
```
docker compose up --build
```

#### Start microservice 
java -jar kartstore-0.0.1-SNAPSHOT.jar

# swagger 
```
localhost:8080/kartstore/swagger-ui/index.html
```

#### Create Sale JSON
```
curl --location --request POST 'http://localhost:8080/kartstore/sale' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '[
{
"numControl": "8a",
"created": "2024-04-09 03:03:48",
"productName": "Oleo",
"productValue": 1,
"amount": 2,
"clientId": 2
} ,
{
"numControl": "9a",
"created": "2024-04-09 03:03:48",
"productName": "corrente",
"productValue": 1,
"amount": 2,
"clientId": 9
}  
]'
```

#### Create Sale XML
```
curl --location --request POST 'http://localhost:8080/kartstore/sale' \
--header 'Accept: application/xml' \
--header 'Content-Type: application/xml' \
--data-raw '<sales>
<Sale>
	<numControl>123</numControl>
	<created>2024-01-01 00:00:00</created>
	<productName>Volante</productName>
	<productValue>1</productValue>
	<amount>1</amount>
	<clientId>1</clientId>
</Sale>
</sales>'
```


#### get all Sale 
```
curl --location --request GET 'http://localhost:8080/kartstore/sale?page=0&size=5&sort=id' \
--header 'Accept: application/json'
```


#### get Sale by id
```
curl --location --request GET 'http://localhost:8080/kartstore/sale?saleId=6&page=0&size=5&sort=id' \
--header 'Accept: application/json'
```


#### get Sale by created date
```
curl --location --request GET 'http://localhost:8080/kartstore/sale?createdStart=2024-04-08&createdEnd=2024-04-10&page=0&size=5&sort=id' \
--header 'Accept: application/json'
```







