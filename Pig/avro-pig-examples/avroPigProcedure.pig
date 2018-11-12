REGISTER avro-1.8.2.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

OrderData = LOAD '/input/avro/cyliner_procedure.avro' USING AvroStorage () as (
	originId:int, cylindernr:int, buildinDate:chararray, diameter:double, runtime:chararray, quality:int,
	evaluation:chararray, buildoutDate:chararray, buildoutReason:chararray);
OriginData = LOAD '/input/avro/cyliner_origin.avro' USING AvroStorage () as (
	orId:int, originName:chararray);
QualityData = LOAD '/input/avro/cyliner_quality.avro' USING AvroStorage () as (
	qualId:int, qualityName:chararray);
	
ordOriMerge = JOIN OrderData BY originId, OriginData BY orId;
ordOriQualMerge = JOIN ordOriMerge BY quality, QualityData BY qualId;

out = FOREACH ordOriQualMerge GENERATE originName, cylindernr, buildinDate, diameter, runtime, qualityName, evaluation, buildoutDate, buildoutReason;
DUMP out;

STORE out INTO '/output' using AvroStorage();