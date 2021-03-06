/*
 * G2S web API
 * A Genome to Strucure (G2S) API Supports Automated Mapping and Annotating Genomic Variants in 3D Protein Structures. Supports Inputs from Human Genome Position, Uniprot and Human Ensembl Names.
 *
 * OpenAPI spec version: 1.0 (beta)
 * Contact: wangjue@missouri.edu
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.genomenexus.g2s.client.api;

import org.genomenexus.g2s.client.ApiCallback;
import org.genomenexus.g2s.client.ApiClient;
import org.genomenexus.g2s.client.ApiException;
import org.genomenexus.g2s.client.ApiResponse;
import org.genomenexus.g2s.client.Configuration;
import org.genomenexus.g2s.client.Pair;
import org.genomenexus.g2s.client.ProgressRequestBody;
import org.genomenexus.g2s.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import org.genomenexus.g2s.client.model.Alignment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAlignmentsApi {
    private ApiClient apiClient;

    public GetAlignmentsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public GetAlignmentsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /* Build call for getAlignmentByPDBUsingGET */
    private com.squareup.okhttp.Call getAlignmentByPDBUsingGETCall(String idType, String id, String pdbId, String chainId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/api/alignments/{id_type}/{id}/pdb/{pdb_id}_{chain_id}".replaceAll("\\{format\\}","json")
        .replaceAll("\\{" + "id_type" + "\\}", apiClient.escapeString(idType.toString()))
        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()))
        .replaceAll("\\{" + "pdb_id" + "\\}", apiClient.escapeString(pdbId.toString()))
        .replaceAll("\\{" + "chain_id" + "\\}", apiClient.escapeString(chainId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getAlignmentByPDBUsingGETValidateBeforeCall(String idType, String id, String pdbId, String chainId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'idType' is set
        if (idType == null) {
            throw new ApiException("Missing the required parameter 'idType' when calling getAlignmentByPDBUsingGET(Async)");
        }
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAlignmentByPDBUsingGET(Async)");
        }
        
        // verify the required parameter 'pdbId' is set
        if (pdbId == null) {
            throw new ApiException("Missing the required parameter 'pdbId' when calling getAlignmentByPDBUsingGET(Async)");
        }
        
        // verify the required parameter 'chainId' is set
        if (chainId == null) {
            throw new ApiException("Missing the required parameter 'chainId' when calling getAlignmentByPDBUsingGET(Async)");
        }
        
        
        com.squareup.okhttp.Call call = getAlignmentByPDBUsingGETCall(idType, id, pdbId, chainId, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Get PDB Alignments by ProteinId, PDBId and Chain
     * 
     * @param idType Input id_type: ensembl; uniprot; uniprot_isoform (required)
     * @param id Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9  (required)
     * @param pdbId Input PDB Id e.g. 2fej (required)
     * @param chainId Input Chain e.g. A (required)
     * @return List&lt;Alignment&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<Alignment> getAlignmentByPDBUsingGET(String idType, String id, String pdbId, String chainId) throws ApiException {
        ApiResponse<List<Alignment>> resp = getAlignmentByPDBUsingGETWithHttpInfo(idType, id, pdbId, chainId);
        return resp.getData();
    }

    /**
     * Get PDB Alignments by ProteinId, PDBId and Chain
     * 
     * @param idType Input id_type: ensembl; uniprot; uniprot_isoform (required)
     * @param id Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9  (required)
     * @param pdbId Input PDB Id e.g. 2fej (required)
     * @param chainId Input Chain e.g. A (required)
     * @return ApiResponse&lt;List&lt;Alignment&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<Alignment>> getAlignmentByPDBUsingGETWithHttpInfo(String idType, String id, String pdbId, String chainId) throws ApiException {
        com.squareup.okhttp.Call call = getAlignmentByPDBUsingGETValidateBeforeCall(idType, id, pdbId, chainId, null, null);
        Type localVarReturnType = new TypeToken<List<Alignment>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get PDB Alignments by ProteinId, PDBId and Chain (asynchronously)
     * 
     * @param idType Input id_type: ensembl; uniprot; uniprot_isoform (required)
     * @param id Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9  (required)
     * @param pdbId Input PDB Id e.g. 2fej (required)
     * @param chainId Input Chain e.g. A (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAlignmentByPDBUsingGETAsync(String idType, String id, String pdbId, String chainId, final ApiCallback<List<Alignment>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getAlignmentByPDBUsingGETValidateBeforeCall(idType, id, pdbId, chainId, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<Alignment>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /* Build call for getAlignmentUsingGET */
    private com.squareup.okhttp.Call getAlignmentUsingGETCall(String idType, String id, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/api/alignments/{id_type}/{id}".replaceAll("\\{format\\}","json")
        .replaceAll("\\{" + "id_type" + "\\}", apiClient.escapeString(idType.toString()))
        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getAlignmentUsingGETValidateBeforeCall(String idType, String id, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'idType' is set
        if (idType == null) {
            throw new ApiException("Missing the required parameter 'idType' when calling getAlignmentUsingGET(Async)");
        }
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAlignmentUsingGET(Async)");
        }
        
        
        com.squareup.okhttp.Call call = getAlignmentUsingGETCall(idType, id, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Get PDB Alignments by ProteinId
     * 
     * @param idType Input id_type: ensembl; uniprot; uniprot_isoform (required)
     * @param id Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9  (required)
     * @return List&lt;Alignment&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<Alignment> getAlignmentUsingGET(String idType, String id) throws ApiException {
        ApiResponse<List<Alignment>> resp = getAlignmentUsingGETWithHttpInfo(idType, id);
        return resp.getData();
    }

    /**
     * Get PDB Alignments by ProteinId
     * 
     * @param idType Input id_type: ensembl; uniprot; uniprot_isoform (required)
     * @param id Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9  (required)
     * @return ApiResponse&lt;List&lt;Alignment&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<Alignment>> getAlignmentUsingGETWithHttpInfo(String idType, String id) throws ApiException {
        com.squareup.okhttp.Call call = getAlignmentUsingGETValidateBeforeCall(idType, id, null, null);
        Type localVarReturnType = new TypeToken<List<Alignment>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get PDB Alignments by ProteinId (asynchronously)
     * 
     * @param idType Input id_type: ensembl; uniprot; uniprot_isoform (required)
     * @param id Input id e.g. ensembl:ENSP00000484409.1/ENSG00000141510.16/ENST00000504290.5; uniprot:P04637/P53_HUMAN; uniprot_isoform:P04637_9/P53_HUMAN_9  (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAlignmentUsingGETAsync(String idType, String id, final ApiCallback<List<Alignment>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getAlignmentUsingGETValidateBeforeCall(idType, id, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<Alignment>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /* Build call for getPdbAlignmentBySequenceUsingGET */
    private com.squareup.okhttp.Call getPdbAlignmentBySequenceUsingGETCall(String sequence, List<String> paramList, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/api/alignments".replaceAll("\\{format\\}","json");

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        if (sequence != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("", "sequence", sequence));
        if (paramList != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("multi", "paramList", paramList));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getPdbAlignmentBySequenceUsingGETValidateBeforeCall(String sequence, List<String> paramList, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'sequence' is set
        if (sequence == null) {
            throw new ApiException("Missing the required parameter 'sequence' when calling getPdbAlignmentBySequenceUsingGET(Async)");
        }
        
        
        com.squareup.okhttp.Call call = getPdbAlignmentBySequenceUsingGETCall(sequence, paramList, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Get PDB Alignments by Protein Sequence
     * 
     * @param sequence Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP (required)
     * @param paramList Default Blast Parameters:  Evalue&#x3D;1e-10,Wordsize&#x3D;3,Gapopen&#x3D;11,Gapextend&#x3D;1,  Matrix&#x3D;BLOSUM62,Comp_based_stats&#x3D;2, Threshold&#x3D;11,Windowsize&#x3D;40 (optional)
     * @return List&lt;Alignment&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<Alignment> getPdbAlignmentBySequenceUsingGET(String sequence, List<String> paramList) throws ApiException {
        ApiResponse<List<Alignment>> resp = getPdbAlignmentBySequenceUsingGETWithHttpInfo(sequence, paramList);
        return resp.getData();
    }

    /**
     * Get PDB Alignments by Protein Sequence
     * 
     * @param sequence Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP (required)
     * @param paramList Default Blast Parameters:  Evalue&#x3D;1e-10,Wordsize&#x3D;3,Gapopen&#x3D;11,Gapextend&#x3D;1,  Matrix&#x3D;BLOSUM62,Comp_based_stats&#x3D;2, Threshold&#x3D;11,Windowsize&#x3D;40 (optional)
     * @return ApiResponse&lt;List&lt;Alignment&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<Alignment>> getPdbAlignmentBySequenceUsingGETWithHttpInfo(String sequence, List<String> paramList) throws ApiException {
        com.squareup.okhttp.Call call = getPdbAlignmentBySequenceUsingGETValidateBeforeCall(sequence, paramList, null, null);
        Type localVarReturnType = new TypeToken<List<Alignment>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get PDB Alignments by Protein Sequence (asynchronously)
     * 
     * @param sequence Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP (required)
     * @param paramList Default Blast Parameters:  Evalue&#x3D;1e-10,Wordsize&#x3D;3,Gapopen&#x3D;11,Gapextend&#x3D;1,  Matrix&#x3D;BLOSUM62,Comp_based_stats&#x3D;2, Threshold&#x3D;11,Windowsize&#x3D;40 (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getPdbAlignmentBySequenceUsingGETAsync(String sequence, List<String> paramList, final ApiCallback<List<Alignment>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getPdbAlignmentBySequenceUsingGETValidateBeforeCall(sequence, paramList, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<Alignment>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /* Build call for getPdbAlignmentBySequenceUsingPOST */
    private com.squareup.okhttp.Call getPdbAlignmentBySequenceUsingPOSTCall(String sequence, List<String> paramList, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/api/alignments".replaceAll("\\{format\\}","json");

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        if (sequence != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("", "sequence", sequence));
        if (paramList != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("multi", "paramList", paramList));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getPdbAlignmentBySequenceUsingPOSTValidateBeforeCall(String sequence, List<String> paramList, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'sequence' is set
        if (sequence == null) {
            throw new ApiException("Missing the required parameter 'sequence' when calling getPdbAlignmentBySequenceUsingPOST(Async)");
        }
        
        
        com.squareup.okhttp.Call call = getPdbAlignmentBySequenceUsingPOSTCall(sequence, paramList, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Get PDB Alignments by Protein Sequence
     * 
     * @param sequence Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP (required)
     * @param paramList Default Blast Parameters:  Evalue&#x3D;1e-10,Wordsize&#x3D;3,Gapopen&#x3D;11,Gapextend&#x3D;1,  Matrix&#x3D;BLOSUM62,Comp_based_stats&#x3D;2, Threshold&#x3D;11,Windowsize&#x3D;40 (optional)
     * @return List&lt;Alignment&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<Alignment> getPdbAlignmentBySequenceUsingPOST(String sequence, List<String> paramList) throws ApiException {
        ApiResponse<List<Alignment>> resp = getPdbAlignmentBySequenceUsingPOSTWithHttpInfo(sequence, paramList);
        return resp.getData();
    }

    /**
     * Get PDB Alignments by Protein Sequence
     * 
     * @param sequence Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP (required)
     * @param paramList Default Blast Parameters:  Evalue&#x3D;1e-10,Wordsize&#x3D;3,Gapopen&#x3D;11,Gapextend&#x3D;1,  Matrix&#x3D;BLOSUM62,Comp_based_stats&#x3D;2, Threshold&#x3D;11,Windowsize&#x3D;40 (optional)
     * @return ApiResponse&lt;List&lt;Alignment&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<Alignment>> getPdbAlignmentBySequenceUsingPOSTWithHttpInfo(String sequence, List<String> paramList) throws ApiException {
        com.squareup.okhttp.Call call = getPdbAlignmentBySequenceUsingPOSTValidateBeforeCall(sequence, paramList, null, null);
        Type localVarReturnType = new TypeToken<List<Alignment>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get PDB Alignments by Protein Sequence (asynchronously)
     * 
     * @param sequence Input Protein Sequence: ETGQSVNDPGNMSFVKETVDKLLKGYDIRLRPDFGGPP (required)
     * @param paramList Default Blast Parameters:  Evalue&#x3D;1e-10,Wordsize&#x3D;3,Gapopen&#x3D;11,Gapextend&#x3D;1,  Matrix&#x3D;BLOSUM62,Comp_based_stats&#x3D;2, Threshold&#x3D;11,Windowsize&#x3D;40 (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getPdbAlignmentBySequenceUsingPOSTAsync(String sequence, List<String> paramList, final ApiCallback<List<Alignment>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getPdbAlignmentBySequenceUsingPOSTValidateBeforeCall(sequence, paramList, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<Alignment>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
