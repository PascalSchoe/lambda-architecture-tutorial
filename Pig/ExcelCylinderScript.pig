REGISTER avro-1.8.2.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

CylinderData = LOAD '/machines/pm/pm1/excel/input/cyliner_roller.avro' USING AvroStorage() as (
	originId:int, cylinderNr:int, preferenceType:int, producer:int, rammy:chararray, hardness:chararray,
	preferenceLength:double, bombage:chararray, newDate:chararray, maxO:chararray, minO:chararray);
DUMP CylinderData;
OriginData = LOAD '/machines/pm/pm1/excel/input/cyliner_origin.avro' USING AvroStorage() as (
	orId:int, originName:chararray);
DUMP OriginData;
QualityData = LOAD '/machines/pm/pm1/excel/input/cyliner_quality.avro' USING AvroStorage() as (
	qualId:int, qualityName:chararray);
DUMP QualityData;
ProducerData = LOAD '/machines/pm/pm1/excel/input/cyliner_producer.avro' USING AvroStorage() as (
	prodId:int, producerName:chararray);
DUMP ProducerData;
	
cylOriMerge = JOIN CylinderData BY originId, OriginData BY orId;
cylOriQualMerge = JOIN cylOriMerge BY CylinderData::preferenceType, QualityData BY qualId;
cylOriQialProdMerge = JOIN cylOriQualMerge BY CylOriMerge::CylinderData::producer, ProducerData BY prodId;

out = FOREACH cylOriQialProdMerge GENERATE cylOriQualMerge::cylOriMerge::OriginData::originName as originName, cylOriQualMerge::cylOriMerge::CylinderData::cylinderNr as cylinderNr, 
	cylOriQualMerge::QualityData::qualityName as qualityName, ProducerData::producerName as producerName, cylOriQualMerge::cylOriMerge::CylinderData::rammy as rammy, 
	cylOriQualMerge::cylOriMerge::CylinderData::hardness as hardness, cylOriQualMerge::cylOriMerge::CylinderData::preferenceLength as preferenceLength, 
	cylOriQualMerge::cylOriMerge::CylinderData::bombage as bombage, cylOriQualMerge::cylOriMerge::CylinderData::newDate as newDate, cylOriQualMerge::cylOriMerge::CylinderData::maxO as maxO, 
	cylOriQualMerge::cylOriMerge::CylinderData::minO as minO;

STORE out INTO '/machines/pm/pm1/excel/output' using AvroStorage();