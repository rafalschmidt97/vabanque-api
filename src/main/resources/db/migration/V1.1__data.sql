insert into account (id, email, password, nickname)
values (nextval('account_id_seq'), 'account1@example.com',
        '$2a$10$aQmAAVVEOw3Z/YFB92YwD.XkYnl2KBnzU86jaM7Qc/zayKjGz4BbK', 'Oliver'),
       (nextval('account_id_seq'), 'account2@example.com',
        '$2a$10$aQmAAVVEOw3Z/YFB92YwD.XkYnl2KBnzU86jaM7Qc/zayKjGz4BbK', 'George'),
       (nextval('account_id_seq'), 'account3@example.com',
        '$2a$10$aQmAAVVEOw3Z/YFB92YwD.XkYnl2KBnzU86jaM7Qc/zayKjGz4BbK', 'Harry');

insert into refresh_token (id, expired_at, token, account_id)
values (nextval('refresh_token_id_seq'), current_timestamp + interval '2 year', 'abc', 1),
       (nextval('refresh_token_id_seq'), current_timestamp + interval '2 year', 'def', 2),
       (nextval('refresh_token_id_seq'), current_timestamp + interval '2 year', 'ghi', 3);

insert into debtor (id, creditor_account_id, debtor_account_id, amount)
values (nextval('debtor_id_seq'), 2, 1, '1€'),
       (nextval('debtor_id_seq'), 2, 1, '2€'),
       (nextval('debtor_id_seq'), 1, 2, '5€');
