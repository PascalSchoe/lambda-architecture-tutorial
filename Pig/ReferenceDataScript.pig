REGISTER avro-1.8.2.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

ProduceData = LOAD '/machines/pm/pm1/daa/input/TambourRaw.avro' USING AvroStorage () as (timestamp:long, prod:int, weight:int, sortKey:chararray, sortName:chararray, rollNr:long);
OrderData = LOAD '/machines/pm/pm1/tips/input/UnititemRaw.avro' USING AvroStorage () as (unitItemCode:long, ordeRowPart:chararray);
	
Reference = JOIN WeightData BY timestamp, ProduceData BY timestamp;

out = FOREACH Reference GENERATE ProduceData::timestamp as timestamp, ProduceData::rollNr as rollNr, ProduceData::prod as prod, OrderData::ordeRowPart as order;

STORE out INTO '/reference/output' using AvroStorage();