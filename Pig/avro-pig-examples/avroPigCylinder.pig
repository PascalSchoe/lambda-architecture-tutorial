REGISTER avro-1.8.2.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

CylinderData = LOAD '/input/avro/cyliner_roller.avro' USING AvroStorage() as (
	originId:int, cylinderNr:int, preferenceType:int, producer:int, rammy:chararray, hardness:chararray,
	preferenceLength:double, bombage:chararray, newDate:chararray, maxO:chararray, minO:chararray);
DUMP CylinderData;
OriginData = LOAD '/input/avro/cyliner_origin.avro' USING AvroStorage() as (
	orId:int, originName:chararray);
DUMP OriginData;
QualityData = LOAD '/input/avro/cyliner_quality.avro' USING AvroStorage() as (
	qualId:int, qualityName:chararray);
DUMP QualityData;
ProducerData = LOAD '/input/avro/cyliner_producer.avro' USING AvroStorage() as (
	prodId:int, producerName:chararray);
DUMP ProducerData;
	
cylOriMerge = JOIN CylinderData BY originId, OriginData BY orId;
cylOriQualMerge = JOIN cylOriMerge BY preferenceType, QualityData BY qualId;
cylOriQialProdMerge = JOIN cylOriQualMerge BY producer, ProducerData BY prodId;

out = FOREACH cylOriQialProdMerge GENERATE originName, cylinderNr, qualityName, producerName, rammy, hardness, preferenceLength, bombage, newDate, maxO, minO;
DUMP out;

STORE out INTO '/output' using AvroStorage();