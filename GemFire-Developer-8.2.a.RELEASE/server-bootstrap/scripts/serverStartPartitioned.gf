start locator --name=locator --port=41111 --properties-file=gemfire-server.properties --initial-heap=50m --max-heap=50m

start server --name=server1 --server-port=0 --classpath=${SYS_CLASSPATH} --cache-xml-file=xml/server-cache-partitioned.xml --properties-file=gemfire-server.properties --initial-heap=50m --max-heap=50m
start server --name=server2 --server-port=0 --classpath=${SYS_CLASSPATH} --cache-xml-file=xml/server-cache-partitioned.xml --properties-file=gemfire-server.properties --initial-heap=50m --max-heap=50m
start server --name=server3 --server-port=0 --classpath=${SYS_CLASSPATH} --cache-xml-file=xml/server-cache-partitioned.xml --properties-file=gemfire-server.properties --initial-heap=50m --max-heap=50m
