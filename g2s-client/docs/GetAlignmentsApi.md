# GetAlignmentsApi

All URIs are relative to *https://g2s.genomenexus.org/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAlignmentByPDBUsingGET**](GetAlignmentsApi.md#getAlignmentByPDBUsingGET) | **GET** /api/alignments/{id_type}/{id}/pdb/{pdb_id}_{chain_id} | Get PDB Alignments by ProteinId, PDBId and Chain
[**getAlignmentUsingGET**](GetAlignmentsApi.md#getAlignmentUsingGET) | **GET** /api/alignments/{id_type}/{id} | Get PDB Alignments by ProteinId
[**getPdbAlignmentBySequenceUsingGET**](GetAlignmentsApi.md#getPdbAlignmentBySequenceUsingGET) | **GET** /api/alignments | Get PDB Alignments by Protein Sequence
[**getPdbAlignmentBySequenceUsingPOST**](GetAlignmentsApi.md#getPdbAlignmentBySequenceUsingPOST) | **POST** /api/alignments | Get PDB Alignments by Protein Sequence


<a name="getAlignmentByPDBUsingGET"></a>
# **getAlignmentByPDBUsingGET**
> List&lt;Alignment&gt; getAlignmentByPDBUsingGET(idType, id, pdbId, chainId)

Get PDB Alignments by ProteinId, PDBId and Chain

### Example
```java
// Import classes:
//import org.genomenexus.g2s.client.ApiException;
//import org.genomenexus.g2s.client.api.GetAlignmentsApi;


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
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idType** | **String**| Input id_type: ensembl; uniprot; uniprot_isoform |
 **id** | **String**| Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9  |
 **pdbId** | **String**| Input PDB Id e.g. 2fej |
 **chainId** | **String**| Input Chain e.g. A |

### Return type

[**List&lt;Alignment&gt;**](Alignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAlignmentUsingGET"></a>
# **getAlignmentUsingGET**
> List&lt;Alignment&gt; getAlignmentUsingGET(idType, id)

Get PDB Alignments by ProteinId

### Example
```java
// Import classes:
//import org.genomenexus.g2s.client.ApiException;
//import org.genomenexus.g2s.client.api.GetAlignmentsApi;


GetAlignmentsApi apiInstance = new GetAlignmentsApi();
String idType = "idType_example"; // String | Input id_type: ensembl; uniprot; uniprot_isoform
String id = "id_example"; // String | Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9 
try {
    List<Alignment> result = apiInstance.getAlignmentUsingGET(idType, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GetAlignmentsApi#getAlignmentUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idType** | **String**| Input id_type: ensembl; uniprot; uniprot_isoform |
 **id** | **String**| Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9  |

### Return type

[**List&lt;Alignment&gt;**](Alignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getPdbAlignmentBySequenceUsingGET"></a>
# **getPdbAlignmentBySequenceUsingGET**
> List&lt;Alignment&gt; getPdbAlignmentBySequenceUsingGET(sequence, paramList)

Get PDB Alignments by Protein Sequence

### Example
```java
// Import classes:
//import org.genomenexus.g2s.client.ApiException;
//import org.genomenexus.g2s.client.api.GetAlignmentsApi;


GetAlignmentsApi apiInstance = new GetAlignmentsApi();
String sequence = "sequence_example"; // String | Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP
List<String> paramList = Arrays.asList("paramList_example"); // List<String> | Default Blast Parameters:  Evalue=1e-10,Wordsize=3,Gapopen=11,Gapextend=1,  Matrix=BLOSUM62,Comp_based_stats=2, Threshold=11,Windowsize=40
try {
    List<Alignment> result = apiInstance.getPdbAlignmentBySequenceUsingGET(sequence, paramList);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GetAlignmentsApi#getPdbAlignmentBySequenceUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **sequence** | **String**| Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP |
 **paramList** | [**List&lt;String&gt;**](String.md)| Default Blast Parameters:  Evalue&#x3D;1e-10,Wordsize&#x3D;3,Gapopen&#x3D;11,Gapextend&#x3D;1,  Matrix&#x3D;BLOSUM62,Comp_based_stats&#x3D;2, Threshold&#x3D;11,Windowsize&#x3D;40 | [optional]

### Return type

[**List&lt;Alignment&gt;**](Alignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getPdbAlignmentBySequenceUsingPOST"></a>
# **getPdbAlignmentBySequenceUsingPOST**
> List&lt;Alignment&gt; getPdbAlignmentBySequenceUsingPOST(sequence, paramList)

Get PDB Alignments by Protein Sequence

### Example
```java
// Import classes:
//import org.genomenexus.g2s.client.ApiException;
//import org.genomenexus.g2s.client.api.GetAlignmentsApi;


GetAlignmentsApi apiInstance = new GetAlignmentsApi();
String sequence = "sequence_example"; // String | Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP
List<String> paramList = Arrays.asList("paramList_example"); // List<String> | Default Blast Parameters:  Evalue=1e-10,Wordsize=3,Gapopen=11,Gapextend=1,  Matrix=BLOSUM62,Comp_based_stats=2, Threshold=11,Windowsize=40
try {
    List<Alignment> result = apiInstance.getPdbAlignmentBySequenceUsingPOST(sequence, paramList);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GetAlignmentsApi#getPdbAlignmentBySequenceUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **sequence** | **String**| Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP |
 **paramList** | [**List&lt;String&gt;**](String.md)| Default Blast Parameters:  Evalue&#x3D;1e-10,Wordsize&#x3D;3,Gapopen&#x3D;11,Gapextend&#x3D;1,  Matrix&#x3D;BLOSUM62,Comp_based_stats&#x3D;2, Threshold&#x3D;11,Windowsize&#x3D;40 | [optional]

### Return type

[**List&lt;Alignment&gt;**](Alignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

