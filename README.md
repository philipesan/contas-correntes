# Projeto Santander - Contas e Transações

Projeto realizado por Victor Philipe

**Link Documentação dos Endpoints:** https://www.postman.com/philipesan123/workspace/contas-santander


# Introdução

Este projeto é composto de um serviço que realiza o cadastro de endereços, clientes, contas e efetuas transações, contatando um serviço REST externo para enviar notificações Push aos usuários.

# Principais Entidades

## Endereço
Recebe o endereço dos usuários, o cadastro de um endereço válido é um pré-requisito para registrar um cliente, endereços são compostos de:
|Tipo|Campo  |
|--|--|
|Long | id|
|String | zipCode|
|String | streetName|
|String | number|
|String | city|
|String | country|
|String | neighborhood|
|String | stateSymbol|
|String | complement|
|Integer|  status|

Endereços podem possuir dois status

|Status|Valor  |
|--|--|
| Ativo | 0 |
| Inativo| 1 |

Clientes sem endereços validos ficarão barrados de realizar operações.

## Cliente
Clientes carregam informações pessoais da pessoa que será responsavel pelas contas, clientes são compostos por: 

|Tipo|Campo  |
|--|--|
|Long | id|
| String|name|
|String|document|
| String|type|
|String|password|
|Integer|status|
|Address | address|

Clientes também possuem dois status

|Status|Valor  |
|--|--|
| Ativo | 0 |
| Inativo| 1 |

O campo Type funciona como um campo identificador que pode ser PF ou PJ, entretanto, não existe validação deste campo, tornando seu uso a critério do front-end.

## Conta
Conta é o meio que os clientes interagem com o sistema e realizam operações, cada conta tem seu saldo próprio, o saldo da conta não pode ser alterado diretamente, sendo necessário realizar uma transação para altera-lo, a conta é composta por:

|Tipo|Campo  |
|--|--|
|Long |id|
| String|branch|
|BigDecimal |balance|
|Integer|status|
|Client|accountHolder|

A conta compartilha dos dois status dos objetos anteriores

|Status|Valor  |
|--|--|
| Ativo | 0 |
| Inativo| 1 |

## Transações
Transações funcionam como um cabeçalho de agrupamento para as operações (Tabela fato), e carergam relativamente poucos dados, os campos que compões as transações são:

|Tipo|Campo  |
|--|--|
|Long|id|
|LocalDate|transactionDate|
|Account|account|

Transações não possuem status, entretanto, suas dimensões, as operações sim.

## Operações

Operações são a tabela detalhamento das transações, operações são compostas dos dados relevantes das transações como o valor, a conta alvo, e parametros de configuração, como se o cliente deve ser notificado da operação em questão, os campos das operações são:

|Tipo|Campo  |
|--|--|
|Long | id|
|Integer | operationLine|
|LocalDate |operationDate|
|BigDecimal | amount|
|Boolean  |notificationFlag|
 |LocalDate|  notificationDate|
|Integer | status|
|Account|  account|

Operações possuem quatro status possiveis:

|Status|Valor  |
|--|--|
| Não Processada| 0 |
| Débito realizado| 1 |
| Notificação Realizada| 2 |
| Estornada| 3 |

Operações não podem ser estornadas individualmente, transações podem ser estornadas em sua totalidade através do endpoint de estorno de operações descrito no postman, não é possivel editar operações ou transações, em caso de erro, elas deverão ser estornadas e refeitas.

# O que foi feito
**Cadastro de endereços, clientes e contas.
Transações e operações estornaveis e que atualizam o saldo.
Validações nas entradas dos dados utilizando Jackarta Validation.
Validação de campos e mensagens de erros significativas para equipe de sustenção.
Logging do processo da aplicação.
Tratamento de erro em caso de erro/indisponíblidade no serviço de notificações.
Documentação dos endpoints.
Gitflow.
Segui o padrão de codificação em inglês, como é solicitado pelo banco**

## Sugestões de melhoria na arquitetura
**O Serviço de notificações poderia ser uma mensageria ao invés de um serviço Rest.
Segregar a aplicação em microsserviços de acordo com o dominio, um para endereços, um para clientes, um para contas e um para transações.
A senha não faz sentido ficar no dominio de dados do cliente, uma senha para cada conta acrescenta na segurança.
Utilizar um cronjob para periodicamente verificar as operações que não foram notificadas e automaticamente notificar aos clientes.**

## O que infelizmente não deu tempo
**Testes Unitários - Eu gostaria muito de ter tido um dia a mais para implementa-los, é um trabalho que eu gosto de fazer! Eu implantei testes unitarios usando JUnit e Mockito para realizar testes em APIs utilizando o RestTemplate no repositório https://github.com/philipesan/hoteis-cvc
Refatorar um pouco do código para deixa-lo mais limpo.
Eu não consegui fazer o Apache Camel rodar no meu ambiente em tempo hábil, então decidi que seria melhor deixar de lado e focar nas entregas das funcionalidade core.
Implementar alguns design patterns, como por exemplo, utilizar um builder para implementar as transações, ou então utilizar uma maquina de estados para controlar seu fluxo.
Eu tenho o código pronto para utilizar filtros dinamicos no JPA para realizar buscas através de specifications, eu acabei não tendo o tempo de implementar, mas o código está disponível no repositório https://github.com/philipesan/teste-attornatus/tree/main**
