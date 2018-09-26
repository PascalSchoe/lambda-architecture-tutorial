OrderData = LOAD '/machines/pm/pm1/excel/input/Walzen_Order.csv' using PigStorage(',') AS (OriginId:int,WalzenNr:int,Einbaudatum:chararray,Durchm.[mm]:chararray,Laufzeit:chararray,Qualität:chararray,Beurteilung:chararray,Ausbaudatum:chararray,GrundFürAusbau:chararray);
OriginData = LOAD '/machines/pm/pm1/excel/input/Walzen_Origin.csv' using PigStorage(',') AS (ID:int,Name:chararray);
output = JOIN OrderData BY OriginId, OriginData BY ID;
STORE OrderData INTO '/machines/pm/pm1/excel/output' using PigStorage('\t','-schema');
hadoop fs -cat /machines/pm/pm1/excel/output/.pig_header /machines/pm/pm1/excel/output/part-x-xxxxx | hadoop fs -put - /machines/pm/pm1/excel/output/result/orderoutput.csv