package org.kobic.omics.lucene.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

public class CreateLuceneIndex {

	public void createIndex(String data, String index) {
		String docsPath = this.getClass().getResource(data).getPath().replace("%20", " "); // 1. 색인 대상 문서가 있는 경로
		String indexPath = this.getClass().getResource(index).getPath().replace("%20", " "); // 2. 색인 파일이 만들어질 경로

		final File docDir = new File(docsPath);
		
		if (!docDir.exists() || !docDir.canRead()) {
			System.out.println("Document directory '" + docDir.getAbsolutePath()
					+ "' does not exist or is not readable, please check the path");
			System.exit(1);
		}
		
		Date start = new Date();
		
		try {
			System.out.println("Indexing to directory '" + indexPath + "'...");

			//3. 여기는 루씬에서 색인을 위한 IndexWriter를 생성하는 부분입니다.
			Directory dir = NIOFSDirectory.open(new File(indexPath));
			CJKAnalyzer analyzer = new CJKAnalyzer(Version.LUCENE_48); //문서 내용을 분석 할 때 사용 될 Analyzer
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, analyzer);
			
			boolean create = true; //4. 색인 파일을 새로 생성 할 것인지의 여부
		     
			if (create) {
				// Create a new index in the directory, removing any
				// previously indexed documents:
				iwc.setOpenMode(OpenMode.CREATE); //5. 새로운 인덱스 파일을 만든다. 기존 인덱스 파일은 삭제됩니다.
			} else {
				// Add new documents to an existing index:
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND); //6. 원래 있던 인덱스 파일에 문서를 추가합니다.
			}

			// Optional: for better indexing performance, if you
			// are indexing many documents, increase the RAM
			// buffer.  But if you do this, increase the max heap
			// size to the JVM (eg add -Xmx512m or -Xmx1g):
			//
			// iwc.setRAMBufferSizeMB(256.0); //7. IndexWriterConfig가 새로 생긴 클래스입니다. 이 부분은 색인 속도 증가를 위해 사용되는 옵션으로 보입니다.

			IndexWriter writer = new IndexWriter(dir, iwc); //8. 드디어 IndexWriter를 생성합니다.
			indexDocs(writer, docDir); //9. 색인 대상 문서들이 있는 디렉토리에서 문서를 읽어 색인을 합니다.

			// NOTE: if you want to maximize search performance,
			// you can optionally call optimize here.  This can be
			// a costly operation, so generally it's only worth
			// it when your index is relatively static (ie you're
			// done adding documents to it):
			//
			// writer.optimize();

			writer.close();

			Date end = new Date();
			System.out.println(end.getTime() - start.getTime() + " total milliseconds");

		    } catch (IOException e) {
		    	System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		    }
	}

	private void indexDocs(IndexWriter writer, File file) throws IOException {

		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();
				// an IO error could occur
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						if (files[i].endsWith("txt"))
							indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {

				Path path = Paths.get(file.getPath());

				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

				String s = null;

				while ((s = reader.readLine()) != null) {

					FieldType fieldType = new FieldType();
					fieldType.setStored(true);
					fieldType.setTokenized(true);
					fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
					fieldType.setStoreTermVectors(true);
					fieldType.setTokenized(true);
					fieldType.setStoreTermVectors(true);
					fieldType.setStoreTermVectorOffsets(true);

					Field contestField = new Field("CONTENT", s, fieldType);
					
					Document doc = new Document();
					doc.add(contestField);
					
					if (writer.getConfig().getOpenMode() == OpenMode.CREATE) { 
						writer.addDocument(doc);
					} else {
						writer.updateDocument(new Term("CONTENT", s), doc);
					}
				}
			}
		}
	}
}