REGISTER avro-1.4.0.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

OrderData = LOAD '/machines/pm/pm1/excel/input/Walzen_Order.avro' USING AvroStorage () as (
	originId:int, cylindernr:int, buildinDate:chararray, diameter:double, runtime:chararray, quality:int,
	evaluation:chararray, buildoutDate:chararray, buildoutReason:chararray);
OriginData = LOAD '/machines/pm/pm1/excel/input/Walzen_Origin.avro' USING AvroStorage () as (
	orId:int, originName:chararray);
QualityData = LOAD '/machines/pm/pm1/excel/input/Walzen_Quality.avro' USING AvroStorage () as (
	qualId:int, qualityName:chararray);
	
ordOriMerge = JOIN OrderData BY originId, OriginData BY orId;
ordOriQualMerge = JOIN ordOriMerge BY qualityId, QualityData BY qualId;

output = FOREACH ordOriQualMerge GENERATE originName, cylinderNr, buildinDate, diameter, runtime, qualityName, evaluation, buildoutDate, buildoutReason;
DUMP output;

STORE output2 INTO '/machines/pm/pm1/excel/outputprocedure/output/' using AvroStorage();