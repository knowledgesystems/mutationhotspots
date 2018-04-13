# mutationhotspots
Algorithms to detect mutational hotspots. Currently, MutationHotspotsDetection only supports detection of intra-chain hotspots in 3D protein structures.

Binary JAR file is available in [releases](https://github.com/knowledgesystems/mutationhotspots/releases/latest).

## Usage


`java -jar MutationHotspotsDetection-1.1.0.jar <input.maf> <output.results.txt>`

Here is [an example input MAF file](https://github.com/knowledgesystems/mutationhotspots/blob/1.0.1/MutationHotspotsDetection/src/main/resources/data/example.maf)

Either `ENSP` or `SWISSPROT` should have data for each mutation. `CODE` is not required. All other columns are required.

**IMPORTANT**: The mutations in the input MAF should be pre-sorted by `ENSP` or `SWISSPROT` column.

## Citation

Gao J, Chang MT, Johnsen HC, Gao SP, Sylvester BE, Sumer SO, Zhang H, Solit DB, Taylor BS, Schultz N, Sander C.
__3D clusters of somatic mutations in cancer reveal numerous rare mutations as functional targets__.
_Genome Med_. 2017 Jan 23;9(1):4. doi: 10.1186/s13073-016-0393-x.
