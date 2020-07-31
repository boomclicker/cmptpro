package org.apache.lucene.demo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SimpleMetrics {
  private  String index;
  private  String field ;
  private  String queryString ;
  private  String[] temp;
  private  IndexReader reader;
  private  int numTerm ;
  public SimpleMetrics(){
    this.index = "index";
    this.field = "content";
    this.queryString = null;
    this.reader = null;
    this.temp=null;
    this.numTerm=0;
  }
  public SimpleMetrics(String searchString,String indexString,String fieldString) throws IOException {
  	this.index = indexString;
  	this.field = fieldString;
  	this.queryString = searchString;
  	this.reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
  	this.temp=queryString.split(" ");
  	this.numTerm = temp.length;
  }    
public HashMap getDfAndTDF() throws IOException{
    HashMap<String,Long> map = new HashMap<String, Long>();
    int docFre =0;
    long totalDocFre=0;
    for(int i = 0;i<this.numTerm;i++){
    		docFre = docFre + this.reader.docFreq(new Term(this.field,this.temp[i]));
    		int df = this.reader.docFreq(new Term(this.field,this.temp[i]));
    		long helper = new Long(df);
    		map.put(this.temp[i],helper);
    		totalDocFre = totalDocFre + this.reader.totalTermFreq(new Term(this.field,this.temp[i]));
    		String h1 = "Doc";
    		long helper2 = this.reader.totalTermFreq(new Term(this.field,this.temp[i]));
    		String h2 = h1.concat(" ").concat(this.temp[i]);
    		map.put(h2,helper2);
    }
    long helper3 = new Long(docFre);
    map.put("total Docfre",helper3);
    map.put("all terms totalDocFre",totalDocFre);
    return map;	
  }   
}
