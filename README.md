# SenML_API
The senml app is an API for encoding and decoding SenML messages in JSON or CBOR format.  
It handles the formatting of the SenML pack conforming to [RFC 8428](https://tools.ietf.org/html/rfc8428).

## Maven Dependencies

To use the API on a Maven project, include the following in your `pom.xml` file:
```
<repositories>
  <repository>
    <id>SenML_API-mvn-repo</id>
    <url>https://github.com/team-ethernet/SenML_API/raw/mvn-repo</url>
    <releases>
      <enabled>true</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>team-ethernet</groupId>
    <artifactId>senml-api</artifactId>
    <version>1.3.0</version>
  </dependency>
<dependencies>
```

## Project code needs

### Dependencies
* [Jackson Databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.9.8)
* [Jackson Dataformat: CBOR](https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-cbor/2.9.8)
* [JUnit](https://mvnrepository.com/artifact/junit/junit)

## Use

The API is used through the methods 

`SenMLAPI.initJson()`, `SenMLAPI.initJson(byte[])`, `SenMLAPI.initCbor()`, `SenMLAPI.initCbor(byte[])`, `SenMLAPI#addRecord(Label.Pair, ...)`, `SenMLAPI#addRecord(byte[])`, `SenMLAPI#getSenML()`, `SenMLAPI#getValue(Label, int)`, `SenMLAPI#getRecord(int)`, `SenMLAPI#getRecords()`, `SenMLAPI#getLabels(int)`, `Label<T>#attachValue(T)` and `Label#getFormattedLabel(Class<T extends Formatter>)`

The supported labels are:

| Field name    | Data type |
| ------------- |:---------:|
| BASE_NAME     | String    |
| BASE_TIME     | double    |
| BASE_UNIT     | String    |
| BASE_VALUE    | double    |
| BASE_SUM      | double    |
| BASE_VERSION  | int       |
| NAME          | String    |
| UNIT          | String    |
| VALUE         | double    |
| STRING_VALUE  | String    |
| BOOLEAN_VALUE | boolean   |
| DATA_VALUE    | String    |
| SUM           | double    |
| TIME          | double    |
| UPDATE_TIME   | double    |

### Methods

```java
// Creates and begins a new empty SenML message in JSON format
SenMLAPI SenMLAPI.initJson();

// Creates and begins a SenML message from the byte stream in JSON format
SenMLAPI SenMLAPI.initJson(byte[]);

// Creates and begins new empty SenML message in CBOR format
SenMLAPI SenMLAPI.initCbor();

// Creates and begins a SenML message from the byte stream in CBOR format
SenMLAPI SenMLAPI.initCbor(byte[]);

// Adds a record with the given fields
// For example 
// SenMLAPI#addRecord(Label.BASE_NAME.attachValue("name"), Label.BASE_UNIT.attachValue("unit"), Label.VALUE.attachValue(4.6))
// adds a record with the fields bn = name, bu = unit, v = 4.6
void SenMLAPI#addRecord(Label.Pair, ...);

// Adds a record from the encoded JSON/CBOR message
// For example 
// SenMLAPI#addRecord("{"bn":"name","bu":"unit","v":4.6}".getBytes())
// adds a record with the fields bn = name, bu = unit, v = 4.6
void SenMLAPI#addRecord(byte[]);

// Returns the encoded SenML message
byte[] SenMLAPI#getSenML();

// Get the value for the given label at the given record index
// For example 
// String = SenMLAPI#getValue(Label.BASE_NAME, 0)
// Returns the base name for the first record.
T SenMLAPI#getValue(Label<T>, int);

// Returns the record that exist at the given record index
byte[] SenMLAPI#getRecord(int);

// Returns a List of all records that exist
List<byte[]> SenMLAPI#getRecords();

// Returns a List of all Labels that exist at the given record index
List<Label> SenMLAPI#getLabels(int);

// Returns a Label-Value Pair that is used in the SenMLAPI#addRecord method
Label.Pair Label<T>#attachValue(T)

// Returns the label as how it is encoded in the JSON/CBOR message
String Label#getFormattedLabel(Class<S extends Formatter>)
```

## Example usage

### Encoding
#### JSON
```java
SenMLAPI senMLAPI = SenMLAPI.initJson();
senMLAPI.addRecord(Label.BASE_NAME.attachValue("name"), Label.BASE_UNIT.attachValue("unit"), Label.VALUE.attachValue(4.6));
senMLAPI.addRecord(Label.NAME.attachValue("current"), Label.UNIT.attachValue("A"), Label.VALUE.attachValue(1.2));
byte[] json = senMLAPI.getSenML();

System.out.println(new String(json));
```
Should print
```json
[{"bn":"name","bu":"unit","v":4.6},{"n":"current","u":"A","v":1.2}]
```

#### CBOR
```java
SenMLAPI senMLAPI = SenMLAPI.initCbor();
senMLAPI.addRecord(Label.BASE_NAME.attachValue("name"), Label.BASE_UNIT.attachValue("unit"), Label.VALUE.attachValue(4.6));
senMLAPI.addRecord(Label.NAME.attachValue("current"), Label.UNIT.attachValue("A"), Label.VALUE.attachValue(1.2));
byte[] cbor = senMLAPI.getSenML();

System.out.println(new String(cbor));
```
Should print
```cbor
��b-2dnameb-4dunita2�@ffffff��a0gcurrenta1aAa2�?�333333�
```

### Decoding
#### JSON
```java
String sampleJson = "[{\"bn\":\"mac:urn:dev:1234\", \"v\": 30.0}]";
SenMLAPI senMLAPI = SenMLAPI.initJson(sampleJson.getBytes());
double v = senMLAPI.getValue(Label.VALUE, 0);

System.out.println(v);
```
Should print
```
30.0
```

#### CBOR
```java
byte[] sampleCbor = new byte[]{-127, -94, 98, 98, 110, 112, 109, 97, 99, 58, 117, 114, 110, 58, 100, 101, 118, 58, 49, 50, 51, 52, 97, 118, -7, 79, -128};
SenMLAPI senMLAPI = SenMLAPI.initCbor(sampleCbor);
double v = senMLAPI.getValue(Label.VALUE, 0);

System.out.println(v);
```
Should print
```
30.0
```

## Code structure
The different lables are defined in `Label.java`.  
The main code that handles the different labels is in `SenMLAPI.java`.  
Formatting is handled in `CborFormatter.java` and `JsonFormatter.java`, both implementing the functions defined in `Formatter.java`  

## Authors
Erik Flink   \
Isak Olsson \
Nelly Friman \
Anton Bothin   \
Andreas Sjödin \
Jacob Klasmark  \
Carina Wickström \
Valter Lundegårdh 
