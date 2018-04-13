# GetResidueMappingApi

All URIs are relative to *https://g2s.genomenexus.org/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getPdbAlignmentReisudeBySequenceUsingGET**](GetResidueMappingApi.md#getPdbAlignmentReisudeBySequenceUsingGET) | **GET** /api/alignments/residueMapping | Get PDB Residue Mapping by Protein Sequence and Residue position
[**getPdbAlignmentReisudeBySequenceUsingPOST**](GetResidueMappingApi.md#getPdbAlignmentReisudeBySequenceUsingPOST) | **POST** /api/alignments/residueMapping | Get PDB Residue Mapping by Protein Sequence and Residue position
[**postResidueMappingByPDBUsingGET**](GetResidueMappingApi.md#postResidueMappingByPDBUsingGET) | **GET** /api/alignments/{id_type}/{id}/pdb/{pdb_id}_{chain_id}/residueMapping | Post Residue Mapping by ProteinId, PDBId and Chain
[**postResidueMappingByPDBUsingPOST**](GetResidueMappingApi.md#postResidueMappingByPDBUsingPOST) | **POST** /api/alignments/{id_type}/{id}/pdb/{pdb_id}_{chain_id}/residueMapping | Post Residue Mapping by ProteinId, PDBId and Chain
[**postResidueMappingUsingGET**](GetResidueMappingApi.md#postResidueMappingUsingGET) | **GET** /api/alignments/{id_type}/{id}/residueMapping | POST PDB Residue Mapping by ProteinId
[**postResidueMappingUsingPOST**](GetResidueMappingApi.md#postResidueMappingUsingPOST) | **POST** /api/alignments/{id_type}/{id}/residueMapping | POST PDB Residue Mapping by ProteinId


<a name="getPdbAlignmentReisudeBySequenceUsingGET"></a>
# **getPdbAlignmentReisudeBySequenceUsingGET**
> List&lt;Alignment&gt; getPdbAlignmentReisudeBySequenceUsingGET(sequence, positionList, paramList)

Get PDB Residue Mapping by Protein Sequence and Residue position

### Example
```java
// Import classes:
//import org.genomenexus.g2s.client.ApiException;
//import org.genomenexus.g2s.client.api.GetResidueMappingApi;


GetResidueMappingApi apiInstance = new GetResidueMappingApi();
String sequence = "sequence_example"; // String | Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP
List<String> positionList = Arrays.asList("positionList_example"); // List<String> | Input Residue Positions e.g. 10,20
List<String> paramList = Arrays.asList("paramList_example"); // List<String> | Default Blast Parameters:  Evalue=1e-10,Wordsize=3,Gapopen=11,Gapextend=1,  Matrix=BLOSUM62,Comp_based_stats=2, Threshold=11,Windowsize=40
try {
    List<Alignment> result = apiInstance.getPdbAlignmentReisudeBySequenceUsingGET(sequence, positionList, paramList);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GetResidueMappingApi#getPdbAlignmentReisudeBySequenceUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **sequence** | **String**| Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP |
 **positionList** | [**List&lt;String&gt;**](String.md)| Input Residue Positions e.g. 10,20 | [optional]
 **paramList** | [**List&lt;String&gt;**](String.md)| Default Blast Parameters:  Evalue&#x3D;1e-10,Wordsize&#x3D;3,Gapopen&#x3D;11,Gapextend&#x3D;1,  Matrix&#x3D;BLOSUM62,Comp_based_stats&#x3D;2, Threshold&#x3D;11,Windowsize&#x3D;40 | [optional]

### Return type

[**List&lt;Alignment&gt;**](Alignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getPdbAlignmentReisudeBySequenceUsingPOST"></a>
# **getPdbAlignmentReisudeBySequenceUsingPOST**
> List&lt;Alignment&gt; getPdbAlignmentReisudeBySequenceUsingPOST(sequence, positionList, paramList)

Get PDB Residue Mapping by Protein Sequence and Residue position

### Example
```java
// Import classes:
//import org.genomenexus.g2s.client.ApiException;
//import org.genomenexus.g2s.client.api.GetResidueMappingApi;


GetResidueMappingApi apiInstance = new GetResidueMappingApi();
String sequence = "sequence_example"; // String | Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP
List<String> positionList = Arrays.asList("positionList_example"); // List<String> | Input Residue Positions e.g. 10,20
List<String> paramList = Arrays.asList("paramList_example"); // List<String> | Default Blast Parameters:  Evalue=1e-10,Wordsize=3,Gapopen=11,Gapextend=1,  Matrix=BLOSUM62,Comp_based_stats=2, Threshold=11,Windowsize=40
try {
    List<Alignment> result = apiInstance.getPdbAlignmentReisudeBySequenceUsingPOST(sequence, positionList, paramList);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GetResidueMappingApi#getPdbAlignmentReisudeBySequenceUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **sequence** | **String**| Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP |
 **positionList** | [**List&lt;String&gt;**](String.md)| Input Residue Positions e.g. 10,20 | [optional]
 **paramList** | [**List&lt;String&gt;**](String.md)| Default Blast Parameters:  Evalue&#x3D;1e-10,Wordsize&#x3D;3,Gapopen&#x3D;11,Gapextend&#x3D;1,  Matrix&#x3D;BLOSUM62,Comp_based_stats&#x3D;2, Threshold&#x3D;11,Windowsize&#x3D;40 | [optional]

### Return type

[**List&lt;Alignment&gt;**](Alignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postResidueMappingByPDBUsingGET"></a>
# **postResidueMappingByPDBUsingGET**
> List&lt;Alignment&gt; postResidueMappingByPDBUsingGET(idType, id, pdbId, chainId, positionList)

Post Residue Mapping by ProteinId, PDBId and Chain

### Example
```java
// Import classes:
//import org.genomenexus.g2s.client.ApiException;
//import org.genomenexus.g2s.client.api.GetResidueMappingApi;


GetResidueMappingApi apiInstance = new GetResidueMappingApi();
String idType = "idType_example"; // String | Input id_type: ensembl; uniprot; uniprot_isoform; hgvs-grch37; hgvs-grch38
String id = "id_example"; // String | Input id e.g.  ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9; hgvs-grch37:17:g.79478130C>G; hgvs-grch38:17:g.7676594T>G
String pdbId = "pdbId_example"; // String | Input PDB Id e.g. 2fej
String chainId = "chainId_example"; // String | Input Chain e.g. A
List<String> positionList = Arrays.asList("positionList_example"); // List<String> | Input Residue Positions e.g. 10,100 (Anynumber for hgvs); Return all residue mappings if none
try {
    List<Alignment> result = apiInstance.postResidueMappingByPDBUsingGET(idType, id, pdbId, chainId, positionList);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GetResidueMappingApi#postResidueMappingByPDBUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idType** | **String**| Input id_type: ensembl; uniprot; uniprot_isoform; hgvs-grch37; hgvs-grch38 |
 **id** | **String**| Input id e.g.  ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9; hgvs-grch37:17:g.79478130C&gt;G; hgvs-grch38:17:g.7676594T&gt;G |
 **pdbId** | **String**| Input PDB Id e.g. 2fej |
 **chainId** | **String**| Input Chain e.g. A |
 **positionList** | [**List&lt;String&gt;**](String.md)| Input Residue Positions e.g. 10,100 (Anynumber for hgvs); Return all residue mappings if none | [optional]

### Return type

[**List&lt;Alignment&gt;**](Alignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postResidueMappingByPDBUsingPOST"></a>
# **postResidueMappingByPDBUsingPOST**
> List&lt;Alignment&gt; postResidueMappingByPDBUsingPOST(idType, id, pdbId, chainId, positionList)

Post Residue Mapping by ProteinId, PDBId and Chain

### Example
```java
// Import classes:
//import org.genomenexus.g2s.client.ApiException;
//import org.genomenexus.g2s.client.api.GetResidueMappingApi;


GetResidueMappingApi apiInstance = new GetResidueMappingApi();
String idType = "idType_example"; // String | Input id_type: ensembl; uniprot; uniprot_isoform; hgvs-grch37; hgvs-grch38
String id = "id_example"; // String | Input id e.g.  ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9; hgvs-grch37:17:g.79478130C>G; hgvs-grch38:17:g.7676594T>G
String pdbId = "pdbId_example"; // String | Input PDB Id e.g. 2fej
String chainId = "chainId_example"; // String | Input Chain e.g. A
List<String> positionList = Arrays.asList("positionList_example"); // List<String> | Input Residue Positions e.g. 10,100 (Anynumber for hgvs); Return all residue mappings if none
try {
    List<Alignment> result = apiInstance.postResidueMappingByPDBUsingPOST(idType, id, pdbId, chainId, positionList);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GetResidueMappingApi#postResidueMappingByPDBUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idType** | **String**| Input id_type: ensembl; uniprot; uniprot_isoform; hgvs-grch37; hgvs-grch38 |
 **id** | **String**| Input id e.g.  ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9; hgvs-grch37:17:g.79478130C&gt;G; hgvs-grch38:17:g.7676594T&gt;G |
 **pdbId** | **String**| Input PDB Id e.g. 2fej |
 **chainId** | **String**| Input Chain e.g. A |
 **positionList** | [**List&lt;String&gt;**](String.md)| Input Residue Positions e.g. 10,100 (Anynumber for hgvs); Return all residue mappings if none | [optional]

### Return type

[**List&lt;Alignment&gt;**](Alignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postResidueMappingUsingGET"></a>
# **postResidueMappingUsingGET**
> List&lt;Alignment&gt; postResidueMappingUsingGET(idType, id, positionList)

POST PDB Residue Mapping by ProteinId

### Example
```java
// Import classes:
//import org.genomenexus.g2s.client.ApiException;
//import org.genomenexus.g2s.client.api.GetResidueMappingApi;


GetResidueMappingApi apiInstance = new GetResidueMappingApi();
String idType = "idType_example"; // String | Input id_type: ensembl; uniprot; uniprot_isoform; hgvs-grch37; hgvs-grch38
String id = "id_example"; // String | Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9; hgvs-grch37:17:g.79478130C>G; hgvs-grch38:17:g.7676594T>G
List<String> positionList = Arrays.asList("positionList_example"); // List<String> | Input Residue Positions e.g. 10,100; Anynumber for hgvs; Return all residue mappings if none
try {
    List<Alignment> result = apiInstance.postResidueMappingUsingGET(idType, id, positionList);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GetResidueMappingApi#postResidueMappingUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idType** | **String**| Input id_type: ensembl; uniprot; uniprot_isoform; hgvs-grch37; hgvs-grch38 |
 **id** | **String**| Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9; hgvs-grch37:17:g.79478130C&gt;G; hgvs-grch38:17:g.7676594T&gt;G |
 **positionList** | [**List&lt;String&gt;**](String.md)| Input Residue Positions e.g. 10,100; Anynumber for hgvs; Return all residue mappings if none | [optional]

### Return type

[**List&lt;Alignment&gt;**](Alignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postResidueMappingUsingPOST"></a>
# **postResidueMappingUsingPOST**
> List&lt;Alignment&gt; postResidueMappingUsingPOST(idType, id, positionList)

POST PDB Residue Mapping by ProteinId

### Example
```java
// Import classes:
//import org.genomenexus.g2s.client.ApiException;
//import org.genomenexus.g2s.client.api.GetResidueMappingApi;


GetResidueMappingApi apiInstance = new GetResidueMappingApi();
String idType = "idType_example"; // String | Input id_type: ensembl; uniprot; uniprot_isoform; hgvs-grch37; hgvs-grch38
String id = "id_example"; // String | Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9; hgvs-grch37:17:g.79478130C>G; hgvs-grch38:17:g.7676594T>G
List<String> positionList = Arrays.asList("positionList_example"); // List<String> | Input Residue Positions e.g. 10,100; Anynumber for hgvs; Return all residue mappings if none
try {
    List<Alignment> result = apiInstance.postResidueMappingUsingPOST(idType, id, positionList);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GetResidueMappingApi#postResidueMappingUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idType** | **String**| Input id_type: ensembl; uniprot; uniprot_isoform; hgvs-grch37; hgvs-grch38 |
 **id** | **String**| Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9; hgvs-grch37:17:g.79478130C&gt;G; hgvs-grch38:17:g.7676594T&gt;G |
 **positionList** | [**List&lt;String&gt;**](String.md)| Input Residue Positions e.g. 10,100; Anynumber for hgvs; Return all residue mappings if none | [optional]

### Return type

[**List&lt;Alignment&gt;**](Alignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

