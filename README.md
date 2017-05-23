# mutationhotspots
Algorithms to detect mutational hotspots. Currently, MutationHotspotsDetection only supports detection of intra-chain hotspots in 3D protein structures.

Binary JAR file is available in [releases](https://github.com/knowledgesystems/mutationhotspots/releases).

## Usage


`java -jar MutationHotspotsDetection-1.0.1.jar <input.maf> <output.results.txt>`

Here is [an example input MAF file](https://github.com/knowledgesystems/mutationhotspots/blob/1.0.1/MutationHotspotsDetection/src/main/resources/data/example.maf)

Either `ENSP` or `SWISSPROT` should have data for each mutation. `CODE` is not required. All other columns are required.

**IMPORTANT**: The mutations in the input MAF should be pre-sorted by `ENSP` or `SWISSPROT` column.
