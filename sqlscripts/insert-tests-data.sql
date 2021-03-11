insert into account(id, name, bankName, agency, `number`, balance) values (1, 'Client 1', 'BV Financeira', '001', '001-1', 10000.00);
insert into account(id, name, bankName, agency, `number`, balance) values (2, 'Client 2', 'BV Financeira', '001', '001-2', 20000.00);
insert into account(id, name, bankName, agency, `number`, balance) values (3, 'Client 3', 'BV Financeira', '001', '001-3', 30000.00);


-- INSERT INTO MY_TABLE(id, blob_col) VALUES(1, LOAD_FILE('/full/path/to/file/myfile.png')
update account 
set imglogo = LOAD_FILE('C:\ws-developer\convert-bitmap-to-base64\src\main\resources\static\images\logobvsmall.png')
where id = 1;