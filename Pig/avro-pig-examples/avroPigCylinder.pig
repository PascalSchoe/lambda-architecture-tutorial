REGISTER avro-1.4.0.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

CylinderData = LOAD '/machines/pm/pm1/excel/input/Walzen_Order.avro' USING AvroStorage() as (
	originId:int, cylinderNr:int, preferenceType:int, producer:int, rammy:chararray, hardness:chararray,
	preferenceLength:double, bombage:chararray, newDate:chararray, maxO:chararray, minO:chararray);
OriginData = LOAD '/machines/pm/pm1/excel/input/Walzen_Origin.avro' USING AvroStorage() as (
	orId:int, originName:chararray);
QualityData = LOAD '/machines/pm/pm1/excel/input/Walzen_Quality.avro' USING AvroStorage() as (
	qualId:int, qualityName:chararray);
ProducerData = LOAD '/machines/pm/pm1/excel/input/Walzen_Producer.avro' USING AvroStorage() as (
	prodId:int, producerName:chararray);
	
cylOriMerge = JOIN CylinderData BY originId, OriginData BY orId;
cylOriQualMerge = JOIN cylOriMerge BY preferenceType, QualityData BY qualId;
cylOriQialProdMerge = JOIN cylOriQualMerge BY producer, ProducerData BY prodId;

output = FOREACH cylOriQialProdMerge GENERATE originName, cylinderNr, qualityName, producerName, rammy, hardness, preferenceLength, bombage, newDate, maxO, minO;
DUMP output;

STORE output2 INTO '/machines/pm/pm1/excel/outputprocedure/output/' using AvroStorage();