REGISTER avro-1.8.2.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

OrderData = LOAD '/machines/pm/pm1/excel/input/cyliner_procedure.avro' USING AvroStorage () as (
	originId:int, cylindernr:int, buildinDate:chararray, diameter:double, runtime:chararray, quality:int,
	evaluation:chararray, buildoutDate:chararray, buildoutReason:chararray);
OriginData = LOAD '/machines/pm/pm1/excel/input/cyliner_origin.avro' USING AvroStorage () as (
	orId:int, originName:chararray);
QualityData = LOAD '/machines/pm/pm1/excel/input/cyliner_quality.avro' USING AvroStorage () as (
	qualId:int, qualityName:chararray);
	
ordOriMerge = JOIN OrderData BY originId, OriginData BY orId;
ordOriQualMerge = JOIN ordOriMerge BY OrderData::quality, QualityData BY qualId;

out = FOREACH ordOriQualMerge GENERATE ordOriQualMerge::OriginData::originName as originName, ordOriQualMerge::OrderData::cylindernr as cylindernr, ordOriQualMerge::OrderData::buildinDate as buildinDate,
	ordOriQualMerge::OrderData::diameter as diameter, ordOriQualMerge::OrderData::runtime as runtime, QualityData::qualityName as qualityName, ordOriQualMerge::OrderData::evaluation as evaluation, 
	ordOriQualMerge::OrderData::buildoutDate as buildoutDate, ordOriQualMerge::OrderData::buildoutReason as buildoutReason;

STORE out INTO '/machines/pm/pm1/excel/output' using AvroStorage();