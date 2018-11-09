REGISTER avro-1.4.0.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

OrderData = LOAD '/machines/pm/pm1/excel/input/Walzen_Order.avro' USING AvroStorage ();
OriginData = LOAD '/machines/pm/pm1/excel/input/Walzen_Origin.avro' USING AvroStorage ();
output = JOIN OrderData BY originId, OriginData BY id;
DUMP output;

RollerData = LOAD '/machines/pm/pm1/excel/input/Walzen_Roller.csv' using PigStorage(',') AS (id:chararray,code:chararray);
OriginData = LOAD '/machines/pm/pm1/excel/input/Walzen_Origin.csv' using PigStorage(',') AS (id:chararray,name:chararray);
ProducerData = LOAD '/machines/pm/pm1/excel/input/Walzen_Producer.csv' using PigStorage(',') AS (id:chararray,name:chararray);
QualityData = LOAD '/machines/pm/pm1/excel/input/Walzen_Quality.csv' using PigStorage(',') AS (id:chararray,name:chararray);
output2 = JOIN OrderData BY originId, OriginData BY id;
output3 = JOIN output2 BY producerId, ProducerData BY id;
output4 = JOIN output3 BY qualityId, QualityData BY id;
STORE insertformat INTO 'cql://cql3ks/simple_table1?output_query=UPDATE+cql3ks.simple_table1+set+b+%3D+%3F' USING CqlNativeStorage;
DUMP output4;