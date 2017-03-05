# mutationhotspots
Algorithms to detect mutational hotspots. Currently, MutationHotspotsDetection only supports detection of intra-chain hotspots in 3D protein structures.

Binary JAR file is available in [releases](releases).

## Usage


`java -jar MutationHotspotsDetection-1.0.0.jar <input.maf> <output.results.txt>`

Here is [an example input MAF file](MutationHotspotsDetection/src/main/resources/data/example.maf)

Either `ENSP` or `SWISSPROT` should have data for each mutation. `CODE` is not required. All other columns are required.
