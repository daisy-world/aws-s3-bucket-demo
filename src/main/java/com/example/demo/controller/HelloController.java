package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@RestController
public class HelloController {
	
	@Autowired
	private AmazonS3 amazonS3;

	@Value("${aws.s3.bucket}")
	private String s3bucket;

	
	@GetMapping("/")
	
	public String showFolderSize() {
		
		String folderSize = getAWSS3FolderSize();
		return folderSize;
		
	}
	
	public String getAWSS3FolderSize() {
		
		
		long totalSize = 0;
		
		String actual_size;
		 
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(s3bucket)
               .withPrefix("lipsa").withDelimiter("");
		
		 ObjectListing objects = amazonS3.listObjects(listObjectsRequest);
		  
		 List<S3ObjectSummary> summaries = objects.getObjectSummaries();
		        for (S3ObjectSummary objectSummary : summaries) {
		        	
		        	System.out.println(objectSummary.getKey());
		        	totalSize+=objectSummary.getSize();
		            
		        }
		     
		        long  size_kb = totalSize /1024;
			      System.out.println("size_kb.... "  +  size_kb);
			      
			      long size_mb = size_kb / 1024;
			      System.out.println("size_mb.... "  +  size_mb);
			      

			      long size_gb = size_mb / 1024 ;
			      System.out.println("size_gb.... "  +  size_gb);
			      

					 if (size_gb > 0){
						 actual_size = size_gb + " GB";
				        }else if(size_mb > 0){
				        	actual_size = size_mb + " MB";

				        }else{
				        	actual_size = size_kb + " KB";

				        }	     
		        
		        
		    return actual_size ;	 
		
	}

}
