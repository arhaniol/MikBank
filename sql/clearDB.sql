TRUNCATE `mikbank2`.`accountdata`;
DELETE FROM mikbank2.clientdata where clientID>0;
DELETE FROM mikbank2.banktransaction where transactionID>0;