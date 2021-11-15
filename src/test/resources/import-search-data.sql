INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, USER_TYPE) VALUES
    (1, 'John', 'Smith', 1),
    (2, 'Jane', 'Doe', 0),
    (3, 'Bob', 'Phelps', 1),
    (4, 'ONE_MATCH', 'Davis', 1),
    (5, 'MrStartDate', 'John', 0);

INSERT INTO CONTRACTS (ID, START_DATE, CONTRACT_TYPE, USER_ID) VALUES
    (1, '1990-01-01', 0, 1),
    (2, '2010-01-01', 2, 3),
    (3, '2015-01-01', 1, 1),
    (4, '2020-01-01', 0, 2),
    (5, '2011-06-06', 1, 4),
    (6, '2015-01-01', 2, 5),
    (7, '2030-01-01', 1, 3);