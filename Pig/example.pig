OrderData = LOAD 'hdfs:/machines/pm/pm1/excel/input/Walzen_Order.csv' using PigStorage(',') AS (OriginId:int,WalzenNr:int,Einbaudatum:chararray,Durchm:chararray,Laufzeit:chararray,Qualitat:chararray,Beurteilung:chararray,Ausbaudatum:chararray,GrundFurAusbau:chararray);
OriginData = LOAD 'hdfs:/machines/pm/pm1/excel/input/Walzen_Origin.csv' using PigStorage(',') AS (ID:int,Name:chararray);
outputBernd = JOIN OrderData BY OriginId, OriginData BY ID;
/*STORE OrderData INTO 'hdfs:/machines/pm/pm1/excel/output' using PigStorage('\t','-schema');*/
DUMP outputBernd
/*hadoop fs -cat /machines/pm/pm1/excel/output/.pig_header /machines/pm/pm1/excel/output/part-x-xxxxx | hadoop fs -put - /machines/pm/pm1/excel/output/result/orderoutput.csv*/
