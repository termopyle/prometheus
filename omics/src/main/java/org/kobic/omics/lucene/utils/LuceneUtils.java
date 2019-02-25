package org.kobic.omics.lucene.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuceneUtils {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuceneUtils.class);

	public void addDoc(IndexWriter indexWriter, String name)
			throws IOException {
		
		Document doc = new Document();
		doc.add(new TextField("name", name, Field.Store.YES));

		indexWriter.addDocument(doc);
	}
	
	public void addDoc(IndexWriter indexWriter, String name, String id)
			throws IOException {

		Document doc = new Document();
		doc.add(new TextField("id", id, Field.Store.YES));
		doc.add(new TextField("name", name, Field.Store.YES));

		indexWriter.addDocument(doc);
	}
	
	private Directory indexDir = null;
	
	@SuppressWarnings("deprecation")
	public void indexing(){
		
		ClassLoader classLoader = getClass().getClassLoader();
		
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("/omics/props/globals.properties"));
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		File file = Paths.get(props.getProperty("Globals.lucene.index.path"), "species").toFile();
		
		if(file.listFiles().length == 0){
			try {
				indexDir = NIOFSDirectory.open(Paths.get(props.getProperty("Globals.lucene.index.path"), "species").toFile());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			File taxonFile = new File(classLoader.getResource("species_data/ncbi_species.txt").getFile().replace("%20", " "));
			
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_48, new StandardAnalyzer(Version.LUCENE_48));
			
			try {
				
				IndexWriter indexWriter = 
						new IndexWriter(indexDir, config.setOpenMode(OpenMode.CREATE));
				
				String[] tmp = null;
				
				try {
					for(String line : FileUtils.readLines(taxonFile)){
						
						tmp = line.split("\t");
						
						addDoc(indexWriter, tmp[0], tmp[1]);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				indexWriter.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			LOGGER.info("인덱스 파일이 존재 합니다.");
		}
	}
	
	public List<String> searching(String key){

		Properties props = new Properties();
		
		try {
			props.load(getClass().getResourceAsStream("/omics/props/globals.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			indexDir = NIOFSDirectory.open(Paths.get(props.getProperty("Globals.lucene.index.path"), "species").toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>();
		
		try {
			String queryString = key;
			
			String[] fields = new String[]{"name", "id"};

			IndexReader reader = DirectoryReader.open(indexDir);
			IndexSearcher searcher = new IndexSearcher(reader);
			
			CJKAnalyzer analyzer = new CJKAnalyzer(Version.LUCENE_48);
			MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_48, fields, analyzer);
			Query query = parser.parse(queryString);
			
			System.out.println("Query String: [" + queryString + "]");
			System.out.println("Query: [" + query.toString() + "]");
			System.out.println("Searching for: [" + Arrays.toString(fields) + "]");

			TopDocs results = searcher.search(query, Integer.MAX_VALUE);
			ScoreDoc[] hits = results.scoreDocs;

			int numTotalHits = results.totalHits;
			System.out.println("Total matching documents: [" + numTotalHits + "]");
			
			for (int i = 0; i < numTotalHits; i++) {
				Document doc = searcher.doc(hits[i].doc);
				list.add(doc.get("name") + "\t" + doc.get("id"));
			}
			
		} catch (ParseException | IOException e) {

			e.printStackTrace();
		}
		
		return list;	
	}
}
