# Test createIndex API
curl -XPOST "http://localhost:8080/createIndex?indexName=myindex" \
-H 'Content-Type: application/json' \
-d '{"settings":{"number_of_shards":1,"number_of_replicas":0},"mappings":{"properties":{"field1":{"type":"text"},"field2":{"type":"keyword"}}}}'

# Test createDocument API
curl -XPOST "http://localhost:8080/createDocument?indexName=myindex" \
-H 'Content-Type: application/json' \
-d '{"field1": "value1", "field2": "value2"}'

# Test fetchDocument API
curl -XGET "http://localhost:8080/getDocument?indexName=myindex&id=1234"