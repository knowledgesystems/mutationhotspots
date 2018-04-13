rm -rf g2s-client
mkdir g2s-client
cd g2s-client
swagger-codegen generate -i https://g2s.genomenexus.org/v2/api-docs?group=api -l java --group-id org.genomenexus --artifact-id genomenexus-g2s-client --invoker-package org.genomenexus.g2s.client --model-package org.genomenexus.g2s.client.model --api-package org.genomenexus.g2s.client.api
