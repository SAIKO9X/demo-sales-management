# Sales Management System

Este é um sistema de gerenciamento de vendas desenvolvido em Java, utilizando JDBC para operações de banco de dados.

## Estrutura do Projeto

O projeto é dividido nos seguintes pacotes principais:

- `org.sales.management.application`: Contém a classe `Program` que executa o programa principal.
- `org.sales.management.model.dao`: Contém as interfaces DAO (`SellerDao` e `DepartmentDao`) que definem as operações de banco de dados.
- `org.sales.management.model.dao.implement`: Contém as implementações JDBC das interfaces DAO (`SellerDaoJDBC` e `DepartmentDaoJDBC`).
- `org.sales.management.model.entities`: Contém as entidades de domínio (`Seller` e `Department`).
- `resources`: Contém o script SQL para a criação e população do banco de dados.

### Estrutura do Banco de Dados

```sql
CREATE DATABASE sales_management;

CREATE TABLE department (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(60) DEFAULT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE seller (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(60) NOT NULL,
  Email varchar(100) NOT NULL,
  BirthDate datetime NOT NULL,
  BaseSalary double NOT NULL,
  DepartmentId int(11) NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (DepartmentId) REFERENCES department (id)
);

INSERT INTO department (Name) VALUES 
  ('Computers'),
  ('Electronics'),
  ('Fashion'),
  ('Books');

INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES 
  ('Bob Brown','bob@gmail.com','1998-04-21 00:00:00',1000,1),
  ('Maria Green','maria@gmail.com','1979-12-31 00:00:00',3500,2),
  ('Alex Grey','alex@gmail.com','1988-01-15 00:00:00',2200,1),
  ('Martha Red','martha@gmail.com','1993-11-30 00:00:00',3000,4),
  ('Donald Blue','donald@gmail.com','2000-01-09 00:00:00',4000,3),
  ('Alex Pink','bob@gmail.com','1997-03-04 00:00:00',3000,2);
  ```

### Como Executar o Projeto

- Clone o repositório para a sua máquina local.
- Configure o banco de dados utilizando o script SQL localizado em resources/sales_management.sql.
- Configure a conexão com o banco de dados no código-fonte, caso necessário.
- Compile e execute a classe Program localizada em org.sales.management.application.

### Dependências
- Java 8 ou superior
- JDBC Driver para MySQL

```xml
 <dependencies>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.0.0</version>
        </dependency>
    </dependencies>
```