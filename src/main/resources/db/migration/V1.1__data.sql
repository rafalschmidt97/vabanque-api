insert into account (email, password, nickname)
values ('account1@example.com', '$2a$10$aQmAAVVEOw3Z/YFB92YwD.XkYnl2KBnzU86jaM7Qc/zayKjGz4BbK', 'Oliver'),
       ('account2@example.com', '$2a$10$aQmAAVVEOw3Z/YFB92YwD.XkYnl2KBnzU86jaM7Qc/zayKjGz4BbK', 'George'),
       ('account3@example.com', '$2a$10$aQmAAVVEOw3Z/YFB92YwD.XkYnl2KBnzU86jaM7Qc/zayKjGz4BbK', 'Harry');

insert into refresh_token (expired_at, token, account_id)
values (current_timestamp(6) + interval 2 year, 'abc', 1),
       (current_timestamp(6) + interval 2 year, 'def', 2),
       (current_timestamp(6) + interval 2 year, 'ghi', 3);

insert into debtor (creditor_account_id, debtor_account_id, amount)
values (2, 1, '1€'),
       (2, 1, '2€'),
       (1, 2, '5€');
