start locator --name=locator1 --port=41111 --properties-file=./gemfire.properties

start server --name=server1 --classpath=../target/classes --server-port=0 --properties-file=./gemfire.properties --J=-Dgemfire.start-dev-rest-api=true --J=-Dgemfire.http-service-port=7071

