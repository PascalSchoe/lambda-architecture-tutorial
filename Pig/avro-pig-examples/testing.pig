REGISTER avro-1.8.2.jar
REGISTER json-simple-1.1.jar
REGISTER piggybank-0.15.0.jar
REGISTER jackson-core-asl-1.5.5.jar
REGISTER jackson-mapper-asl-1.5.5.jar
// Avro-Schema muss nicht mit angegeben werden
OriginData = LOAD '/input/avro/cyliner_origin.avro' USING AvroStorage ();

DUMP OriginData; // Gibt den Inhalt auf der Konsole aus

//AvroStorage erstellt automatisch ein Avro-Schema aus dem vorhandenen Pig-schema
STORE OriginData INTO '/output' using AvroStorage();