REGISTER avro-1.8.2.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

WeightData = LOAD '/machines/pm/pm1/daa/input/WeightsRaw.avro' USING AvroStorage () as (timestamp:long, tankLevelB304:double, tankLevelB301:double, r2Grammage:double, r1Grammage:double);
ProduceData = LOAD '/machines/pm/pm1/daa/input/TambourRaw.avro' USING AvroStorage () as (timestamp:long, prod:int, weight:int, sortKey:chararray, sortName:chararray, rollNr:long);
OrderData = LOAD '/machines/pm/pm1/tips/input/UnititemRaw.avro' USING AvroStorage () as (unitItemCode:long, ordeRowPart:chararray);
	
WeightProd = JOIN WeightData BY timestamp, ProduceData BY timestamp;
WeightProdOrder = JOIN WeightProd BY ProduceData::rollNr, OrderData BY unitItemCode;

out = FOREACH ordOriQualMerge GENERATE WeightProdOrder::WeightData::timestamp as timestamp, WeightProdOrder::ProduceData::rollNr as rollNr, WeightProdOrder::ProduceData::prod as prod, 
	OrderData::ordeRowPart as order, WeightProdOrder::WeightData::tankLevelB304 as tankLevelB304, WeightProdOrder::WeightData::tankLevelB301 as tankLevelB301,
	WeightProdOrder::WeightData::r2Grammage as r2Grammage, WeightProdOrder::WeightData::r1Grammage as r1Grammage;

STORE out INTO '/machines/pm/pm1/daa/output' using AvroStorage();