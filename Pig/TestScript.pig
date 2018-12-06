REGISTER avro-1.8.2.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar

WeightData = LOAD '/machines/pm/pm1/daa/input/WeightsRaw.avro' USING AvroStorage () as (timestamp:long, tankLevelB304:double, tankLevelB301:double, r2Grammage:double, r1Grammage:double);

SET mapreduce.output.basename 'custom-name';

STORE WeightData INTO '/machines/pm/pm1/daa/output' using AvroStorage('{
  "type": "record",
  "name": "WeightProcessed",
  "doc": "This is a sample Avro schema to get you started. Please edit",
  "namespace": "org.mitsubishi.fl.pm1.daa",
  "fields": [
    {"name": "timestamp",
    "type": "long",
    "doc": "Zeitstempel"},
    {"name": "tankLevelB304",
    "type": "double",
    "doc": "PM1 Streichküche//Behälterwaage_B304 (WIS304_01-X) | Unit:"},
    {"name": "tankLevelB301",
    "type": "double",
    "doc": "PM1 Streichküche//Behälterwaage_B301 (WIS301_01-X) | Unit: "},
    {"name": "r2Grammage",
    "type": "double",
    "doc": "PM1 Papier//R2_Flächengewicht [g/m2] ()| Unit: g/m2"},
    {"name": "r1Grammage",
    "type": "double",
    "doc": "PM1 Papier//R1_Flächengewicht [g/m2] | Unit: g/m2"}
  ]
}');