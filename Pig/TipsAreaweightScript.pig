REGISTER avro-1.8.2.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

MeasData = LOAD '/machines/pm/pm1/tips/input/MeasurementsRaw.avro' USING AvroStorage () as (reelcode:long, measId:int, reelSet:int, reelPos:int, valNbr:double, valStat:int, valString:chararray, 
	aggrMin:double, aggrMax:double, aggrStd:int, oId:chararray, ucnt: int, iDate:long, uDate:long, proccode:chararray, locacode:chararray, propCode:chararray, measLevelCode:chararray);
ProduceData = LOAD '/machines/pm/pm1/daa/input/TambourRaw.avro' USING AvroStorage () as (timestamp:long, prod:int, weight:int, sortKey:chararray, sortName:chararray, rollNr:long);
OrderData = LOAD '/machines/pm/pm1/tips/input/UnititemRaw.avro' USING AvroStorage () as (unitItemCode:long, ordeRowPart:chararray);

AddData = JOIN ProduceData BY rollNr, OrderData BY unitItemCode;

FilterAreaWeigth = FILTER measData BY propCode == 'FLGW';
FilterAreaWeigthRoh = FILTER measData BY propCode == 'FLGW-ROH';
FilterAreaWeigthEnd = FILTER measData BY propCode == 'FLG-END';

JoinedData = JOIN FilterAreaWeight BY reelcode, FilterAreaWeigthRoh BY reelcode;
FinalJoinedData = JOIN JoinedData BY FilterAreaWeight::reelcode, FilterAreaWeigthEnd BY reelcode;

FinalMeas = FOREACH FinalJoinedData GENERATE FilterAreaWeigth::reelcode as reelcode, FilterAreaWeight::valNbr as areaWeight, FilterAreaWeigthRoh::valNbr as areaWeightRoh, FilterAreaWeigthEnd::valNbr as areaWeightEnd,
	FilterAreaWeight::aggrMax as areaWeightMax, FilterAreaWeight::aggrMin as areaWeightMin, FilterAreaWeigthRoh::aggrMax as areaWeightRohMax, FilterAreaWeigthRoh::aggrMin as areaWeightRohMin FilterAreaWeight::iDate as timestamp;
	
OutJoin = JOIN FinalMeas BY reelcode, AddData BY ProduceData::rollNr;

Out = FOREACH OutJoin GENERATE FinalMeas::reelcode as reelcode, FinalMeas::areaWeight as areaWeight, FinalMeas::areaWeightRoh as areaWeightRoh, FinalMeas::areaWeightEnd as areaWeightEnd,
	FinalMeas::areaWeightMax as areaWeightMax, FinalMeas::areaWeightMin as areaWeightMin, FinalMeas::areaWeightRohMax as areaWeightRohMax, FinalMeas::areaWeightRohMin as areaWeightRohMin, FinalMeas::timestamp as timestamp,
	AddData::ProduceData::prod as prodNr, AddData::OrderData::ordeRowPart as order;

STORE Out INTO '/machines/pm/pm1/tips/output' using AvroStorage();