package quickstart;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;

/**
 * This example measures the time it takes for an acknowledgment to arrive back
 * at the producer. For this example, the producer region has distributed-ack
 * scope while the consumer region has distributed-no-ack scope. The producer
 * takes a timestamp, puts an entry value, waits for the ACK, and compares the
 * current timestamp with the timestamp taken before the put. Please refer to
 * the quickstart guide for instructions on how to run this example.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since 4.1.1
 */
public class BenchmarkAckConsumer {

  public static void main(String[] args) throws Exception {

    // Create the cache which causes the cache-xml-file to be parsed
    Cache cache = new CacheFactory()
        .set("name", "BenchmarkAckConsumer")
        .set("cache-xml-file", "xml/BenchmarkAckConsumer.xml")
        .create();

    System.out.println();
    System.out.println("Please start the BenchmarkAckProducer, and press Enter when the benchmark finishes.");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    bufferedReader.readLine();

    // Close the cache and disconnect from GemFire distributed system
    System.out.println("Closing the cache and disconnecting.");
    cache.close();
  }
}
