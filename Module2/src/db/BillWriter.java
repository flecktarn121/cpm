package db;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BillWriter {
	private String filePath;
	private String bill;
	public BillWriter(String filePath, String bill) {
		this.filePath = filePath;
		this.bill = bill;
		writeFile();
	}
	private void writeFile() {
		try(FileWriter writer = new FileWriter((filePath));BufferedWriter bw = new BufferedWriter(writer)){
			bw.write(bill);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
