CREATE SEQUENCE IF NOT EXISTS emp_no START 1;

create table employees(
emp_id VARCHAR(20) PRIMARY KEY DEFAULT ('EMP' || nextval('emp_no'::regclass)::text),
emp_name VARCHAR(20) not null,
emp_dob DATE not null, 	
emp_address VARCHAR(20) not null,
emp_email VARCHAR(100) UNIQUE not null,
department_name VARCHAR(20) not null
);
 
create type emp_status as ENUM ('ADMIN','MANAGER','USER');

create table emp_roles(
emp_id VARCHAR(20) not null ,
emp_role emp_status not null,
PRIMARY KEY (emp_id,emp_role),
CONSTRAINT fk_employee 
  FOREIGN KEY (emp_id)
  REFERENCES employees(emp_id)
  ON DELETE CASCADE
);
 
create table emp_login(
emp_id VARCHAR(20) not null UNIQUE,
emp_password VARCHAR(64) not null,
CONSTRAINT fk_login 
  FOREIGN KEY (emp_id)
  REFERENCES employees(emp_id)
  ON DELETE CASCADE
);

INSERT INTO employees (emp_name, emp_dob, emp_address, emp_email, department_name) 
VALUES ('admins', '2003-09-12', 'hyd', 'admins@gmail.com', 'dev');

INSERT INTO emp_login (emp_id, emp_password) 
VALUES ('EMP1', '58acb7acccce58ffa8b953b12b5a7702bd42dae441c1ad85057fa70b'); // password:admin

INSERT INTO emp_roles (emp_id, emp_role) 
VALUES ('EMP1', 'ADMIN');

select * from employees;
select * from emp_login;
select * from emp_roles;

// login details:
id:EMP1
password:admin