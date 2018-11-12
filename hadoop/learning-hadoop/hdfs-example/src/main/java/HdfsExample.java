import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsExample {
	
	public static final String fileName = "hadoop.txt";
	public static final String message = "My First Hadoop API call!\n";
	
	public static void main(String[] args) throws IOException {
		// Hadoop Default Configuration
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://hadoop-master:9000");
		
		//Hadoop FileSystem
		FileSystem fs = FileSystem.get(conf);

		// Pfad des Hadoop File Systems
		Path filenamePath = new Path(fileName);
		
		try {
			if(fs.exists(filenamePath)) {
				fs.delete(filenamePath);
			}

			// write Configuration to file 
			FSDataOutputStream out = fs.create(filenamePath);
			out.writeUTF(message);
			out.close();
			
			// Open Configuration to read file
			FSDataInputStream in = fs.open(filenamePath);
			String msgIn = in.readUTF();
			System.out.println(msgIn);
			in.close();
		}catch(IOException ioe){
			System.err.println("IOException operation: " + ioe.toString());
			System.exit(1);
		}
		

	}
}
