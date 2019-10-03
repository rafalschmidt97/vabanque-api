insert into account (id, email, password, nickname)
values (nextval('account_id_seq'), 'account1@example.com',
        '$2a$10$aQmAAVVEOw3Z/YFB92YwD.XkYnl2KBnzU86jaM7Qc/zayKjGz4BbK', 'Oliver'),
       (nextval('account_id_seq'), 'account2@example.com',
        '$2a$10$aQmAAVVEOw3Z/YFB92YwD.XkYnl2KBnzU86jaM7Qc/zayKjGz4BbK', 'George'),
       (nextval('account_id_seq'), 'account3@example.com',
        '$2a$10$aQmAAVVEOw3Z/YFB92YwD.XkYnl2KBnzU86jaM7Qc/zayKjGz4BbK', 'Harry');
