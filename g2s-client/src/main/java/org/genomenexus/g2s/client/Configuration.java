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


package org.genomenexus.g2s.client;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-05-23T09:13:49.555-04:00")
public class Configuration {
    private static ApiClient defaultApiClient = new ApiClient();

    /**
     * Get the default API client, which would be used when creating API
     * instances without providing an API client.
     *
     * @return Default API client
     */
    public static ApiClient getDefaultApiClient() {
        return defaultApiClient;
    }

    /**
     * Set the default API client, which would be used when creating API
     * instances without providing an API client.
     *
     * @param apiClient API client
     */
    public static void setDefaultApiClient(ApiClient apiClient) {
        defaultApiClient = apiClient;
    }
}
