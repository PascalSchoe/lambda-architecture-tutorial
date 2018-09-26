OrderData = LOAD '/machines/pm/pm1/excel/input/Walzen_Order.csv' using PigStorage(',') AS (id:chararray,code:chararray);
OriginData = LOAD '/machines/pm/pm1/excel/input/Walzen_Origin.csv' using PigStorage(',') AS (id:chararray,name:chararray);
output = JOIN OrderData BY originId, OriginData BY id;
STORE OrderData INTO '/machines/pm/pm1/excel/output' using PigStorage('\t','-schema');
hadoop fs -cat /machines/pm/pm1/excel/output/.pig_header /machines/pm/pm1/excel/output/part-x-xxxxx | hadoop fs -put - /machines/pm/pm1/excel/output/result/orderoutput.csv