# genomenexus-g2s-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>org.genomenexus</groupId>
    <artifactId>genomenexus-g2s-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "org.genomenexus:genomenexus-g2s-client:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/genomenexus-g2s-client-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import org.genomenexus.g2s.client.*;
import org.genomenexus.g2s.client.auth.*;
import org.genomenexus.g2s.client.model.*;
import org.genomenexus.g2s.client.api.GetAlignmentsApi;

import java.io.File;
import java.util.*;

public class GetAlignmentsApiExample {

    public static void main(String[] args) {
        
        GetAlignmentsApi apiInstance = new GetAlignmentsApi();
        String idType = "idType_example"; // String | Input id_type: ensembl; uniprot; uniprot_isoform
        String id = "id_example"; // String | Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9 
        String pdbId = "pdbId_example"; // String | Input PDB Id e.g. 2fej
        String chainId = "chainId_example"; // String | Input Chain e.g. A
        try {
            List<Alignment> result = apiInstance.getAlignmentByPDBUsingGET(idType, id, pdbId, chainId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling GetAlignmentsApi#getAlignmentByPDBUsingGET");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://g2s.genomenexus.org/*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*GetAlignmentsApi* | [**getAlignmentByPDBUsingGET**](docs/GetAlignmentsApi.md#getAlignmentByPDBUsingGET) | **GET** /api/alignments/{id_type}/{id}/pdb/{pdb_id}_{chain_id} | Get PDB Alignments by ProteinId, PDBId and Chain
*GetAlignmentsApi* | [**getAlignmentUsingGET**](docs/GetAlignmentsApi.md#getAlignmentUsingGET) | **GET** /api/alignments/{id_type}/{id} | Get PDB Alignments by ProteinId
*GetAlignmentsApi* | [**getPdbAlignmentBySequenceUsingGET**](docs/GetAlignmentsApi.md#getPdbAlignmentBySequenceUsingGET) | **GET** /api/alignments | Get PDB Alignments by Protein Sequence
*GetAlignmentsApi* | [**getPdbAlignmentBySequenceUsingPOST**](docs/GetAlignmentsApi.md#getPdbAlignmentBySequenceUsingPOST) | **POST** /api/alignments | Get PDB Alignments by Protein Sequence
*GetResidueMappingApi* | [**getPdbAlignmentReisudeBySequenceUsingGET**](docs/GetResidueMappingApi.md#getPdbAlignmentReisudeBySequenceUsingGET) | **GET** /api/alignments/residueMapping | Get PDB Residue Mapping by Protein Sequence and Residue position
*GetResidueMappingApi* | [**getPdbAlignmentReisudeBySequenceUsingPOST**](docs/GetResidueMappingApi.md#getPdbAlignmentReisudeBySequenceUsingPOST) | **POST** /api/alignments/residueMapping | Get PDB Residue Mapping by Protein Sequence and Residue position
*GetResidueMappingApi* | [**postResidueMappingByPDBUsingGET**](docs/GetResidueMappingApi.md#postResidueMappingByPDBUsingGET) | **GET** /api/alignments/{id_type}/{id}/pdb/{pdb_id}_{chain_id}/residueMapping | Post Residue Mapping by ProteinId, PDBId and Chain
*GetResidueMappingApi* | [**postResidueMappingByPDBUsingPOST**](docs/GetResidueMappingApi.md#postResidueMappingByPDBUsingPOST) | **POST** /api/alignments/{id_type}/{id}/pdb/{pdb_id}_{chain_id}/residueMapping | Post Residue Mapping by ProteinId, PDBId and Chain
*GetResidueMappingApi* | [**postResidueMappingUsingGET**](docs/GetResidueMappingApi.md#postResidueMappingUsingGET) | **GET** /api/alignments/{id_type}/{id}/residueMapping | POST PDB Residue Mapping by ProteinId
*GetResidueMappingApi* | [**postResidueMappingUsingPOST**](docs/GetResidueMappingApi.md#postResidueMappingUsingPOST) | **POST** /api/alignments/{id_type}/{id}/residueMapping | POST PDB Residue Mapping by ProteinId


## Documentation for Models

 - [Alignment](docs/Alignment.md)
 - [ResidueMapping](docs/ResidueMapping.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

wangjue@missouri.edu

