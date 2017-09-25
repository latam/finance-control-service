INSERT INTO USER(id, first_name, last_name, email, password, roles) VALUES
	(1, 'Test', 'User', 'TestUser@gmail.com', '$2a$10$LMjGf7l.LX0ouvsmKrptLOUHeohdSRWnz/5qyMsjsD4tlSU1V6oIK', 'ROLE_USER');

INSERT INTO ACCOUNT(ID, DESCRIPTION, NAME, TYPE, PARENT_ACCOUNT_ID, USER_ID) VALUES 
	(1, 'All expenses', 'Expenses', 'EXPENSE', null, 1),
	(2, 'All income', 'Income', 'INCOME', null, 1),
	(3, 'Tobaco and e-cigarette expenses', 'Cigarettes', 'EXPENSE', 1, 1);

INSERT INTO ACCOUNT_CHILD_ACCOUNTS(ACCOUNT_ID, CHILD_ACCOUNTS_ID) VALUES
	(1, 3);
	
INSERT INTO OPERATION(ID, USER_ID, TYPE, DATE, TO_ACCOUNT_ID, FROM_ACCOUNT_ID, FUNDS) VALUES
	(1, 1, 'INCOME', '2017-09-25T12:55:55', 2, null, 100),
	(2, 1, 'EXPENSE', '2017-09-25T13:55:55', 3, 2, 30),
	(3, 1, 'EXPENSE', '2017-09-25T14:55:55', 3, 2, 50),
	(4, 1, 'INCOME', '2017-09-25T15:55:55', 2, null, 200),
	(5, 1, 'EXPENSE', '2017-09-25T16:55:55', 3, 2, 80),
	(6, 1, 'EXPENSE', '2017-09-25T17:55:55', 3, 2, 20);